package com.cashmanager.frontend.activite;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActionBar;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.cashmanager.frontend.R;

import java.lang.reflect.Method;

public class ScanProduitActivity extends AppCompatActivity {
    private TextView scanText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final ActionBar actionBar = getActionBar();
        setContentView(R.layout.activity_scan_produit);
        scanText = findViewById(R.id.scanText);
        SharedPreferences preferences = getBaseContext().getSharedPreferences("PREFS", MODE_PRIVATE);
        String JSESSIONID = preferences.getString("PREFS_JSESSION", "");
        scanText.setText(JSESSIONID);
        Log.e("recu", " " + JSESSIONID);

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
}