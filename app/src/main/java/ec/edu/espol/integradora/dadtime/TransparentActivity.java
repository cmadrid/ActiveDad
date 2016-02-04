package ec.edu.espol.integradora.dadtime;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;

import java.io.File;
import java.util.List;

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

        File file = getImage();

        Drawable d=null;
        if(file != null && file.exists())
            d = Drawable.createFromPath(file.getAbsolutePath());


        AlertDialog.Builder builder = new AlertDialog.Builder(activity)
                .setTitle("DadTime - " + title)
                .setMessage(content)
                .setPositiveButton(R.string.accept, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // continue with delete
                        activity.finish();

                    }
                })
                .setNeutralButton(R.string.postpone, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // do nothing
                        reactive(title, content);
                        activity.finish();
                    }
                })
                .setCancelable(false);
                if(d!=null)
                    builder.setIcon(d);
                else
                    builder.setIcon(R.mipmap.ic_dadtime);
                builder.show();

    }

    private void reactive(String title,String content){
        int iUniqueId = (int) (System.currentTimeMillis() & 0xfffffff);


        Intent intent = new Intent(activity,TransparentActivity.class);

        intent.putExtra("title",title);
        intent.putExtra("content",content);
        //intent.setFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);

        PendingIntent pIntent = PendingIntent.getActivity(activity, iUniqueId, intent, 0);
        Notification noti = new Notification.Builder(activity)
                .setTicker("DadTime Notification")
                .setContentTitle("DadTime - " + title)
                .setContentText(content)
                .setSmallIcon(R.drawable.ic_notification)
                .setStyle(new Notification.BigTextStyle()
                        .bigText(content))
                .setContentIntent(pIntent).getNotification();
        noti.flags=Notification.FLAG_AUTO_CANCEL;
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(0, noti);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    public File getImage(){
        List<File> photos= Collage.getListFiles(new File(ProfileThirdActivity.PATH));
        if(photos==null || photos.size()==0) {
            photos = Collage.getListFiles(new File(Environment.getExternalStoragePublicDirectory(
                    Environment.DIRECTORY_PICTURES) + "/DadTime/Collage-DT"));
            if (photos == null || photos.size() == 0) {
                photos = Collage.getListFiles(new File(Environment.getExternalStoragePublicDirectory(
                        Environment.DIRECTORY_PICTURES) + "/DadTime"));
                if (photos == null || photos.size() == 0)
                    photos = null;
            }
        }
        if(photos==null)
            return null;
        else{
            photos = Collage.shuffleFiles(photos);
            return photos.get(0);
        }
    }

}
