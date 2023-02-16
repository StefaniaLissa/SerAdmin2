package com.example.seradmin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

public class NuevaContraseña extends AppCompatActivity {

    private static final int CLAVE_CONTRASEÑA_CAMBIADA = 55;
    EditText nuevaContraseña, repetirContraseña;
    TextView mensaje;
    ImageView imagen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nueva_contrasenya);

        nuevaContraseña = findViewById(R.id.nuevaContraseña);
        repetirContraseña = findViewById(R.id.repetirContraseña);
        mensaje = findViewById(R.id.mensaje);

        repetirContraseña.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void afterTextChanged(Editable s) {

                if(repetirContraseña.getText().toString().equals(nuevaContraseña.getText().toString())){

                    lanzarLogin();

                } else {
                    AlphaAnimation animation = new AlphaAnimation(0, 1);
                    animation.setDuration(4000);
                    mensaje.startAnimation(animation);
                    mensaje.setVisibility(View.VISIBLE);
                    AlphaAnimation animation2 = new AlphaAnimation(1, 0);
                    animation2.setDuration(4000);
                    mensaje.startAnimation(animation2);
                    mensaje.setVisibility(View.INVISIBLE);
                }
            }
        });

    }

    public void lanzarLogin() {
        Intent intent = new Intent(NuevaContraseña.this, Login.class);
        intent.putExtra("CONTRASEÑA_CAMBIADA",CLAVE_CONTRASEÑA_CAMBIADA);
        startActivity(intent);
    }
}