package com.nk.todomvvm;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

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

import com.nk.todomvvm.models.ToDo;
import com.nk.todomvvm.viewmodels.ReadToDoViewModel;

public class ReadToDoActivity extends AppCompatActivity {

    public static final String EXTRA_NAME = "TO_DO_ID";
    public static final int DELETE_ITEM = 100;
    public static final int UPDATED_ITEM = 101;
    private boolean isUpdated = false;
    private int ID;

    private ImageView backBtnImageView, editImageView, deleteImageView;
    private TextView titleTextView, contentTextView;

    private ReadToDoViewModel readToDoViewModel;
    private ActivityResultLauncher editToDoResultLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_to_do);
        setReferences();
        setResultLauncher();

        Intent intent = getIntent();
            ID = intent.getIntExtra(EXTRA_NAME, 0);

        backBtnImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isUpdated) {
                    setResult(UPDATED_ITEM);
                } else {
                    onBackPressed();
                }
                finish();
            }
        });

        editImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), AddEditToDoActivity.class);
                    intent.putExtra(AddEditToDoActivity.EXTRA_ACTION, 1);
                    intent.putExtra(AddEditToDoActivity.EXTRA_NAME, ID);
                editToDoResultLauncher.launch(intent);
            }
        });

        deleteImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                readToDoViewModel.deleteToDo(ID);
            }
        });

        setViewModel();
    }

    private void setReferences() {
        backBtnImageView = findViewById(R.id.back_btn_image_view);
        editImageView = findViewById(R.id.edit_image_view);
        deleteImageView = findViewById(R.id.delete_image_view);
        titleTextView = findViewById(R.id.title_text_view);
        contentTextView = findViewById(R.id.content_text_view);
    }

    private void setResultLauncher() {
        editToDoResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult o) {
                if (o.getResultCode() == AddEditToDoActivity.EDIT_ITEM) {
                    readToDoViewModel.updateToDo(ID);
                    isUpdated = true;
                }
            }
        });
    }

    private void setViewModel() {
        readToDoViewModel = new ViewModelProvider(this).get(ReadToDoViewModel.class);
        readToDoViewModel.init(getBaseContext());

        readToDoViewModel.getToDoData(ID).observe(this, new Observer<ToDo>() {
            @Override
            public void onChanged(ToDo toDo) {
                if (toDo == null) {
//                    onBackPressed();
                    setResult(DELETE_ITEM);
                    finish();
                    return;
                }

                setValues(toDo);
            }
        });
    }

    private void setValues(ToDo toDo) {
        titleTextView.setText(toDo.getTitle());
        contentTextView.setText(toDo.getContent());
    }
}