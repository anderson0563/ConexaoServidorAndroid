package br.edu.unicarioca.informatica.anderson.androidwebservice;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

public class Principal extends AppCompatActivity {
    SoapPrimitive resultString;
    String getCel, resposta;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

    }

    public void onClick(View view) {
        EditText texto = (EditText) findViewById(R.id.texto);
        getCel = texto.getText().toString();
        AsyncCallWS task = new AsyncCallWS();
        task.execute();
    }


    private class AsyncCallWS extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            calculate();
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            Toast.makeText(Principal.this, resultString.toString(), Toast.LENGTH_LONG).show();
        }


    }

    public void calculate() {
        String SOAP_ACTION = "\"http://pacote/hello\"";
        String METHOD_NAME = "hello";
        String NAMESPACE = "http://pacote/";
        String URL = "http://10.0.2.2:8080/WebServiceAndroid/NovoWebService?wsdl";

        try {


            SoapObject Request = new SoapObject(NAMESPACE, METHOD_NAME);
            Request.addProperty("txt", getCel);

            SoapSerializationEnvelope soapEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            soapEnvelope.implicitTypes = true;
            soapEnvelope.setAddAdornments(false);
            soapEnvelope.dotNet = false;
            soapEnvelope.setOutputSoapObject(Request);


            HttpTransportSE transport = new HttpTransportSE(URL);

            transport.call(SOAP_ACTION, soapEnvelope);
            resultString = (SoapPrimitive) soapEnvelope.getResponse();

        } catch (Exception ex) {

            Log.e("Response ", "Error: " + ex.getMessage());
        }
    }
}
