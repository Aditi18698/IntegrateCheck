package com.example.integratecheck.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.integratecheck.CustomOnItemSelectedListener;
import com.example.integratecheck.DataBaseHelper1;
import com.example.integratecheck.CustomOnItemSelectedListener;
import com.example.integratecheck.DataBaseHelper1;
import com.example.integratecheck.R;
import com.example.integratecheck.ui.home.HomeViewModel;

public class HomeFragment extends Fragment {
    EditText user_name,bt_name;
    Button signup;
    Spinner spinner1;
    DataBaseHelper1 dataBaseHelper;

    private HomeViewModel homeViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dataBaseHelper=new DataBaseHelper1(getActivity());



    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        final TextView textView = root.findViewById(R.id.text_home);
        homeViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });


        user_name = root.findViewById(R.id.etName);
        bt_name = root.findViewById(R.id.etBT_Name);
        signup = root.findViewById(R.id.btnSignUp);
        spinner1 = root.findViewById(R.id.spinner);

        addListenerOnButton();
        addListenerOnSpinnerItemSelection();

        return root;

    }
    public void addListenerOnSpinnerItemSelection ()
    {
        spinner1.setOnItemSelectedListener(new CustomOnItemSelectedListener());
    }

    // get the selected dropdown list value
    public void addListenerOnButton ()
    {

        signup.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Toast.makeText(getActivity(), "Yes selected", Toast.LENGTH_LONG).show();

            }

        });
    }
}