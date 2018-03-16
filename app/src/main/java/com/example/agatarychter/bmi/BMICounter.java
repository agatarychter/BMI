package com.example.agatarychter.bmi;

/**
 * Created by Agata Rychter on 09.03.2018.
 */

public abstract class BMICounter {

    protected static class WrongDataException extends Exception{
        public WrongDataException(){
            super();
        }
    }
    protected static class WrongHeightException extends WrongDataException {
        public WrongHeightException() {
            super();
        }
    }
    protected static class WrongMassException extends WrongDataException{
        public WrongMassException(){
            super();
        }
    }
    protected static class WrongBothInputsException extends WrongDataException{
        public WrongBothInputsException() {
            super();
        }
    }
    private double mass;
    private double height;

    public BMICounter(double mass, double height)
    {
        this.mass = mass;
        this.height = height;
    }

    public double getMass() {
        return mass;
    }

    public double getHeight(){
        return height;
    }

    public void setMass(double mass) {
        this.mass = mass;
    }

    public void setHeight(double height){
        this.height = height;
    }

    public abstract double calculateBMI() throws WrongDataException;

}
