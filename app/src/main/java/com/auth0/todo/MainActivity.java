package com.auth0.todo;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import com.auth0.todo.util.ToDoListAdapter;

public class MainActivity extends AppCompatActivity {
    private ToDoListAdapter toDoListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // create and configure the adapter
        this.toDoListAdapter = new ToDoListAdapter(this);
        ListView microPostsListView = findViewById(R.id.micro_posts);
        microPostsListView.setAdapter(toDoListAdapter);
    }
}
