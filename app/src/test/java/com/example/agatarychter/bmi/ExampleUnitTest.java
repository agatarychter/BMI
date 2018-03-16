package com.example.agatarychter.bmi;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void for_valid_data_BMI_is_returned_correctly_kgm() throws BMICounter.WrongDataException {
        BMICounter bmiCounter = new BMIForKgM(60,1.70);
        double bmi = bmiCounter.calculateBMI();
        assertEquals(20.76124567,bmi,0.001);
    }

    @Test
    public void for_valid_data_BMI_is_returned_correctly_lbi() throws BMICounter.WrongDataException {
        BMICounter bmiCounter = new BMIForLbInch(150,70);
        double bmi = bmiCounter.calculateBMI();
        assertEquals(21.5204,bmi,0.001);
    }

    @Test (expected = BMICounter.WrongBothInputsException.class)
    public void zero_mass_and_height_throw_exception_kgm() throws BMICounter.WrongDataException {
        BMICounter bmiCounter = new BMIForKgM(0,0);
        double bmi = bmiCounter.calculateBMI();
    }
    @Test(expected = BMICounter.WrongBothInputsException.class)
    public void too_low_mass_too_low_height_throw_exception_kgm() throws BMICounter.WrongDataException {
        BMICounter bmiCounter = new BMIForKgM(1,0.1);
        double bmi = bmiCounter.calculateBMI();
    }
    @Test(expected = BMICounter.WrongBothInputsException.class)
    public void zero_mass_zero_height_throw_exception_lbi() throws BMICounter.WrongDataException{
        BMICounter bmiCounter = new BMIForLbInch(0,0);
        double bmi = bmiCounter.calculateBMI();
    }

    @Test(expected = BMICounter.WrongMassException.class)
    public void zero_mass_ok_height_throw_exception_lbi() throws BMICounter.WrongDataException {
        BMICounter bmiCounter = new BMIForLbInch(0,144);
        double bmi = bmiCounter.calculateBMI();
    }

    @Test(expected = BMICounter.WrongMassException.class)
    public void zero_mass_ok_height_throw_exception_kgm() throws BMICounter.WrongDataException {
        BMICounter bmiCounter = new BMIForKgM(0,1.44);
        double bmi = bmiCounter.calculateBMI();
    }
    @Test(expected = BMICounter.WrongHeightException.class)
    public void ok_mass_zero_height_throw_exception_lbi() throws BMICounter.WrongDataException {
        BMICounter bmiCounter = new BMIForLbInch(550,0);
        double bmi = bmiCounter.calculateBMI();
    }

    @Test(expected = BMICounter.WrongHeightException.class)
    public void ok_mass_zero_height_throw_exception_kgm() throws BMICounter.WrongDataException {
        BMICounter bmiCounter = new BMIForKgM(55,0);
        double bmi = bmiCounter.calculateBMI();
    }


}



