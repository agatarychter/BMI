package com.example.agatarychter.bmi;

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
    final double MIN_BMI = 18;
    final double MAX_BMI = 25;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        bmiToShow = findViewById(R.id.for_bmi);
        ActionBar actionBar =getSupportActionBar();
        if(actionBar!=null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        if(getIntent()==null) Toast.makeText(getApplicationContext(),R.string.no_data,Toast.LENGTH_SHORT).show();
        else
        {
            double calculatedBMI = getIntent().getDoubleExtra(MainActivity.CALCULATED_BMI_TEXT,0);
            ConstraintLayout myLayout = findViewById(R.id.layout_id_res);
            if(calculatedBMI<MIN_BMI)
            {
                myLayout.setBackgroundColor(getResources().getColor(R.color.blue));
            }
            else if(calculatedBMI>=MIN_BMI && calculatedBMI<=MAX_BMI)
            {
                myLayout.setBackgroundColor(getResources().getColor(R.color.green));
            }
            else
            {
                myLayout.setBackgroundColor(getResources().getColor(R.color.red));
            }
            bmiToShow.setText(Double.toString(calculatedBMI));
        }
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
