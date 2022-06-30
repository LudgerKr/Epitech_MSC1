package com.cashmanager.frontend.activite;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.service.autofill.RegexValidator;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.basgeekball.awesomevalidation.utility.RegexTemplate;
import com.cashmanager.frontend.R;
import com.cashmanager.frontend.activite.ui.login.LoginActivity;
import com.cashmanager.frontend.entites.Assurance;
import com.cashmanager.frontend.entites.User;
import com.cashmanager.frontend.interfaces.AssuranceInterface;
import com.cashmanager.frontend.interfaces.RetrofitUserInterface;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RegisterActivity extends AppCompatActivity {
    private EditText lastName_editxt;
    private EditText firstName_editxt;
    private EditText email__editxt;
    private EditText street_editxt;
    private EditText city_editx;
    private EditText zipCode_editxt;
    private EditText phoneNumber_editxt;
    private EditText password_editxt_editxt;
    private EditText repassword_editxt;
    private Button register_btn;
    private TextView testApi;

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://10.0.2.2:8080/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    RetrofitUserInterface userInterface = retrofit.create(RetrofitUserInterface.class);

    /**
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AwesomeValidation validation = new AwesomeValidation(ValidationStyle.BASIC);
        setContentView(R.layout.activity_register);
        lastName_editxt = findViewById(R.id.lastName);
        firstName_editxt = findViewById(R.id.firstName);
        email__editxt = findViewById(R.id.email);
        street_editxt = findViewById(R.id.street);
        zipCode_editxt = findViewById(R.id.zipCode);
        phoneNumber_editxt = findViewById(R.id.phoneNumber);
        password_editxt_editxt = findViewById(R.id.password);
        repassword_editxt = findViewById(R.id.repassword);
        city_editx = findViewById(R.id.city_editx);
        register_btn = findViewById(R.id.btn_register);
        validation.addValidation(lastName_editxt, RegexTemplate.NOT_EMPTY, "Enter lastName");
        validation.addValidation(phoneNumber_editxt, Patterns.PHONE, "Enter valide phone number");
        validation.addValidation(firstName_editxt, RegexTemplate.NOT_EMPTY, "Enter firstName");
        validation.addValidation(email__editxt, Patterns.EMAIL_ADDRESS, "Enter email");
        validation.addValidation(city_editx, RegexTemplate.NOT_EMPTY, "Enter city");
        validation.addValidation(street_editxt, RegexTemplate.NOT_EMPTY, "Enter street");
        validation.addValidation(password_editxt_editxt, ".{8,}", "Enter password");
        validation.addValidation(repassword_editxt, RegexTemplate.NOT_EMPTY, "Enter repassword");
        validation.addValidation(zipCode_editxt, RegexTemplate.NOT_EMPTY, "Enter zip code");


        register_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validation.validate()) {
                    //register_btn.setEnabled(false);
                    createUser();

                }
            }
        });

    }

    private void createUser() {
        User user = new User();
        user.setLastName(lastName_editxt.getText().toString());
        user.setFirstName(firstName_editxt.getText().toString());
        user.setEmail(email__editxt.getText().toString());
        user.setPassword(password_editxt_editxt.getText().toString());
        user.setStreet(street_editxt.getText().toString());
        user.setCity(city_editx.getText().toString());
        user.setZipCode(zipCode_editxt.getText().toString());
        user.setPhoneNumber(phoneNumber_editxt.getText().toString());
        String confirmPassword = repassword_editxt.getText().toString();


        Call<User> call = userInterface.createUser(lastName_editxt.getText().toString(), firstName_editxt.getText().toString(), email__editxt.getText().toString(), password_editxt_editxt.getText().toString(), street_editxt.getText().toString(), city_editx.getText().toString(), zipCode_editxt.getText().toString(), phoneNumber_editxt.getText().toString());
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (!response.isSuccessful()) {
                    Log.e("error", "echec");
                    Log.e("echec", "requete " + response.code());

                }
                afficherConnexion();
                String header = response.headers().toString();
                Log.e("header", " " + header);
                String content = "";
                content += response.code();
                Log.e("code error", "code requete " + content);
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });
    }

    public void afficherConnexion() {
        final Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);

        startActivity(intent);
    }
}
