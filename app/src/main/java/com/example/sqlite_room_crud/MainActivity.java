package com.example.sqlite_room_crud;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.Toolbar;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;

import java.util.List;

public class MainActivity extends AppCompatActivity implements UpdateDialogue.updateDialogueListener,DeleteDialogue.deleteDialogueListener {

    private Toolbar toolbar;
    private AppCompatEditText nameEditText,markEditText,genderEditText;
    private MaterialButton addButton,displayButton,displayListViewButton,displayRecycleViewButton,updateButton,deleteButton;
    StudentDatabase studentDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findSection();
        setUpDB();
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String name= nameEditText.getEditableText().toString();
                final String mark= markEditText.getEditableText().toString();
                final String gender= genderEditText.getEditableText().toString();
                if(name.equals("") || mark.equals("") || gender.equals("")){
                    Toast.makeText(MainActivity.this, "Filled all TextField", Toast.LENGTH_SHORT).show();
                }
                else {
                    insertData(name,mark,gender);
                }

            }
        });

        displayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                List<Student> studentList= studentDatabase.dao().showData();
                StringBuffer stringBuffer= new StringBuffer();
                if(studentList.isEmpty()){

                    showData("Error","No Data Found");
                    return;
                }
                else {
                    for(int i=0;i<studentList.size();i++){

                       stringBuffer.append("ID: "+studentList.get(i).getStudentId()+"\n");
                        stringBuffer.append("Name: "+studentList.get(i).getStudentName()+"\n");
                        stringBuffer.append("Mark: "+studentList.get(i).getStudentMark()+"\n");
                       stringBuffer.append("Gender: "+studentList.get(i).getStudentGender()+"\n\n");

                    }
                    showData("Student List",stringBuffer.toString());

                }

            }
        });

        displayListViewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent= new Intent(MainActivity.this,ShowActivity.class);
                startActivity(intent);
            }
        });

        displayRecycleViewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(MainActivity.this,RecycleActivity.class);
                startActivity(intent);

            }
        });
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openUpdateDialogue();
            }
        });
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDeleteDialogue();
            }
        });
    }

    private void openDeleteDialogue() {
        DeleteDialogue deleteDialogue= new DeleteDialogue();
        deleteDialogue.show(getSupportFragmentManager(),"delete dialogue");

    }

    private void openUpdateDialogue() {

        UpdateDialogue updateDialogue= new UpdateDialogue();
        updateDialogue.show(getSupportFragmentManager(),"update dialogue");
    }

    private void showData(String title, String data) {

        AlertDialog.Builder builder= new AlertDialog.Builder(this);
        builder.setTitle(title);
        builder.setMessage(data);
        builder.setCancelable(true);
        builder.show();
    }


    private void insertData(String name, String mark, String gender) {

        Student student= new Student(name,mark,gender);
       long resultId =  studentDatabase.dao().insertData(student);
       if(resultId==-1){
           Toast.makeText(MainActivity.this, "Inserted Failed", Toast.LENGTH_SHORT).show();
       }
       else {
           Toast.makeText(MainActivity.this, "Inserted successfully", Toast.LENGTH_SHORT).show();
           nameEditText.getEditableText().clear();
           markEditText.getEditableText().clear();
           genderEditText.getEditableText().clear();
       }

    }

    private void findSection() {
        toolbar= findViewById(R.id.mainActivityToolbarId);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Room SQLite");

        //find button and editText
        nameEditText= findViewById(R.id.nameId);
        markEditText= findViewById(R.id.markId);
        genderEditText= findViewById(R.id.genderId);
        addButton= findViewById(R.id.addButtonId);
        displayButton= findViewById(R.id.displayButtonId);
        updateButton= findViewById(R.id.updateButtonId);
        displayListViewButton= findViewById(R.id.displayListViewButtonId);
        displayRecycleViewButton= findViewById(R.id.displayRecycleViewButtonId);
        deleteButton= findViewById(R.id.deleteButtonId);

    }
    private void setUpDB(){
        studentDatabase= Room.databaseBuilder(MainActivity.this,StudentDatabase.class,"student_database")
                .allowMainThreadQueries().build();
    }

    @Override
    public void updateText(String id, String name, String mark, String gender) {
        //Toast.makeText(MainActivity.this, ""+id+"\n"+name+"\n"+mark+"\n"+gender, Toast.LENGTH_SHORT).show();

        int isUpdate= studentDatabase.dao().updateData(name,mark,gender,Integer.parseInt(id));
        if(isUpdate>0){
            Toast.makeText(MainActivity.this, "Updated successfully", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(MainActivity.this, "Updated Failed", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void deleteText(String id) {
        Toast.makeText(this, ""+id, Toast.LENGTH_SHORT).show();

        int value= studentDatabase.dao().deleteData(Integer.parseInt(id));
        if(value>0){
            Toast.makeText(this, "Delete Successfully", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(this, "Delete Failed", Toast.LENGTH_SHORT).show();
        }

    }
}
