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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;


public class Main4Activity extends AppCompatActivity {


    EditText pename, pepno, peemail, peaddress, pepin, petime, pedate;
    Button pbplaceorder;
    long now = System.currentTimeMillis() - 1000;

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

        final DatePicker pedate = findViewById(R.id.epdate);
        Calendar calendar = Calendar.getInstance();

        final TimePicker petime = findViewById(R.id.eptime);
        petime.setIs24HourView(true);
        pedate.setMinDate(now + (1000 * 60 * 60 * 24));
        pedate.setMaxDate(now + (1000 * 60 * 60 * 24 * 7));







        pbplaceorder = findViewById(R.id.bplaceorder);
        final String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]{2,3}";


        pbplaceorder.setOnClickListener(
                new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        if (pename.getText().toString().trim().length() == 0) {
                            pename.setError("ADD NAME");
                            Toast.makeText(getApplicationContext(), "ENTER NAME", Toast.LENGTH_SHORT).show();
                        } else if (pepno.getText().toString().trim().length() != 10) {
                            pepno.setError("ADD VALID PHONE NUMBER");
                            Toast.makeText(getApplicationContext(), "INVALID PHONE NUMBER", Toast.LENGTH_SHORT).show();
                        } else if (peemail.getText().toString().trim().length() == 0 || (!peemail.getText().toString().trim().matches(emailPattern))) {
                            peemail.setError("ADD  VALID EMAIL");
                            Toast.makeText(getApplicationContext(), "INVALID EMAIL ADDRESS", Toast.LENGTH_SHORT).show();
                        } else if (peaddress.getText().toString().trim().length() == 0) {
                            peaddress.setError("ADD ADDRESS");
                            Toast.makeText(getApplicationContext(), "ENTER ADDRESS", Toast.LENGTH_SHORT).show();
                        } else if (pepin.getText().toString().trim().length() != 6) {
                            pepin.setError("ADD VALID PINCODE");
                            Toast.makeText(getApplicationContext(), "INALID PINCODE", Toast.LENGTH_SHORT).show();
                        } else if (petime.getCurrentHour() < 10 || petime.getCurrentHour() > 19) {
                            pepno.setError("");
                            Toast.makeText(getApplicationContext(), "CHOOSE TIME BETWEEN 10AM AND 7PM", Toast.LENGTH_SHORT).show();

                        } else {

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
                            PICKUPDATE = "" + pedate.getYear() + "/" + pedate.getMonth() + "/" + pedate.getDayOfMonth();
                            PICKUPTIME = "" + petime.getCurrentHour() + ":" + petime.getCurrentMinute();
                            // Toast.makeText(getApplicationContext(), "Please wait...", Toast.LENGTH_SHORT).show();
                            UserDetailsTable userDetail = new UserDetailsTable(NAME, PHONE_NO, EMAIL, ADDRESS, PINCODE, PICKUPDATE, PICKUPTIME);
                            new AsyncCreateUser().execute(userDetail);
                        }

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
