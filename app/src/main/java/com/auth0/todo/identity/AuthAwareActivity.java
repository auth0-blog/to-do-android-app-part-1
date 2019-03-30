package com.auth0.todo.identity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.auth0.todo.R;
import com.auth0.todo.util.LoginListener;
import com.auth0.todo.util.LogoutListener;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public abstract class AuthAwareActivity extends AppCompatActivity {
    protected AuthenticationHandler authenticationHandler;
    protected Menu menu;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.authenticationHandler = new AuthenticationHandler(this);

    }

    public void refreshMenu() {
        MenuItem firstOption = menu.findItem(R.id.first_action);

        // reconfiguring button
        if (!authenticationHandler.hasValidCredentials()) {
            firstOption.setTitle(R.string.login);
            firstOption.setOnMenuItemClickListener(new LoginListener(authenticationHandler));
        } else {
            firstOption.setTitle(R.string.logout);
            firstOption.setOnMenuItemClickListener(new LogoutListener(authenticationHandler));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);

        this.menu = menu;
        refreshMenu();

        return true;
    }
}