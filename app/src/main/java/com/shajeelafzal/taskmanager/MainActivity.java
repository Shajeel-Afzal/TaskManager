package com.shajeelafzal.taskmanager;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Arrays;

import models.TaskModel;

public class MainActivity extends AppCompatActivity {

    private static final int RC_SIGN_IN = 101;
    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseApp.initializeApp(this);
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);

        mRecyclerView = (RecyclerView) findViewById(R.id.task_list_rv);

        if (FirebaseAuth.getInstance().getCurrentUser() == null) {
            startActivityForResult(
                    AuthUI.getInstance()
                            .createSignInIntentBuilder()
                            .setAvailableProviders(
                                    Arrays.asList(new AuthUI.IdpConfig.Builder(AuthUI.EMAIL_PROVIDER).build()))
                            .build(),
                    RC_SIGN_IN);
        } else {
            DatabaseReference userTasksQuery = FirebaseDatabase.getInstance().getReference()
                    .child(Constants.FIREBASE_LOCATION_TASKS_LIST)
                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid());
            userTasksQuery.keepSynced(true);

            FirebaseRecyclerAdapter<TaskModel, TaskListViewHolder> mAdapter =
                    new FirebaseRecyclerAdapter<TaskModel, TaskListViewHolder>(
                            TaskModel.class,
                            R.layout.task_list_item_layout,
                            TaskListViewHolder.class,
                            userTasksQuery) {

                        @Override
                        protected void populateViewHolder(TaskListViewHolder viewHolder, TaskModel model, int position) {
                            viewHolder.setData(model);
                        }

                        @Override
                        public TaskListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

                            TaskListViewHolder holder = new TaskListViewHolder(LayoutInflater.from(parent.getContext())
                                    .inflate(R.layout.task_list_item_layout, parent, false));

                            holder.setOnRecyclerViewClickListener(new OnRecyclerViewClickListener() {
                                @Override
                                public void onListItemClick(TaskModel taskModel) {
                                    Intent intent = new Intent(MainActivity.this, AddTaskActivity.class);
                                    intent.putExtra(Constants.KEY_TASK_MODEL, taskModel.getTaskDetail());
                                    intent.putExtra(Constants.KEY_PRIORITY, taskModel.isPriority());
                                    startActivity(intent);
                                }
                            });

                            return holder;
                        }
                    };

            mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
            mRecyclerView.setAdapter(mAdapter);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN && resultCode == RESULT_OK) {
            // TODO: Check stats

            if (FirebaseAuth.getInstance().getCurrentUser() != null) {
                Toast.makeText(this, "Login Successfull!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Login Un-Successfull!", Toast.LENGTH_SHORT).show();
            }

        }
    }

    public void onFloatingActionButtonClicked(View view) {

        Intent intent = new Intent(MainActivity.this, AddTaskActivity.class);
        startActivity(intent);

    }
}
