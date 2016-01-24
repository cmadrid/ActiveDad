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
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
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

    SharedPreferences preferenceSettings;
    SharedPreferences.Editor preferenceEditor;
    ProgressBar progressBar;
    ListView lvEntertainments;
    ArrayList<Entertainment> entertainments;

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
        preferenceSettings = getActivity().getSharedPreferences("myPreferences", Context.MODE_PRIVATE);
        preferenceEditor = preferenceSettings.edit();
        preferenceEditor.putString("email", "modificado@email.com");
        preferenceEditor.putString("nombre", "Prueba");
        preferenceEditor.commit();
        progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
        progressBar.getIndeterminateDrawable().setColorFilter(getResources().getColor(R.color.colorAccent), PorterDuff.Mode.MULTIPLY);
        progressBar.setVisibility(View.VISIBLE);
        lvEntertainments = (ListView) view.findViewById(R.id.lvEntertainments);
        LoadActivities loadActivities = new LoadActivities();
        loadActivities.execute();
        return view;
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
                    entertainment.setSchedule(respond.getProperty("schedule").toString());
                    entertainment.setPrice(respond.getProperty("price").toString());
                    entertainment.setDescription(respond.getProperty("description").toString());
                    entertainment.setMinimumAge(Integer.parseInt(respond.getProperty("minimumAge").toString()));
                    entertainment.setImage(BitmapFactory.decodeStream((InputStream) new URL(respond.getProperty("image").toString()).getContent()));
                    entertainments.add(entertainment);
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
                lvEntertainments.setAdapter(new CustomAdapterEntertainment(getActivity(), entertainments));
                lvEntertainments.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent intent = new Intent(getActivity(), EntertainmentActivity.class);
                        intent.putExtra("Entertainment", entertainments.get(position));
                        startActivity(intent);
                    }
                });
            }
            else
            {
                Toast.makeText(getActivity(), "Ocurrió un error", Toast.LENGTH_LONG).show();
            }
        }

    }

}