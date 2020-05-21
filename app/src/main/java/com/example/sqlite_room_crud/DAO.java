package com.example.sqlite_room_crud;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface DAO {

    @Insert
    public long insertData(Student student);

    @Query("SELECT * FROM Student")
    public List<Student> showData();

    @Query("UPDATE Student SET studentName= :name , studentMark= :mark , studentGender =:gender WHERE studentId=:id")
    public int updateData(String name,String mark ,String gender, int id);

    @Query("DELETE FROM Student WHERE studentId =:id")
    public int deleteData(int id);

}
