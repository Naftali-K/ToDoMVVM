package com.nk.todomvvm.DBRoom;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.nk.todomvvm.models.ToDo;

@Database(entities = {ToDo.class}, version = 1)
public abstract class ToDoDatabase extends RoomDatabase {
    public abstract ToDoDAO toDoDAO();
}
