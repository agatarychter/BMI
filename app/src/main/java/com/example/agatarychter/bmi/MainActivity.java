package com.example.agatarychter.bmi;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import static java.security.AccessController.getContext;


public class MainActivity extends AppCompatActivity {

    private EditText massText;
    private EditText heightText;
    private static final String TO_SAVE_MASS_TXT = "Saved mass";
    private static final String TO_SAVE_HEIGHT_TXT = "Saved height";
    private static final String TO_SAVE_SWITCH = "Saved switch";
    private static final String SHARED_PREFERENCES = "My preferences";
    private double mass;
    private double height;
    private Switch switchBtn;
    private Button countButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        loadSavedInputs();
        initListeners();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.about_author:
                startActivity(new Intent(this,Author.class));
                return true;
            case R.id.save_icon:
                saveInputs();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void initViews()
    {
        massText= (EditText)findViewById(R.id.mass);
        heightText =(EditText) findViewById(R.id.height);
        countButton = (Button)findViewById(R.id.count_btn);
        switchBtn =(Switch) findViewById(R.id.switch_btn);
    }

    private void initListeners()
    {
        switchBtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                clearData();
            }
        });

        countButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onCountButtonClick();
            }}
        );

    }

    private void onCountButtonClick()
    {
        if(areInputsEmpty())
        {
            Toast.makeText(getApplicationContext(),getString(R.string.enter_data),Toast.LENGTH_SHORT).show();
        }
        else
        {
            boolean notParsebleString = false;
            try
            {
                mass = Double.parseDouble(massText.getText().toString());
                height = Double.parseDouble(heightText.getText().toString());
            }
            catch(NumberFormatException e)
            {
                notParsebleString = true;
            }
            if(notParsebleString)
            {
                Toast.makeText(getApplicationContext(),getString(R.string.wrong_data),Toast.LENGTH_SHORT).show();
            }
            else
            {
                BMICounter bmiCounter;
                if(switchBtn.isChecked())
                    bmiCounter= new BMIForLbInch(mass,height);
                else
                    bmiCounter = new BMIForKgM(mass,height);
                try
                {
                    double calculatedBMI = bmiCounter.calculateBMI();
                    ResultActivity.start(this,calculatedBMI);
                }

                catch (BMICounter.WrongDataException e)
                {
                    chooseException(e);
                }
            }
        }
    }

    private void chooseException(BMICounter.WrongDataException e) {
        if(e.getClass().equals(BMICounter.WrongMassException.class))
            Toast.makeText(getApplicationContext(),R.string.wrong_mass,Toast.LENGTH_SHORT).show();
        else if (e.getClass().equals(BMICounter.WrongHeightException.class))
            Toast.makeText(getApplicationContext(),R.string.wrong_height,Toast.LENGTH_SHORT).show();
        else if(e.getClass().equals(BMICounter.WrongBothInputsException.class))
            Toast.makeText(getApplicationContext(),R.string.wrong_data,Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(getApplicationContext(),R.string.wrong_data,Toast.LENGTH_SHORT).show();
    }

    private void saveInputs()
    {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFERENCES,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(TO_SAVE_MASS_TXT,massText.getText().toString());
        editor.putString(TO_SAVE_HEIGHT_TXT,heightText.getText().toString());
        editor.putBoolean(TO_SAVE_SWITCH, switchBtn.isChecked());
        Toast.makeText(getApplicationContext(),getString(R.string.save_info),Toast.LENGTH_SHORT).show();
        editor.apply();
    }

    private void loadSavedInputs()
    {
        SharedPreferences myPreferences = this.getSharedPreferences(SHARED_PREFERENCES,Context.MODE_PRIVATE);
        massText.setText(myPreferences.getString(TO_SAVE_MASS_TXT,""));
        heightText.setText(myPreferences.getString(TO_SAVE_HEIGHT_TXT,""));
        switchBtn.setChecked(myPreferences.getBoolean(TO_SAVE_SWITCH,false));
    }

    private boolean areInputsEmpty() {
        return massText.getText().length() == 0 || heightText.getText().length() == 0;
    }

    private void clearData(){
        massText.setText("");
        heightText.setText("");
    }
}
