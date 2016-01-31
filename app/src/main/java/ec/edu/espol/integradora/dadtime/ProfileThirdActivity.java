package ec.edu.espol.integradora.dadtime;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;


import java.util.Calendar;

public class ProfileThirdActivity extends AppCompatActivity {

    private static Activity activity;
    private SharedPreferences preferenceSettings;
    private SharedPreferences.Editor preferenceEditor;
    FloatingActionButton fabAddSons;
    private Button btnFinalize;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_third);
        getSupportActionBar().setTitle("Hijos");
        this.activity = this;
        fabAddSons = (FloatingActionButton)findViewById(R.id.fabAddSons);
        btnFinalize = (Button)findViewById(R.id.btnFinalize);
        fabAddSons.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater inflater = activity.getLayoutInflater();
                View view = inflater.inflate(R.layout.dialog_add_son, null);
                final EditText etName = (EditText) view.findViewById(R.id.etName);
                final EditText etBirthday = (EditText) view.findViewById(R.id.etBirthday);
                etBirthday.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Calendar calendar = Calendar.getInstance();
                        int year = calendar.get(Calendar.YEAR);
                        int month = calendar.get(Calendar.MONTH);
                        int day = calendar.get(Calendar.DAY_OF_MONTH);
                        DatePickerDialog datePicker = new DatePickerDialog(activity, new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int day) {
                                etBirthday.setText(day + "/" + month + "/" + year);
                            }
                        }, year, month, day);
                        datePicker.setCancelable(false);
                        datePicker.show();
                    }
                });
                new AlertDialog.Builder(activity).setView(view).setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setCancelable(false)
                .show();
            }
        });
        btnFinalize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileThirdActivity.this, MainActivity.class);
                startActivity(intent);
                preferenceSettings = getSharedPreferences("myPreferences", Context.MODE_PRIVATE);
                preferenceEditor = preferenceSettings.edit();
                preferenceEditor.putBoolean("profile", true);
                preferenceEditor.commit();
                if(ProfileFirstActivity.activity != null)
                {
                    ProfileFirstActivity.activity.finish();
                }
                if(ProfileSecondActivity.activity != null)
                {
                    ProfileSecondActivity.activity.finish();
                }
                finish();
            }
        });
    }
}
