package com.example.sqlite_room_crud;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

public class RecycleActivity extends AppCompatActivity {

    private Toolbar toolbar;

    private RecyclerView recyclerView;
    private UserAdapter adapter;
    private StudentDatabase studentDatabase;
   // ArrayList<Student> students;
   private  List<Student> students;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycle);
        toolbar= findViewById(R.id.recycleActivityToolbarId);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Student List");
        setUpDB();
        recyclerView= findViewById(R.id.recycleViewId);

        students= studentDatabase.dao().showData();

        /*students= new ArrayList<>();
        for(int i=0;i<10;i++){
            Student student= new Student("Khairul"+i,"24","Male");
            students.add(student);
        }*/

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter= new UserAdapter(students);
        recyclerView.setAdapter(adapter);

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

        return true;
    }

    private void setUpDB(){
        studentDatabase= Room.databaseBuilder(RecycleActivity.this,StudentDatabase.class,"student_database")
                .allowMainThreadQueries().build();
    }
}
