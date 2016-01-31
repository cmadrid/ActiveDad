package database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.Date;

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
            + IMAGE + " integer"
            +");";

    public DBActivity(Context contexto) {
        this.ctx=contexto;
        helper = new DbHelper(contexto);
        db = helper.getWritableDatabase();
    }
    /*
    public ContentValues generarContentValues(String id,String nombre,String logo,int minutos){
        ContentValues valores = new ContentValues();
        valores.put(ID,id);
        valores.put(NOMBRE,nombre);
        valores.put(LOGO,logo);
        valores.put(MINUTOS, minutos);

        return valores;
    }
    /*
    public void insertar(String id,String nombre,String logo,int minutos){
        //insert  into contactos
        db.insert(NOMBRE_TABLA, null, generarContentValues(id, nombre, logo, minutos));
    }
    public void insertaroActualizar(String id,String nombre,String logo,int minutos){

        Cursor c = consultar(id);
        if(c.moveToFirst()) {
            String[] args = new String[] {id};
            int minutos_antes = consultarMinutos(id);
            if(minutos_antes!=minutos) {
                DBSesiones dbSesiones=new DBSesiones(ctx);
                dbSesiones.insertaroActualizar(id, (minutos - minutos_antes) + "", new Date());
                dbSesiones.close();

            }
            db.update(NOMBRE_TABLA, generarContentValues(id, nombre, logo, minutos), ID + "=?", args);

        }else
            db.insert(NOMBRE_TABLA,null,generarContentValues(id,nombre,logo,minutos));
    }

    public int consultarMinutos(String id){
        String[] campos = new String[] {MINUTOS};
        String[] args = new String[] {id};
        int minutos=0;
        Cursor c = db.query(NOMBRE_TABLA, campos, ID+"=?", args, null, null, null);
        if(c.moveToFirst())
            minutos=c.getInt(0);
        c.close();
        return minutos;
    }


    public Cursor consultar(String id){

        String[] campos = new String[] {ID, NOMBRE , MINUTOS,LOGO};
        //Cursor c = db.query(NOMBRE_TABLA, campos, "usuario=?(where)", args(para el where), group by, having, order by, num);

        String[] args = new String[] {id};

        if(id==null)return db.query(NOMBRE_TABLA, campos, null, null, null, null,null);
        return db.query(NOMBRE_TABLA, campos, ID+"=?", args, null, null, null);
    }


    public void vaciar(){
        db.delete(NOMBRE_TABLA,null,null);
    }
*/
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
