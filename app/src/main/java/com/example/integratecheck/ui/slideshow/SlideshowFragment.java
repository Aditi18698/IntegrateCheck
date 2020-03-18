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
    EditText start,end, avail, consumed;
    Button avg;
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

        start = root.findViewById(R.id.etStart);
        end = root.findViewById(R.id.etEnd);
        avail = root.findViewById(R.id.etAvail);
        consumed = root.findViewById(R.id.etcon);
        avg = root.findViewById(R.id.btnAvg);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        avg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(),"Entered Onclick 1",Toast.LENGTH_LONG).show();

                final float start1 = Float.valueOf(start.getText().toString());
                final float end1, consumed1;
                final float avail1 = Float.valueOf(avail.getText().toString());
                float temp_end,temp_con;
                Toast.makeText(getActivity(),"Entered Onclick",Toast.LENGTH_LONG).show();


                if ((Float.valueOf(end.getText().toString()) == 0) || (Float.valueOf(end.getText().toString()) == null))
                    temp_end= 0;
                else
                    temp_end= Float.valueOf(end.getText().toString());

                end1=temp_end;

                if ((Float.valueOf(consumed.getText().toString()) == 0) || (Float.valueOf(consumed.getText().toString()) == null))
                    temp_con= 0;
                else
                    temp_con= Float.valueOf(consumed.getText().toString());
                consumed1=temp_con;

                consumedFuel=avail1-consumed1;
                travelledDistance= end1-start1;
                avg_trip= travelledDistance/consumedFuel;
                boolean result = dataBaseHelper.addData(String.valueOf(consumedFuel),String.valueOf(travelledDistance),String.valueOf(avg_trip));

                if (result == true)
                    Toast.makeText(getActivity(),"Data Inserted Successfully",Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(getActivity(),"Data NotInserted",Toast.LENGTH_LONG).show();
                Cursor cursor = dataBaseHelper.getData();
                if(cursor.getCount()==0) {
                    showmsg("Error..!! ","DB Empty");
                    return;
                }

                while (cursor.moveToNext())
                {
                    ans.setText(String.valueOf(cursor.getString(2)));
                }

            }
        });
    }
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