package com.naridsara.myrestaurants;

public class Food {

   private int Food_ID;
   private String Food_Name;
   private String Food_NameUS;
   private String Food_Image;
   private int Food_Price;
   private String Food_Type_Name;

    public Food() {
    }

    public Food(int food_ID, String food_Name, String food_NameUS, String food_Image, int food_Price, String food_Type_Name) {
        Food_ID = food_ID;
        Food_Name = food_Name;
        Food_NameUS = food_NameUS;
        Food_Image = food_Image;
        Food_Price = food_Price;
        Food_Type_Name = food_Type_Name;
    }

    public int getFood_ID() {
        return Food_ID;
    }

    public void setFood_ID(int food_ID) {
        Food_ID = food_ID;
    }

    public String getFood_Name() {
        return Food_Name;
    }

    public void setFood_Name(String food_Name) {
        Food_Name = food_Name;
    }

    public String getFood_NameUS() {
        return Food_NameUS;
    }

    public void setFood_NameUS(String food_NameUS) {
        Food_NameUS = food_NameUS;
    }

    public String getFood_Image() {
        return Food_Image;
    }

    public void setFood_Image(String food_Image) {
        Food_Image = food_Image;
    }

    public int getFood_Price() {
        return Food_Price;
    }

    public void setFood_Price(int food_Price) {
        Food_Price = food_Price;
    }

    public String getFood_Type_Name() {
        return Food_Type_Name;
    }

    public void setFood_Type_Name(String food_Type_Name) {
        Food_Type_Name = food_Type_Name;
    }

}
