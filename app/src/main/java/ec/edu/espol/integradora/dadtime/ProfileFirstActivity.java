package ec.edu.espol.integradora.dadtime;

import android.app.Activity;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationManager;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

public class ProfileFirstActivity extends AppCompatActivity {

    public static Activity activity;
    private SharedPreferences preferenceSettings;
    private SharedPreferences.Editor preferenceEditor;
    private ProfileGlobalClass profileGlobalClass;
    private EditText etName;
    private EditText etUser;
    private Button btnSunday;
    private Button btnMonday;
    private Button btnTuesday;
    private Button btnWednesday;
    private Button btnThursday;
    private Button btnFriday;
    private Button btnSaturday;
    private EditText etEntryTime;
    private EditText etExitTime;
    private CheckBox cbFreeDay;
    private Button btnNext;
    private int index = 0;
    private ArrayList<Workday> workdays;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_first);
        this.activity = this;
        preferenceSettings = getSharedPreferences("myPreferences", Context.MODE_PRIVATE);
        preferenceEditor = preferenceSettings.edit();
        if (preferenceSettings.getBoolean("profile", false))
        {
            Intent intent = new Intent(ProfileFirstActivity.this, MainActivity.class);
            startActivity(intent);
        }
        else
        {
            preferenceEditor.putBoolean("profile", false);
            preferenceEditor.commit();
        }
        profileGlobalClass = (ProfileGlobalClass) getApplicationContext();
        etName = (EditText)findViewById(R.id.etName);
        btnSunday = (Button)findViewById(R.id.btnSunday);
        btnMonday = (Button)findViewById(R.id.btnMonday);
        btnTuesday = (Button)findViewById(R.id.btnTuesday);
        btnWednesday = (Button)findViewById(R.id.btnWednesday);
        btnThursday = (Button)findViewById(R.id.btnThursday);
        btnFriday = (Button)findViewById(R.id.btnFriday);
        btnSaturday = (Button)findViewById(R.id.btnSaturday);
        etEntryTime = (EditText)findViewById(R.id.etEntryTime);
        etExitTime = (EditText)findViewById(R.id.etExitTime);
        cbFreeDay = (CheckBox)findViewById(R.id.cbFreeDay);
        btnNext = (Button)findViewById(R.id.btnNext);
        workdays = new ArrayList<>();
        Workday sunday = new Workday();
        sunday.setDay("Domingo");
        workdays.add(sunday);
        Workday monday = new Workday();
        monday.setDay("Lunes");
        workdays.add(monday);
        Workday tuesday = new Workday();
        tuesday.setDay("Martes");
        workdays.add(tuesday);
        Workday wednesday = new Workday();
        wednesday.setDay("Miércoles");
        workdays.add(wednesday);
        Workday thursday = new Workday();
        thursday.setDay("Jueves");
        workdays.add(thursday);
        Workday friday = new Workday();
        friday.setDay("Viernes");
        workdays.add(friday);
        Workday saturday = new Workday();
        saturday.setDay("Sábado");
        workdays.add(saturday);
        btnSunday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnSunday.setBackground(getResources().getDrawable(R.drawable.button_day_selected));
                btnMonday.setBackground(getResources().getDrawable(R.drawable.button_days));
                btnTuesday.setBackground(getResources().getDrawable(R.drawable.button_days));
                btnWednesday.setBackground(getResources().getDrawable(R.drawable.button_days));
                btnThursday.setBackground(getResources().getDrawable(R.drawable.button_days));
                btnFriday.setBackground(getResources().getDrawable(R.drawable.button_days));
                btnSaturday.setBackground(getResources().getDrawable(R.drawable.button_days));
                index = 0;
                etEntryTime.setText(workdays.get(index).getEntryTime());
                etExitTime.setText(workdays.get(index).getExitTime());
                cbFreeDay.setChecked(workdays.get(index).getFreeDay());
            }
        });
        btnMonday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnSunday.setBackground(getResources().getDrawable(R.drawable.button_days));
                btnMonday.setBackground(getResources().getDrawable(R.drawable.button_day_selected));
                btnTuesday.setBackground(getResources().getDrawable(R.drawable.button_days));
                btnWednesday.setBackground(getResources().getDrawable(R.drawable.button_days));
                btnThursday.setBackground(getResources().getDrawable(R.drawable.button_days));
                btnFriday.setBackground(getResources().getDrawable(R.drawable.button_days));
                btnSaturday.setBackground(getResources().getDrawable(R.drawable.button_days));
                index = 1;
                etEntryTime.setText(workdays.get(index).getEntryTime());
                etExitTime.setText(workdays.get(index).getExitTime());
                cbFreeDay.setChecked(workdays.get(index).getFreeDay());
            }
        });
        btnTuesday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnSunday.setBackground(getResources().getDrawable(R.drawable.button_days));
                btnMonday.setBackground(getResources().getDrawable(R.drawable.button_days));
                btnTuesday.setBackground(getResources().getDrawable(R.drawable.button_day_selected));
                btnWednesday.setBackground(getResources().getDrawable(R.drawable.button_days));
                btnThursday.setBackground(getResources().getDrawable(R.drawable.button_days));
                btnFriday.setBackground(getResources().getDrawable(R.drawable.button_days));
                btnSaturday.setBackground(getResources().getDrawable(R.drawable.button_days));
                index = 2;
                etEntryTime.setText(workdays.get(index).getEntryTime());
                etExitTime.setText(workdays.get(index).getExitTime());
                cbFreeDay.setChecked(workdays.get(index).getFreeDay());
            }
        });
        btnWednesday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnSunday.setBackground(getResources().getDrawable(R.drawable.button_days));
                btnMonday.setBackground(getResources().getDrawable(R.drawable.button_days));
                btnTuesday.setBackground(getResources().getDrawable(R.drawable.button_days));
                btnWednesday.setBackground(getResources().getDrawable(R.drawable.button_day_selected));
                btnThursday.setBackground(getResources().getDrawable(R.drawable.button_days));
                btnFriday.setBackground(getResources().getDrawable(R.drawable.button_days));
                btnSaturday.setBackground(getResources().getDrawable(R.drawable.button_days));
                index = 3;
                etEntryTime.setText(workdays.get(index).getEntryTime());
                etExitTime.setText(workdays.get(index).getExitTime());
                cbFreeDay.setChecked(workdays.get(index).getFreeDay());
            }
        });
        btnThursday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnSunday.setBackground(getResources().getDrawable(R.drawable.button_days));
                btnMonday.setBackground(getResources().getDrawable(R.drawable.button_days));
                btnTuesday.setBackground(getResources().getDrawable(R.drawable.button_days));
                btnWednesday.setBackground(getResources().getDrawable(R.drawable.button_days));
                btnThursday.setBackground(getResources().getDrawable(R.drawable.button_day_selected));
                btnFriday.setBackground(getResources().getDrawable(R.drawable.button_days));
                btnSaturday.setBackground(getResources().getDrawable(R.drawable.button_days));
                index = 4;
                etEntryTime.setText(workdays.get(index).getEntryTime());
                etExitTime.setText(workdays.get(index).getExitTime());
                cbFreeDay.setChecked(workdays.get(index).getFreeDay());
            }
        });
        btnFriday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnSunday.setBackground(getResources().getDrawable(R.drawable.button_days));
                btnMonday.setBackground(getResources().getDrawable(R.drawable.button_days));
                btnTuesday.setBackground(getResources().getDrawable(R.drawable.button_days));
                btnWednesday.setBackground(getResources().getDrawable(R.drawable.button_days));
                btnThursday.setBackground(getResources().getDrawable(R.drawable.button_days));
                btnFriday.setBackground(getResources().getDrawable(R.drawable.button_day_selected));
                btnSaturday.setBackground(getResources().getDrawable(R.drawable.button_days));
                index = 5;
                etEntryTime.setText(workdays.get(index).getEntryTime());
                etExitTime.setText(workdays.get(index).getExitTime());
                cbFreeDay.setChecked(workdays.get(index).getFreeDay());
            }
        });
        btnSaturday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnSunday.setBackground(getResources().getDrawable(R.drawable.button_days));
                btnMonday.setBackground(getResources().getDrawable(R.drawable.button_days));
                btnTuesday.setBackground(getResources().getDrawable(R.drawable.button_days));
                btnWednesday.setBackground(getResources().getDrawable(R.drawable.button_days));
                btnThursday.setBackground(getResources().getDrawable(R.drawable.button_days));
                btnFriday.setBackground(getResources().getDrawable(R.drawable.button_days));
                btnSaturday.setBackground(getResources().getDrawable(R.drawable.button_day_selected));
                index = 6;
                etEntryTime.setText(workdays.get(index).getEntryTime());
                etExitTime.setText(workdays.get(index).getExitTime());
                cbFreeDay.setChecked(workdays.get(index).getFreeDay());
            }
        });
        etEntryTime.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus)
                {
                    Calendar calendar = Calendar.getInstance();
                    int hour = calendar.get(Calendar.HOUR_OF_DAY);
                    int minute = calendar.get(Calendar.MINUTE);
                    TimePickerDialog timePicker = new TimePickerDialog(ProfileFirstActivity.this, new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker view, int hour, int minute) {
                            etEntryTime.setText(completeTime(hour) + ":" + completeTime(minute) + ":" + "00");
                            workdays.get(index).setEntryTime(etEntryTime.getText().toString());
                        }
                    }, hour, minute, true);
                    timePicker.setCancelable(false);
                    timePicker.show();
                }
            }
        });
        etExitTime.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus)
                {
                    Calendar calendar = Calendar.getInstance();
                    int hour = calendar.get(Calendar.HOUR_OF_DAY);
                    int minute = calendar.get(Calendar.MINUTE);
                    TimePickerDialog timePicker = new TimePickerDialog(ProfileFirstActivity.this, new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker view, int hour, int minute) {
                            etExitTime.setText(completeTime(hour) + ":" + completeTime(minute) + ":" + "00");
                            workdays.get(index).setExitTime(etExitTime.getText().toString());
                        }
                    }, hour, minute, true);
                    timePicker.setCancelable(false);
                    timePicker.show();
                }
            }
        });
        cbFreeDay.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                {
                    etEntryTime.setText("");
                    etExitTime.setText("");
                    workdays.get(index).setEntryTime("");
                    workdays.get(index).setExitTime("");
                }
                etEntryTime.setEnabled(!isChecked);
                etExitTime.setEnabled(!isChecked);
                workdays.get(index).setFreeDay(isChecked);
            }
        });
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                profileGlobalClass.setName(etName.getText().toString());
                profileGlobalClass.setWorkdays(workdays);
                Intent intent = new Intent(ProfileFirstActivity.this, ProfileSecondActivity.class);
                startActivity(intent);
            }
        });
    }

    private static String completeTime(int t) {
        if (t >= 10)
        {
            return String.valueOf(t);
        }
        else
        {
            return "0" + String.valueOf(t);
        }
    }
}
