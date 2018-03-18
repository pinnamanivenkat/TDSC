package com.wise.tailorshome;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;

public class MainActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {

    private GoogleApiClient mGoogleApiClient;
    private static final int RC_SIGN_IN = 7;

    private SignInButton signInButton;

    private LinearLayout prof_section;

    private static final String TAG = "MainActivity";

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        signInButton = findViewById(R.id.signin);
        prof_section = findViewById(R.id.prof_section);

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });

        prof_section.setVisibility(View.GONE);

        signInButton.setSize(SignInButton.SIZE_STANDARD);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        signInButton.setScopes(gso.getScopeArray());
    }

    public void submitOrder(View v) {
        Button button = (Button) v;
        startActivity(new Intent(getApplicationContext(), Main2Activity.class));
    }


    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    private void signIn() {
        if (Utils.hasActiveInternetConnection(MainActivity.this)) {
            progressDialog = Utils.showLoadingDialog(MainActivity.this, true);
            Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
            startActivityForResult(signInIntent, RC_SIGN_IN);
        } else {
            Toast.makeText(this, "No internet, Please try again", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            if (resultCode == RESULT_OK) {
                GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
                handleSignInResult(result);
            } else {
                if (data.getExtras() != null) {
                    for (String key : data.getExtras().keySet()) {
                        Log.i(TAG, "onActivityResult: " + key + " " + data.getExtras().get("googleSignInStatus"));
                    }
                }
                Toast.makeText(this, "Login failed, please try again", Toast.LENGTH_SHORT).show();
            }
        }

        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.cancel();
        }
    }

    private void handleSignInResult(GoogleSignInResult result) {
        if (result != null) {
            if (result.isSuccess()) {
                GoogleSignInAccount acct = result.getSignInAccount();
                if (acct != null) {
                    String personName = acct.getDisplayName();
                    String photoUrl = null;
                    if (acct.getPhotoUrl() != null) {
                        photoUrl = acct.getPhotoUrl().toString();
                    }
                    String email = acct.getEmail();
                    Log.i(TAG, "handleSignInResult: " + personName + " " + email + " " + photoUrl);
                } else {
                    updateUI(false);
                }
            } else {
                updateUI(false);
            }
        } else {
            updateUI(false);
        }
    }

    private void updateUI(boolean isLogin) {
        if (isLogin) {
            prof_section.setVisibility(View.VISIBLE);
            signInButton.setVisibility(View.GONE);
        } else {
            Toast.makeText(this, "Login failed,Please try again", Toast.LENGTH_SHORT).show();
            prof_section.setVisibility(View.GONE);
            signInButton.setVisibility(View.VISIBLE);
        }
    }
}