package ec.edu.espol.integradora.dadtime;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;
import java.util.Calendar;

public class ProfileSecondActivity extends AppCompatActivity {

    private Activity activity;
    private ProfileGlobalClass profileGlobalClass;
    private Button btnAddSon;
    private TableLayout tlBody;
    private Button btnPrevious;
    private Button btnFinalize;
    private ArrayList<Son> sons;

    private static final String LOGTAG = "LogsAndroidDADTIME";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_second);


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
                etBirthday.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                    @Override
                    public void onFocusChange(View v, boolean hasFocus) {
                        if (hasFocus) {
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
                                        .setTitle("Eliminar un perfil")
                                        .setMessage("¿Esta seguro que desea eliminar ese perfil?")
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
                Son son = new Son();
                //son.setName(etName.getText().toString());
                //son.setBirthdate(etBirthday.getText().toString());
                sons.add(son);
                profileGlobalClass.setSons(sons);
                Intent intent = new Intent(ProfileSecondActivity.this, MainActivity.class);
                startActivity(intent);
                //SaveProfile saveProfile = new SaveProfile();
                //saveProfile.execute();
                if(ProfileFirstActivity.activity!=null)
                    ProfileFirstActivity.activity.finish();
                finish();
            }
        });
    }

    private class SaveProfile extends AsyncTask<Void, Void, String>
    {
        @Override
        protected String doInBackground(Void ...param)
        {
            try
            {
                String NAMESPACE = "urn:DadTime";
                String URL = "http://www.corporacionsmartest.com/DadTimeWebServices/wsDadTime.php";
                String SOAP_ACTION = "urn:DadTime#InsertProfile";
                String METHOD_NAME = "InsertProfile";
                SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
                request.addProperty("name", "Cesar");
                SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapSerializationEnvelope.VER11);
                envelope.dotNet = true;
                envelope.setOutputSoapObject(request);
                HttpTransportSE httpTransport = new HttpTransportSE(URL);
                httpTransport.call(SOAP_ACTION, envelope);
                SoapObject response = (SoapObject) envelope.bodyIn;
                String wsResponse = (String)response.getProperty(0);
                return wsResponse;
            }
            catch (Exception e)
            {
                return e.toString();
            }
        }

        @Override
        protected void onPostExecute(String wsResponse)
        {
            Log.i(LOGTAG, wsResponse);
        }
    }
}
