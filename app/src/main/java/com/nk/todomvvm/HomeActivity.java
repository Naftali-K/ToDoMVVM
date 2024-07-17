package com.nk.todomvvm;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.nk.todomvvm.adapters.ToDoRecyclerViewAdapter;
import com.nk.todomvvm.dialogs.MenuActionBottomSheetDialog;
import com.nk.todomvvm.models.ToDo;
import com.nk.todomvvm.viewmodels.HomeViewModel;

import java.util.List;

public class HomeActivity extends AppCompatActivity {

    private static final String TAG = "TestCode";
    private RecyclerView toDoRecyclerView;
    private FloatingActionButton fabBtn;
    private ActivityResultLauncher addNewToDo, readToTo;

    private HomeViewModel homeViewModel;

    private ToDoRecyclerViewAdapter adapter;

    @Override
    protected void onResume() {
        super.onResume();

        homeViewModel.getToDoList();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        setReferences();
        setResultLaunchers();

        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        homeViewModel.init(getBaseContext());

        homeViewModel.getToDoList().observe(this, new Observer<List<ToDo>>() {
            @Override
            public void onChanged(List<ToDo> toDos) {
                adapter.setList(toDos);
            }
        });

        fabBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNewToDo.launch(new Intent(getBaseContext(), AddEditToDoActivity.class));
            }
        });

        setRecyclerView();
    }

    private void setReferences() {
        toDoRecyclerView = findViewById(R.id.to_do_recycler_view);
        fabBtn = findViewById(R.id.fab_btn);
    }

    private void setResultLaunchers() {
        addNewToDo = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult o) {
                homeViewModel.getUpdatedToDoList();
            }
        });

        readToTo = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult o) {
                if (o.getResultCode() == ReadToDoActivity.DELETE_ITEM) {
                    Log.d(TAG, "onActivityResult: TODO deleted");
                    homeViewModel.getUpdatedToDoList();
                    return;
                }
                if (o.getResultCode() == ReadToDoActivity.UPDATED_ITEM) {
                    Log.d(TAG, "onActivityResult: TODO Updated");
                    homeViewModel.getUpdatedToDoList();
                    return;
                }
            }
        });
    }

    private void setRecyclerView() {
        adapter = new ToDoRecyclerViewAdapter(getBaseContext(), homeViewModel.getToDoList().getValue(), new ToDoRecyclerViewAdapter.CallBack() {
            @Override
            public void openToDo(int ID) {
                openToDoActivity(ID);
            }

            @Override
            public void openDialog(int ID) {
                openMenuDialog(ID);
            }
        });
        toDoRecyclerView.setAdapter(adapter);
    }

    private void openMenuDialog(int ID) {
        MenuActionBottomSheetDialog dialog = new MenuActionBottomSheetDialog(new MenuActionBottomSheetDialog.CallBack() {
            @Override
            public void read() {
                openToDoActivity(ID);
            }

            @Override
            public void edit() {
                openEditToDo(ID);
            }

            @Override
            public void delete() {
                deleteToDo(ID);
            }
        });
        dialog.show(getSupportFragmentManager(), MenuActionBottomSheetDialog.DIALOG_TAG);
    }

    private void openToDoActivity(int ID) {
        Intent intent = new Intent(getBaseContext(), ReadToDoActivity.class);
            intent.putExtra(ReadToDoActivity.EXTRA_NAME, ID);
        readToTo.launch(intent);
    }

    private void openEditToDo(int ID) {
        Intent intent = new Intent(getBaseContext(), AddEditToDoActivity.class);
            intent.putExtra(AddEditToDoActivity.EXTRA_ACTION, 1);
            intent.putExtra(AddEditToDoActivity.EXTRA_NAME, ID);
        addNewToDo.launch(intent);
    }

    private void deleteToDo(int ID) {
        homeViewModel.deleteToDo(ID);
    }
}