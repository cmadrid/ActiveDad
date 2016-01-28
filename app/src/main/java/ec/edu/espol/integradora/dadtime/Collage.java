package ec.edu.espol.integradora.dadtime;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Collage extends AppCompatActivity {
    //ImageView cv1;
    View screen;
    Bitmap bmScreen;


    public void initData(){
        screen = (findViewById(R.id.screen_collage));
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collage);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initData();

        List<File> files = getListFiles(new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES) + "/DadTime"));

        try {
            if(files.size()>5){
                ImageView iv;
                iv=((ImageView) findViewById(R.id.image1));
                iv.setImageBitmap(getSmallBitmap(files.get(0).getAbsolutePath()));
                iv.setScaleType(ImageView.ScaleType.CENTER_CROP);
                iv=((ImageView) findViewById(R.id.image2));
                iv.setImageBitmap(getSmallBitmap(files.get(1).getAbsolutePath()));
                iv.setScaleType(ImageView.ScaleType.CENTER_CROP);
                iv=((ImageView) findViewById(R.id.image3));
                iv.setImageBitmap(getSmallBitmap(files.get(2).getAbsolutePath()));
                iv.setScaleType(ImageView.ScaleType.CENTER_CROP);
                iv=((ImageView) findViewById(R.id.image4));
                iv.setImageBitmap(getSmallBitmap(files.get(3).getAbsolutePath()));
                iv.setScaleType(ImageView.ScaleType.CENTER_CROP);
                iv=((ImageView) findViewById(R.id.image5));
                iv.setImageBitmap(getSmallBitmap(files.get(4).getAbsolutePath()));
                iv.setScaleType(ImageView.ScaleType.CENTER_CROP);
                iv=((ImageView) findViewById(R.id.image6));
                iv.setImageBitmap(getSmallBitmap(files.get(5).getAbsolutePath()));
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
            if (file.isDirectory()) {
                //inFiles.addAll(getListFiles(file));//ignoro subcarpetas
            } else {
                if(file.getName().endsWith(".jpg")){
                    inFiles.add(file);
                }
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
            saveImage(bmScreen);
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




    //http://stackoverflow.com/questions/20599834/android-scale-and-compress-a-bitmap
    /**
     * Calcuate how much to compress the image
     * @param options informacion de la imagen original
     * @param reqWidth el nuevo ancho a asignar a la imagen
     * @return el resultado
     */
    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth ) {
// Raw height and width of image


        final int height = options.outHeight;
        final int width = options.outWidth;
        int reqHeight = (int) (height * reqWidth / (double) width); // casts to avoid truncating


        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) > reqHeight
                    && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }


    /**
     * resize image to 480x800
     * @param filePath el url de la imagen que queremos reducir de tamaño
     * @return un bitmap con dimesiones reducidas a la original
     */
    public static Bitmap getSmallBitmap(String filePath) {

        File file = new File(filePath);
        long originalSize = file.length();

        //MyLogger.Verbose("Original image size is: " + originalSize + " bytes.");

        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, options);

        // Calculate inSampleSize based on a preset ratio
        options.inSampleSize = calculateInSampleSize(options, 1080);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;

        return BitmapFactory.decodeFile(filePath, options);
    }




    protected void saveImage(Bitmap bmScreen2) {
        // TODO Auto-generated method stub


        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "/DadTime/Collage-DT/JPEG_" + timeStamp + ".jpg";
        new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES)+"/DadTime/Collage-DT").mkdirs();

        // String fname = "Upload.png";
        File saved_image_file = new File(
                Environment.getExternalStoragePublicDirectory(
                        Environment.DIRECTORY_PICTURES)
                        + imageFileName);
        try {
            FileOutputStream out = new FileOutputStream(saved_image_file);
            bmScreen2.compress(Bitmap.CompressFormat.JPEG, 100, out);
            out.flush();
            out.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        addImageToGallery(saved_image_file.getAbsolutePath(),getApplicationContext());

    }

    //necesario para actualizar la galeria
    public static void addImageToGallery(final String filePath, final Context context) {

        ContentValues values = new ContentValues();

        values.put(MediaStore.Images.Media.DATE_TAKEN, System.currentTimeMillis());
        values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
        values.put(MediaStore.MediaColumns.DATA, filePath);

        context.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

    }
}
