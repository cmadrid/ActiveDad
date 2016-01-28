package ec.edu.espol.integradora.dadtime;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomAdapterMemory extends BaseAdapter {

    Activity activity;
    ArrayList<Memory> memories;

    public CustomAdapterMemory(Activity activity, ArrayList<Memory> memories) {
        this.activity = activity;
        this.memories = memories;
    }

    @Override
    public int getCount() {
        return memories.size();
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
        View rowView = inflater.inflate(R.layout.list_memories, null, true);
        ImageView ivImage = (ImageView) rowView.findViewById(R.id.collage);
        TextView tvTitle = (TextView) rowView.findViewById(R.id.titleCollage);
        ivImage.setImageBitmap(memories.get(position).getCollage());
        tvTitle.setText(memories.get(position).getTitle());
        return rowView;
    }
}
