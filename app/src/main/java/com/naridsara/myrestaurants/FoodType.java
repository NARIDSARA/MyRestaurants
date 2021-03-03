package com.naridsara.myrestaurants;

public class FoodType {

    private int Food_Type_ID;
    private String Food_Type_Name;

    public FoodType() {
    }

    public FoodType(int food_Type_ID, String food_Type_Name) {
        Food_Type_ID = food_Type_ID;
        Food_Type_Name = food_Type_Name;
    }

    public int getFood_Type_ID() {
        return Food_Type_ID;
    }

    public void setFood_Type_ID(int food_Type_ID) {
        Food_Type_ID = food_Type_ID;
    }

    public String getFood_Type_Name() {
        return Food_Type_Name;
    }

    public void setFood_Type_Name(String food_Type_Name) {
        Food_Type_Name = food_Type_Name;
    }

}
