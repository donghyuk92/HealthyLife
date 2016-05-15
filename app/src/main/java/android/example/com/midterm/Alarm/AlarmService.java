package android.example.com.midterm.Alarm;


import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.example.com.midterm.MainActivity;
import android.example.com.midterm.R;
import android.example.com.midterm.ThreeFragment;
import android.os.IBinder;


public class AlarmService extends Service {
    private NotificationManager nm = null;

     @Override
     public IBinder onBind(Intent arg0)
     {
       // TODO Auto-generated method stub
        return null;
     }

    @Override  
    public void onCreate() 
    {
       // TODO Auto-generated method stub  
       super.onCreate();
    }

   @Override
   public int onStartCommand(Intent intent, int flags, int startId)
   {
	   if(intent != null) {
		   Intent dialogIntent = new Intent(getBaseContext(), AlarmingActivity.class);
		   dialogIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		   getApplication().startActivity(dialogIntent);
	   }

       nm = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);

       Intent notificationIntent = new Intent(this, MainActivity.class);
       PendingIntent pendingIntent = PendingIntent.getActivity(this,0,notificationIntent,0);
       Notification notification = new Notification.Builder(getBaseContext())
               .setSmallIcon(R.drawable.icon_alarm)
               .setTicker("Let's go!")
               .setContentTitle("Alarm")
               .setContentText("you should be walking to a gym!")
               .setContentIntent(pendingIntent)
               .setAutoCancel(true)
               .build();

       notification.flags = notification.flags | notification.FLAG_AUTO_CANCEL;
       nm.notify(1234, notification);

       startForeground(startId,notification);

       return START_STICKY;
    }

    @Override
    public void onDestroy() 
    {
        // TODO Auto-generated method stub
        super.onDestroy();
    }

}