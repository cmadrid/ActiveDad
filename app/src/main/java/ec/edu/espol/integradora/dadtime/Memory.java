package ec.edu.espol.integradora.dadtime;

import android.graphics.Bitmap;

/**
 * Created by ces_m on 1/28/2016.
 */
public class Memory {
    private String title;
    private Bitmap collage;

    public Memory(String title,Bitmap collage){
        this.title=title;
        this.collage=collage;
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
