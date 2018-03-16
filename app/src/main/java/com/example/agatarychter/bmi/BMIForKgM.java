package com.example.agatarychter.bmi;

/**
 * Created by Agata Rychter on 09.03.2018.
 */

public class BMIForKgM extends BMICounter {
    private final double MIN_MASS = 10 ;
    private final double MAX_MASS = 1000;
    private final double MIN_HEIGHT = 0.3;
    private final double MAX_HEIGHT = 5 ;

    public BMIForKgM(double mass, double height) {
        super(mass, height);
    }

    public double calculateBMI() throws WrongDataException{
        if((getMass()<=MIN_MASS ||getMass()>=MAX_MASS) && (getHeight()<=MIN_HEIGHT || getHeight()>=MAX_HEIGHT))
            throw new WrongBothInputsException();
        else if(getMass()<=MIN_MASS ||getMass()>=MAX_MASS)
            throw new WrongMassException();
        else if(getHeight()<=MIN_HEIGHT || getHeight()>=MAX_HEIGHT)
            throw new WrongHeightException();
        else
            return getMass()/(getHeight()*getHeight());
    }


}
