package ec.edu.espol.integradora.dadtime;

import android.app.Application;

import java.util.ArrayList;

/**
 * Created by user on 30/12/2015.
 */
public class ProfileGlobalClass extends Application {

    private String name;
    private ArrayList<Workday> workdays;
    private ArrayList<Son> sons;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Workday> getWorkdays() {
        return workdays;
    }

    public void setWorkdays(ArrayList<Workday> workdays) {
        this.workdays = workdays;
    }

    public ArrayList<Son> getSons() {
        return sons;
    }

    public void setSons(ArrayList<Son> sons) {
        this.sons = sons;
    }
}
