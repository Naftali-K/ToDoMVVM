package com.nk.todomvvm.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nk.todomvvm.R;
import com.nk.todomvvm.dialogs.MenuActionBottomSheetDialog;
import com.nk.todomvvm.models.ToDo;

import java.util.ArrayList;
import java.util.List;

public class ToDoRecyclerViewAdapter extends RecyclerView.Adapter<ToDoRecyclerViewAdapter.ToDoRecyclerViewHolder> {

    private Context context;
    private List<ToDo> list = new ArrayList<>();
    private CallBack callBack;

    public ToDoRecyclerViewAdapter(CallBack callBack) {
        this.callBack = callBack;
    }

    public ToDoRecyclerViewAdapter(Context context, List<ToDo> list, CallBack callBack) {
        this.context = context;
        this.list = list;
        this.callBack = callBack;
    }

    @NonNull
    @Override
    public ToDoRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view, parent, false);
        return new ToDoRecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ToDoRecyclerViewHolder holder, int position) {
        holder.bind(list.get(position), position);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ToDoRecyclerViewHolder extends RecyclerView.ViewHolder {

        private LinearLayout container;
        private TextView textView;

        public ToDoRecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            container = itemView.findViewById(R.id.container);
            textView = itemView.findViewById(R.id.text_view);
        }

        void bind(ToDo toDo, int position) {
            textView.setText(toDo.getTitle());

            container.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    callBack.openDialog(toDo.getId());
                    return false;
                }
            });

            container.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    callBack.openToDo(toDo.getId());
                }
            });
        }
    }

    public void setList(List<ToDo> list) {
        if (list == null) {
            return;
        }
        this.list = list;
        notifyDataSetChanged();
    }

    public interface CallBack {
        void openToDo(int ID);
        void openDialog(int ID);
    }
}
