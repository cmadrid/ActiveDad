package ec.edu.espol.integradora.dadtime;

import android.content.ContentValues;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by ces_m
 * on 1/29/2016.
 */
public class ImageHandler {



    public static String saveImage(Bitmap bmScreen2,String relativePath,String prefix ,Context ctx) {
        if(relativePath==null)
            relativePath="";
        if(prefix==null)
            prefix="";

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(new Date());
        String imageFileName = "/DadTime/"+relativePath+"/"+prefix+ timeStamp + ".jpg";
        new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES)+"/DadTime/"+relativePath+"/").mkdirs();

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

        addImageToGallery(saved_image_file.getAbsolutePath(), ctx);
        return saved_image_file.getAbsolutePath();
    }


    public static void addImageToGallery(final String filePath, final Context context) {

        ContentValues values = new ContentValues();

        values.put(MediaStore.Images.Media.DATE_TAKEN, System.currentTimeMillis());
        values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
        values.put(MediaStore.MediaColumns.DATA, filePath);

        context.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
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
     * @param weigth ancho de la imagen
     * @return un bitmap con dimesiones reducidas a la original
     */
    public static Bitmap getSmallBitmap(String filePath,int weigth) {

        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, options);

        // Calculate inSampleSize based on a preset ratio
        options.inSampleSize = calculateInSampleSize(options, weigth);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;

        return BitmapFactory.decodeFile(filePath, options);
    }


    public static void copyFile(String inputPath, String inputFile, String outputPath,String outputFile) {
        InputStream in = null;
        OutputStream out = null;
        try {
            //create output directory if it doesn't exist
            File dir = new File (outputPath);
            if (!dir.exists())
            {
                dir.mkdirs();
            }
            in = new FileInputStream(inputPath + inputFile);
            out = new FileOutputStream(outputPath + outputFile);
            byte[] buffer = new byte[1024];
            int read;
            while ((read = in.read(buffer)) != -1) {
                out.write(buffer, 0, read);
            }
            in.close();
            in = null;
            // write the output file (You have now copied the file)
            out.flush();
            out.close();
            out = null;
        }  catch (FileNotFoundException fnfe1) {
            Log.e("tag", fnfe1.getMessage());
        }
        catch (Exception e) {
            Log.e("tag", e.getMessage());
        }
    }
    public static void deleteFile(String inputPath, String inputFile) {
        try {
            // delete the original file
            new File(inputPath + inputFile).delete();
        } catch (Exception e) {
            Log.e("tag", e.getMessage());
        }
    }
}
