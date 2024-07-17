package com.nk.todomvvm;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.nk.todomvvm.models.ToDo;
import com.nk.todomvvm.viewmodels.AddEditToDoViewModel;

public class AddEditToDoActivity extends AppCompatActivity {

    public static final String EXTRA_NAME = "TO_DO_ID";
    public static final String EXTRA_ACTION = "ADD_EDIT";
    private int ID;
    private int action;
    private ToDo toDoData;
    public static final int EDIT_ITEM = 100;

    private ImageView backBtnImageView;
    private EditText titleEditText, contentEditText;
    private TextView errorTextView;
    private Button btn;

    private AddEditToDoViewModel addEditToDoViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_to_do);
        setReferences();
        setViewModels();

        Intent intent = getIntent();
            action = intent.getIntExtra(EXTRA_ACTION, 0);
            if (action > 0) {
                ID = intent.getIntExtra(EXTRA_NAME, 0);
                btn.setText(R.string.save);
                addEditToDoViewModel.getToDoByID(ID);
            }

        backBtnImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(Activity.RESULT_CANCELED);
                finish();
            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (action == 1) {
                    updateToDo();
                    return;
                }
                addNewToDo();
            }
        });
    }

    private void setReferences() {
        backBtnImageView = findViewById(R.id.back_btn_image_view);
        titleEditText = findViewById(R.id.title_edit_text);
        contentEditText = findViewById(R.id.content_edit_text);
        errorTextView = findViewById(R.id.error_text_view);
        btn = findViewById(R.id.btn);
    }

    private void setViewModels() {
        addEditToDoViewModel = new ViewModelProvider(this).get(AddEditToDoViewModel.class);
        addEditToDoViewModel.init(getBaseContext());

        addEditToDoViewModel.getError().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean) {
                    errorTextView.setVisibility(View.VISIBLE);
                } else {
                    errorTextView.setVisibility(View.INVISIBLE);
                }
            }
        });

        addEditToDoViewModel.getToDo().observe(this, new Observer<ToDo>() {
            @Override
            public void onChanged(ToDo toDo) {
                if (toDo == null) {
                    setResult(RESULT_CANCELED);
                    finish();
                    return;
                }
                toDoData = toDo;
                setValues(toDo);
            }
        });

        addEditToDoViewModel.getUpdated().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean) {
                    setResult(EDIT_ITEM);
                    finish();
                }
            }
        });
    }

    private void updateToDo() {
        toDoData.setTitle(titleEditText.getText().toString());
        toDoData.setContent(contentEditText.getText().toString());

        addEditToDoViewModel.updateToDo(toDoData);
    }

    private void addNewToDo() {
        String title = titleEditText.getText().toString();
        String content = contentEditText.getText().toString();

        ToDo newToDo = new ToDo(0, title, content);
        addEditToDoViewModel.addNewToDo(newToDo).observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean) {
                    setResult(RESULT_OK);
                    finish();
                } else {
                    Toast.makeText(getBaseContext(), "Error add new ToDo", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private void setValues(ToDo toDo) {
        titleEditText.setText(toDo.getTitle());
        contentEditText.setText(toDo.getContent());
    }
}