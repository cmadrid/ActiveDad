package ec.edu.espol.integradora.dadtime;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.Set;

/**
 * Created by ces_m
 * on 1/5/2016.
 */
public class CameraActivity extends Activity {
    private String CurrentPhotoPath;
    private Uri CapturedImageURI;

    private SharedPreferences preferenceSettings;
    private int REQUEST_TAKE_PHOTO = 102;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        String action = intent.getAction();

        if (Intent.ACTION_SEND.equals(action)) {
            if (extras.containsKey(Intent.EXTRA_STREAM)) {
                Uri uri = (Uri) getIntent().getExtras().get(Intent.EXTRA_STREAM);
                Bitmap bitmap=null;
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
                    System.out.println(uri);
                    getImageShared(bitmap);


                    System.out.println(uri.getPath());
                } catch (IOException e) {
                    e.printStackTrace();
                }

                return;
            }
        }

        dispatchTakePictureIntent();

    }

    public void getImageShared(final Bitmap bitmap){

        final Context ctx = this;
        LayoutInflater inflater = this.getLayoutInflater();
        View view = inflater.inflate(R.layout.post_camera, null);
        ImageView imageCam = (ImageView)view.findViewById(R.id.imageCam);
        imageCam.setImageBitmap(bitmap);
        imageCam.setScaleType(ImageView.ScaleType.CENTER_CROP);
        Spinner s = (Spinner) view.findViewById(R.id.listActivities);
        String[] array =  getActivities();
        ArrayAdapter<String> adapter;
        if(array!=null && array.length>0 )
            adapter = new ArrayAdapter(this,android.R.layout.simple_spinner_dropdown_item,array);
        else
            adapter = new ArrayAdapter(this,android.R.layout.simple_spinner_dropdown_item,new String[]{});
        s.setAdapter(adapter);
        new AlertDialog.Builder(this).setView(view)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        try {
                            saveFile(bitmap);
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                        //writeTag("tagSeleccionadoShared");
                        finish();
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                })
                .setCancelable(false)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    protected void saveFile(Bitmap bmScreen2) {
        // TODO Auto-generated method stub


        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "/DadTime/SHARED_" + timeStamp + ".jpg";
        new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES)+"/DadTime/").mkdirs();

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

        addImageToGallery(saved_image_file.getAbsolutePath(), getApplicationContext());

    }

    protected void dispatchTakePictureIntent() {

        // Check if there is a camera.
        Context context = this;
        PackageManager packageManager = context.getPackageManager();
        if (!packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
            Toast.makeText(context, "Este dispositivo no dispone de cámara.", Toast.LENGTH_SHORT)
                    .show();
            finish();
            return;
        }

        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        CameraActivity activity = (CameraActivity) context;
        if (takePictureIntent.resolveActivity(activity.getPackageManager()) != null) {
            if (createImageProcess(activity)) {
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,
                        CapturedImageURI);
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }
    }

    public boolean createImageProcess(Context ctx){

        File photoFile = null;
        try {
            photoFile = createImageFile();
        } catch (IOException ex) {
            // Error occurred while creating the File
            ex.printStackTrace();
            Toast toast = Toast.makeText(ctx, "Error guardando la foto.", Toast.LENGTH_SHORT);
            toast.show();
            finish();
        }
        // Continue only if the File was successfully created
        if (photoFile != null) {
            Uri fileUri = Uri.fromFile(photoFile);
            CapturedImageURI = fileUri;
            CurrentPhotoPath = fileUri.getPath();
            return true;
        }
        return false;
    }

    protected File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(new Date());
        String imageFileName = "DadTime/JPEG_" + timeStamp + "_";
        new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES) + "/DadTime").mkdirs();
        File storageDir = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );
        CurrentPhotoPath = "file:" + image.getAbsolutePath();
        return image;
    }

    private void getTagString(String tag, ExifInterface exif)
    {
        System.out.println(tag + " : " + exif.getAttribute(tag) + "\n");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        System.out.println("request: " + requestCode);
        if (requestCode == REQUEST_TAKE_PHOTO && resultCode == Activity.RESULT_OK) {
            add();
            LayoutInflater inflater = this.getLayoutInflater();
            View view = inflater.inflate(R.layout.post_camera, null);
            ImageView imageCam = (ImageView)view.findViewById(R.id.imageCam);
            imageCam.setImageBitmap(Collage.getSmallBitmap(CurrentPhotoPath));
            imageCam.setScaleType(ImageView.ScaleType.CENTER_CROP);
            Spinner s = (Spinner) view.findViewById(R.id.listActivities);
            String[] array =  getActivities();
            ArrayAdapter<String> adapter;
            if(array!=null && array.length>0 )
                adapter = new ArrayAdapter(this,android.R.layout.simple_spinner_dropdown_item,array);
            else
                adapter = new ArrayAdapter(this,android.R.layout.simple_spinner_dropdown_item,new String[]{});
            s.setAdapter(adapter);

            new AlertDialog.Builder(this).setView(view)
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    })
                    .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    })
                    .setCancelable(false)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();

        } else {
            File tmp = new File(CurrentPhotoPath);
            if (tmp.exists()) tmp.delete();
            Toast.makeText(this, "Fallo la captura de la imagen.", Toast.LENGTH_SHORT)
                    .show();
            finish();

        }

    }

    public void writeTag(String tag){
        try {
            ExifInterface exif = new ExifInterface(CurrentPhotoPath);
            System.out.println(CurrentPhotoPath);
            exif.setAttribute(ExifInterface.TAG_MAKE, "DadTime");
            exif.setAttribute(ExifInterface.TAG_MODEL, tag);
            getTagString(ExifInterface.TAG_MAKE, exif);
            getTagString(ExifInterface.TAG_MODEL, exif);
            exif.saveAttributes();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected void add() {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(CurrentPhotoPath);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        sendBroadcast(mediaScanIntent);
    }

    public String[] getActivities(){
        preferenceSettings = getSharedPreferences("myPreferences", Context.MODE_PRIVATE);
        Set<String> getSetTitleEntertainments = preferenceSettings.getStringSet("titleEntertainments", null);
//Si es diferente de null tiene actividades seleccionadas, eso debes validar q no puede abrir nuestra camara si no existen actividades....
        if (getSetTitleEntertainments != null)
        {
            //En este array estan los títulos de actividades seleccionadas, es decir debes ponerlas en donde tenías los checks después de tomar la foto...
            ArrayList<String> titleEntertainments = new ArrayList<String>(getSetTitleEntertainments);

            String[] titleEntertainmentsArray = new String[titleEntertainments.size()];
            titleEntertainmentsArray = titleEntertainments.toArray(titleEntertainmentsArray);
            return titleEntertainmentsArray;
        }
        return null;
    }
    public static void addImageToGallery(final String filePath, final Context context) {

        ContentValues values = new ContentValues();

        values.put(MediaStore.Images.Media.DATE_TAKEN, System.currentTimeMillis());
        values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
        values.put(MediaStore.MediaColumns.DATA, filePath);

        context.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

    }
}
