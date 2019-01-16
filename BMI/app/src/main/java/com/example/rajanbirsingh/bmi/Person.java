package com.example.rajanbirsingh.bmi;

public class Person {
    private String name;
    private double weight;
    private double height;

    public Person(String name, double weight, double height){
        this.name = name;
        this.weight = weight;
        this.height = height;
    }

    public String getName(){
        return new String(this.name);
    }
    public double getWeight(){
        return new Double (this.weight);
    }
    public double getHeight(){
        return new Double(this.height);
    }

    public double getBMI(){
        return weight / (height * height);
    }

    public void setWeight(double w){
        this.weight += w;
    }

    public String toString(){
        String result;
        if(name.equals("")){
            result = String.format("BMI is: %.2f", this.getBMI());
        }
        else{
            result = String.format("%s\'s BMI is: %.2f", this.name.trim(), this.getBMI());
        }
        return result;
    }

}
