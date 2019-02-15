package zcdog.com.daemon;

import android.app.Notification;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import zcdog.com.deamservice.IProcessConnection;

/**
 * @author: zhangzhilong
 * @date: 2019/2/15
 * @des:
 */
public class WorkService extends Service {


    @Override
    public void onCreate() {
        super.onCreate();

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true){
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Log.i("WorkService","Test 哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈哈");
                }
            }
        }).start();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        startForeground(1,new Notification());

        bindService(new Intent(this,DaemonService.class),mServiceConnection, Context.BIND_IMPORTANT);

        return START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new IProcessConnection.Stub(){};
    }

    private ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            startService(new Intent(WorkService.this,DaemonService.class));

            bindService(new Intent(WorkService.this, DaemonService.class),
                    mServiceConnection, Context.BIND_IMPORTANT);
        }
    };
}
