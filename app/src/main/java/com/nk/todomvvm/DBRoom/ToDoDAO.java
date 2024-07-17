package com.nk.todomvvm.DBRoom;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.nk.todomvvm.models.ToDo;

import java.util.List;

@Dao
public interface ToDoDAO {

    @Insert
    long addNewToDo(ToDo toDo);

    @Query("SELECT * FROM ToDo")
    List<ToDo> getAllToDo();

    @Query("SELECT * FROM ToDo WHERE id=:id")
    ToDo getSingleTodo(int id);

    @Update
    void updateToDo(ToDo toDo);

    @Delete
    void deleteToDo(ToDo toDo);

    @Query("DELETE FROM ToDo WHERE id = :id")
    int deleteToDoById(long id);
//    int deleteToDoById(long id);
}
