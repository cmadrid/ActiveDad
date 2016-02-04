package ec.edu.espol.integradora.dadtime;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.ExifInterface;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Pair;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import layout.AddImageDialog;
import layout.FragmentMemories;

public class Collage extends AppCompatActivity {

    View screen;
    Bitmap bmScreen;



    public void initData(){
        screen = findViewById(R.id.screen_collage);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collage);
        if(null != getSupportActionBar()) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        initData();
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, -7);

        //first Fav, second NoFav
        Pair<List<File>,List<File>> filesPair = getListFilesTagDate(new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES) + "/DadTime"),null,null);
        List<File> favs=filesPair.first;
        List<File> noFavs=filesPair.second;

        int imgNum=6;
        shuffleFiles(favs);
        shuffleFiles(noFavs);

        List<File> files=new ArrayList<>(favs);
        files.addAll(noFavs);


        try {
            if(files.size()>=imgNum){
                ImageView iv;
                iv=((ImageView) findViewById(R.id.image1));
                iv.setImageBitmap(ImageHandler.getSmallBitmap(files.get(0).getAbsolutePath(),1080));
                iv.setScaleType(ImageView.ScaleType.CENTER_CROP);
                iv=((ImageView) findViewById(R.id.image2));
                iv.setImageBitmap(ImageHandler.getSmallBitmap(files.get(1).getAbsolutePath(),1080));
                iv.setScaleType(ImageView.ScaleType.CENTER_CROP);
                iv=((ImageView) findViewById(R.id.image3));
                iv.setImageBitmap(ImageHandler.getSmallBitmap(files.get(2).getAbsolutePath(),1080));
                iv.setScaleType(ImageView.ScaleType.CENTER_CROP);
                iv=((ImageView) findViewById(R.id.image4));
                iv.setImageBitmap(ImageHandler.getSmallBitmap(files.get(3).getAbsolutePath(),1080));
                iv.setScaleType(ImageView.ScaleType.CENTER_CROP);
                iv=((ImageView) findViewById(R.id.image5));
                iv.setImageBitmap(ImageHandler.getSmallBitmap(files.get(4).getAbsolutePath(),1080));
                iv.setScaleType(ImageView.ScaleType.CENTER_CROP);
                iv=((ImageView) findViewById(R.id.image6));
                iv.setImageBitmap(ImageHandler.getSmallBitmap(files.get(5).getAbsolutePath(),1080));
                iv.setScaleType(ImageView.ScaleType.CENTER_CROP);

                //setEvents(cv1, files.get(0).getAbsolutePath());
                //cv1.setImageBitmap(getSmallBitmap(files.get(0).getAbsolutePath()));

            }
            else{
                Toast.makeText(getApplicationContext(), "No cuenta con un minimo de fotos para un Collage.", Toast.LENGTH_SHORT)
                        .show();
            }

        }catch (Exception e){
            Toast.makeText(getApplicationContext(), "Error cargando las imagenes.", Toast.LENGTH_SHORT)
                    .show();
            e.printStackTrace();
            finish();
        }
    }

    public static List<File> getListFiles(File parentDir) {
        ArrayList<File> inFiles = new ArrayList<>();
        if(!parentDir.exists())
            return inFiles;
        File[] files = parentDir.listFiles();
        for (File file : files) {
            if (!file.isDirectory() && file.getName().endsWith(".jpg")) {
                inFiles.add(file);
            }
        }
        return inFiles;
    }


    static List<File> shuffleFiles(List<File> ar)
    {
        Random rnd;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            rnd = ThreadLocalRandom.current();
        }
        else
            rnd = new Random();
        for (int i = ar.size() - 1; i > 0; i--)
        {
            int index = rnd.nextInt(i + 1);
            File a = ar.get(index);
            ar.set(index, ar.get(i));
            ar.set(i, a);
        }
        return ar;
    }


    public static Pair<List<File>,List<File>> getListFilesTagDate(File parentDir,String tag,Date date) {
        ArrayList<File> filesFav = new ArrayList<>();
        ArrayList<File> filesNoFav = new ArrayList<>();
        if(!parentDir.exists())
            return new Pair(filesFav,filesNoFav);
        File[] files = parentDir.listFiles();
        for (File file : files) {
            if (!file.isDirectory() && file.getName().endsWith(".jpg") && file.lastModified()>(date==null?0:date.getTime())) {
                try {
                    ExifInterface exif = new ExifInterface(file.getAbsolutePath());
                    if(tag!=null && exif.getAttribute(ExifInterface.TAG_MODEL)!=null && !exif.getAttribute(ExifInterface.TAG_MODEL).equals(tag))
                        continue;

                    if (exif.getAttribute(ExifInterface.TAG_MAKE)!=null && exif.getAttribute(ExifInterface.TAG_MAKE).equals(AddImageDialog.FAV))
                        filesFav.add(file);
                    else if (exif.getAttribute(ExifInterface.TAG_MAKE)!=null && exif.getAttribute(ExifInterface.TAG_MAKE).equals(AddImageDialog.NO_FAV))
                        filesNoFav.add(file);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return new Pair(filesFav,filesNoFav);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_collage, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.refresh) {
            startActivity(new Intent(getApplicationContext(), Collage.class));
            finish();
            return true;
        }
        else if(id == R.id.save_collage){
            screen.setDrawingCacheEnabled(true);
            bmScreen = screen.getDrawingCache();
            String path = ImageHandler.saveImage(bmScreen,"Collage-DT","JPEG_",getApplicationContext());
            screen.setDrawingCacheEnabled(false);
            if(FragmentMemories.fragmentMemories!=null) {
                Calendar cal = Calendar.getInstance();
                cal.setTimeInMillis(new File(path).lastModified());
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
                dateFormat.setTimeZone(cal.getTimeZone());
                FragmentMemories.fragmentMemories.getMemories().add(0,new Memory(dateFormat.format(cal.getTime()), path));
                FragmentMemories.fragmentMemories.setAdapterGvMemories(null);
            }
            Toast.makeText(getApplicationContext(), "Collage Guardado Correctamente", Toast.LENGTH_SHORT)
                    .show();
            finish();
        }else if(id==android.R.id.home) {
            this.finish();
            //onBackPressed();
            return true;
        }
        return false;
    }



    public static void createNotification(Context ctx){

        Intent intent = new Intent(ctx,Collage.class);

        int iUniqueId = (int) (System.currentTimeMillis() & 0xfffffff);

        //intent.setFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);

        PendingIntent pIntent = PendingIntent.getActivity(ctx, iUniqueId, intent, 0);
        Notification noti = new Notification.Builder(ctx)
                .setTicker("DadTime Notification")
                .setContentTitle("DadTime - Nuevo Collage disponible")
                .setContentText("Dispone de un nuevo collage de sus actividades realizadas.")
                .setSmallIcon(R.drawable.ic_notification)
                .setStyle(new Notification.BigTextStyle()
                        .bigText("Dispone de un nuevo collage de sus actividades realizadas."))
                .setContentIntent(pIntent).getNotification();
        noti.flags = Notification.FLAG_AUTO_CANCEL;
        NotificationManager notificationManager = (NotificationManager) ctx.getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(0, noti);
    }



}
