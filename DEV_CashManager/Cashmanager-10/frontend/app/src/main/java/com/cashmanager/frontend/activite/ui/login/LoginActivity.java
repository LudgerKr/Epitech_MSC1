package com.cashmanager.frontend.activite.ui.login;

import android.app.ActionBar;
import android.app.Activity;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.cashmanager.frontend.R;
import com.cashmanager.frontend.activite.RegisterActivity;
import com.cashmanager.frontend.activite.ScanProduitActivity;
import com.cashmanager.frontend.asynchrone.RegisterAsynchTask;
import com.cashmanager.frontend.entites.Assurance;
import com.cashmanager.frontend.entites.LoggedUser;
import com.cashmanager.frontend.entites.User;
import com.cashmanager.frontend.interfaces.AssuranceInterface;
import com.cashmanager.frontend.interfaces.RetrofitUserInterface;

import java.lang.reflect.Method;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {

    private EditText username;
    private EditText password;
    private Button loginButton;
    private TextView message_error_txt;

    private static final String PREFS = "PREFS";
    private static final String PREFS_JSESSION = "PREFS_JSESSION";
    SharedPreferences sharedPreferences;
    /**
     * Instance retrofit
     */
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://10.0.2.2:8080/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    RetrofitUserInterface userInterface = retrofit.create(RetrofitUserInterface.class);


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final ActionBar actionBar = getActionBar();
        setContentView(R.layout.activity_login);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password_edtx);
        loginButton = findViewById(R.id.login);
        message_error_txt = findViewById(R.id.messe_error_txt);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logUser();
            }
        });

        if (actionBar != null) {
            actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM,
                    ActionBar.DISPLAY_SHOW_CUSTOM);
            actionBar.setCustomView(R.layout.barre_action_titre_noir);
            actionBar.setDisplayHomeAsUpEnabled(true);

            actionBar.getCustomView().setOnClickListener(new View.OnClickListener() {
                /**
                 * Appui sur la barre d'action.
                 *
                 * @param vue Vue de la barre d'action.
                 */
                public void onClick(final View vue) {
                    onBackPressed();
                }
            });

            final View view = actionBar.getCustomView();
            final TextView titreTextView = view.findViewById(R.id.titreTextView);

            actionBar.setDisplayShowCustomEnabled(true);
            titreTextView.setText(R.string.titre_activite_scan_produit);
        }
    }

    /**
     * Création de l'activité
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_barre_action_items, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onMenuOpened(final int featureId, final Menu menu) {
        if (featureId == Window.FEATURE_ACTION_BAR && menu != null) {
            if (menu.getClass().getSimpleName().equals("MenuBuilder")) {
                try {
                    final Method methode = menu.getClass().getDeclaredMethod("setOptionalIconsVisible",
                            Boolean.TYPE);

                    methode.setAccessible(true);
                    methode.invoke(menu, true);
                } catch (final Exception noSuchMethodException) {
                }
            }
        }

        return super.onMenuOpened(featureId, menu);
    }

    /**
     * Appui sur une option du menu de l'activité.
     *
     * @param elementMenu : MenuItem
     * @return true, si vrai
     */
    @Override
    public boolean onOptionsItemSelected(final MenuItem elementMenu) {
        final int identifiantElementMenu = elementMenu.getItemId();

        if (identifiantElementMenu == R.id.registerItem) {
            afficherInscription();
        }

        return super.onOptionsItemSelected(elementMenu);
    }

    /**
     * Afficher l'écran de création de compte
     */
    public void afficherInscription() {
        final Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);

        startActivity(intent);
    }


    /**
     * Authentification de l'utilisateur
     */
    public void logUser() {
        LoggedUser loggedUser = new LoggedUser();
        loggedUser.setUsername(username.getText().toString());
        loggedUser.setPassword(password.getText().toString());

        Log.e("username", " " + loggedUser.getUsername());
        Log.e("password", " " + loggedUser.getPassword());
        Log.e("usern", " " + username.getText().toString());
        Log.e("pass", " " + password.getText().toString());

        Call<Void> call = userInterface.logUser(username.getText().toString(), password.getText().toString());
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (!response.isSuccessful()) {
                    message_error_txt.setVisibility(View.VISIBLE);
                    message_error_txt.setText("Email ou mot de passe incorrect");
                    Log.e("error", "echec");
                    Log.e("echec", "requete " + response.code());

                }
                if (response.isSuccessful()) {
                    String header = response.headers().toString();
                    Log.e("headers", " " + header);
                    Log.e("code reponse", " " + response.code());
                    String cookies= response.headers().get("Set-Cookie").toString();
                    Log.e("RECUPERATION DE JSESSIONID", " " + cookies);
                    String [] cookiesElements = cookies.split("\\;");
                    String JSESSIONID = cookiesElements[0];
                    Log.e("idSeulement", " " + JSESSIONID);
                    setPrefsJsession(JSESSIONID);
                    afficherScan();
                }

            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.e("error", " " + t.getMessage());
                message_error_txt.setVisibility(View.VISIBLE);
                message_error_txt.setText("Une erreur est survenu, veillez reessayer !!!");
            }
        });
    }

    public void afficherScan() {
        final Intent intent = new Intent(LoginActivity.this, ScanProduitActivity.class);

        startActivity(intent);
    }

    /**
     * Mettre le JSESSIONID en memoire
     */
    public void setPrefsJsession(String jsessionID){
        sharedPreferences = getBaseContext().getSharedPreferences(PREFS, MODE_PRIVATE);

        //objectif : sauvegarder 1 seule fois le nom et l'age de l'utilisateur

        //pour cela, on commence par regarder si on a déjà des éléments sauvegardés
            sharedPreferences
                    .edit()
                    .putString(PREFS_JSESSION, jsessionID)
                    .apply();
            Toast.makeText(this, "Sauvegardé, relancez l'application pour voir le résultat", Toast.LENGTH_SHORT).show();
            Log.e("pref ", " reussi");

    }

}