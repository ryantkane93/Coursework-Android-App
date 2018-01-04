package com.example.ryan.bcs421ryankanehwk5courseworkapp2;

import java.io.Serializable;

/**
 * Created by Ryan on 3/29/2016.
 */
public class CourseWorkItem implements Serializable{

    //Member Variables
    private String name, category;
    private int grade;

    //Default Constructor
    CourseWorkItem(){
        name = "";
        category = "";
        grade = 0;
    }

    //Parameterized Constructor
    CourseWorkItem(String newName, String newCategory, int newGrade){
        name=newName;
        category = newCategory;
        grade = newGrade;
    }

    //Getters
    public String getName(){
        return name;
    }

    public String getCategory(){
        return category;
    }

    public int getGrade(){
        return grade;
    }

    //Setters
    public void setName(String newName){
        name =newName;
    }

    public void setCategory(String newCategory)
    {
        category = newCategory;
    }

    public void setGrade(int newGrade)
    {
        grade = newGrade;
    }

    @Override //Override the toString() method so that only the name of the assignment is seen when the array is placed in the listView.
    public String toString() {
        return name;
    }
}
