package ec.edu.espol.integradora.dadtime;

import android.app.Activity;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import java.util.ArrayList;

public class CustomAdapterWorkday extends BaseAdapter {

    private Activity activity;
    private ArrayList<String> entryTime;
    private ArrayList<String> exitTime;
    private ArrayList<Boolean[]> days;

    public CustomAdapterWorkday(Activity activity, ArrayList<String> entryTime, ArrayList<String> exitTime, ArrayList<Boolean[]> days) {
        this.activity = activity;
        this.entryTime = entryTime;
        this.exitTime = exitTime;
        this.days = days;
    }

    @Override
    public int getCount() {
        return entryTime.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View view, ViewGroup parent) {
        LayoutInflater inflater = activity.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.list_workday, null, true);
        TextView tvSchedule = (TextView) rowView.findViewById(R.id.tvSchedule);
        ToggleButton tbMonday = (ToggleButton) rowView.findViewById(R.id.tbMonday);
        ToggleButton tbTuesday = (ToggleButton) rowView.findViewById(R.id.tbTuesday);
        ToggleButton tbWednesday = (ToggleButton) rowView.findViewById(R.id.tbWednesday);
        ToggleButton tbThursday = (ToggleButton) rowView.findViewById(R.id.tbThursday);
        ToggleButton tbFriday = (ToggleButton) rowView.findViewById(R.id.tbFriday);
        ToggleButton tbSaturday = (ToggleButton) rowView.findViewById(R.id.tbSaturday);
        ToggleButton tbSunday = (ToggleButton) rowView.findViewById(R.id.tbSunday);
        tvSchedule.setText(entryTime.get(position) + " - " + exitTime.get(position));
        tbMonday.setChecked(days.get(position)[0]);
        tbTuesday.setChecked(days.get(position)[1]);
        tbWednesday.setChecked(days.get(position)[2]);
        tbThursday.setChecked(days.get(position)[3]);
        tbFriday.setChecked(days.get(position)[4]);
        tbSaturday.setChecked(days.get(position)[5]);
        tbSunday.setChecked(days.get(position)[6]);
        return rowView;
    }
}
