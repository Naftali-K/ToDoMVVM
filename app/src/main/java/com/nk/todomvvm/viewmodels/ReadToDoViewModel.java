package com.nk.todomvvm.viewmodels;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.room.Room;

import com.nk.todomvvm.DBRoom.ToDoDAO;
import com.nk.todomvvm.DBRoom.ToDoDatabase;
import com.nk.todomvvm.DataManager;
import com.nk.todomvvm.models.ToDo;

public class ReadToDoViewModel extends ViewModel {

    private ToDoDatabase db;
    private ToDoDAO toDoDAO;
    private Context context;

    private MutableLiveData<ToDo> toDoData = new MutableLiveData<>();

    public void init(Context context) {
        this.context = context;

        db = Room.databaseBuilder(context, ToDoDatabase.class, DataManager.TODO_DATABASE_NAME).allowMainThreadQueries().build();
        toDoDAO = db.toDoDAO();
    }

    public LiveData<ToDo> getToDoData(int ID) {
        toDoData.setValue(toDoDAO.getSingleTodo(ID));
        return toDoData;
    }

    public void deleteToDo(int ID) {
        int deleted = toDoDAO.deleteToDoById(ID);

        if (deleted > 0) {
            toDoData.setValue(null);
        }
    }

    public void updateToDo(int ID) {
        toDoData.setValue(toDoDAO.getSingleTodo(ID));
    }
}
