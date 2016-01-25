package ec.edu.espol.integradora.dadtime;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class EntertainmentActivity extends AppCompatActivity {

    private ImageView ivImage;
    private TextView tvTitle;
    private TextView tvCompany;
    private TextView tvCategory;
    private TextView tvSchedule;
    private TextView tvPrice;
    private TextView tvDescription;
    private CheckBox cbEntertainmentPerformed;
    private SharedPreferences preferenceSettings;
    private SharedPreferences.Editor preferenceEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entertainment);
        preferenceSettings = getSharedPreferences("myPreferences", Context.MODE_PRIVATE);
        preferenceEditor = preferenceSettings.edit();
        ivImage = (ImageView)findViewById(R.id.ivImage);
        tvTitle = (TextView)findViewById(R.id.tvTitle);
        tvCompany = (TextView)findViewById(R.id.tvCompany);
        tvCategory = (TextView)findViewById(R.id.tvCategory);
        tvSchedule = (TextView)findViewById(R.id.tvSchedule);
        tvPrice = (TextView)findViewById(R.id.tvPrice);
        tvDescription = (TextView)findViewById(R.id.tvDescription);
        cbEntertainmentPerformed = (CheckBox)findViewById(R.id.cbEntertainmentPerformed);
        final Entertainment entertainment = getIntent().getParcelableExtra("Entertainment");
        ivImage.setImageBitmap(entertainment.getImage());
        tvTitle.setText(entertainment.getTitle());
        tvCompany.setText(entertainment.getCompany());
        tvCategory.setText(entertainment.getCategory());
        tvSchedule.setText(entertainment.getSchedule());
        tvPrice.setText(entertainment.getPrice());
        tvDescription.setText(entertainment.getDescription());
        Set<String> getSetIdEntertainments = preferenceSettings.getStringSet("idEntertainments", null);
        if (getSetIdEntertainments != null)
        {
            ArrayList<String> idEntertainments = new ArrayList<String>(getSetIdEntertainments);
            if (ArrayContains(idEntertainments, Integer.toString(entertainment.getIdActivity())) != -1)
            {
                cbEntertainmentPerformed.setChecked(true);
            }
        }
        cbEntertainmentPerformed.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                {
                    Set<String> getSetIdEntertainments = preferenceSettings.getStringSet("idEntertainments", null);
                    Set<String> getSetTitleEntertainments = preferenceSettings.getStringSet("titleEntertainments", null);
                    if (getSetIdEntertainments != null)
                    {
                        ArrayList<String> idEntertainments = new ArrayList<String>(getSetIdEntertainments);
                        ArrayList<String> titleEntertainments = new ArrayList<String>(getSetTitleEntertainments);
                        if (ArrayContains(idEntertainments, Integer.toString(entertainment.getIdActivity())) == -1)
                        {
                            Set<String> setIdEntertainments = new HashSet<String>();
                            idEntertainments.add(Integer.toString(entertainment.getIdActivity()));
                            setIdEntertainments.addAll(idEntertainments);
                            preferenceEditor.putStringSet("idEntertainments", setIdEntertainments);
                            preferenceEditor.commit();
                            Set<String> setTitleEntertainments = new HashSet<String>();
                            titleEntertainments.add(entertainment.getTitle());
                            setTitleEntertainments.addAll(titleEntertainments);
                            preferenceEditor.putStringSet("titleEntertainments", setTitleEntertainments);
                            preferenceEditor.commit();
                        }
                    }
                    else
                    {
                        Set<String> setIdEntertainments = new HashSet<String>();
                        ArrayList<String> idEntertainments = new ArrayList<String>();
                        idEntertainments.add(Integer.toString(entertainment.getIdActivity()));
                        setIdEntertainments.addAll(idEntertainments);
                        preferenceEditor.putStringSet("idEntertainments", setIdEntertainments);
                        preferenceEditor.commit();
                        Set<String> setTitleEntertainments = new HashSet<String>();
                        ArrayList<String> titleEntertainments = new ArrayList<String>();
                        titleEntertainments.add(entertainment.getTitle());
                        setTitleEntertainments.addAll(titleEntertainments);
                        preferenceEditor.putStringSet("titleEntertainments", setTitleEntertainments);
                        preferenceEditor.commit();
                    }
                }
                else
                {
                    Set<String> getSetIdEntertainments = preferenceSettings.getStringSet("idEntertainments", null);
                    Set<String> getSetTitleEntertainments = preferenceSettings.getStringSet("titleEntertainments", null);
                    if (getSetIdEntertainments != null)
                    {
                        ArrayList<String> idEntertainments = new ArrayList<String>(getSetIdEntertainments);
                        Set<String> setIdEntertainments = new HashSet<String>();
                        idEntertainments.remove(Integer.toString(entertainment.getIdActivity()));
                        setIdEntertainments.addAll(idEntertainments);
                        preferenceEditor.putStringSet("idEntertainments", setIdEntertainments);
                        preferenceEditor.commit();
                        ArrayList<String> titleEntertainments = new ArrayList<String>(getSetTitleEntertainments);
                        Set<String> setTitleEntertainments = new HashSet<String>();
                        titleEntertainments.remove(entertainment.getTitle());
                        setTitleEntertainments.addAll(titleEntertainments);
                        preferenceEditor.putStringSet("titleEntertainments", setTitleEntertainments);
                        preferenceEditor.commit();
                    }
                }
            }
        });
    }

    private int ArrayContains(ArrayList<String> A, String s)
    {
        for (int i = 0; i < A.size(); i++)
        {
            if (A.get(i).equalsIgnoreCase(s))
            {
                return i;
            }
        }
        return -1;
    }
}
