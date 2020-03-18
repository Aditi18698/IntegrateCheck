package com.example.integratecheck.ui.slideshow;

import android.Manifest;
import androidx.appcompat.app.AppCompatActivity;
import android.app.AlertDialog;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import com.example.integratecheck.DataBaseHelper;
import com.example.integratecheck.R;


public class SlideshowFragment extends Fragment {
    private float consumedFuel = 0.0f;
    private float travelledDistance = 0.0f;
    private float avg_trip = 0.0f;
    EditText odometerstart,odometerend, availableFuel1, availableFuel2;
    Button avg,reset;
    TextView ans;
    DataBaseHelper dataBaseHelper;

    private SlideshowViewModel slideshowViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dataBaseHelper=new DataBaseHelper(getActivity());

    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        slideshowViewModel =
                ViewModelProviders.of(this).get(SlideshowViewModel.class);
        View root = inflater.inflate(R.layout.fragment_slideshow, container, false);
        final TextView textView = root.findViewById(R.id.text_slideshow);
        slideshowViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });

        odometerstart = root.findViewById(R.id.etStart);
        odometerend = root.findViewById(R.id.etEnd);
        availableFuel1 = root.findViewById(R.id.etAvail);
        availableFuel2 = root.findViewById(R.id.etcon);
        avg = root.findViewById(R.id.btnAvg);

        ans = root.findViewById(R.id.ans);  //<<<<<<<<<--------------------------------------HE KON SET KARNAR!!!

        reset = root.findViewById(R.id.reset);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        avg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //CONVERTING INTO FLOAT
                final float temp_odometerend, temp_availableFuel2;

                final float temp_odometerstart = Float.valueOf(odometerstart.getText().toString());
                final float temp_availableFuel1 = Float.valueOf(availableFuel1.getText().toString());

                float temp_end,temp_con;


                if ((Float.valueOf(odometerend.getText().toString()) == 0) || (Float.valueOf(odometerend.getText().toString()) == null))
                    temp_end= 0;
                else
                    temp_end= Float.valueOf(odometerend.getText().toString());
                temp_odometerend=temp_end;


                if ((Float.valueOf(availableFuel2.getText().toString()) == 0) || (Float.valueOf(availableFuel2.getText().toString()) == null))
                    temp_con= 0;
                else
                    temp_con= Float.valueOf(availableFuel2.getText().toString());
                temp_availableFuel2=temp_con;

                //CALCULATION
                consumedFuel=temp_availableFuel1-temp_availableFuel2;
                travelledDistance= temp_odometerend-temp_odometerstart;
                avg_trip= travelledDistance/consumedFuel;

                //INSERTING INTO DATABASE
                boolean result = dataBaseHelper.addData(String.valueOf(consumedFuel),
                                                String.valueOf(travelledDistance),
                                                String.valueOf(avg_trip));

                if (result == true)
                    Toast.makeText(getActivity(),"Data Inserted Successfully",Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(getActivity(),"Data NotInserted",Toast.LENGTH_LONG).show();

                //RETRIVING FROM DATABASE
                Cursor cursor = dataBaseHelper.getData();
                if(cursor.getCount()==0) {
                    showmsg("Error..!! ","DB Empty");
                    return;
                }

                StringBuffer buffer=new StringBuffer();
                while (cursor.moveToNext())
                {
                    buffer.append("consumedFuel "+cursor.getString(0)+"\n");
                    buffer.append("travelledDistance "+cursor.getString(1)+"\n");
                    buffer.append("avg_trip "+cursor.getString(2)+"\n");
                    ans.setText(String.valueOf(cursor.getString(2)));
                }

                showmsg("Data",buffer.toString());

            }
        });

        //RESETING TRIP DATA
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor cursor=dataBaseHelper.reset("trip");

                if (cursor.getCount()==0){
                    Toast.makeText(getActivity(),"DATA RESET",Toast.LENGTH_LONG).show();
                }
                else {
                    Toast.makeText(getActivity(),"NOT DATA INSERTED",Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    //ALERT BOX FOR DISPLAYING DATA
    public void showmsg(String title,String msg){
        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        builder.setCancelable(true);
        builder.setMessage(title);
        builder.setMessage(msg);
        builder.show();

    }
    @Override
    public void onDestroy() {
        super.onDestroy();

    }
}