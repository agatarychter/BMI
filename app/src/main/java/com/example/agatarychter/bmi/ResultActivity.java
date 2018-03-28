package com.example.agatarychter.bmi;

import android.content.Context;
import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

public class ResultActivity extends AppCompatActivity {
    private TextView bmiToShow;
    private ConstraintLayout myLayout;
    private static final String CALCULATED_BMI_TEXT = "Calculated bmi";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        initViews();
        ActionBar actionBar =getSupportActionBar();
        if(actionBar!=null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getAndShowBmi();
    }
    private void initViews()
    {
        bmiToShow = findViewById(R.id.for_bmi);
        myLayout = findViewById(R.id.layout_id_res);
    }

    private void getAndShowBmi(){
        Intent intent = getIntent();
        if(intent==null)
            Toast.makeText(getApplicationContext(),R.string.no_data,Toast.LENGTH_SHORT).show();
        else
        {
            double calculatedBMI = intent.getDoubleExtra(CALCULATED_BMI_TEXT,0);
            String toShow;
            toShow = String.format("%2f",calculatedBMI);
            bmiToShow.setText(toShow);
            setMyLayoutColor(calculatedBMI);
        }
    }

    public static void start(Context context,double value) {
        Intent starter = new Intent(context, ResultActivity.class);
        starter.putExtra(CALCULATED_BMI_TEXT,value);
        context.startActivity(starter);
    }

    private void setMyLayoutColor(double calculatedBMI)
    {
        int color = BMICounter.returnColorDependingOnBMI(calculatedBMI);
        myLayout.setBackgroundColor(getResources().getColor(color));
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();
        if(id == android.R.id.home)
            this.finish();
        return super.onOptionsItemSelected(item);
    }
}
