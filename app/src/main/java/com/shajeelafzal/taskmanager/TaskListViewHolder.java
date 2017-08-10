package com.shajeelafzal.taskmanager;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import models.TaskModel;

/**
 * Created by shajeelafzal on 10/08/2017.
 */

public class TaskListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private final TextView titleTV;
    private final CheckBox priorityCB;
    private TaskModel data;
    private OnRecyclerViewClickListener onRecyclerViewClickListener;

    public void setOnRecyclerViewClickListener(OnRecyclerViewClickListener onRecyclerViewClickListener) {
        this.onRecyclerViewClickListener = onRecyclerViewClickListener;
    }

    public TaskListViewHolder(View itemView) {
        super(itemView);

        titleTV = (TextView) itemView.findViewById(R.id.task_title_tv);
        priorityCB = (CheckBox) itemView.findViewById(R.id.task_priority_cb);

        itemView.setOnClickListener(this);
    }

    public void setData(TaskModel data) {
        this.data = data;
        titleTV.setText(data.getTaskDetail());
        priorityCB.setChecked(data.isPriority());
    }

    @Override
    public void onClick(View v) {
        if (onRecyclerViewClickListener != null)
            onRecyclerViewClickListener.onListItemClick(data);
    }
}
