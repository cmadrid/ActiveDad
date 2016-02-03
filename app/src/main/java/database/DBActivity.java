package database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.util.Pair;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import ec.edu.espol.integradora.dadtime.Entertainment;

public class DBActivity {
    public static final String TABLE_NAME = "activity";
    public static final String ID = "_id"+"_"+TABLE_NAME;
    public static final String TITLE = "title"+"_"+TABLE_NAME;
    public static final String COMPANY = "company"+"_"+TABLE_NAME;
    public static final String CATEGORY = "category"+"_"+TABLE_NAME;
    public static final String DAY = "day"+"_"+TABLE_NAME;
    public static final String SCHEDULE = "schedule"+"_"+TABLE_NAME;
    public static final String PRICE = "price"+"_"+TABLE_NAME;
    public static final String DESCRPTION = "description"+"_"+TABLE_NAME;
    public static final String MINIMUM_AGE = "minimum_age"+"_"+TABLE_NAME;
    public static final String IMAGE = "image"+"_"+TABLE_NAME;
    public Context ctx;


    private DbHelper helper;
    private SQLiteDatabase db;

    public static final String CREATE_TABLE = "create table "+ TABLE_NAME +" ("
            + ID + " integer primary key,"
            + TITLE + " text not null,"
            + COMPANY + " text not null,"
            + CATEGORY + " text not null,"
            + DAY + " text not null,"
            + SCHEDULE + " text not null,"
            + PRICE + " text not null,"
            + DESCRPTION + " text,"
            + MINIMUM_AGE + " integer,"
            + IMAGE + " blob"
            +");";

    public DBActivity(Context contexto) {
        this.ctx=contexto;
        helper = new DbHelper(contexto);
        db = helper.getWritableDatabase();
    }

    public ContentValues generarContentValues(Integer id,String title,String company,String category,String day,String schedule,String price,String description,Integer minAge,byte[] image){
        ContentValues valores = new ContentValues();
        if(id!=null)
            valores.put(ID,id);
        if(title!=null)
            valores.put(TITLE,title);
        if(company!=null)
            valores.put(COMPANY,company);
        if(category!=null)
            valores.put(CATEGORY, category);
        if(day!=null)
            valores.put(DAY,day);
        if(schedule!=null)
            valores.put(SCHEDULE,schedule);
        if(price!=null)
            valores.put(PRICE, price);
        if(description!=null)
            valores.put(DESCRPTION,description);
        if(minAge!=null)
            valores.put(MINIMUM_AGE,minAge);
        if(image!=null)
            valores.put(IMAGE, image);
        return valores;
    }

    public void insertar(Integer id,String title,String company,String category,String day,String schedule,String price,String description,int minAge,Bitmap imageBitmap)
    {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        imageBitmap.compress(Bitmap.CompressFormat.PNG, 0, stream);
        byte[] image = stream.toByteArray();
        db.insert(TABLE_NAME, null, generarContentValues(id, title, company, category, day, schedule, price, description, minAge, image));
    }

    public boolean insertaroActualizar(Integer id,String title,String company,String category,String day,String schedule,String price,String description,int minAge,Bitmap imageBitmap)
    {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        imageBitmap.compress(Bitmap.CompressFormat.PNG, 0, stream);
        byte[] image = stream.toByteArray();
        Cursor c = consultar(id);
        if(c.moveToFirst())
        {
            String[] args = new String[] {id+""};
            db.update(TABLE_NAME, generarContentValues(id, title, company, category,day,schedule,price,description,minAge,null), ID + "=?", args);
            return true;
        }else
            db.insert(TABLE_NAME,null,generarContentValues(id, title, company, category,day,schedule,price,description,minAge,image));
        return false;
    }

    public String[] consultarCategorias(){
        String[] campos = new String[] {CATEGORY};
        //Cursor c = db.query(NOMBRE_TABLA, campos, "usuario=?(where)", args(para el where), group by, having, order by, num);
        ArrayList<String> categories = new ArrayList<>();
        Cursor c = db.query(TABLE_NAME, campos, null, null, "1", null, null);
        if(c.moveToFirst()){
            do{
                categories.add(c.getString(0));
            }while(c.moveToNext());
        }
        String[] categoriesArray = new String[categories.size()];
        categoriesArray = categories.toArray(categoriesArray);
        return categoriesArray;

    }


    public Entertainment[] consultarActivities(Date date1,Date date2){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd 00:00", Locale.US);
        String[] campos = new String[] {ID,TITLE};
        //Cursor c = db.query(NOMBRE_TABLA, campos, "usuario=?(where)", args(para el where), group by, having, order by, num);

        ArrayList<Entertainment> activities = new ArrayList<>();
        Cursor c = (date1==null || date2==null)
                ?db.query(TABLE_NAME, campos, null, null, "1", null, null)
                :db.query(TABLE_NAME, campos,SCHEDULE+" between "+dateFormat.format(date1)+" and "+dateFormat.format(date2), null, "1", null, null);
        if(c.moveToFirst()){
            do{
                Entertainment e = new Entertainment();
                e.setIdActivity(c.getInt(0));
                e.setTitle(c.getString(1));
                activities.add(e);
            }while(c.moveToNext());
        }
        Entertainment[] activitiesArray = new Entertainment[activities.size()];
        activitiesArray = activities.toArray(activitiesArray);
        return activitiesArray;

    }

    public Cursor consultar(Integer id){

        String[] campos = new String[] {ID,TITLE,COMPANY,CATEGORY,DAY,SCHEDULE,PRICE,DESCRPTION,MINIMUM_AGE,IMAGE};
        //Cursor c = db.query(NOMBRE_TABLA, campos, "usuario=?(where)", args(para el where), group by, having, order by, num);

        String[] args = new String[] {id+""};

        if(id==null)return db.query(TABLE_NAME, campos, null, null, null, null,null);
        return db.query(TABLE_NAME, campos, ID+"=?", args, null, null, null);
    }


    public void vaciar(){
        db.delete(TABLE_NAME,null,null);
    }

    public void close(){
        try {
            if(helper!=null){
                helper.close();
                helper=null;
            }

            if(db!=null){
                db.close();
                db=null;
            }

        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
