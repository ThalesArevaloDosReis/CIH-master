package com.example.aluno.cih.View;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.aluno.cih.R;

// classe da tela inicial

public class activity_principal extends AppCompatActivity {
    public TextView mTextMessage;

    private Fragment fragment_publicacao;
    private Fragment fragment_nova;
    private Fragment fragment_conta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        fragment_publicacao = new fragment_publicacao();
        fragment_nova = new fragment_nova();
        fragment_conta = new fragment_conta();

        setFragment(fragment_publicacao);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    setFragment(fragment_publicacao);
                    return true;
                case R.id.navigation_profile:
                    setFragment(fragment_conta);
                    return true;
                case R.id.navigation_new:
                    setFragment(fragment_nova);
                    return true;
            }
            return false;
        }
    };

    private void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main_frame, fragment);
        fragmentTransaction.commit();
    }
}