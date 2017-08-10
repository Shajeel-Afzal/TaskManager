package com.shajeelafzal.taskmanager;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import models.TaskModel;

public class AddTaskActivity extends AppCompatActivity {

    private EditText mTaskTextET;
    private ToggleButton mPriorityTB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        String taskTitle = getIntent().getStringExtra(Constants.KEY_TASK_MODEL);
        boolean priority = getIntent().getBooleanExtra(Constants.KEY_PRIORITY, false);

        mTaskTextET = (EditText) findViewById(R.id.task_text_et);
        mPriorityTB = (ToggleButton) findViewById(R.id.priority_tb);

        if (taskTitle != null) {
            mTaskTextET.setText(taskTitle);
            mPriorityTB.setChecked(priority);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_task_option_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.add_edit_task_menu) {
            addTask();
        }

        return super.onOptionsItemSelected(item);
    }

    private void addTask() {

        String taskDetail = mTaskTextET.getText().toString();
        boolean isPriority = mPriorityTB.isChecked();

        TaskModel model = new TaskModel(taskDetail, isPriority);

        FirebaseApp.initializeApp(this);

        FirebaseDatabase.getInstance().getReference()
                .child(Constants.FIREBASE_LOCATION_TASKS_LIST)
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .push()
                .setValue(model);

        Toast.makeText(AddTaskActivity.this, "Task Added", Toast.LENGTH_SHORT).show();
        finish();
    }
}
