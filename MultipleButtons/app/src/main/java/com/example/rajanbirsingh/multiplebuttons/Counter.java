package com.example.rajanbirsingh.multiplebuttons;

public class Counter {
    private final int MIN_COUNTER = -2;
    private final int MAX_COUNTER = 3;
    private int value;

    public Counter(){   //default value
        value = 1;
    }

    public Counter(int value){
        setCounter(value);
    }

    /*
    Accessors and mutators
     */
    public int getCounter(){
        return this.value;
    }

    public void setCounter(int value){
        this.value = value;
    }

    public void increment(){
        this.value += 1;
    }

    public void decrement(){
        this.value -= 1;
    }

    public boolean hasReachedMaximum(){
        return this.value == MAX_COUNTER;
    }

    public boolean hasReachedMinimum(){
        return this.value == MIN_COUNTER;
    }

    public String toString(){
        return "Counter Value is: " + this.value;
    }
}
