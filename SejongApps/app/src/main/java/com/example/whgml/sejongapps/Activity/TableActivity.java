package com.example.whgml.sejongapps.Activity;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import com.example.whgml.sejongapps.R;
import com.example.whgml.sejongapps.sql.DatabaseHelper;

import de.codecrafters.tableview.TableHeaderAdapter;
import de.codecrafters.tableview.TableView;
import de.codecrafters.tableview.toolkit.SimpleTableDataAdapter;
import de.codecrafters.tableview.toolkit.SimpleTableHeaderAdapter;

public class TableActivity extends AppCompatActivity {

    private TableView<String[]> tableView;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.table_activity);
        initialize();
    }

    private void initialize()
    {
        dbHelper = new DatabaseHelper(this);
        tableView = (TableView<String[]>)findViewById(R.id.sqlTableView);
        tableView.setColumnCount(3);
        tableView.setHeaderBackgroundColor(Color.parseColor("#2ecc71"));
        String[] headers = {"Name", "Email", "Age"};
        tableView.setHeaderAdapter(new SimpleTableHeaderAdapter(this, headers));
        tableView.setDataAdapter(new SimpleTableDataAdapter(this, dbHelper.getAllUser()));
    }
}
