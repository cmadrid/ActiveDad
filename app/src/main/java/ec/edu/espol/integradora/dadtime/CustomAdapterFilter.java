package ec.edu.espol.integradora.dadtime;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;


public class CustomAdapterFilter extends BaseAdapter {

    Activity activity;
    String[] options;
    ListView lv;

    public CustomAdapterFilter(Activity activity,String[] options) {
        this.activity = activity;
        this.options = options;
    }

    @Override
    public int getCount() {
        return options.length;
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
        View rowView = inflater.inflate(R.layout.filter_list_item, null, true);
        TextView option = (TextView) rowView.findViewById(R.id.item_filter);
        option.setText(options[position].toUpperCase());

        return rowView;
    }



}
