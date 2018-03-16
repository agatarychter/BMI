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


public class MainActivity extends AppCompatActivity {

    private EditText massText;
    private EditText heightText;
    private static final String TO_SAVE_MASS_TXT = "Saved mass";
    private static final String TO_SAVE_HEIGHT_TXT = "Saved height";
    private static final String TO_SAVE_SWITCH = "Saved switch";
    private static final String SHARED_PREFERENCES = "My preferences";
    private double mass;
    private double height;
    public static final String CALCULATED_BMI_TEXT = "Calculated bmi";
    private Switch switchBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        massText= (EditText)findViewById(R.id.mass);
        heightText =(EditText) findViewById(R.id.height);
        Button countButton = (Button)findViewById(R.id.count_btn);

        switchBtn =(Switch) findViewById(R.id.switch_btn);
        loadSavedInputs();
        switchBtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                clearData();
            }
        });
        countButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(areInputsEmpty())
                {
                    Toast.makeText(getApplicationContext(),getString(R.string.enter_data),Toast.LENGTH_SHORT).show();
                }
                else
                {
                    mass = Double.parseDouble(massText.getText().toString());
                    height = Double.parseDouble(heightText.getText().toString());
                    BMICounter bmiCounter;
                    if(switchBtn.isChecked())
                        bmiCounter= new BMIForLbInch(mass,height);
                    else
                        bmiCounter = new BMIForKgM(mass,height);
                    try
                    {
                        Intent intent= new Intent(MainActivity.this,ResultActivity.class);
                        double calculatedBMI = bmiCounter.calculateBMI();
                        intent.putExtra(CALCULATED_BMI_TEXT,calculatedBMI);
                        startActivity(intent);
                    }
                    catch (BMICounter.WrongMassException e)
                    {
                        Toast.makeText(getApplicationContext(),R.string.wrong_mass,Toast.LENGTH_SHORT).show();
                    }
                    catch (BMICounter.WrongHeightException e)
                    {
                        Toast.makeText(getApplicationContext(),R.string.wrong_height,Toast.LENGTH_SHORT).show();

                    }
                    catch(BMICounter.WrongBothInputsException e)
                    {
                        Toast.makeText(getApplicationContext(),R.string.wrong_data,Toast.LENGTH_SHORT).show();

                    }
                    catch (BMICounter.WrongDataException e)
                    {
                        Toast.makeText(getApplicationContext(),R.string.wrong_data,Toast.LENGTH_SHORT).show();
                    }
                }
            }}
            );

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
                startActivity(new Intent(getApplicationContext(),Author.class));
                return true;
            case R.id.save_icon:
                saveInputs();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
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
