package com.i2nexted.updater;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;

import com.i2nexted.updater.interfaces.IDownloadAgent;
import com.i2nexted.updater.util.ErrorMessageUtil;
import com.i2nexted.updater.util.UpdateUtil;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Administrator on 2017/5/27.
 */

public class UpdateDownloader extends AsyncTask<Void, Integer, Long> {
    // 下载的超时时间
    private static final int TIME_OUT = 30000;
    // 设置缓存的大小
    private static final int BUFFER_SIZE = 1024*100;
    // 下载过程的标记
    private static final int EVENT_START = 1;
    private static final int EVENT_PROGRESS = 2;
    private static final int EVENT_COMPLETE = 3;

    private Context context;
    private IDownloadAgent agent;
    private File tempFile;
    private String mUrl;

    // 文件大小的相关属性
    private long mBytesLoaded = 0;
    private long mBytesToatle = 0;
    private long mBytesTemp = 0;
    private long mTimeBegin = 0;
    private long mTimeUsed = 1;
    private long mTimeLast = 2;
    private long mSpeed = 0;

    private HttpURLConnection httpURLConnection;

    public UpdateDownloader(Context context, IDownloadAgent agent, File tempFile, String mUrl) {
        super();
        this.context = context;
        this.agent = agent;
        this.tempFile = tempFile;
        this.mUrl = mUrl;
        if (tempFile.exists()){
            mBytesTemp = tempFile.length();
        }
    }

    /**
     * 获取已下载的字节数
     * */
    public long getBytesLoaded(){
        return mBytesLoaded + mBytesTemp;
    }

    /**
     *
     * */

    @Override
    protected Long doInBackground(Void... params) {
        mTimeBegin = System.currentTimeMillis();
        try {
            long result = download();
            if (isCancelled()){
                agent.setError(new UpdateError(ErrorMessageUtil.DOWNLOAD_CANCELLED));
            }else if (result == -1){
                agent.setError(new UpdateError(ErrorMessageUtil.DOWNLOAD_UNKNOWN));
            }else if (!UpdateUtil.verify(tempFile, tempFile.getName())){
                agent.setError(new UpdateError(ErrorMessageUtil.DOWNLOAD_VERIFY));
            }
        }catch (UpdateError e){
            e.printStackTrace();
            agent.setError(e);
        }catch (FileNotFoundException e){
            e.printStackTrace();
            agent.setError(new UpdateError(ErrorMessageUtil.DOWNLOAD_DISK_IO));
        }catch (IOException e) {
            e.printStackTrace();
            agent.setError(new UpdateError(ErrorMessageUtil.DOWNLOAD_NETWORK_IO));
        }finally {
            if (httpURLConnection != null){
                httpURLConnection.disconnect();
            }
        }

        return null;
    }


    @Override
    protected void onProgressUpdate(Integer... values)
    {
        switch (values[0]){
            case EVENT_START:
                agent.start();
                break;
            case EVENT_PROGRESS:
                long now = System.currentTimeMillis();
                if (now - mTimeLast < 900){
                    break;
                }
                mTimeLast = now;
                mTimeUsed = now - mTimeBegin;
                mSpeed = mBytesLoaded*1000 / mTimeUsed;
                agent.onProgress((int)(getBytesLoaded() * 100/ mBytesToatle));
                break;
        }
    }

    @Override
    protected void onPostExecute(Long aLong) {
        agent.onFinish();
    }

    /**
     * 检查网络连接情况
     * */
    void checkNetwork() throws UpdateError{
        if (!UpdateUtil.checkNetwork(context)){
            throw new UpdateError(ErrorMessageUtil.DOWNLOAD_NETWORK_BLOCKED);
        }
    }

    /**
     * 检查http状态
     * */
    void checkStatus() throws UpdateError ,IOException{
        int statusCode = httpURLConnection.getResponseCode();
        if (statusCode != 200 && statusCode != 206){
            throw new UpdateError(ErrorMessageUtil.CHECK_HTTP_STATUS);
        }
    }

