package com.example.hp.seccion_01;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.Image;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.security.cert.CertPathBuilderSpi;

public class ThirdActivity extends AppCompatActivity {

    private EditText textPhone;
    private EditText textNombre;
    private ImageButton imagenBtnPhone;
    private ImageButton imagenBtnWeb;
    private ImageButton imagenBtnCamera;

    private final int phone_code = 100;


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);

        textPhone = (EditText) findViewById(R.id.txtPhone);
        textNombre = (EditText) findViewById(R.id.txtNombre);

        imagenBtnPhone = (ImageButton) findViewById(R.id.imgBtnPhone);
        imagenBtnWeb = (ImageButton) findViewById(R.id.imgBtnNombre);
        imagenBtnCamera = (ImageButton) findViewById(R.id.imgBtnCamara);

        // Boton para la llamada
        imagenBtnPhone.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                String numeroTelefono = textPhone.getText().toString();

                if (numeroTelefono!=null && !numeroTelefono.isEmpty()) {
                    // comprobar version actual de androis que estamos corriendo
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                        // Comprobar si ha aceptado ,si no ha acepto, nunca le han preguntado

                        if(CheckPermission(Manifest.permission.CALL_PHONE)){
                            // Ha aceptado
                            Intent i = new Intent(Intent.ACTION_CALL,Uri.parse("tel:"+numeroTelefono));
                            if (ActivityCompat.checkSelfPermission(ThirdActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                                return ;
                            }
                                startActivity(i);
                        }
                        else{
                            // No ha aceptado
                            if(shouldShowRequestPermissionRationale(Manifest.permission.CALL_PHONE)){
                                // No se le ha preguntado aun

                                requestPermissions(new String[]{Manifest.permission.CALL_PHONE}, phone_code);

                            }else{
                                    // Ha denegado o es la primera vez que se le pregunta

                                if(shouldShowRequestPermissionRationale(Manifest.permission.CALL_PHONE)){
                                    // No se le ha preguntado aun
                                    requestPermissions(new String[]{Manifest.permission.CALL_PHONE}, phone_code);

                                }
                                else{
                                    // Ha denegado
                                    Toast.makeText(ThirdActivity.this,"Porfavor, habilita los permisos",Toast.LENGTH_LONG).show();
                                    Intent i = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                    i.addCategory(Intent.CATEGORY_DEFAULT);
                                    i.setData(Uri.parse("package:"+getPackageName()));
                                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    i.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                                    i.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
                                    startActivity(i);
                                }
                            }
                        }

                    } else {
                        OlderVersions(numeroTelefono);
                    }
                }else{
                    Toast.makeText(ThirdActivity.this,"Inserte Un Numero De Telefono",Toast.LENGTH_SHORT).show();
                }
            }

            private void OlderVersions(String numeroTelefono) {
                Intent llamarTelefono = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + numeroTelefono));
                if (CheckPermission(Manifest.permission.CALL_PHONE)) {
                    try {
                        startActivity(llamarTelefono);
                    } catch (SecurityException ex) {
                        Toast.makeText(ThirdActivity.this, "Exception The Segurity", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(ThirdActivity.this, "No tienes acceso", Toast.LENGTH_SHORT).show();
                }
            }
        });


        // Boton para el navegador WEB
        imagenBtnWeb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url  = textNombre.getText().toString();
                String email ="hernan_1203@hotmail.es";

                if(url != null && !url.isEmpty()){
                    Intent web = new Intent();
                    web.setAction(web.ACTION_VIEW);
                    web.setData(Uri.parse("http://"+url));

                    // Contacts
                    Intent intentContacts = new Intent();
                    intentContacts.setAction(intentContacts.ACTION_VIEW);
                    intentContacts.setData(Uri.parse("content://contacts/people"));

                    // Email
                    Intent intentMailTo = new Intent();
                    intentMailTo.setAction(intentMailTo.ACTION_SENDTO);
                    intentMailTo.setData(Uri.parse("mailto:"+email));

                    // Email Completo
                    Intent intentMail = new Intent(Intent.ACTION_VIEW,Uri.parse("mailto:"+email));

                    intentMail.setType("plain/text");
                    intentMail.putExtra(Intent.EXTRA_SUBJECT,"ESTE ES UN MENSAJE DE TEXTO");
                    intentMail.putExtra(Intent.EXTRA_TEXT,"hola");
                    intentMail.putExtra(Intent.EXTRA_EMAIL,new String[]{"hernand76@gmail.com"});

                    // Telefono 2 , sin permisos requeridos
                    Intent intentPhone = new Intent(Intent.ACTION_DIAL,Uri.parse("tel:66611222"));

                    startActivity(intentContacts);

                }
                else{
                    Toast.makeText(ThirdActivity.this,"La url esta vacio",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        // Estamos en el caso del telefono
        switch (requestCode) {
            case phone_code:
                String permiso = permissions[0];
                int result = grantResults[0];

                if (permiso.equals(Manifest.permission.CALL_PHONE)) {
                    // Comprobar si ha sido aceptado o denegado la peticion de permiso

                    if (result == PackageManager.PERMISSION_GRANTED) {
                        // Concedio su permiso
                        String numeroTelefono = textPhone.getText().toString();
                        Intent intentCall = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + numeroTelefono));
                        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                            return;
                        }
                        startActivity(intentCall);
                }else{
                    // No concedio su permiso
                    Toast.makeText(ThirdActivity.this,"No tienes acceso",Toast.LENGTH_SHORT).show();
                }
                }

                break;

            default:
                super.onRequestPermissionsResult(requestCode,permissions,grantResults);
                break;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }


    private boolean CheckPermission(String permiso)
    {
        int result = this.checkCallingOrSelfPermission(permiso);
        return result == PackageManager.PERMISSION_GRANTED;
    }
}
