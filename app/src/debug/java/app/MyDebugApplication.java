package app;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.StrictMode;
import android.widget.Toast;

import com.facebook.stetho.Stetho;
import com.i2nexted.mvpframe.app.MyApplication;
import com.squareup.leakcanary.LeakCanary;

/**
 * Created by Administrator on 2017/5/17.
 */

public class MyDebugApplication extends MyApplication {
    @Override
    public void onCreate() {
        super.onCreate();
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        initStetho();
        enableStrictMode();
        initLeakCanary();
    }

    /**
     * 初始化stetho chrome调试工具
     */
    private void initStetho() {
            Toast.makeText(this, "debug_mode", Toast.LENGTH_SHORT).show();
            Stetho.initialize(Stetho.newInitializerBuilder(this)  //Chrome调试
                    .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
                    .enableWebKitInspector(Stetho.defaultInspectorModulesProvider(this))
                    .build());
    }

    /**
     * 在开发的过程中开启严格模式，避免线程方面的违规操作
     * */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private void enableStrictMode(){
            StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                    .detectCustomSlowCalls() //API等级11，使用StrictMode.noteSlowCode
                    .detectDiskReads()
                    .detectDiskWrites()
                    .detectNetwork()   // or .detectAll() for all detectable problems
                    .penaltyDialog() //弹出违规提示对话框
                    .penaltyLog() //在Logcat 中打印违规异常信息
                    .penaltyFlashScreen() //API等级11
                    .build());
            StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                    .detectLeakedSqlLiteObjects()
                    .detectLeakedClosableObjects() //API等级11
                    .penaltyLog()
                    .penaltyDeath()
                    .build());
    }

    /**
     * 初始化leakcanary
     * */
    private void initLeakCanary(){
        LeakCanary.install(this);
    }
}
