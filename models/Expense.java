package models;

public class Expense {
    public Float amount;
    public SplitType splitType;

    public Expense(Float amount, SplitType splitType) {
        this.amount = amount;
        this.splitType = splitType;
    }

}

/*
 * 
 * 
 * * 3. Expense
 * a. amount - Integer
 * b. splitType - enum
 * 
 * 
 */