package com.wise.tailorshome;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, GoogleApiClient.OnConnectionFailedListener {

    private static final int REQ_CODE = 9001;
    private LinearLayout prof_section;
    private Button Signout;
    private SignInButton Signin;
    private TextView Name, Email;
    private ImageView g_image;
    private GoogleApiClient googleApiClient;

    public static boolean isNetworkStatusAvialable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            NetworkInfo netInfos = connectivityManager.getActiveNetworkInfo();
            if (netInfos != null) {
                return netInfos.isConnected();
            }
        }
        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        if (isNetworkStatusAvialable(getApplicationContext())) {
            Toast.makeText(getApplicationContext(), "Internet detected", Toast.LENGTH_SHORT).show();
            setContentView(R.layout.activity_main);

            try {
                prof_section = findViewById(R.id.prof_section);
                Signout = findViewById(R.id.logout);
                Signin = findViewById(R.id.signin);
                Name = findViewById(R.id.name);
                Email = findViewById(R.id.email);
                g_image = findViewById(R.id.g_image);
            } catch (Exception e) {
                
            }
            Signin.setOnClickListener(this);
            Signout.setOnClickListener(this);
            prof_section.setVisibility(View.GONE);
            GoogleSignInOptions signInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
            googleApiClient = new GoogleApiClient.Builder(this).enableAutoManage(this, this).addApi(Auth.GOOGLE_SIGN_IN_API, signInOptions).build();


        } else {
            Toast.makeText(getApplicationContext(), "Please check your Internet Connection", Toast.LENGTH_SHORT).show();

        }

    }

    public void submitOrder(View v) {
        Button button = (Button) v;
        startActivity(new Intent(getApplicationContext(), Main2Activity.class));

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.signin:
                signin();
                break;
            case R.id.logout:
                signout();
                break;

        }

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }


    private void signin() {
        Intent intent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
        startActivityForResult(intent, REQ_CODE);
        //startActivity(new Intent(getApplicationContext(), Main2Activity.class));
    }

    private void signout() {
        Auth.GoogleSignInApi.signOut(googleApiClient).setResultCallback(new ResultCallback<Status>() {
            @Override
            public void onResult(@NonNull Status status) {
                updateUI(false);
            }
        });

    }

    private void handleresult(GoogleSignInResult result) {
        if (result.isSuccess()) {
            GoogleSignInAccount account = result.getSignInAccount();
            String name = account.getDisplayName();
            String email = account.getEmail();
            String img_url = account.getPhotoUrl().toString();
            Name.setText(name);
            Email.setText(email);
            Glide.with(this).load(img_url).into(g_image);
            updateUI(true);
        } else {
            updateUI(false);
        }

    }

    private void updateUI(boolean isLogin) {
        if (isLogin) {
            prof_section.setVisibility(View.VISIBLE);
            Signin.setVisibility(View.GONE);
        } else {
            prof_section.setVisibility(View.GONE);
            Signin.setVisibility(View.VISIBLE);

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQ_CODE) {
            if (resultCode == RESULT_OK) {
                GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
                int statusCode = result.getStatus().getStatusCode();
                handleresult(result);
            }
        }

           /* GoogleSignInResult result= Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleresult(result);*/

    }
}
