package com.example.hp.seccion_01;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class SecondActivity extends AppCompatActivity {

    private TextView texto;

    private Button siguiente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        texto =(TextView) findViewById(R.id.mensajeBienvenida);

        // Tomar los datos del Intent

        Bundle datos = getIntent().getExtras();

        if(datos !=null && datos.getString("nombre") != null){
           String mensaje= datos.getString("nombre");

           texto.setText("Bienvenido "+mensaje+"!!");

            Toast.makeText(SecondActivity.this, "\tBienvenido  " + mensaje + "\n Tu Ingreso Fue Exitoso !!", Toast.LENGTH_LONG).show();
        }
        else{
            Toast.makeText(SecondActivity.this, "\tNombre vacio !!", Toast.LENGTH_LONG).show();
        }

        siguiente =(Button)findViewById(R.id.btnSiguiente);

        siguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View vista) {
                Intent tercerActivity=new Intent(SecondActivity.this,ThirdActivity.class);

                startActivity(tercerActivity);
            }
        });

    }
}
