package com.example.mhealthapp;

import android.content.Context;

public class DbFoodInsert {

    Context ctx;

    public DbFoodInsert(Context ctx) {
        this.ctx = ctx;
    }

    public void setupinsertfood(String data){

        DBAdapter db = new DBAdapter(ctx);
        db.open();
        db.insertRecord("food","_id, food_title,food_cal,food_text",data);
        db.close();
    }

    public void insertAllfood(){

        setupinsertfood("NULL, 'Egg, whole cooked','155','Rich in Protein! Better for Low Blood Pressure , Anaemia'");
        setupinsertfood("NULL, 'Rice','130','High Carbohydrate'");
        setupinsertfood("NULL, 'Mashed Potatoes','88','Gives Energy!'");
        setupinsertfood("NULL, 'Noodles','138','Gives Energy!'");
        setupinsertfood("NULL, 'Pasta','131','Gives Energy!'");
        setupinsertfood("NULL, 'Oatmeal Porridge','389.1','High Calories'");
        setupinsertfood("NULL, 'Bread','265','Rich in Carbohydrate'");

        setupinsertfood("NULL, 'Cooked Chicken','239','Rich in Protein'");
        setupinsertfood("NULL, 'Cooked Lamb','294','Eat Occassionally!! Bad for Heart and Diabetic Patient'");
        setupinsertfood("NULL, 'Cooked Beef','250','Eat Occassionally!! Bad for Heart and Diabetic Patient'");
        setupinsertfood("NULL, 'Cooked Mutton','294','Eat Occassionally!! Bad for Heart and Diabetic Patient'");

        setupinsertfood("NULL, 'Chicken Salad','48','Healthy Food!'");
        setupinsertfood("NULL, 'Cooked Vegetables','48.18','Rich in Fibre'");
        setupinsertfood("NULL, 'Cooked or Boiled Beans(lentils,soybean)','116','Rich in Protein! Good for Vegetarian '");
        setupinsertfood("NULL, 'Vegetable Soup ','159','Healthy Food'");
        setupinsertfood("NULL, 'Mushroom','22','Rich in Protein! Good for Vegetarian'");
        setupinsertfood("NULL, 'Fruit Salad','50','Healthy Food! Good for Skin'");
        setupinsertfood("NULL, 'Walnuts','654','Reduce Risk of Heart Disease '");
        setupinsertfood("NULL, 'Tofu','76','Rich in Protein ! Good for Vegans'");
        setupinsertfood("NULL, 'Barley','354','Adds Benefits to Heart,Blood Pressure'");
        setupinsertfood("NULL, 'Leafy Vegetables ','23','Very Healthy! Adds Fibre'");


        setupinsertfood("NULL, 'Salmon','208','Rich in Protein! Good For Heart Diseased Patients'");
        setupinsertfood("NULL, 'Sea Food','99.7','Healthy Food!'");

        setupinsertfood("NULL, 'Butter','717','Bad for Heart Disease Patient'");
        setupinsertfood("NULL, 'Dark Chocolate','546','Good for Heart Disease Patients '");
        setupinsertfood("NULL, 'Milk','42','Completes Balanced Diet'");
        setupinsertfood("NULL, 'Fat-Free Yoghurt','61','Healthy! Effective for Diabetic patient'");
        setupinsertfood("NULL, 'Cheese','402','High Calories! Bad for Heart and Diabetic Patient'");
        setupinsertfood("NULL, 'Cookie','502','Try to eat less'");
        setupinsertfood("NULL, 'Dairy Products','46','Eat Carefully ! Some people are intolerant of it'");

        setupinsertfood("NULL, 'French Fries','312','Eat Occasionally'");
        setupinsertfood("NULL, 'Sausage','301','Eat Occasionally'");
        setupinsertfood("NULL, 'Soda','41','Avoid!!'");



    }

}

