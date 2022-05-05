package edu.sharif.ce.mas.weather;

import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.text.InputType;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;

import java.util.Timer;
import java.util.TimerTask;

public class HomeFragment extends Fragment {
    LinearLayout cityLayout;
    RadioGroup radioGroup;
    Timer timer = new Timer();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_home, container
                , false);
        radioGroup = root.findViewById(R.id.radiogroup);
        cityLayout = root.findViewById(R.id.cityLayout);
        radioGroup.check(0);
        radioGroup.setOnCheckedChangeListener((radioGroup, i) -> {
                if (i == R.id.cityradiobutton) {
                    cityLayout.removeAllViews();
                    EditText cityInp = new EditText(getContext());
                    cityInp.setBackgroundResource(R.drawable.edit_text_bg);
                    RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams (
                            RelativeLayout.LayoutParams.MATCH_PARENT,
                            RelativeLayout.LayoutParams.WRAP_CONTENT);
                    cityInp.setLayoutParams(params);
                    cityInp.setHint("City Name");
                    cityInp.setGravity(Gravity.CENTER);
                    cityInp.setInputType(InputType.TYPE_CLASS_TEXT);
                    cityLayout.addView(cityInp);
                    cityInp.setOnKeyListener((v, keyCode, event) -> {
                        if (event.getAction() == KeyEvent.ACTION_DOWN) {
                            timer.cancel();
                            timer = new Timer();
                            timer.schedule(new TimerTask() {
                                @Override
                                public void run() {
                                    HomeFragment.getCoordinatesFromName(cityInp.getText().toString());
                                }
                            }, 5000);
                            if (keyCode == KeyEvent.KEYCODE_ENTER) {
                                HomeFragment.getCoordinatesFromName(cityInp.getText().toString());
                                return true;
                            }}
                        return false;
                    });
                }
                else if (i == R.id.xyradiobutton){
                    cityLayout.removeAllViews();
                    EditText xInp = new EditText(getContext());
                    xInp.setBackgroundResource(R.drawable.edit_text_bg);
                    RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams (
                            530, ViewGroup.LayoutParams.WRAP_CONTENT);
                    xInp.setLayoutParams(params);
                    xInp.setHint("X");
                    xInp.setInputType(InputType.TYPE_CLASS_NUMBER);
                    xInp.setGravity(Gravity.CENTER);
                    cityLayout.addView(xInp);
                    EditText yInp = new EditText(getContext());
                    yInp.setBackgroundResource(R.drawable.edit_text_bg);
                    yInp.setLayoutParams(params);
                    yInp.setHint("Y");
                    yInp.setInputType(InputType.TYPE_CLASS_NUMBER);
                    yInp.setGravity(Gravity.CENTER);
                    cityLayout.addView(yInp);
                    xInp.setOnKeyListener((v, keyCode, event) -> {
                        if (event.getAction() == KeyEvent.ACTION_DOWN && !yInp.getText().toString().equals("")) {
                            timer.cancel();
                            timer = new Timer();
                            timer.schedule(new TimerTask() {
                                @Override
                                public void run() {
                                    HomeFragment.requestData(xInp.getText().toString(), yInp.getText().toString());
                                }
                            }, 5000);
                            if (keyCode == KeyEvent.KEYCODE_ENTER) {
                                HomeFragment.requestData(xInp.getText().toString(), yInp.getText().toString());
                                return true;
                            }}
                        return false;
                    });
                    yInp.setOnKeyListener((v, keyCode, event) -> {
                        if (event.getAction() == KeyEvent.ACTION_DOWN &&
                                !xInp.getText().toString().equals("")) {
                            timer.cancel();
                            timer = new Timer();
                            timer.schedule(new TimerTask() {
                                @Override
                                public void run() {
                                    HomeFragment.requestData(xInp.getText().toString(),
                                            yInp.getText().toString());
                                }
                            }, 5000);
                            if (keyCode == KeyEvent.KEYCODE_ENTER) {
                                HomeFragment.requestData(xInp.getText().toString(),
                                        yInp.getText().toString());
                                return true;
                            }}
                        return false;
                    });

                }
        });
        return root;
    }


    public static void requestData(String x, String y){
        // TODO: request weather data from x y
        System.out.println(x);
        System.out.println(y);
    }
    public static void getCoordinatesFromName(String name){
        // TODO: request coordinates from city name and the requestData
        System.out.println(name);
    }
}