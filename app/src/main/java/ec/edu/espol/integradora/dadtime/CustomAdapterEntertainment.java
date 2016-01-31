package ec.edu.espol.integradora.dadtime;

import android.app.Activity;
import android.graphics.Bitmap;
import android.widget.BaseAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomAdapterEntertainment extends BaseAdapter {

    Activity activity;
    ArrayList<Entertainment> entertainments;

    public CustomAdapterEntertainment(Activity activity, ArrayList<Entertainment> entertainments) {
        this.activity = activity;
        this.entertainments = entertainments;
    }

    @Override
    public int getCount() {
        return entertainments.size();
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
        View rowView = inflater.inflate(R.layout.list_entertainments, null, true);
        ImageView ivImage = (ImageView) rowView.findViewById(R.id.ivImage);
        TextView tvTitle = (TextView) rowView.findViewById(R.id.tvTitle);
        TextView tvCompany = (TextView) rowView.findViewById(R.id.tvCompany);
        TextView tvCategory = (TextView) rowView.findViewById(R.id.tvCategory);
        ivImage.setImageBitmap(entertainments.get(position).getImage());
        tvTitle.setText(entertainments.get(position).getTitle());
        tvCompany.setText(entertainments.get(position).getCompany());
        tvCategory.setText(entertainments.get(position).getCategory());
        return rowView;
    }
}
