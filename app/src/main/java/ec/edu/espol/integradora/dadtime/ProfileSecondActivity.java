package ec.edu.espol.integradora.dadtime;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;

public class ProfileSecondActivity extends AppCompatActivity {

    Button btnNext;
    Button btnAdd;
    TableLayout tlChildren;
    Activity activity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_second);
        this.activity=this;
        tlChildren = (TableLayout)findViewById(R.id.tlChildren);
        btnAdd = (Button)findViewById(R.id.btnAdd);
        btnNext = (Button)findViewById(R.id.btnNext);

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileSecondActivity.this, ProfileThirdActivity.class);
                startActivity(intent);
            }
        });
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addRow();
            }
        });
    }

    private void addRow()
    {
        final LayoutInflater inflater =activity.getLayoutInflater();
        final View v = inflater.inflate(R.layout.dialog_add_child, null);
        final TextView tvName = new TextView(this);
        final TextView tvBirthday = new TextView(this);
        final TextView tvAction = new TextView(this);

        final EditText etName = (EditText) v.findViewById(R.id.txtName);
        final EditText etBirthday = (EditText) v.findViewById(R.id.txtbirthday);
        final EditText etAction = (EditText) v.findViewById(R.id.txtAction);
        etBirthday.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                if(hasFocus)
                {
                    Calendar calendar = Calendar.getInstance();
                    int year = calendar.get(Calendar.YEAR);
                    int month = calendar.get(Calendar.MONTH);
                    int day = calendar.get(Calendar.DAY_OF_MONTH);
                    DatePickerDialog datePicker = new DatePickerDialog(activity, new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                            etBirthday.setText(dayOfMonth + "/" + monthOfYear + "/" + year);
                        }
                    }, year, month, day);
                    datePicker.setCancelable(false);
                    datePicker.show();
                    etAction.requestFocus();
                }
            }
        });
        new AlertDialog.Builder(activity)
                .setView(v)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        tvName.setText(etName.getText());
                        tvBirthday.setText(etBirthday.getText());
                        tvAction.setText(etAction.getText());
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

        TableRow trChild = new TableRow(this);
        trChild.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT));

        trChild.addView(tvName);
        trChild.addView(tvBirthday);
        trChild.addView(tvAction);
        tlChildren.addView(trChild);
    }
}
