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

public class CoordinatesFragment extends Fragment {

    EditText xInput;
    EditText yInput;
    Timer timer = new Timer();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_city, container
                , false);
        xInput = root.findViewById(R.id.xinput);
        yInput = root.findViewById(R.id.yinput);
        xInput.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN && yInput.getText() != null) {
                    timer.cancel();
                    timer = new Timer();
                    timer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            HomeFragment.requestData(xInput.getText().toString(), yInput.getText().toString());
                        }
                    }, 5000);
                    if (keyCode == KeyEvent.KEYCODE_ENTER) {
                        HomeFragment.requestData(xInput.getText().toString(), yInput.getText().toString());
                        return true;
                    }}
                return false;
            }
        });
        yInput.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN && xInput.getText() != null) {
                    timer.cancel();
                    timer = new Timer();
                    timer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            HomeFragment.requestData(xInput.getText().toString(),
                                    yInput.getText().toString());
                        }
                    }, 5000);
                    if (keyCode == KeyEvent.KEYCODE_ENTER) {
                        HomeFragment.requestData(xInput.getText().toString(),
                                yInput.getText().toString());
                        return true;
                    }}
                return false;
            }
        });
        return root;
    }
}
