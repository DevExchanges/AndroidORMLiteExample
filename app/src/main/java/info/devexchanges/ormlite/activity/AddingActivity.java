package info.devexchanges.ormlite.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import info.devexchanges.ormlite.R;
import info.devexchanges.ormlite.database.DatabaseManager;
import info.devexchanges.ormlite.model.Cat;
import info.devexchanges.ormlite.model.Kitten;

public class AddingActivity extends AppCompatActivity {

    private View btnAddCat;
    private View btnAddKitten;
    private View btnOK;
    private Spinner spinner;
    private ViewGroup layoutAddCat;
    private ViewGroup layoutAddKitten;
    private ViewGroup layoutButtons;
    private EditText editCat;
    private EditText editKitten;
    private View btnCancel;
    private List<Cat> cats;
    private boolean havingCat = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adding);

        btnAddCat = findViewById(R.id.btn_add_cat);
        btnOK = findViewById(R.id.btn_ok);
        layoutAddKitten = (ViewGroup) findViewById(R.id.ll_kit);
        layoutAddCat = (ViewGroup) findViewById(R.id.ll_cat);
        layoutButtons = (ViewGroup) findViewById(R.id.ll_buttons);
        btnCancel = findViewById(R.id.btn_cancel);
        spinner = (Spinner) findViewById(R.id.spinner);
        btnAddKitten = findViewById(R.id.btn_add_kitten);
        editCat = (EditText) findViewById(R.id.txt_name);
        editKitten = (EditText) findViewById(R.id.txt_kitten_name);

        btnAddKitten.setOnClickListener(onAddKittenListener());
        btnAddCat.setOnClickListener(onAddCatListner());
        btnCancel.setOnClickListener(onCancelListener());
        btnOK.setOnClickListener(onConfirmListener());
    }

    private View.OnClickListener onConfirmListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (layoutAddCat.getVisibility() == View.VISIBLE) {
                    if (editCat.getText().toString().trim().equals("")) {
                        Toast.makeText(getBaseContext(), "Please input cat name", Toast.LENGTH_SHORT).show();
                    } else {
                        Cat cat = new Cat();
                        cat.setName(editCat.getText().toString().trim());

                        //save new object to db
                        DatabaseManager.getInstance().addCat(cat);
                    }
                } else if (layoutAddKitten.getVisibility() == View.VISIBLE) {
                    if (editKitten.getText().toString().trim().equals("")) {
                        Toast.makeText(getBaseContext(), "Please input kitten name", Toast.LENGTH_SHORT).show();
                    } else {
                        Kitten kitten = new Kitten();
                        Cat cat = (Cat) spinner.getSelectedItem();
                        kitten.setName(editKitten.getText().toString().trim());
                        kitten.setCat(cat);

                        //save to database
                        DatabaseManager.getInstance().newKittenAppend(kitten);
                        DatabaseManager.getInstance().updateKitten(kitten);
                    }
                }
                goneLayouts();
            }
        };
    }

    private View.OnClickListener onCancelListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goneLayouts();
            }
        };
    }

    private View.OnClickListener onAddCatListner() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goneLayouts();
                layoutAddCat.setVisibility(View.VISIBLE);
                layoutButtons.setVisibility(View.VISIBLE);
            }
        };
    }

    private View.OnClickListener onAddKittenListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!havingCat) {
                    Toast.makeText(getBaseContext(), "None Cat in DB, please add cat first", Toast.LENGTH_SHORT).show();
                } else {
                    goneLayouts();

                    cats = DatabaseManager.getInstance().getAllCats();
                    if (cats.size() == 0) {
                        havingCat = false;
                    } else {
                        //set spinner adapter
                        ArrayAdapter<Cat> adapter = new ArrayAdapter<>(AddingActivity.this,
                                android.R.layout.simple_dropdown_item_1line, cats);
                        spinner.setAdapter(adapter);
                    }
                    layoutAddKitten.setVisibility(View.VISIBLE);
                    layoutButtons.setVisibility(View.VISIBLE);
                }
            }
        };
    }

    private void goneLayouts() {
        layoutAddCat.setVisibility(View.GONE);
        layoutAddKitten.setVisibility(View.GONE);
        layoutButtons.setVisibility(View.GONE);
        editCat.setText("");
        editKitten.setText("");
    }
}
