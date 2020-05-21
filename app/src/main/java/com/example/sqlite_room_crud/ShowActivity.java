package com.example.sqlite_room_crud;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.room.Room;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ShowActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private ListView listView;
    private StudentDatabase studentDatabase;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);

        toolbar= findViewById(R.id.showActivityToolbarId);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Student List");
        setUpDB();
        listView= findViewById(R.id.listViewId);
        loadData();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater= getMenuInflater();
        menuInflater.inflate(R.menu.menu_layout,menu);

        MenuItem menuItem= menu.findItem(R.id.searchId);
        SearchView searchView= (SearchView) menuItem.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                adapter.getFilter().filter(newText);
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    private void loadData() {
        ArrayList<String> arrayList= new ArrayList<>();
        //StringBuffer stringBuffer= new StringBuffer();
        List<Student> studentList= studentDatabase.dao().showData();
        if(studentList.isEmpty()){
            Toast.makeText(ShowActivity.this, "No Data", Toast.LENGTH_SHORT).show();
        }
        else {
            for(int i=0;i<studentList.size();i++){

                arrayList.add("ID: "+studentList.get(i).getStudentId()+"\n"+
                        "Name: "+studentList.get(i).getStudentName()+"\n"+
                        "Mark: "+studentList.get(i).getStudentMark()+"\n"+
                        "Gender: "+studentList.get(i).getStudentGender());
            }
        }

        adapter= new ArrayAdapter<String>(ShowActivity.this,R.layout.menu_item,R.id.menuId, arrayList);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //String select= parent.getItemAtPosition(position).toString();
                //String select= parent.getSelectedItemId(id);
                Toast.makeText(ShowActivity.this, ""+position+1, Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void setUpDB(){
        studentDatabase= Room.databaseBuilder(ShowActivity.this,StudentDatabase.class,"student_database")
                .allowMainThreadQueries().build();
    }
}
