package layout;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.media.ExifInterface;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.Spinner;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Set;

import ec.edu.espol.integradora.dadtime.ImageHandler;
import ec.edu.espol.integradora.dadtime.R;

/**
 * Created by ces_m
 * on 1/29/2016.
 */
public class AddImageDialog {

    Context ctx;
    Bitmap bitmap;
    String CurrentPhotoPath;

    public AddImageDialog(final Context ctx, final Bitmap bitmap, final String CurrentPhotoPath){
        this.ctx=ctx;
        this.bitmap=bitmap;
        this.CurrentPhotoPath=CurrentPhotoPath;

        LayoutInflater inflater = ((Activity)ctx).getLayoutInflater();
        final View view = inflater.inflate(R.layout.post_camera, null);
        final Spinner s = (Spinner) view.findViewById(R.id.listActivities);
        final CheckBox cb = (CheckBox) view.findViewById(R.id.fav);
        ImageView imageCam = (ImageView)view.findViewById(R.id.imageCam);
        imageCam.setImageBitmap(bitmap!=null?bitmap:ImageHandler.getSmallBitmap(CurrentPhotoPath, 360));
        imageCam.setScaleType(ImageView.ScaleType.CENTER_CROP);
        String[] array =  getActivities(ctx);
        ArrayAdapter<String> adapter;
        if(array!=null && array.length>0 )
            adapter = new ArrayAdapter(ctx,android.R.layout.simple_spinner_dropdown_item,array);
        else
            adapter = new ArrayAdapter(ctx,android.R.layout.simple_spinner_dropdown_item,new String[]{"------------------------------"});
        s.setAdapter(adapter);


        AlertDialog alert = new AlertDialog.Builder(ctx).setView(view)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        String tag = s.getSelectedItem().toString();
                        String path=null;
                        boolean fav = cb.isChecked();
                        System.out.println(tag);
                            if(bitmap==null)
                            {
                                path = CurrentPhotoPath;
                                add();
                        }
                        else
                            try {
                                path = ImageHandler.saveImage(bitmap, null, "SHARED_", ctx);
                            }catch (Exception e){
                                e.printStackTrace();
                            }
                        writeTag(path,fav,tag);
                        ((Activity)ctx).finish();
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        if(bitmap==null) {
                            File tmp = new File(CurrentPhotoPath);
                            if (tmp.exists()) tmp.delete();
                        }
                        ((Activity)ctx).finish();
                    }
                })
                .setCancelable(false)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();

        s.setOnItemSelectedListener(AddImageDialog.spinnerListener(alert));


    }

    public  void writeTag(String path,boolean fav,String activity){
        try {
            String title = fav?"DadTime*":"DadTime";

            ExifInterface exif = new ExifInterface(path);
            System.out.println(path);
            exif.setAttribute(ExifInterface.TAG_MAKE, title);
            exif.setAttribute(ExifInterface.TAG_MODEL, activity);
            exif.saveAttributes();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void add() {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(CurrentPhotoPath);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        ctx.sendBroadcast(mediaScanIntent);
    }


    public static AdapterView.OnItemSelectedListener spinnerListener(final AlertDialog alert){
        return new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position!=0)
                    alert.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(true);
                else
                    alert.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(false);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                alert.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(false);
            }
        };
    }



    public static String[] getActivities(Context ctx){
        SharedPreferences preferenceSettings = ctx.getSharedPreferences("myPreferences", Context.MODE_PRIVATE);
        Set<String> getSetTitleEntertainments = preferenceSettings.getStringSet("titleEntertainments", null);
//Si es diferente de null tiene actividades seleccionadas, eso debes validar q no puede abrir nuestra camara si no existen actividades....
        if (getSetTitleEntertainments != null)
        {
            //En este array estan los títulos de actividades seleccionadas, es decir debes ponerlas en donde tenías los checks después de tomar la foto...
            ArrayList<String> titleEntertainments = new ArrayList<String>(getSetTitleEntertainments);
            titleEntertainments.add(0,"------------------------------");
            String[] titleEntertainmentsArray = new String[titleEntertainments.size()];
            titleEntertainmentsArray = titleEntertainments.toArray(titleEntertainmentsArray);
            return titleEntertainmentsArray;
        }
        return null;
    }

}
