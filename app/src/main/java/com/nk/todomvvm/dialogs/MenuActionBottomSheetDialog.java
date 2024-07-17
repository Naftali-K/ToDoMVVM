package com.nk.todomvvm.dialogs;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.nk.todomvvm.R;

public class MenuActionBottomSheetDialog extends BottomSheetDialogFragment {
    public static final String DIALOG_TAG = "MenuActionBottomSheetDialog";

    private ImageView closeBtnImageView;
    private TextView readTextView, editTextView, deleteTextView;
    private CallBack callBack;

    public MenuActionBottomSheetDialog(CallBack callBack) {
        this.callBack = callBack;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NORMAL, R.style.customBottomSheetDialog);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bottom_sheet_dialog_menu_actions, container, false);
        setReferences(view);

        closeBtnImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        readTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callBack.read();
                dismiss();
            }
        });

        editTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callBack.edit();
                dismiss();
            }
        });

        deleteTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callBack.delete();
                dismiss();
            }
        });

        return view;
    }

    private void setReferences(View view) {
        closeBtnImageView = view.findViewById(R.id.close_btn_image_view);
        readTextView = view.findViewById(R.id.read_text_view);
        editTextView = view.findViewById(R.id.edit_text_view);
        deleteTextView = view.findViewById(R.id.delete_text_view);
    }

    public interface CallBack {
        void read();
        void edit();
        void delete();
    }
}
