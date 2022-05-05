package edu.sharif.ce.mas.weather;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioGroup;

public class HomeFragment extends Fragment {
    LinearLayout cityLayout;
    RadioGroup radioGroup;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_home, container
                , false);
        radioGroup = root.findViewById(R.id.radiogroup);
        cityLayout = root.findViewById(R.id.cityLayout);
        cityLayout.removeAllViews();
        cityLayout.addView(inflater.inflate(R.layout.fragment_coordinates, cityLayout,
                false));
        radioGroup.setOnCheckedChangeListener((radioGroup, i) -> {
                if (i == R.id.cityradiobutton) {
                    cityLayout.removeAllViews();
                    cityLayout.addView(inflater.inflate(R.layout.fragment_city, cityLayout,
                            false));}
                else if (i == R.id.xyradiobutton){
                    cityLayout.removeAllViews();
                    cityLayout.addView(inflater.inflate(R.layout.fragment_coordinates, cityLayout,
                            false));
                }
        });
        return root;
    }


    public static void requestData(String x, String y){
        System.out.println(x);
        System.out.println(y);
    }
    public static void getCoordinatesFromName(String name){
        System.out.println(name);
    }
}