package ec.edu.espol.integradora.dadtime;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Environment;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Pair;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

import database.DBActivity;

/**
 * Created by ces_m
 * on 2/2/2016.
 */
public class ServiceCollageBackground extends Service {
    public static final String MyPREFERENCES = "memoriesPreferences" ;
    SharedPreferences sharedpreferences;
    private SharedPreferences.Editor preferenceEditor;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent,int flags,int startId){
        sharedpreferences=getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        preferenceEditor = sharedpreferences.edit();
        //customUrl = sharedpreferences.getString(GamingCoach.Pref.CustomUrl, "");
        start();
        Toast.makeText(this, "Collage Started", Toast.LENGTH_SHORT).show();
        return START_STICKY;//averiguar bien
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        stop();
        Toast.makeText(this,"Collage Stoped",Toast.LENGTH_SHORT).show();
    }



    private Timer timer;
    private TimerTask timerTask = new TimerTask() {

        @Override
        public void run() {
            System.out.println("progreso del servicio!!!");
            Entertainment[] activities=null;
            DBActivity dbActivity=null;
            try {
                dbActivity = new DBActivity(getApplicationContext());
                activities = dbActivity.consultarActivities(null, null);

            }catch (Exception e){
                e.printStackTrace();
            }finally {
                if(dbActivity!=null)
                    dbActivity.close();
            }
            ArrayList<Pair<Entertainment,JSONArray>> availables = new ArrayList<>();

            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.DAY_OF_MONTH, -1);
            for(Entertainment activity:activities)
            {
                Pair<List<File>,List<File>> filesPair = Collage.getListFilesTagDate(new File(Environment.getExternalStoragePublicDirectory(
                        Environment.DIRECTORY_PICTURES) + "/DadTime"), activity.getIdActivity()+"", calendar.getTime());
                if(filesPair.first.size()+filesPair.second.size()>=6)
                {
                    List<File> favs = filesPair.first; List<File> noFavs = filesPair.second;
                    favs=Collage.shuffleFiles(favs);noFavs=Collage.shuffleFiles(noFavs);
                    List<File> files=new ArrayList<>(favs);
                    files.addAll(noFavs);
                    JSONArray filesPath = new JSONArray(files);
                    availables.add(new Pair<>(activity,filesPath));
                }
            }
            preferenceEditor.clear();

            for(Pair<Entertainment,JSONArray> p : availables)
                if(sharedpreferences.getString(p.first.getIdActivity()+"",null)==null)
                    notificar(p.first.getTitle());
                /*else
                {
                    try {
                        JSONArray json = new JSONArray(sharedpreferences.getString(p.first, null));
                        for(int i = 0; i < json.length(); i++){
                            System.out.println(json.getString(i));
                        }
                        System.out.println(json.length());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }*/
            preferenceEditor.clear();
            for(Pair<Entertainment,JSONArray> p : availables)
                preferenceEditor.putString(p.first.getIdActivity()+"",p.second.toString());
            preferenceEditor.commit();

        }
    };

    public void notificar(String actividad){
        System.out.println("existe un nuevo collage de la actividad: "+actividad);
    }

    public void start() {
        if(timer != null) {
            return;
        }
        timer = new Timer();
        timer.scheduleAtFixedRate(timerTask, 0, 30 * 1000);
    }

    public void stop() {
        if(timer!=null)
            timer.cancel();
        timer = null;
    }



}
