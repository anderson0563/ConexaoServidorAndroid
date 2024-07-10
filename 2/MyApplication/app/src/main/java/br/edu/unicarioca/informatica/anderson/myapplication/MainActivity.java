package br.edu.unicarioca.informatica.anderson.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    private Button btnSend;
    private TextView txtStatus;
    private TextView txtValor;
    private TextView txtHostPort;
    private SocketTask st;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnSend = (Button) findViewById(R.id.btnSend);
        txtStatus = (TextView) findViewById(R.id.txtStatus);
        txtValor = (TextView) findViewById(R.id.txtValor);
        txtHostPort = (TextView) findViewById(R.id.txtHostPort);
        btnSend.setOnClickListener(btnConnectListener);
    }

    private View.OnClickListener btnConnectListener = new View.OnClickListener() {
        public void onClick(View v) {

            // Recupera host e porta
            String hostPort = txtHostPort.getText().toString();
            int idxHost = hostPort.indexOf(":");

            // Instancia a classe de conexão com socket


            st = new SocketTask("10.0.2.2", 8000, 5000) {
                @Override
                protected void onProgressUpdate(String... progress) {
                    SimpleDateFormat sdf = new SimpleDateFormat(
                            "dd/MM/yyyy HH:mm:ss");
                    // Recupera o retorno
                    txtStatus.setText(sdf.format(new Date()) + " - "
                            + progress[0]);
                }
            };

            st.execute(txtValor.getText() == null ? "" : txtValor.getText()
                    .toString()); // Envia os dado
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        st.cancel(true);
    }

}
