package ec.edu.espol.integradora.dadtime;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by ces_m
 * on 1/5/2016.
 */
public class CameraActivity extends AppCompatActivity {
    FrameLayout frame;
    Button button;
    String CurrentPhotoPath;
    Uri CapturedImageURI;
    int REQUEST_TAKE_PHOTO = 102;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_image_tag);


        if ((getIntent().getFlags() & Intent.FLAG_ACTIVITY_LAUNCHED_FROM_HISTORY) != 0) {
            this.finish();
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
        }

        //getSupportActionBar().hide();


        View mControlsView = findViewById(R.id.fullscreen_content_controls);
        View mContentView = findViewById(R.id.fullscreen_content);
        mContentView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | (Build.VERSION.SDK_INT > 15 ? SYSTEM_UI_FLAG_IMMERSIVE_STICKY() : 0)
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);

        mControlsView.setVisibility(View.VISIBLE);


        frame = (FrameLayout) findViewById(R.id.frame);
        button = (Button) findViewById(R.id.dummy_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        dispatchTakePictureIntent();


        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    public int SYSTEM_UI_FLAG_IMMERSIVE_STICKY() {
        return View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
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

            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
                ex.printStackTrace();
                Toast toast = Toast.makeText(activity, "Error guardando la foto.", Toast.LENGTH_SHORT);
                toast.show();
                finish();

            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri fileUri = Uri.fromFile(photoFile);
                CapturedImageURI = fileUri;
                CurrentPhotoPath = fileUri.getPath();
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,
                        CapturedImageURI);
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }
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

        if (requestCode == REQUEST_TAKE_PHOTO && resultCode == Activity.RESULT_OK) {
            add();

            setBackground(CurrentPhotoPath);
        } else {
            File tmp = new File(CurrentPhotoPath);
            if (tmp.exists()) tmp.delete();
            Toast.makeText(this, "Image Capture Failed", Toast.LENGTH_SHORT)
                    .show();
            finish();

        }

    }

    protected void add() {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(CurrentPhotoPath);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        sendBroadcast(mediaScanIntent);
    }

    private void setBackground(String imagePath) {

        Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
        BitmapDrawable dr = new BitmapDrawable(bitmap);
        frame.setBackground(dr);
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Camera Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://ec.edu.espol.integradora.dadtime/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Camera Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://ec.edu.espol.integradora.dadtime/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }
}
