package zcdog.com.daemon;

import android.app.Notification;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.annotation.Nullable;

import zcdog.com.deamservice.IProcessConnection;

/**
 * @author: zhangzhilong
 * @date: 2019/2/15
 * @des:
 */
public class DaemonService extends Service {

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // 提高进程优先级
        startForeground(1,new Notification());

        bindService(new Intent(this,WorkService.class),mServiceConnection, Context.BIND_IMPORTANT);

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
            startService(new Intent(DaemonService.this,WorkService.class));

            bindService(new Intent(DaemonService.this, WorkService.class),
                    mServiceConnection, Context.BIND_IMPORTANT);
        }
    };
}
