package ec.edu.espol.integradora.dadtime;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

/**
 * Created by ces_m on 12/16/2015.
 */
public class TransparentActivity extends Activity {
    private Activity activity;
    private Context context;
    private Intent intent;
    private String title;
    private String content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.intent = getIntent();
        this.activity = this;
        this.context = getApplicationContext();

        title=intent.getStringExtra("title");
        content=intent.getStringExtra("content");


        if((getIntent().getFlags() & Intent.FLAG_ACTIVITY_LAUNCHED_FROM_HISTORY) !=0)
        {
            activity.finish();
            startActivity(new Intent(this.context,MainActivity.class));
        }
        setContentView(R.layout.transparent_activity);


        new AlertDialog.Builder(activity)
                .setTitle("DadTime - " + title)
                .setMessage(content)
                .setPositiveButton(R.string.accept, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // continue with delete
                        activity.finish();

                    }
                })
                .setNegativeButton(R.string.notAccept, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // do nothing

                        activity.finish();
                    }
                })
                .setNeutralButton(R.string.postpone, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // do nothing
                        reactive(title,content);
                        activity.finish();
                    }
                })
                .setCancelable(false)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();

    }

    private void reactive(String title,String content){
        int iUniqueId = (int) (System.currentTimeMillis() & 0xfffffff);


        Intent intent = new Intent(activity,TransparentActivity.class);

        intent.putExtra("title",title);
        intent.putExtra("content",content);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);

        PendingIntent pIntent = PendingIntent.getActivity(activity, iUniqueId, intent, 0);
        Notification noti = new Notification.Builder(activity)
                .setTicker("DadTime Notification")
                .setContentTitle("DadTime - "+title)
                .setContentText(content)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentIntent(pIntent).getNotification();
        noti.flags=Notification.FLAG_AUTO_CANCEL;
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(0, noti);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

}
