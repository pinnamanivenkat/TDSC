package com.example.andriod.tailors;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class Main4Activity extends AppCompatActivity {


    EditText pename, pepno, peemail, peaddress, pepin, petime, pedate;
    Button pbplaceorder;

    private ProgressDialog dialog; // this = YourActivity

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
        if (!isNetworkStatusAvialable(getApplicationContext())) {
            Toast.makeText(getApplicationContext(), "Please check your Internet Connection", Toast.LENGTH_SHORT).show();

        } else {
            setContentView(R.layout.activity_main4);

        }
        //Show the Up button in the action bar.

        pename = findViewById(R.id.ename);
        pepno = findViewById(R.id.epno);
        peemail = findViewById(R.id.eemail);
        peaddress = findViewById(R.id.eaddress);
        pepin = findViewById(R.id.epin);

        pedate = findViewById(R.id.epdate);
        petime = findViewById(R.id.eptime);


        pbplaceorder = findViewById(R.id.bplaceorder);


        pbplaceorder.setOnClickListener(
                new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        // TODO Auto-generated method stub
                        dialog = new ProgressDialog(Main4Activity.this);
                        dialog.setMax(1000);
                        dialog.setMessage("Please Wait...");
                        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                        dialog.show();
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    Thread.sleep(200);

                                } catch (Exception e) {
                                    e.printStackTrace();
                                }

                            }
                        }).start();
                        String NAME, PHONE_NO, EMAIL, ADDRESS, PINCODE, PICKUPTIME, PICKUPDATE;
                        NAME = pename.getText().toString();
                        PHONE_NO = pepno.getText().toString();
                        EMAIL = peemail.getText().toString();
                        ADDRESS = peaddress.getText().toString();
                        PINCODE = pepin.getText().toString();
                        PICKUPDATE = pedate.getText().toString();
                        PICKUPTIME = petime.getText().toString();
                        // Toast.makeText(getApplicationContext(), "Please wait...", Toast.LENGTH_SHORT).show();
                        UserDetailsTable userDetail = new UserDetailsTable(NAME, PHONE_NO, EMAIL, ADDRESS, PINCODE, PICKUPDATE, PICKUPTIME);
                        new AsyncCreateUser().execute(userDetail);

                    }
                });
    }

    protected class AsyncCreateUser extends
            AsyncTask<UserDetailsTable, Void, Void> {

        @Override
        protected Void doInBackground(UserDetailsTable... params) {

            RestAPI api = new RestAPI();
            try {
                if (!isNetworkStatusAvialable(getApplicationContext())) {
                    Toast.makeText(getApplicationContext(), "Please check your Internet Connection", Toast.LENGTH_SHORT).show();
                } else {


                    api.CreateNewAccount(params[0].getNAME(),
                            params[0].getPHONE_NO(),
                            params[0].getEMAIL(),
                            params[0].getADDRESS(),
                            params[0].getPINCODE(),
                            params[0].getPICKUPDATE(),
                            params[0].getPICKUPTIME()
                    );


                }


            } catch (Exception e) {
                // TODO Auto-generated catch block
                Log.d("AsyncCreateUser", e.getMessage());

            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {

            startActivity(new Intent(getApplicationContext(), Main6Activity.class));
        }

    }




    /*public void pickupDetails(View view)
    {
        Button button= (Button) view;
        startActivity(new Intent(getApplicationContext(), Main6Activity.class));
    }*/

}
