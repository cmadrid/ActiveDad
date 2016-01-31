package ec.edu.espol.integradora.dadtime;

import android.app.Activity;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomAdapterWorkday extends BaseAdapter {

    Activity activity;
    String[] title;

    public CustomAdapterWorkday(Activity activity, String[] title) {
        this.activity = activity;
        this.title = title;
    }

    @Override
    public int getCount() {
        return title.length;
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
        TextView tvTitle = (TextView) rowView.findViewById(R.id.tvTitle);
        tvTitle.setText(title[position]);
        return rowView;
    }
}
