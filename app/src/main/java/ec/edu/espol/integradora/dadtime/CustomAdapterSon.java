package ec.edu.espol.integradora.dadtime;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import java.util.ArrayList;
import java.util.Calendar;

public class CustomAdapterSon extends BaseAdapter {

    private Activity activity;
    private ArrayList<String> name;
    private ArrayList<String> birthday;
    private ArrayList<String> sex;
    private ArrayList<String> photo;

    public CustomAdapterSon(Activity activity,ArrayList<String> photo, ArrayList<String> name, ArrayList<String> birthday, ArrayList<String> sex) {
        this.activity = activity;
        this.photo = photo;
        this.name = name;
        this.birthday = birthday;
        this.sex = sex;
    }

    @Override
    public int getCount() {
        return name.size();
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
        View rowView = inflater.inflate(R.layout.list_son, null, true);
        ImageView ivPhoto = (ImageView) rowView.findViewById(R.id.ivImage);
        TextView tvName = (TextView) rowView.findViewById(R.id.tvName);
        TextView tvYearOld = (TextView) rowView.findViewById(R.id.tvYearOld);
        TextView tvSex = (TextView) rowView.findViewById(R.id.tvSex);
        tvName.setText(name.get(position));
        tvSex.setText(sex.get(position));
        if(photo.get(position)==null)
            ivPhoto.setImageResource(sex.get(position).equalsIgnoreCase("masculino")?R.drawable.male:R.drawable.female);
        else
            ivPhoto.setImageBitmap(ImageHandler.getSmallBitmap(photo.get(position),360));


        String[] bd = birthday.get(position).split("/");
        if(bd.length==3) {
            Calendar c = Calendar.getInstance();
            c.set(Integer.parseInt(bd[2]), Integer.parseInt(bd[1]), Integer.parseInt(bd[0]));
            tvYearOld.setText(getYears(c) + " Años");
        }
        return rowView;
    }

    public static int getYears(Calendar birthday) {
        Calendar b = Calendar.getInstance();
        int diff = b.get(Calendar.YEAR) - birthday.get(Calendar.YEAR);
        if (birthday.get(Calendar.MONTH) > b.get(Calendar.MONTH) ||
                (birthday.get(Calendar.MONTH) == b.get(Calendar.MONTH) && birthday.get(Calendar.DATE) > b.get(Calendar.DATE))) {
            diff--;
        }
        return diff<0?0:diff;
    }
}
