package edu.sharif.ce.mas.weather;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.fragment.app.Fragment;

import java.util.Timer;
import java.util.TimerTask;

public class CityInputFragment extends Fragment {

    EditText cityName;
    Timer timer = new Timer();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_city, container
                , false);
        System.out.println(1);
        cityName = root.findViewById(R.id.cityName);
        cityName.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {

                    timer.cancel();
                    timer = new Timer();
                    timer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            HomeFragment.getCoordinatesFromName(cityName.getText().toString());
                        }
                    }, 5000);
                    if (keyCode == KeyEvent.KEYCODE_ENTER) {

                        HomeFragment.getCoordinatesFromName(cityName.getText().toString());
                        return true;
                }}
                return false;
            }
        });
        return root;
    }
}
