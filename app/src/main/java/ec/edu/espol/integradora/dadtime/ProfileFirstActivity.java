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
    private Button btnNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_first);
        getSupportActionBar().setTitle("Papá");
        this.activity = this;
        preferenceSettings = getSharedPreferences("myPreferences", Context.MODE_PRIVATE);
        preferenceEditor = preferenceSettings.edit();
        profileGlobalClass = (ProfileGlobalClass) getApplicationContext();
        etName = (EditText)findViewById(R.id.etName);
        btnNext = (Button)findViewById(R.id.btnNext);
        if (preferenceSettings.getBoolean("profile", false))
        {
            Intent intent = new Intent(ProfileFirstActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
        else
        {
            preferenceEditor.putBoolean("profile", false);
            preferenceEditor.commit();
        }
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //profileGlobalClass.setName(etName.getText().toString());
                //profileGlobalClass.setWorkdays(workdays);
                Intent intent = new Intent(ProfileFirstActivity.this, ProfileSecondActivity.class);
                startActivity(intent);
            }
        });
    }
}
