package ec.edu.espol.integradora.dadtime;

import android.graphics.Bitmap;

/**
 * Created by ces_m
 * on 1/28/2016.
 */
public class Memory {
    private String title;
    private String path;
    private Bitmap collage;

    public Memory(String title,String path){
        this.title=title;
        this.path=path;
        this.collage=ImageHandler.getSmallBitmap(path, 240);
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Bitmap getCollage() {
        return collage;
    }

    public void setCollage(Bitmap collage) {
        this.collage = collage;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
