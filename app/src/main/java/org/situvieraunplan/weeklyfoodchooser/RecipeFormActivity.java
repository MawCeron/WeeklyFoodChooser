package org.situvieraunplan.weeklyfoodchooser;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

public class RecipeFormActivity extends AppCompatActivity {
    
    private EditText titleEdit, instructionsEdit;
    private ListView ingredientList;
    private Button add_btn,del_btn;
    RecipesDBHelper mydb;
    ArrayList<String> listItems=new ArrayList<String>();
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_form);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,listItems);

        ingredientList = (ListView) findViewById(R.id.ingredients_lv);
        ingredientList.setAdapter(adapter);
        add_btn = (Button) findViewById(R.id.add_ingredient);
        del_btn = (Button) findViewById(R.id.remove_ingredient);

        titleEdit = (EditText) findViewById(R.id.title_edit);

        instructionsEdit = (EditText) findViewById(R.id.instructions_edit);
        mydb = new RecipesDBHelper(this);

        add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ingredientDialog();
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = titleEdit.getText().toString();
                String instructions = instructionsEdit.getText().toString();
                ArrayList ingredients = new ArrayList();

                
                //mydb.addRecipe();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void ingredientDialog() {
        LayoutInflater inflater = LayoutInflater.from(this);
        View promptView = inflater.inflate(R.layout.new_ingredient,null);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(promptView);

        final EditText ingredient_edit = (EditText) promptView.findViewById(R.id.ing_edit);
        final Spinner ingredient_spin = (Spinner) promptView.findViewById(R.id.ing_spinner);
        final CheckBox checkbox = (CheckBox) promptView.findViewById(R.id.ing_check);

        ArrayList<String> currentIngredients = mydb.getIngredientsList();

        ArrayAdapter arrayAdapter = new ArrayAdapter(this,android.R.layout.simple_spinner_item,currentIngredients);
        ingredient_spin.setAdapter(arrayAdapter);

        checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(checkbox.isChecked()){
                    ingredient_edit.setEnabled(true);
                    ingredient_spin.setEnabled(false);
                } else {
                    ingredient_edit.setEnabled(false);
                    ingredient_spin.setEnabled(true);
                }
            }
        });

        builder
                .setCancelable(false)
                .setPositiveButton(R.string.ok_ingredient, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String ingredient;

                        if(checkbox.isChecked()){
                            ingredient = ingredient_edit.getText().toString();
                            mydb.addIngredient(ingredient);
                        } else {
                            ingredient = ingredient_spin.getSelectedItem().toString();
                        }

                        adapter.add(ingredient);
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

        final AlertDialog alert = builder.create();
        alert.show();
    }

}
