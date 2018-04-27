package com.example.hp.seccion_01;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    private Button buton;
    private TextView nombre;
    private TextView password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nombre =(TextView)findViewById(R.id.nombre);

        password =(TextView)findViewById(R.id.password);

        buton = (Button) findViewById(R.id.btnIngresar);

        buton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View vista) {
               // Toast.makeText(MainActivity.this, "\tBienvenido  " + password.getText() + "\n Tu Ingreso Fue Exitoso !!", Toast.LENGTH_LONG).show()

                String nombre1,password1;

                nombre1 = nombre.getText().toString();

                password1 = password.getText().toString();

                if((nombre.getText().toString().equals("Hernan")) && (password.getText().toString().equals("Dario"))) {
                    // Acceder al segundo activity y mandarle un String
                    Intent intent =new Intent(MainActivity.this,SecondActivity.class);
                    intent.putExtra("nombre",nombre1);
                    startActivity(intent);
                    // Toast.makeText(MainActivity.this, "\tBienvenido  " + nombre.getText() + "\n Tu Ingreso Fue Exitoso !!", Toast.LENGTH_LONG).show();
                }
                else{
                    Toast.makeText(MainActivity.this,"El nombre o la contraseña estan errados",Toast.LENGTH_LONG).show();
                }
            }
        });
    }



    public void saludoHola(View vista) {
        int a = 21;
        Toast.makeText(MainActivity.this, "Hola Hernan Tienes \n  " + a + " años de edad", Toast.LENGTH_SHORT).show();
    }
}
