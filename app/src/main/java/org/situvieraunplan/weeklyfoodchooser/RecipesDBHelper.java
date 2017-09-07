package org.situvieraunplan.weeklyfoodchooser;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class RecipesDBHelper extends SQLiteOpenHelper {
    private static int version = 2;
    private static String name = "WFC_DB";
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

        db.execSQL("CREATE TABLE Recipe_Ingredients (");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
