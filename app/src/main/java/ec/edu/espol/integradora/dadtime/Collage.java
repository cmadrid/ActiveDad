package ec.edu.espol.integradora.dadtime;

import android.content.Intent;
import android.graphics.Bitmap;
import android.media.ExifInterface;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Collage extends AppCompatActivity {

    View screen;
    Bitmap bmScreen;


    public void initData(){
        screen = (findViewById(R.id.screen_collage));
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collage);
        if(null != getSupportActionBar()) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        initData();

        List<File> files = getListFiles(new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES) + "/DadTime"));

        for(File file:files) {
            try {
                ExifInterface exif = new ExifInterface(file.getAbsolutePath());
                System.out.println(exif.getAttribute(ExifInterface.TAG_MAKE));
                System.out.println(exif.getAttribute(ExifInterface.TAG_MODEL));

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            if(files.size()>5){
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

        }catch (Exception e){
            Toast.makeText(getApplicationContext(), "Error cargando las imagenes.", Toast.LENGTH_SHORT)
                    .show();
            e.printStackTrace();
            finish();
        }

        System.out.println(files);
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
            ImageHandler.saveImage(bmScreen,"Collage-DT","JPEG_",getApplicationContext());
            screen.setDrawingCacheEnabled(false);
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






}
