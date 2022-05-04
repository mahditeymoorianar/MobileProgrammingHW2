package edu.sharif.ce.mas.weather;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {
    TextView cityNameTextView;
    LinearLayout cityLayout;
    boolean gettingCityName;
    RadioGroup radioGroup;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
//        cityLayout.setOnClickListener(
//        TODO : if clicked change TextView into appropriate edit text(s)
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_home, container
                , false);
        radioGroup = root.findViewById(R.id.radiogroup);
        cityLayout = root.findViewById(R.id.cityLayout);
        cityLayout.removeAllViews();
        cityLayout.addView(inflater.inflate(R.layout.fragment_coordinates, cityLayout, false));
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                    if (i == R.id.cityradiobutton) {
                        cityLayout.removeAllViews();
                        cityLayout.addView(inflater.inflate(R.layout.fragment_city, cityLayout, false));}
                    else if (i == R.id.xyradiobutton){
                        cityLayout.removeAllViews();
                        cityLayout.addView(inflater.inflate(R.layout.fragment_coordinates, cityLayout, false));
                    }
            }
        });
        return root;
    }

    /**
     * Called immediately after {@link #onCreateView(LayoutInflater, ViewGroup, Bundle)}
     * has returned, but before any saved state has been restored in to the view.
     * This gives subclasses a chance to initialize themselves once
     * they know their view hierarchy has been completely created.  The fragment's
     * view hierarchy is not however attached to its parent at this point.
     *
     * @param view               The View returned by {@link #onCreateView(LayoutInflater, ViewGroup, Bundle)}.
     * @param savedInstanceState If non-null, this fragment is being re-constructed
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {


        cityLayout = getView().findViewById(R.id.cityLayout);

        cityLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!gettingCityName) {
//            cityLayout.findViewById(R.id.theCityView).
                    EditText cityNameEditText = new EditText(getContext());
                    cityLayout.addView(cityNameEditText);
                    // TODO : ...
                } else {
                    EditText cityXEditText = new EditText(getContext());
//                    cityXEditText.setInputType();
                    // TODO : ...
                }
            }
        });



        super.onViewCreated(view, savedInstanceState);

    }
}