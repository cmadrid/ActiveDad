package ec.edu.espol.integradora.dadtime;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.util.Calendar;

public class ProfileSecondActivity extends AppCompatActivity {

    public static Activity activity;
    private ProfileGlobalClass profileGlobalClass;
    private ListView lvWorkday;
    private FloatingActionButton fabAddWorkday;
    private Button btnNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_second);
        getSupportActionBar().setTitle("Horario Laboral");
        this.activity = this;
        profileGlobalClass = (ProfileGlobalClass) getApplicationContext();
        lvWorkday = (ListView)findViewById(R.id.lvWorkday);
        lvWorkday.setFocusable(true);
        fabAddWorkday = (FloatingActionButton)findViewById(R.id.fabAddWorkday);
        btnNext = (Button)findViewById(R.id.btnNext);
        String[] values = new String[] { "08:00 - 17:00", "20:00 - 05:00" };
        lvWorkday.setAdapter(new CustomAdapterWorkday(activity, values));
        lvWorkday.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(), "aqui", Toast.LENGTH_LONG).show();
            }
        });
        fabAddWorkday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater inflater = activity.getLayoutInflater();
                View view = inflater.inflate(R.layout.dialog_add_workday, null);
                final EditText etEntryTime = (EditText) view.findViewById(R.id.etEntryTime);
                final EditText etExitTime = (EditText) view.findViewById(R.id.etExitTime);
                final ToggleButton btnMonday = (ToggleButton) view.findViewById(R.id.btnMonday);
                final ToggleButton btnTuesday = (ToggleButton) view.findViewById(R.id.btnTuesday);
                final ToggleButton btnWednesday = (ToggleButton) view.findViewById(R.id.btnWednesday);
                final ToggleButton btnThursday = (ToggleButton) view.findViewById(R.id.btnThursday);
                final ToggleButton btnFriday = (ToggleButton) view.findViewById(R.id.btnFriday);
                final ToggleButton btnSaturday = (ToggleButton) view.findViewById(R.id.btnSaturday);
                final ToggleButton btnSunday = (ToggleButton) view.findViewById(R.id.btnSunday);
                etEntryTime.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Calendar calendar = Calendar.getInstance();
                        int hour = calendar.get(Calendar.HOUR_OF_DAY);
                        int minute = calendar.get(Calendar.MINUTE);
                        TimePickerDialog timePicker = new TimePickerDialog(activity, new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hour, int minute) {
                                etEntryTime.setText(completeTime(hour) + ":" + completeTime(minute));
                            }
                        }, hour, minute, true);
                        timePicker.setCancelable(false);
                        timePicker.show();
                    }
                });
                etExitTime.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Calendar calendar = Calendar.getInstance();
                        int hour = calendar.get(Calendar.HOUR_OF_DAY);
                        int minute = calendar.get(Calendar.MINUTE);
                        TimePickerDialog timePicker = new TimePickerDialog(activity, new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hour, int minute) {
                                etExitTime.setText(completeTime(hour) + ":" + completeTime(minute));
                            }
                        }, hour, minute, true);
                        timePicker.setCancelable(false);
                        timePicker.show();
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
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //profileGlobalClass.setName(etName.getText().toString());
                //profileGlobalClass.setWorkdays(workdays);
                Intent intent = new Intent(ProfileSecondActivity.this, ProfileThirdActivity.class);
                startActivity(intent);
            }
        });
        /*getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.mipmap.ic_dadtime);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        this.activity = this;
        profileGlobalClass = (ProfileGlobalClass) getApplicationContext();
        btnAddSon = (Button)findViewById(R.id.btnAddSon);
        tlBody = (TableLayout)findViewById(R.id.tlBody);
        btnPrevious = (Button)findViewById(R.id.btnPrevious);
        btnFinalize = (Button)findViewById(R.id.btnFinalize);
        sons = new ArrayList<>();
        btnAddSon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater inflater = activity.getLayoutInflater();
                View view = inflater.inflate(R.layout.dialog_add_son, null);
                final TableRow row = new TableRow(activity);
                final TextView tvName = new TextView(activity);
                final TextView tvBirthday = new TextView(activity);
                final ImageButton iBtnRemove = new ImageButton(activity);
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
                        row.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT));
                        row.setWeightSum(4);
                        row.setBackground(getResources().getDrawable(R.drawable.row_layout_body));
                        float scale = activity.getResources().getDisplayMetrics().density;
                        int pixels = (int) (40 * scale + 0.5f);
                        tvName.setLayoutParams(new TableRow.LayoutParams(0, pixels, 1.75f));
                        tvName.setGravity(Gravity.CENTER);
                        tvName.setTextSize(15);
                        tvName.setTextColor(Color.BLACK);
                        tvName.setText(etName.getText());
                        tvBirthday.setLayoutParams(new TableRow.LayoutParams(0, pixels, 1.45f));
                        tvBirthday.setGravity(Gravity.CENTER);
                        tvBirthday.setTextSize(15);
                        tvBirthday.setTextColor(Color.BLACK);
                        tvBirthday.setText(etBirthday.getText());
                        tvBirthday.setBackground(getResources().getDrawable(R.drawable.field_intermediate));
                        iBtnRemove.setLayoutParams(new TableRow.LayoutParams(0, pixels, 0.8f));
                        iBtnRemove.setImageResource(R.mipmap.remove);
                        iBtnRemove.setBackground(getResources().getDrawable(R.drawable.field_last));
                        row.addView(tvName);
                        row.addView(tvBirthday);
                        row.addView(iBtnRemove);
                        tlBody.addView(row);
                        iBtnRemove.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                new AlertDialog.Builder(activity)
                                        .setTitle("Eliminar un registro")
                                        .setMessage("¿Está seguro que desea eliminar el registro?")
                                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                tlBody.removeView(row);
                                            }
                                        })
                                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                            }
                                        })
                                        .setIcon(android.R.drawable.ic_dialog_alert)
                                        .show();
                            }
                        });
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // do nothing
                    }
                })
                .setCancelable(false)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
            }
        });
        btnPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        btnFinalize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Son son = new Son();
                son.setName(etName.getText().toString());
                son.setBirthdate(etBirthday.getText().toString());
                sons.add(son);
                profileGlobalClass.setSons(sons);
                SaveProfile saveProfile = new SaveProfile();
                saveProfile.execute();
                preferenceSettings = getSharedPreferences("myPreferences", Context.MODE_PRIVATE);
                preferenceEditor = preferenceSettings.edit();
                preferenceEditor.putBoolean("profile", true);
                preferenceEditor.commit();
                Intent intent = new Intent(ProfileSecondActivity.this, MainActivity.class);
                startActivity(intent);
                if(ProfileFirstActivity.activity != null)
                {
                    ProfileFirstActivity.activity.finish();
                }
                finish();
            }
        });*/

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
