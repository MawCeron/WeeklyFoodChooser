package org.situvieraunplan.weeklyfoodchooser;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class RecipesDBHelper extends SQLiteOpenHelper {
    private static int version = 2;
    private static String name = "WFC.db";
    private static SQLiteDatabase.CursorFactory factory = null;

    public RecipesDBHelper(Context context)
    {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE Recipe ("+
                    "recipe_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "recipe_name TEXT NOT NULL," +
                    "recipe_instructions TEXT," +
                    "recipe_status INTEGER NOT NULL)");

        db.execSQL("CREATE UNIQUE INDEX recipe_name ON Recipe(recipe_name ASC)");

        db.execSQL("INSERT INTO Recipe(recipe_name, recipe_status) VALUES('Example recipe',0)");

        db.execSQL("CREATE TABLE Recipe_Ingredients ("+
                "recipe_id INTEGER NOT NULL,"+
                "ingredient_id INTEGER NOT NULL)");

        db.execSQL("CREATE TABLE Ingredients ("+
                "ingredient_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "ingredient_name TEXT NOT NULL)");

        db.execSQL("CREATE TABLE Choosed_recipes (" +
                "sun INTEGER," +
                "mon INTEGER," +
                "tue INTEGER," +
                "wed INTEGER," +
                "thu INTEGER," +
                "fri INTEGER," +
                "sat INTEGER)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public ArrayList<String> getRecipesList(){
        ArrayList<String> arrayList = new ArrayList<String>();

        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor result = sqLiteDatabase.rawQuery("SELECT * FROM Recipe", null);
        result.moveToFirst();

        while(!result.isAfterLast()){
            arrayList.add(result.getString(result.getColumnIndex("recipe_name")));
            result.moveToNext();
        }

        return arrayList;

    }

    public ArrayList<String> getIngredientsList(){
        ArrayList<String> arrayList = new ArrayList<String>();

        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor result = sqLiteDatabase.rawQuery("SELECT * FROM Ingredients", null);
        result.moveToFirst();

        while(!result.isAfterLast()){
            arrayList.add(result.getString(result.getColumnIndex("ingredient_name")));
            result.moveToNext();
        }

        return arrayList;
    }

    public boolean addRecipe(String title, ArrayList ingredients, String instructions){
        return true;

    }

    public boolean addIngredient(String ingredient) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("ingredient_name",ingredient);
        db.insert("Ingredients",null,contentValues);
        return true;

    }
}
