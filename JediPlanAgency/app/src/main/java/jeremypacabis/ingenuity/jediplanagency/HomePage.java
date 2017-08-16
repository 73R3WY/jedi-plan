package jeremypacabis.ingenuity.jediplanagency;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

/**
 * Created by Jeremy Patrick on 8/16/2017.
 * Author: Jeremy Patrick G. Pacabis
 * for jeremypacabis.ingenuity.jediplanagency @ JediPlanAgency
 */

public class HomePage extends AppCompatActivity {
    private static final String TAG_HOME = "home";
    private static final String TAG_PROFILE = "profile";
    private User mUser;

    private static String CURRENT_TAG = TAG_HOME;
    private String mTitle;
    private Handler mHandler;

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private ListView menuListView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_page);
        initialize();
        if (savedInstanceState == null) {
            selectMenu(0);
        }
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void initialize() {
        mUser = (User) getIntent().getSerializableExtra(C.TAG_USER_EXTRA);
        mHandler = new Handler();
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        menuListView = (ListView) findViewById(R.id.sidebar_lv_menu);
        menuListView.setAdapter(new MenuAdapter(getApplicationContext()));
        menuListView.setOnItemClickListener(new DrawerMenuItemClickListener());
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.drawer_open, R.string.drawer_close) {
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                getSupportActionBar().setTitle(mTitle);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getSupportActionBar().setTitle(R.string.menu);
            }
        };

        mDrawerLayout.addDrawerListener(mDrawerToggle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
    }

    private void selectMenu(int position) {
        final int v = position;
        Runnable mPendingRunnable = new Runnable() {
            @Override
            public void run() {
                Fragment fragment = getTargetFragment(v);
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
                fragmentTransaction.replace(R.id.content_frame, fragment, CURRENT_TAG);
                fragmentTransaction.commitAllowingStateLoss();
                getSupportActionBar().setTitle(mTitle);
            }
        };

        if (mPendingRunnable != null) {
            mHandler.post(mPendingRunnable);
        }

        menuListView.setItemChecked(position, true);
        mDrawerLayout.closeDrawers();
    }

    private Fragment getTargetFragment(int position) {
        HomeFragment homeFragment = new HomeFragment();
        homeFragment.setButtonClicked(new HomeFragment.ButtonClicked() {
            @Override
            public void ButtonClicked(String string) {
                Toast.makeText(HomePage.this, string, Toast.LENGTH_SHORT).show();

                switch (string) {
                    case C.CLICKED_VIEW_EMPLOYEES:
                        selectMenu(10);
                        break;
                    case C.CLICKED_VIEW_PAYROLL:
                        selectMenu(20);
                        break;
                    case C.CLICKED_VIEW_PAYSLIP:
                        selectMenu(30);
                        break;
                    case C.CLICKED_VIEW_REPORT:
                        selectMenu(40);
                        break;
                }
            }
        });

        switch (position) {
            case 0:
                mTitle = getApplicationContext().getResources().getString(R.string.app_name);
                return homeFragment;
            case 1:
                mTitle = getApplicationContext().getResources().getString(R.string.profile) + " (" + mUser.getUsername() + ")";
                return new ProfileFragment();
            case 10:
                mTitle = getApplicationContext().getResources().getString(R.string.employees);
                return new EmployeesFragment();
            case 20:
                mTitle = getApplicationContext().getResources().getString(R.string.payroll);
                return homeFragment;
            case 30:
                mTitle = getApplicationContext().getResources().getString(R.string.payslip);
                return homeFragment;
            case 40:
                mTitle = getApplicationContext().getResources().getString(R.string.report);
                return homeFragment;
            default:
                return homeFragment;
        }
    }

    private class DrawerMenuItemClickListener implements ListView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            if (position == 2) {
                confirmLogout();
            } else {
                selectMenu(position);
            }
        }
    }

    public User getUser() {
        return mUser;
    }

    private void confirmLogout() {
        AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(this, android.R.style.Theme_Material_Dialog_Alert);
        } else {
            builder = new AlertDialog.Builder(this);
        }

        builder.setTitle("Confirm logout")
                .setPositiveButton("Logout", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SharedPreferences SETTINGS = PreferenceManager.getDefaultSharedPreferences(HomePage.this);
                        SETTINGS.edit().putBoolean(C.loggedIn, false).apply();
                        finish();
                        startActivity(new Intent(HomePage.this, LoginPage.class));
                    }
                })
                .setNegativeButton("No", null)
                .setMessage("Do you wish to logout?")
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }
}
