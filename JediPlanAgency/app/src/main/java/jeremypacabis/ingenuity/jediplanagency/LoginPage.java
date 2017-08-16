package jeremypacabis.ingenuity.jediplanagency;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Jeremy Patrick on 8/16/2017.
 * Author: Jeremy Patrick G. Pacabis
 * for jeremypacabis.ingenuity.jediplanagency @ JediPlanAgency
 */

public class LoginPage extends AppCompatActivity {
    private SharedPreferences SETTINGS;
    private EditText etxt_username, etxt_password;
    private Button btn_login;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_page);
        initialize();
    }

    private void initialize() {
        SETTINGS = PreferenceManager.getDefaultSharedPreferences(LoginPage.this);
        etxt_username = (EditText) findViewById(R.id.etxt_username);
        etxt_password = (EditText) findViewById(R.id.etxt_password);
        btn_login = (Button) findViewById(R.id.btn_login);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isNetworkAvailable()) {
                    authenticateLogin();
                } else {
                    displayAlertDialog("Network error", "There is no internet connection.", android.R.drawable.ic_dialog_alert);
                }
            }
        });

        checkCurrentLogin();
    }

    private void checkCurrentLogin() {
        if (SETTINGS.getBoolean(C.loggedIn, false)) {
            etxt_username.setText(SETTINGS.getString(C.userName, ""));
            etxt_password.setText(SETTINGS.getString(C.passWord, ""));
            btn_login.performClick();
        }
    }

    private void authenticateLogin() {
        String username = etxt_username.getText().toString();
        String password = etxt_password.getText().toString();
        new Login(username, password).execute();
    }

    private class Login extends AsyncTask<String, String, String> {
        private HttpHandler httpHandler;
        private ProgressDialog progressDialog;
        private String requestURL, username, password;
        private User mUser;
        private boolean loggedIn;

        Login(String username, String password) {
            requestURL = C.URL_USERS + "?username=" + username + "&password=" + password;
            httpHandler = new HttpHandler();
            this.username = username;
            this.password = password;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = ProgressDialog.show(LoginPage.this, "", "Logging in", true, false);
        }

        @Override
        protected String doInBackground(String... params) {
            try {
                String results = httpHandler.makeServiceCall(requestURL);
                JSONArray resultsArr = new JSONArray(results);

                if (resultsArr.length() != 0) {
                    JSONObject user = resultsArr.getJSONObject(0);
                    loggedIn = true;
                    mUser = getUser(user.getString("id"));

                    Log.d("id", user.getString("id"));
                    Log.d("last_name", user.getString("last_name"));
                    Log.d("first_name", user.getString("first_name"));
                    Log.d("username", user.getString("username"));
                    Log.d("password", user.getString("password"));
                } else {
                    loggedIn = false;
                }
            } catch (JSONException e) {
                loggedIn = false;
                Log.e("JSON err", e.toString());
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            progressDialog.dismiss();
            if (loggedIn) {
                SETTINGS.edit().putBoolean(C.loggedIn, loggedIn).apply();
                SETTINGS.edit().putString(C.userName, username).apply();
                SETTINGS.edit().putString(C.passWord, password).apply();
                Intent goToHomePage = new Intent(LoginPage.this, HomePage.class);
                goToHomePage.putExtra(C.TAG_USER_EXTRA, mUser);
                finish();
                startActivity(goToHomePage);
//                displayAlertDialog("Log in success", "You have logged in successfully.", android.R.drawable.ic_dialog_alert);
            } else {
                displayAlertDialog("Invalid login", "Your login credentials do not match.", android.R.drawable.ic_dialog_alert);
            }
        }
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    private void displayAlertDialog(String title, String message, int icon) {
        AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(this, android.R.style.Theme_Material_Dialog_Alert);
        } else {
            builder = new AlertDialog.Builder(this);
        }

        builder.setTitle(title)
                .setPositiveButton("Ok", null)
                .setMessage(message)
                .setIcon(icon)
                .show();
    }

    public static User getUser(String id) {
        String first_name = "";
        String last_name = "";
        String username = "";
        String password = "";
        String type = "";
        String position = "";
        String rate = "";
        String sss = "";
        String hdmf = "";
        String phic = "";
        ArrayList<LogEntry> logEntries = new ArrayList<>();
        HttpHandler handler = new HttpHandler();
        String url_user = C.URL_USERS + "?id=" + id;
        String url_profile = C.URL_PROFILES + "?user=" + id;
        String url_finances = C.URL_FINANCES + "?user=" + id;
        String url_log_entry = C.URL_LOG_ENTRY + "?user=" + id;

        try {
            String user_results = handler.makeServiceCall(url_user);
            JSONArray user_arr = new JSONArray(user_results);
            JSONObject user = user_arr.getJSONObject(0);
            first_name = user.getString("first_name");
            last_name = user.getString("last_name");
            username = user.getString("username");
            password = user.getString("password");

            String profile_results = handler.makeServiceCall(url_profile);
            JSONArray profile_arr = new JSONArray(profile_results);
            JSONObject profile = profile_arr.getJSONObject(0);
            type = profile.getString("type");
            position = profile.getString("position");

            String finance_results = handler.makeServiceCall(url_finances);
            JSONArray finance_arr = new JSONArray(finance_results);
            JSONObject finance = finance_arr.getJSONObject(0);
            rate = finance.getString("rate");
            sss = finance.getString("sss");
            hdmf = finance.getString("hdmf");
            phic = finance.getString("phic");

            String log_entry_results = handler.makeServiceCall(url_log_entry);
            JSONArray log_entries_arr = new JSONArray(log_entry_results);
            for (int i = 0; i < log_entries_arr.length(); i++) {
                JSONObject logEntry = log_entries_arr.getJSONObject(i);
                logEntries.add(new LogEntry(logEntry.getString("type"), logEntry.getString("value")));
            }

        } catch (JSONException e) {
            Log.e("JSON error", e.toString());
        }

        return new User(id, first_name, last_name, username, password, type, position, rate, sss, hdmf, phic, logEntries);
    }
}
