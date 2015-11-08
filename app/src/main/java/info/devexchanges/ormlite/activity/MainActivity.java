package info.devexchanges.ormlite.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import info.devexchanges.ormlite.R;
import info.devexchanges.ormlite.adapter.ExpandableListAdapter;
import info.devexchanges.ormlite.database.DatabaseManager;
import info.devexchanges.ormlite.model.Cat;
import info.devexchanges.ormlite.model.Kitten;

public class MainActivity extends AppCompatActivity {

    private ProgressBar progressBar;
    private ExpandableListView listView;
    private TextView notice;
    private List<Cat> cats;
    private ExpandableListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DatabaseManager.init(this);

        notice = (TextView)findViewById(R.id.notice);
        listView = (ExpandableListView)findViewById(R.id.list_item);
        progressBar = (ProgressBar)findViewById(R.id.progress);

        cats = new ArrayList<>();
        //set data to views
        adapter = new ExpandableListAdapter(this, cats);
        listView.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("Main", "resume");
        getDataFromDB();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.add) {
            Intent i = new Intent(this, AddingActivity.class);
            startActivity(i);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void getDataFromDB() {
        if (cats != null) cats.clear();

        //get all data to Lists
        ArrayList<Cat> catArrayList = DatabaseManager.getInstance().getAllCats();
        for (int i = 0; i < catArrayList.size(); i++) {
            cats.add(catArrayList.get(i));
        }

        if (cats.size() == 0) {
            //no data in database
            listView.setVisibility(View.GONE);
            notice.setText("Database is Empty");
            notice.setVisibility(View.VISIBLE);
        } else {
            adapter.notifyDataSetChanged();
        }
    }
}
