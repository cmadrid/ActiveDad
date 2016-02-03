package ec.edu.espol.integradora.dadtime;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.ToggleButton;

import java.util.ArrayList;
import java.util.Calendar;

public class ProfileThirdActivity extends AppCompatActivity {

    private static Activity activity;
    private SharedPreferences preferenceSettings;
    private SharedPreferences.Editor preferenceEditor;
    private ProfileGlobalClass profileGlobalClass;
    private FloatingActionButton fabAddSons;
    private Button btnFinalize;
    private ListView lvSon;
    private ArrayList<String> photo;
    private ArrayList<String> name;
    private ArrayList<String> birthday;
    private ArrayList<String> sex;
    private CustomAdapterSon adapterSon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_third);
        getSupportActionBar().setTitle("Hijos");
        this.activity = this;
        preferenceSettings = getSharedPreferences("myPreferences", Context.MODE_PRIVATE);
        preferenceEditor = preferenceSettings.edit();
        profileGlobalClass = (ProfileGlobalClass) getApplicationContext();

        lvSon = (ListView)findViewById(R.id.lvSons);
        lvSon.setFocusable(true);
        photo = new ArrayList<>();
        name = new ArrayList<>();
        birthday = new ArrayList<>();
        sex = new ArrayList<>();
        adapterSon = new CustomAdapterSon(activity, photo, name, birthday,sex);
        lvSon.setAdapter(adapterSon);
        lvSon.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                final int positionSon = position;
                final CharSequence[] items = {"Editar", "Eliminar"};
                new AlertDialog.Builder(activity).setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (which == 0) {
                            LayoutInflater inflater = activity.getLayoutInflater();
                            View view = inflater.inflate(R.layout.dialog_add_son, null);
                            final ImageView ivSon = (ImageView) view.findViewById(R.id.ivImage);
                            final EditText etName = (EditText) view.findViewById(R.id.etName);
                            final EditText etBirthday = (EditText) view.findViewById(R.id.etBirthday);
                            final Spinner spSex = (Spinner) view.findViewById(R.id.spinner);
                            etName.setText(name.get(position));
                            spSex.setSelection(sex.get(position).equalsIgnoreCase("Masculino") ? 1 : 0);
                            etBirthday.setText(birthday.get(position));
                            if(photo.get(position)==null)
                                ivSon.setImageResource(sex.get(position).equalsIgnoreCase("masculino")?R.drawable.male:R.drawable.female);
                            else
                                ivSon.setImageBitmap(ImageHandler.getSmallBitmap(photo.get(position),360));

                            spSex.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                    if (position == 0) ivSon.setImageResource(R.drawable.female);
                                    else ivSon.setImageResource(R.drawable.male);
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {
                                }
                            });
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
                                    photo.set(position, null);
                                    name.set(position, etName.getText().toString());
                                    birthday.set(position, etBirthday.getText().toString());
                                    sex.set(position, spSex.getSelectedItem().toString());
                                    adapterSon.notifyDataSetChanged();

                                }
                            })
                                    .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {

                                        }
                                    })
                                    .setCancelable(false)
                                    .show();
                        } else {
                            name.remove(positionSon);
                            birthday.remove(positionSon);
                            sex.remove(positionSon);
                            photo.remove(positionSon);
                            adapterSon.notifyDataSetChanged();
                        }
                    }
                })
                        .show();
                return false;
            }
        });
        fabAddSons = (FloatingActionButton)findViewById(R.id.fabAddSons);
        btnFinalize = (Button)findViewById(R.id.btnFinalize);
        fabAddSons.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater inflater = activity.getLayoutInflater();
                View view = inflater.inflate(R.layout.dialog_add_son, null);
                final ImageView ivSon = (ImageView) view.findViewById(R.id.ivImage);
                final EditText etName = (EditText) view.findViewById(R.id.etName);
                final EditText etBirthday = (EditText) view.findViewById(R.id.etBirthday);
                final Spinner spSex = (Spinner) view.findViewById(R.id.spinner);
                spSex.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        if(position==0)ivSon.setImageResource(R.drawable.female);
                        else ivSon.setImageResource(R.drawable.male);
                    }
                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {}
                });
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
                        photo.add(null);
                        name.add(etName.getText().toString());
                        birthday.add(etBirthday.getText().toString());
                        sex.add(spSex.getSelectedItem().toString());
                        adapterSon.notifyDataSetChanged();

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
