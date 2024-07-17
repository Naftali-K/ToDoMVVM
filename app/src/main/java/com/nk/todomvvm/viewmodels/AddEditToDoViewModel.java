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

import java.util.List;

public class AddEditToDoViewModel extends ViewModel {

    private ToDoDatabase db;
    private ToDoDAO toDoDAO;
    private MutableLiveData<Boolean> error = new MutableLiveData<>();
    private MutableLiveData<Boolean> successNewToDo = new MutableLiveData<>();
    private MutableLiveData<ToDo> toDoData = new MutableLiveData<>();
    private MutableLiveData<Boolean> updated = new MutableLiveData<>();

    public void init(Context context) {
        updated.setValue(false);

        db = Room.databaseBuilder(context, ToDoDatabase.class, DataManager.TODO_DATABASE_NAME).allowMainThreadQueries().build();
        toDoDAO = db.toDoDAO();

        error.setValue(false);
//        successNewToDo.setValue(false);
    }

    public LiveData<Boolean> getError() {
        return error;
    }

    public LiveData<ToDo> getToDo() {
        return toDoData;
    }

    public LiveData<Boolean> getUpdated() {
        return updated;
    }

    public LiveData<Boolean> addNewToDo(ToDo toDo) {

        if (!validationData(toDo)) {
            successNewToDo.setValue(false);
            return successNewToDo;
        }

        long id = toDoDAO.addNewToDo(toDo);
        if (id > 0) {
            successNewToDo.setValue(true);
        } else {
            successNewToDo.setValue(false);
        }

        return successNewToDo;
    }

    private boolean validationData(ToDo toDo) {
        if (toDo.getTitle() == null || toDo.getTitle().isEmpty()) {
            error.setValue(true);
            return false;
        }

        return true;
    }

    public void getToDoByID(int ID) {
        toDoData.setValue(toDoDAO.getSingleTodo(ID));
    }

    public void updateToDo(ToDo toDo) {
        toDoDAO.updateToDo(toDo);

        updated.setValue(true);
    }
}
