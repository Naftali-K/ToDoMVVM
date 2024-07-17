package com.nk.todomvvm.viewmodels;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.room.Room;

import com.nk.todomvvm.DBRoom.ToDoDAO;
import com.nk.todomvvm.DBRoom.ToDoDatabase;
import com.nk.todomvvm.DataManager;
import com.nk.todomvvm.models.ToDo;

import java.util.List;

public class HomeViewModel extends ViewModel {

    private MutableLiveData<List<ToDo>> toDoList = new MutableLiveData<>();
    private ToDoDatabase db;
    private ToDoDAO toDoDAO;
    private Context context;

    public void init(Context context) {
        this.context = context;

        db = Room.databaseBuilder(context, ToDoDatabase.class, DataManager.TODO_DATABASE_NAME).allowMainThreadQueries().build();
        toDoDAO = db.toDoDAO();
        toDoList.setValue(toDoDAO.getAllToDo());
    }

    public LiveData<List<ToDo>> getToDoList() {
        return toDoList;
    }

    public void getUpdatedToDoList() {
        toDoList.setValue(toDoDAO.getAllToDo());
    }

    public void deleteToDo(int ID) {
        int deleted = toDoDAO.deleteToDoById(ID);

        if (deleted > 0) {
            toDoList.setValue(toDoDAO.getAllToDo());
        }
    }
}
