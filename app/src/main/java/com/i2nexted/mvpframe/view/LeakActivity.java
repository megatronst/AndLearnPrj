package com.i2nexted.mvpframe.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.i2nexted.mvpframe.R;

import java.lang.ref.WeakReference;

public class LeakActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leak);

       startAsyncTask();

    }
    void startAsyncTask() {
        // This async task is an anonymous class and therefore has a hidden reference to the outer
        // class MainActivity. If the activity gets destroyed before the task finishes (e.g. rotation),
        // the activity instance will leak.
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    Thread.sleep(3000000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//        }).start();
        new MyTherad(this).start();
    }

    private static class MyTherad extends Thread{
        private WeakReference<LeakActivity> weakReference;
        public MyTherad(LeakActivity activity) {
            weakReference = new WeakReference<LeakActivity>(activity);
        }

        @Override
        public void run() {
            super.run();
            try {
                Thread.sleep(3000000);
                if (weakReference.get() != null){
                    weakReference.get().doSomething();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }


    private void doSomething(){}
}
