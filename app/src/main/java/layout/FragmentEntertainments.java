package layout;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.io.InputStream;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Vector;
import java.util.prefs.Preferences;

import ec.edu.espol.integradora.dadtime.CustomAdapterEntertainment;
import ec.edu.espol.integradora.dadtime.Entertainment;
import ec.edu.espol.integradora.dadtime.EntertainmentActivity;
import ec.edu.espol.integradora.dadtime.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentEntertainments extends Fragment {

    TableLayout tlDays;
    private Button btnMonday;
    private Button btnTuesday;
    private Button btnWednesday;
    private Button btnThursday;
    private Button btnFriday;
    private Button btnSaturday;
    private Button btnSunday;
    TextView tvDate;
    ListView lvEntertainments;
    ArrayList<Entertainment> entertainments;
    ArrayList<Entertainment> entertainmentsSpecificDay;
    ProgressBar progressBar;
    Calendar calendar;

    public FragmentEntertainments() {
        // Required empty public constructor
    }

    public static FragmentEntertainments newInstance() {
        FragmentEntertainments fragment = new FragmentEntertainments();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_entertainments, container, false);
        tlDays = (TableLayout) view.findViewById(R.id.tlDays);
        btnMonday = (Button) view.findViewById(R.id.btnMonday);
        btnTuesday = (Button) view.findViewById(R.id.btnTuesday);
        btnWednesday = (Button) view.findViewById(R.id.btnWednesday);
        btnThursday = (Button) view.findViewById(R.id.btnThursday);
        btnFriday = (Button) view.findViewById(R.id.btnFriday);
        btnSaturday = (Button) view.findViewById(R.id.btnSaturday);
        btnSunday = (Button) view.findViewById(R.id.btnSunday);
        tvDate = (TextView) view.findViewById(R.id.tvDate);
        lvEntertainments = (ListView) view.findViewById(R.id.lvEntertainments);
        progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
        calendar = Calendar.getInstance();
        btnMonday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnMonday.setBackground(getResources().getDrawable(R.drawable.button_day_selected));
                btnTuesday.setBackground(getResources().getDrawable(R.drawable.button_days));
                btnWednesday.setBackground(getResources().getDrawable(R.drawable.button_days));
                btnThursday.setBackground(getResources().getDrawable(R.drawable.button_days));
                btnFriday.setBackground(getResources().getDrawable(R.drawable.button_days));
                btnSaturday.setBackground(getResources().getDrawable(R.drawable.button_days));
                btnSunday.setBackground(getResources().getDrawable(R.drawable.button_days));
                if (calendar.get(Calendar.DAY_OF_WEEK) != 1)
                {
                    calendar.add(Calendar.DAY_OF_MONTH, (2 - calendar.get(Calendar.DAY_OF_WEEK)));
                }
                else
                {
                    calendar.add(Calendar.DAY_OF_MONTH, -6);
                }
                tvDate.setText("LUNES, " + Integer.toString(calendar.get(Calendar.DAY_OF_MONTH)) + " DE " + NameMonth(calendar.get(Calendar.MONTH)) + " DE " + Integer.toString(calendar.get(Calendar.YEAR)));
                AdapterEntertainments();
            }
        });
        btnTuesday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnMonday.setBackground(getResources().getDrawable(R.drawable.button_days));
                btnTuesday.setBackground(getResources().getDrawable(R.drawable.button_day_selected));
                btnWednesday.setBackground(getResources().getDrawable(R.drawable.button_days));
                btnThursday.setBackground(getResources().getDrawable(R.drawable.button_days));
                btnFriday.setBackground(getResources().getDrawable(R.drawable.button_days));
                btnSaturday.setBackground(getResources().getDrawable(R.drawable.button_days));
                btnSunday.setBackground(getResources().getDrawable(R.drawable.button_days));
                if (calendar.get(Calendar.DAY_OF_WEEK) != 1)
                {
                    calendar.add(Calendar.DAY_OF_MONTH, (3 - calendar.get(Calendar.DAY_OF_WEEK)));
                }
                else
                {
                    calendar.add(Calendar.DAY_OF_MONTH, -5);
                }
                tvDate.setText("MARTES, " + Integer.toString(calendar.get(Calendar.DAY_OF_MONTH)) + " DE " + NameMonth(calendar.get(Calendar.MONTH)) + " DE " + Integer.toString(calendar.get(Calendar.YEAR)));
                AdapterEntertainments();
            }
        });
        btnWednesday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnMonday.setBackground(getResources().getDrawable(R.drawable.button_days));
                btnTuesday.setBackground(getResources().getDrawable(R.drawable.button_days));
                btnWednesday.setBackground(getResources().getDrawable(R.drawable.button_day_selected));
                btnThursday.setBackground(getResources().getDrawable(R.drawable.button_days));
                btnFriday.setBackground(getResources().getDrawable(R.drawable.button_days));
                btnSaturday.setBackground(getResources().getDrawable(R.drawable.button_days));
                btnSunday.setBackground(getResources().getDrawable(R.drawable.button_days));
                if (calendar.get(Calendar.DAY_OF_WEEK) != 1)
                {
                    calendar.add(Calendar.DAY_OF_MONTH, (4 - calendar.get(Calendar.DAY_OF_WEEK)));
                }
                else
                {
                    calendar.add(Calendar.DAY_OF_MONTH, -4);
                }
                tvDate.setText("MIÉRCOLES, " + Integer.toString(calendar.get(Calendar.DAY_OF_MONTH)) + " DE " + NameMonth(calendar.get(Calendar.MONTH)) + " DE " + Integer.toString(calendar.get(Calendar.YEAR)));
                AdapterEntertainments();
            }
        });
        btnThursday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnMonday.setBackground(getResources().getDrawable(R.drawable.button_days));
                btnTuesday.setBackground(getResources().getDrawable(R.drawable.button_days));
                btnWednesday.setBackground(getResources().getDrawable(R.drawable.button_days));
                btnThursday.setBackground(getResources().getDrawable(R.drawable.button_day_selected));
                btnFriday.setBackground(getResources().getDrawable(R.drawable.button_days));
                btnSaturday.setBackground(getResources().getDrawable(R.drawable.button_days));
                btnSunday.setBackground(getResources().getDrawable(R.drawable.button_days));
                if (calendar.get(Calendar.DAY_OF_WEEK) != 1)
                {
                    calendar.add(Calendar.DAY_OF_MONTH, (5 - calendar.get(Calendar.DAY_OF_WEEK)));
                }
                else
                {
                    calendar.add(Calendar.DAY_OF_MONTH, -3);
                }
                tvDate.setText("JUEVES, " + Integer.toString(calendar.get(Calendar.DAY_OF_MONTH)) + " DE " + NameMonth(calendar.get(Calendar.MONTH)) + " DE " + Integer.toString(calendar.get(Calendar.YEAR)));
                AdapterEntertainments();
            }
        });
        btnFriday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnMonday.setBackground(getResources().getDrawable(R.drawable.button_days));
                btnTuesday.setBackground(getResources().getDrawable(R.drawable.button_days));
                btnWednesday.setBackground(getResources().getDrawable(R.drawable.button_days));
                btnThursday.setBackground(getResources().getDrawable(R.drawable.button_days));
                btnFriday.setBackground(getResources().getDrawable(R.drawable.button_day_selected));
                btnSaturday.setBackground(getResources().getDrawable(R.drawable.button_days));
                btnSunday.setBackground(getResources().getDrawable(R.drawable.button_days));
                if (calendar.get(Calendar.DAY_OF_WEEK) != 1)
                {
                    calendar.add(Calendar.DAY_OF_MONTH, (6 - calendar.get(Calendar.DAY_OF_WEEK)));
                }
                else
                {
                    calendar.add(Calendar.DAY_OF_MONTH, -2);
                }
                tvDate.setText("VIERNES, " + Integer.toString(calendar.get(Calendar.DAY_OF_MONTH)) + " DE " + NameMonth(calendar.get(Calendar.MONTH)) + " DE " + Integer.toString(calendar.get(Calendar.YEAR)));
                AdapterEntertainments();
            }
        });
        btnSaturday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnMonday.setBackground(getResources().getDrawable(R.drawable.button_days));
                btnTuesday.setBackground(getResources().getDrawable(R.drawable.button_days));
                btnWednesday.setBackground(getResources().getDrawable(R.drawable.button_days));
                btnThursday.setBackground(getResources().getDrawable(R.drawable.button_days));
                btnFriday.setBackground(getResources().getDrawable(R.drawable.button_days));
                btnSaturday.setBackground(getResources().getDrawable(R.drawable.button_day_selected));
                btnSunday.setBackground(getResources().getDrawable(R.drawable.button_days));
                if (calendar.get(Calendar.DAY_OF_WEEK) != 1)
                {
                    calendar.add(Calendar.DAY_OF_MONTH, (7 - calendar.get(Calendar.DAY_OF_WEEK)));
                }
                else
                {
                    calendar.add(Calendar.DAY_OF_MONTH, -1);
                }
                tvDate.setText("SÁBADO, " + Integer.toString(calendar.get(Calendar.DAY_OF_MONTH)) + " DE " + NameMonth(calendar.get(Calendar.MONTH)) + " DE " + Integer.toString(calendar.get(Calendar.YEAR)));
                AdapterEntertainments();
            }
        });
        btnSunday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnMonday.setBackground(getResources().getDrawable(R.drawable.button_days));
                btnTuesday.setBackground(getResources().getDrawable(R.drawable.button_days));
                btnWednesday.setBackground(getResources().getDrawable(R.drawable.button_days));
                btnThursday.setBackground(getResources().getDrawable(R.drawable.button_days));
                btnFriday.setBackground(getResources().getDrawable(R.drawable.button_days));
                btnSaturday.setBackground(getResources().getDrawable(R.drawable.button_days));
                btnSunday.setBackground(getResources().getDrawable(R.drawable.button_day_selected));
                if (calendar.get(Calendar.DAY_OF_WEEK) != 1)
                {
                    calendar.add(Calendar.DAY_OF_MONTH, (8 - calendar.get(Calendar.DAY_OF_WEEK)));
                    tvDate.setText("DOMINGO, " + Integer.toString(calendar.get(Calendar.DAY_OF_MONTH)) + " DE " + NameMonth(calendar.get(Calendar.MONTH)) + " DE " + Integer.toString(calendar.get(Calendar.YEAR)));
                    AdapterEntertainments();
                }
            }
        });
        progressBar.getIndeterminateDrawable().setColorFilter(getResources().getColor(R.color.colorAccent), PorterDuff.Mode.MULTIPLY);
        progressBar.setVisibility(View.VISIBLE);
        switch (calendar.get(Calendar.DAY_OF_WEEK))
        {
            case 2:
                btnMonday.setBackground(getResources().getDrawable(R.drawable.button_day_selected));
                tvDate.setText("LUNES, " + Integer.toString(calendar.get(Calendar.DAY_OF_MONTH)) + " DE " + NameMonth(calendar.get(Calendar.MONTH)) + " DE " + Integer.toString(calendar.get(Calendar.YEAR)));
                break;
            case 3:
                btnTuesday.setBackground(getResources().getDrawable(R.drawable.button_day_selected));
                tvDate.setText("MARTES, " + Integer.toString(calendar.get(Calendar.DAY_OF_MONTH)) + " DE " + NameMonth(calendar.get(Calendar.MONTH)) + " DE " + Integer.toString(calendar.get(Calendar.YEAR)));
                break;
            case 4:
                btnWednesday.setBackground(getResources().getDrawable(R.drawable.button_day_selected));
                tvDate.setText("MIÉRCOLES, " + Integer.toString(calendar.get(Calendar.DAY_OF_MONTH)) + " DE " + NameMonth(calendar.get(Calendar.MONTH)) + " DE " + Integer.toString(calendar.get(Calendar.YEAR)));
                break;
            case 5:
                btnThursday.setBackground(getResources().getDrawable(R.drawable.button_day_selected));
                tvDate.setText("JUEVES, " + Integer.toString(calendar.get(Calendar.DAY_OF_MONTH)) + " DE " + NameMonth(calendar.get(Calendar.MONTH)) + " DE " + Integer.toString(calendar.get(Calendar.YEAR)));
                break;
            case 6:
                btnFriday.setBackground(getResources().getDrawable(R.drawable.button_day_selected));
                tvDate.setText("VIERNES, " + Integer.toString(calendar.get(Calendar.DAY_OF_MONTH)) + " DE " + NameMonth(calendar.get(Calendar.MONTH)) + " DE " + Integer.toString(calendar.get(Calendar.YEAR)));
                break;
            case 7:
                btnSaturday.setBackground(getResources().getDrawable(R.drawable.button_day_selected));
                tvDate.setText("SÁBADO, " + Integer.toString(calendar.get(Calendar.DAY_OF_MONTH)) + " DE " + NameMonth(calendar.get(Calendar.MONTH)) + " DE " + Integer.toString(calendar.get(Calendar.YEAR)));
                break;
            case 1:
                btnSunday.setBackground(getResources().getDrawable(R.drawable.button_day_selected));
                tvDate.setText("DOMINGO, " + Integer.toString(calendar.get(Calendar.DAY_OF_MONTH)) + " DE " + NameMonth(calendar.get(Calendar.MONTH)) + " DE " + Integer.toString(calendar.get(Calendar.YEAR)));
                break;
        }
        LoadActivities loadActivities = new LoadActivities();
        loadActivities.execute();
        return view;
    }

    private String NameMonth(int month)
    {
        switch (month)
        {
            case 0:
                return "ENERO";
            case 1:
                return "FEBRERO";
            case 2:
                return "MARZO";
            case 3:
                return "ABRIL";
            case 4:
                return "MAYO";
            case 5:
                return "JUNIO";
            case 6:
                return "JULIO";
            case 7:
                return "AGOSTO";
            case 8:
                return "SEPTIEMBRE";
            case 9:
                return "OCTUBRE";
            case 10:
                return "NOVIEMBRE";
            default:
                return "DICIEMBRE";
        }
    }
    private class LoadActivities extends AsyncTask<Void, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Void... param) {
            try {
                String NAMESPACE = "urn:DadTime";
                String URL = "http://www.corporacionsmartest.com/DadTimeWebServices/wsDadTime.php";
                String SOAP_ACTION = "urn:DadTime#GetActivities";
                String METHOD_NAME = "GetActivities";
                SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
                request.addProperty("minimumAge", 1);
                SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapSerializationEnvelope.VER11);
                envelope.dotNet = true;
                envelope.setOutputSoapObject(request);
                HttpTransportSE httpTransport = new HttpTransportSE(URL);
                httpTransport.call(SOAP_ACTION, envelope);
                SoapObject response = (SoapObject) envelope.bodyIn;
                Vector<?> responseVector = (Vector<?>) response.getProperty(0);
                entertainments = new ArrayList<Entertainment>();
                for (int i = 0; i < responseVector.size(); i++)
                {
                    SoapObject respond = (SoapObject) responseVector.get(i);
                    Entertainment entertainment = new Entertainment();
                    entertainment.setIdActivity(Integer.parseInt(respond.getProperty("idActivity").toString()));
                    entertainment.setTitle(respond.getProperty("title").toString());
                    entertainment.setCompany(respond.getProperty("company").toString());
                    entertainment.setCategory(respond.getProperty("category").toString());
                    entertainment.setDay(respond.getProperty("day").toString());
                    entertainment.setSchedule(respond.getProperty("schedule").toString());
                    entertainment.setPrice(respond.getProperty("price").toString());
                    entertainment.setDescription(respond.getProperty("description").toString());
                    entertainment.setMinimumAge(Integer.parseInt(respond.getProperty("minimumAge").toString()));
                    entertainment.setImage(BitmapFactory.decodeStream((InputStream) new URL(respond.getProperty("image").toString()).getContent()));
                    entertainments.add(entertainment);
                    System.out.println(entertainment);
                }
                return true;
            } catch (Exception e)
            {
                return false;
            }
        }

        @Override
        protected void onPostExecute(Boolean response) {
            progressBar.setVisibility(View.GONE);
            if (response)
            {
                tlDays.setVisibility(View.VISIBLE);
                tvDate.setVisibility(View.VISIBLE);
                AdapterEntertainments();
            }
            else
            {
                Toast.makeText(getActivity(), "Ocurrió un error", Toast.LENGTH_LONG).show();
            }
        }
    }

    private void AdapterEntertainments()
    {
        entertainmentsSpecificDay = new ArrayList<Entertainment>();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        for (int i = 0; i < entertainments.size(); i++)
        {
            if (entertainments.get(i).getDay().equalsIgnoreCase(simpleDateFormat.format(calendar.getTime())))
            {
                entertainmentsSpecificDay.add(entertainments.get(i));
            }
        }
        lvEntertainments.setAdapter(new CustomAdapterEntertainment(getActivity(), entertainmentsSpecificDay));
        lvEntertainments.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), EntertainmentActivity.class);
                intent.putExtra("Entertainment", entertainmentsSpecificDay.get(position));
                startActivity(intent);
            }
        });
    }

}