package ec.edu.espol.integradora.dadtime;

import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TimePicker;

import java.util.ArrayList;
import java.util.Calendar;

public class ProfileFirstActivity extends AppCompatActivity {

    private EditText txtName;
    private EditText txtUser;
    private Button btnSunday;
    private Button btnMonday;
    private Button btnTuesday;
    private Button btnWednesday;
    private Button btnThursday;
    private Button btnFriday;
    private Button btnSaturday;
    private EditText txtEntryTime;
    private EditText txtExitTime;
    private CheckBox cbFreeDay;
    private Button btnNext;
    private int index = 0;
    private ArrayList<Workday> workdays;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_first);
        txtName = (EditText)findViewById(R.id.textName);
        txtUser = (EditText)findViewById(R.id.txtUser);
        btnSunday = (Button)findViewById(R.id.btnSunday);
        btnMonday = (Button)findViewById(R.id.btnMonday);
        btnTuesday = (Button)findViewById(R.id.btnTuesday);
        btnWednesday = (Button)findViewById(R.id.btnWednesday);
        btnThursday = (Button)findViewById(R.id.btnThursday);
        btnFriday = (Button)findViewById(R.id.btnFriday);
        btnSaturday = (Button)findViewById(R.id.btnSaturday);
        txtEntryTime = (EditText)findViewById(R.id.txtEntryTime);
        txtExitTime = (EditText)findViewById(R.id.txtExitTime);
        cbFreeDay = (CheckBox)findViewById(R.id.cbFreeDay);
        btnNext = (Button)findViewById(R.id.btnNext);
        txtEntryTime.setInputType(InputType.TYPE_NULL);
        txtExitTime.setInputType(InputType.TYPE_NULL);
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
                index = 0;
            }
        });
        btnMonday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                index = 1;
            }
        });
        btnTuesday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                index = 2;
            }
        });
        btnWednesday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                index = 3;
            }
        });
        btnThursday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                index = 4;
            }
        });
        btnFriday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                index = 5;
            }
        });
        btnSaturday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                index = 6;
            }
        });
        txtEntryTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int hour = calendar.get(Calendar.HOUR_OF_DAY);
                int minute = calendar.get(Calendar.MINUTE);
                TimePickerDialog timePicker = new TimePickerDialog(ProfileFirstActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hour, int minute) {
                        txtEntryTime.setText(hour + ":" + minute + ":00");
                        workdays.get(index).setEntryTime(txtEntryTime.getText().toString());
                    }
                }, hour, minute, true);
                timePicker.setCancelable(false);
                timePicker.show();
            }
        });
        txtExitTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int hour = calendar.get(Calendar.HOUR_OF_DAY);
                int minute = calendar.get(Calendar.MINUTE);
                TimePickerDialog timePicker = new TimePickerDialog(ProfileFirstActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hour, int minute) {
                        txtExitTime.setText(hour + ":" + minute + ":00");
                        workdays.get(index).setExitTime(txtExitTime.getText().toString());
                    }
                }, hour, minute, true);
                timePicker.setCancelable(false);
                timePicker.show();
            }
        });
        cbFreeDay.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                workdays.get(index).setFreeDay(isChecked);
            }
        });
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!stopService(new Intent(getBaseContext(),ServiceBackground.class)))
                    startService(new Intent(getBaseContext(),ServiceBackground.class));

                Intent intent = new Intent(ProfileFirstActivity.this, ProfileSecondActivity.class);
                startActivity(intent);
            }
        });
    }
}
