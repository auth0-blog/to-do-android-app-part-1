package com.auth0.todo.identity;

import android.app.Dialog;
import android.content.Intent;
import android.widget.Toast;

import com.auth0.android.Auth0;
import com.auth0.android.authentication.AuthenticationAPIClient;
import com.auth0.android.authentication.AuthenticationException;
import com.auth0.android.authentication.storage.CredentialsManagerException;
import com.auth0.android.authentication.storage.SecureCredentialsManager;
import com.auth0.android.authentication.storage.SharedPreferencesStorage;
import com.auth0.android.callback.BaseCallback;
import com.auth0.android.provider.AuthCallback;
import com.auth0.android.provider.WebAuthProvider;
import com.auth0.android.result.Credentials;
import com.auth0.todo.MainActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

public class AuthenticationHandler implements AuthCallback, BaseCallback<Credentials, CredentialsManagerException> {
    private Auth0 auth0;
    private AuthAwareActivity originalActivity;
    private Class<? extends AuthAwareActivity> nextActivity;
    private SecureCredentialsManager credentialsManager;
    private Credentials credentials;

    AuthenticationHandler(AuthAwareActivity originalActivity) {
        this.originalActivity = originalActivity;

        // configuring Auth0
        auth0 = new Auth0(originalActivity);
        auth0.setOIDCConformant(true);
        AuthenticationAPIClient client = new AuthenticationAPIClient(auth0);
        credentialsManager = new SecureCredentialsManager(originalActivity, client, new SharedPreferencesStorage(originalActivity));
    }

    public void startAuthenticationProcess() {
        WebAuthProvider.init(auth0)
                .withScheme("to-do")
                .withScope("openid profile email offline_access")
                .withAudience("https://to-dos-api")
                .start(originalActivity, this);
    }

    public void logout() {
        credentialsManager.clearCredentials();
        originalActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                String message = "See you soon!";
                Toast.makeText(originalActivity, message, Toast.LENGTH_SHORT).show();
                originalActivity.refreshMenu();

                if (originalActivity instanceof MainActivity) return;
                originalActivity.startActivity(new Intent(originalActivity, MainActivity.class));
            }
        });
    }

    void refreshCredentials(Class<? extends AuthAwareActivity> nextActivity) {
        this.nextActivity = nextActivity;
        credentialsManager.getCredentials(this);
    }

    public String getAccessToken() {
        return credentials.getAccessToken();
    }

    public boolean hasValidCredentials() {
        boolean hasValidCredentials = this.credentialsManager.hasValidCredentials();
        if (hasValidCredentials && credentials == null) {
            refreshCredentials(null);
        }
        return hasValidCredentials;
    }

    @Override
    public void onFailure(@NonNull final Dialog dialog) {
        originalActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                dialog.show();
            }
        });
    }

    @Override
    public void onFailure(AuthenticationException exception) {
        new AlertDialog.Builder(originalActivity)
                .setTitle("Authentication Error")
                .setMessage(exception.getMessage())
                .show();
    }

    @Override
    public void onSuccess(@NonNull Credentials credentials) {
        credentialsManager.saveCredentials(credentials);
        this.credentials = credentials;
        if (nextActivity == null) {
            originalActivity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    originalActivity.refreshMenu();
                }
            });
            return;
        }
        originalActivity.startActivity(new Intent(originalActivity, nextActivity));
    }

    @Override
    public void onFailure(CredentialsManagerException error) {
        startAuthenticationProcess();
    }
}