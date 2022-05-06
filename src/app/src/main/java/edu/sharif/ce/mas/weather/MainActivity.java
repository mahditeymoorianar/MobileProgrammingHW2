package edu.sharif.ce.mas.weather;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentContainerView;
import androidx.fragment.app.FragmentTransaction;
import android.os.Bundle;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity {

    FragmentContainerView fragment;
    ImageButton setting;
    ImageButton home;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        setting = findViewById(R.id.settingButton);
        home = findViewById(R.id.homeButton);
        fragment = findViewById(R.id.fragmentContainerView);


        setting.setOnClickListener(view -> {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.fragmentContainerView, new SettingsFragment());
            transaction.addToBackStack(null);
            transaction.commit();
        });
        home.setOnClickListener(view -> {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.fragmentContainerView, new HomeFragment());
            transaction.addToBackStack(null);
            transaction.commit();
        });

    }
}