package com.example.aluno.cih.View;

import android.content.Intent;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.aluno.cih.R;

public class activity_publicacao extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publicacao);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //Mostrar o botão
        getSupportActionBar().setHomeButtonEnabled(true);      //Ativar o botão
        getSupportActionBar().setTitle("Seu titulo aqui");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) { //Botão adicional na ToolBar
        switch (item.getItemId()) {
            case android.R.id.home:  //ID do seu botão (gerado automaticamente pelo android, usando como está, deve funcionar
                startActivity(new Intent(this, activity_principal.class));  //O efeito ao ser pressionado do botão (no caso abre a activity)
                ActivityCompat.finishAffinity(this);   //Método para matar a activity e não deixa-lá indexada na pilhagem
                break;
            default:break;
        }
        return true;
    }

    @Override
    public void onBackPressed(){ //Botão BACK padrão do android
        startActivity(new Intent(this, activity_principal.class)); //O efeito ao ser pressionado do botão (no caso abre a activity)
        ActivityCompat.finishAffinity(this);  //Método para matar a activity e não deixa-lá indexada na pilhagem
        return;
    }
}