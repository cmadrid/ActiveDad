package ec.edu.espol.integradora.dadtime;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.io.File;
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
public class CameraActivity extends AppCompatActivity {
    private ImageView test;
    private Button button;
    private String CurrentPhotoPath;
    private Uri CapturedImageURI;
    private LinearLayout tags;

    private SharedPreferences preferenceSettings;
    private SharedPreferences.Editor preferenceEditor;
    private int REQUEST_TAKE_PHOTO = 102;
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


        test = (ImageView) findViewById(R.id.image);
        tags = (LinearLayout) findViewById(R.id.tags);
        button = (Button) findViewById(R.id.dummy_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addMetadataGallery(CurrentPhotoPath, getApplicationContext());
                finish();
            }
        });

        dispatchTakePictureIntent();


        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
        getActivities();
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
        System.out.println("request: "+requestCode);
        if (requestCode == REQUEST_TAKE_PHOTO && resultCode == Activity.RESULT_OK) {
            add();
            setBackground(CurrentPhotoPath);
        } else {
            File tmp = new File(CurrentPhotoPath);
            if (tmp.exists()) tmp.delete();
            Toast.makeText(this, "Fallo la captura de la imagen.", Toast.LENGTH_SHORT)
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
        test.setImageBitmap(bitmap);
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
    public void getActivities(){
        preferenceSettings = getSharedPreferences("myPreferences", Context.MODE_PRIVATE);
        preferenceEditor = preferenceSettings.edit();
        Set<String> getSetTitleEntertainments = preferenceSettings.getStringSet("titleEntertainments", null);
//Si es diferente de null tiene actividades seleccionadas, eso debes validar q no puede abrir nuestra camara si no existen actividades....
        if (getSetTitleEntertainments != null)
        {
            //En este array estan los títulos de actividades seleccionadas, es decir debes ponerlas en donde tenías los checks después de tomar la foto...
            ArrayList<String> titleEntertainments = new ArrayList<String>(getSetTitleEntertainments);
            for(String actividad:titleEntertainments){
                CheckBox cb = new CheckBox(getApplicationContext());
                cb.setText(actividad);
                cb.setTextColor(Color.WHITE);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    cb.setButtonTintList(ColorStateList.valueOf(Color.WHITE));
                }else
                {
                    int id = Resources.getSystem().getIdentifier("btn_check_holo_dark", "drawable", "android");
                    cb.setButtonDrawable(id);
                }
                tags.addView(cb);
            }
        }
    }
    public static void addMetadataGallery(final String filePath, final Context context) {

        ContentValues values = new ContentValues();

        values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
        values.put(MediaStore.MediaColumns.DATA, filePath);
        values.put(MediaStore.MediaColumns.TITLE, "tipo");


        context.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
    }
}
