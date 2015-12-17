package ec.edu.espol.integradora.dadtime;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class ProfileThirdActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_third);
    }

    /*public class enviarLocationAlWs extends AsyncTask<String, Void, Void>
    {
        @Override
        protected Void doInBackground(String ...param)
        {
            try
            {
                final String NAMESPACE = "http://www.webserviceX.NET/";
                final String URL = "http://www.webservicex.net/CurrencyConvertor.asmx";
                final String SOAP_ACTION = "http://www.webserviceX.NET/ConversionRate";
                final String METHOD_NAME = "ConversionRate";
                HttpTransportSE httpTransport = new HttpTransportSE(globalVariable.getURL());
                SoapObject request = new SoapObject(globalVariable.getNAMESPACE(),globalVariable.getMETHOD_NAME_INGRESAR_PUNTO());
                request.addProperty("idDispositivo",String.valueOf(globalVariable.getIdUsuarioMovil()));
                request.addProperty("latitud",param[0]);
                request.addProperty("longitud",param[1]);
                request.addProperty("fecha",param[2]);
                request.addProperty("hora",param[3]);
                SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapSerializationEnvelope.VER11);
                envelope.dotNet = true;
                envelope.setOutputSoapObject(request);
                httpTransport.call(globalVariable.getSOAP_ACTION_BASE()+globalVariable.getMETHOD_NAME_INGRESAR_PUNTO(), envelope);
                SoapObject response = (SoapObject) envelope.bodyIn;
                String respuestaWS = (String)response.getProperty(0);
                //Log.d(TAG, "5: "+respuestaWS);
            }
            catch (Exception e)
            {
                Log.d(TAG, "Error");
                Log.i("Respuesta", "excepcion");
                Log.i("Respuesta",e.toString());
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void v)
        {
            //Toast.makeText(context, "Registros ingresados en estado pasivo",Toast.LENGTH_LONG).show();
        }
    }*/
}