    /**
     * 检查设备存储空间
     * */
    void checkSpace(long loaded, long total) throws UpdateError{
        long  storage = getAvailableStorage();
        if (total - loaded > storage){
            throw new UpdateError(ErrorMessageUtil.DOWNLOAD_DISK_NO_SPACE);
        }
    }

    /**
     * 创建http连接
     * */
    private HttpURLConnection create(URL url) throws IOException {
        HttpURLConnection connection = (HttpURLConnection)url.openConnection();
        connection.setRequestProperty("Accept", "application/*");
        connection.setConnectTimeout(TIME_OUT);
        return connection;
    }

    private long download() throws IOException, UpdateError{
        checkNetwork();
        httpURLConnection = create(new URL(mUrl));
        httpURLConnection.connect();
        checkStatus();
        mBytesToatle = httpURLConnection.getContentLength();
        checkSpace(mBytesLoaded, mBytesToatle);
        if (mBytesTemp == mBytesToatle){
            publishProgress(EVENT_START);
            return 0;
        }
        if (mBytesTemp > 0){
            httpURLConnection.disconnect();
            httpURLConnection = create(new URL(mUrl));
            httpURLConnection.addRequestProperty("Range", "bytes=" + mBytesTemp + "-");
            httpURLConnection.connect();
            checkStatus();
        }
        publishProgress(EVENT_START);

        int bytesCopied = copy(httpURLConnection.getInputStream(), new LoadingRandomAccessFile(tempFile));
        if (isCancelled()) {
        } else if ((mBytesTemp + bytesCopied) != mBytesToatle && mBytesToatle != -1) {
            UpdateUtil.log("download incomplete(" + mBytesTemp + " + " + bytesCopied + " != " + mBytesTemp + ")");
            throw new UpdateError(ErrorMessageUtil.DOWNLOAD_INCOMPLETE);
        }

        return bytesCopied;
    }

    private int copy(InputStream in, LoadingRandomAccessFile loadingRandomAccessFile) throws IOException, UpdateError {
        byte[] buffer = new byte[BUFFER_SIZE];
        BufferedInputStream bufferedInputStream = new BufferedInputStream(in, BUFFER_SIZE);
        try {
            loadingRandomAccessFile.setLength(loadingRandomAccessFile.length());
            int bytes = 0;
            long previouslyBlockTime = -1;
            while (!isCancelled()){
                int n = bufferedInputStream.read(buffer, 0 ,BUFFER_SIZE);
                if (n == -1) break;
                loadingRandomAccessFile.write(buffer, 0, n);
                bytes += n;
                checkNetwork();

                if (mSpeed != 0){
                    previouslyBlockTime = -1;
                }else if (previouslyBlockTime == -1){
                    previouslyBlockTime = System.currentTimeMillis();
                }else if ((System.currentTimeMillis() - previouslyBlockTime)>TIME_OUT){
                    throw new UpdateError(ErrorMessageUtil.DOWNLOAD_NETWORK_TIMEOUT);
                }
            }
            return bytes;
        }finally {
            loadingRandomAccessFile.close();
            bufferedInputStream.close();
            in.close();
        }
    }

    private final class LoadingRandomAccessFile extends RandomAccessFile {

        public LoadingRandomAccessFile(File file) throws FileNotFoundException {
            super(file, "rw");
        }

        @Override
        public void write(byte[] buffer, int offset, int count) throws IOException {

            super.write(buffer, offset, count);
            mBytesLoaded += count;
            publishProgress(EVENT_PROGRESS);
        }
    }
    public static long getAvailableStorage() {
        try {
            StatFs stat = new StatFs(Environment.getExternalStorageDirectory().toString());
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
                return stat.getAvailableBlocksLong() * stat.getBlockSizeLong();
            } else {
                return (long) stat.getAvailableBlocks() * (long) stat.getBlockSize();
            }
        } catch (RuntimeException ex) {
            return 0;
        }
    }
}
