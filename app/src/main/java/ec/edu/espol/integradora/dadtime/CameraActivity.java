package ec.edu.espol.integradora.dadtime;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;

import java.io.File;
import java.util.Calendar;

/**
 * Created by ces_m on 1/5/2016.
 */
public class CameraActivity extends Activity {
    File mediaFile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.transparent_activity);


        if((getIntent().getFlags() & Intent.FLAG_ACTIVITY_LAUNCHED_FROM_HISTORY) !=0)
        {
            this.finish();
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
        }

        //Si no existe se crea el directorio a guardar
        File directory = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/DCIM/GamingCoach");
        directory.mkdirs();

        int IMAGE_CAPTURE = 102;
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        //Se guarda la fecha para ponerla de nombre en la imagen
        String mydate = java.text.DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());
        //se defina guardar en la carpeta destinada con la fecha de nombre
        mediaFile = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/DCIM/GamingCoach/" + mydate + ".jpg");

        Uri imgUri = Uri.fromFile(mediaFile);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imgUri);
        startActivityForResult(intent, IMAGE_CAPTURE);

        //finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==102){
            System.out.println("result");
            finish();
        }
    }
}
