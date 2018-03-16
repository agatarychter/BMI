package com.example.agatarychter.bmi;

/**
 * Created by Agata Rychter on 10.03.2018.
 */

public class BMIForLbInch extends BMICounter {
    private final double MULTIPLIER = 703;
    private final double MIN_MASS =22.05 ;
    private final double MAX_MASS = 2204;
    private final double MIN_HEIGHT = 11.81;
    private final double MAX_HEIGHT =196.85 ;

    public BMIForLbInch(double mass, double height) {
        super(mass, height);
    }

    public double calculateBMI() throws WrongDataException {
        if((getMass()<=MIN_MASS ||getMass()>=MAX_MASS) && (getHeight()<=MIN_HEIGHT || getHeight()>=MAX_HEIGHT))
            throw new WrongBothInputsException();
        else if(getMass()<=MIN_MASS ||getMass()>=MAX_MASS)
            throw new WrongMassException();
        else if(getHeight()<=MIN_HEIGHT || getHeight()>=MAX_HEIGHT)
            throw new WrongHeightException();
        else
            return getMass()/(getHeight()*getHeight())*MULTIPLIER;
    }
}
