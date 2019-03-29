package com.auth0.todo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

public class ToDoForm extends AppCompatActivity {
    private List<String> toDoList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_do_form);
        toDoList = getIntent().getStringArrayListExtra("to-do-list");
    }

    public void addToDoItem(View view) {
        EditText editText = findViewById(R.id.editText);
        String message = editText.getText().toString();

        toDoList.add(message);

        Intent newIntent = new Intent(this, MainActivity.class);
        newIntent.putStringArrayListExtra("to-do-list", (ArrayList<String>) toDoList);
        startActivity(newIntent);
    }
}
