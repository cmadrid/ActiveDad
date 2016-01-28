package ec.edu.espol.integradora.dadtime;

/**
 * Created by user on 09/12/2015.
 */
public class Workday {

    private String day;
    private String entryTime;
    private String exitTime;
    private boolean freeDay;

    public Workday()
    {
        this.day = "";
        this.entryTime = "";
        this.exitTime = "";
        this.freeDay = false;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getEntryTime() {
        return entryTime;
    }

    public void setEntryTime(String entryTime) {
        this.entryTime = entryTime;
    }

    public String getExitTime() {
        return exitTime;
    }

    public void setExitTime(String exitTime) {
        this.exitTime = exitTime;
    }

    public boolean getFreeDay() {
        return freeDay;
    }

    public void setFreeDay(boolean freeDay) {
        this.freeDay = freeDay;
    }
}
