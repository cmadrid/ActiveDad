package ec.edu.espol.integradora.dadtime;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Pair;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import layout.AddImageDialog;

/**
 * Created by ces_m
 * on 1/5/2016.
 */
public class CameraActivity extends Activity {
    private String CurrentPhotoPath;
    private Uri CapturedImageURI;
    private int REQUEST_TAKE_PHOTO = 593;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Pair<String[],String[]> activities=AddImageDialog.getActivities(getApplicationContext());
        if(activities==null || activities.first==null || activities.first.length<2)
        {
            Toast.makeText(getApplicationContext(), "Debe seleccionar una actividad a asistir antes de usar la camara de la aplicación.", Toast.LENGTH_SHORT)
                    .show();
            finish();
            return;
        }
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        String action = intent.getAction();

        if (Intent.ACTION_SEND.equals(action) && extras.containsKey(Intent.EXTRA_STREAM)) {

            Uri uri = (Uri) getIntent().getExtras().get(Intent.EXTRA_STREAM);
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
                new AddImageDialog(this,bitmap,null);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return;
        }
        dispatchTakePictureIntent();
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


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        System.out.println("request: " + requestCode);
        if (requestCode == REQUEST_TAKE_PHOTO && resultCode == Activity.RESULT_OK) {
            new AddImageDialog(this,null,CurrentPhotoPath);
        } else {
            File tmp = new File(CurrentPhotoPath);
            if (tmp.exists()) tmp.delete();
            Toast.makeText(this, "Fallo la captura de la imagen.", Toast.LENGTH_SHORT)
                    .show();
            finish();


        }

    }

}
