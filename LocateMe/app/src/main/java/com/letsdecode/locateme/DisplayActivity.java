package com.letsdecode.locateme;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.support.v7.widget.ShareActionProvider;

import com.crashlytics.android.Crashlytics;

import io.fabric.sdk.android.Fabric;


public class DisplayActivity extends ActionBarActivity {
    /**
     * constants
     */
    public static final String MyPREFERENCES = "MyPrefs";
    public static final String SECRET_CODE = "secretKey";
    int REQUEST_CODE = 1;
    public static TextView savedSecretCode;
    private Button editButton;
    public static SharedPreferences mSharedpreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //crashylitics takes care of any crashes.
        Fabric.with(this, new Crashlytics());
        //xml object
        setContentView(R.layout.display_activiity);
        //capturing TextView
        savedSecretCode = (TextView) findViewById(R.id.savedSecretCode);
        editButton = (Button) findViewById(R.id.edit);
        //
        mSharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        if (mSharedpreferences != null) {
            String code = mSharedpreferences.getString(SECRET_CODE, "");
            if (code.isEmpty()) {
                onEditClicked();
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        //this method wud diplay the saved code value on displayActivity.
        sharedPreferencesFunction();


    }

    private void sharedPreferencesFunction() {
        mSharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        if (mSharedpreferences.getString(SECRET_CODE, "").isEmpty() == false) {
            final String secretCodeDisplay = mSharedpreferences.getString(SECRET_CODE, "");
            savedSecretCode.setText("saved code " + secretCodeDisplay);
            //editButton clickListener
            editButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onEditClicked();
                }
            });
        }
    }

    private void onEditClicked() {
        Intent intentDisplay = new Intent(this, EditActivity.class);
        startActivityForResult(intentDisplay, REQUEST_CODE);
    }

    // onActivityResult is used to retrieve the value set by editActivity, to display in the diplayActivity.

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        String code;
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE) {

            if (resultCode == RESULT_OK) {
                code = data.getStringExtra("codeValue");
                if (code != null && code.isEmpty() == false) {
                    SharedPreferences.Editor editor = mSharedpreferences.edit();
                    editor.putString(SECRET_CODE, code);
                    editor.apply();
                }
            }
            final String secretCode = mSharedpreferences.getString(SECRET_CODE, "");
            if (secretCode == null || secretCode.isEmpty()) {
                finish();
            }
        }
    }

    /* This is invoked when back is pressed on editActivity,
     as we have fair idea that back will be called only when save button is not clicked on editActivity
     and hence we would have to show previously saved code in the display activity
      */
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        String secretCode = mSharedpreferences.getString(SECRET_CODE, "");
        savedSecretCode.setText("saved code " + secretCode);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_display_activiity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.share_option) {
            MenuItem shareItem = item;
            final ShareActionProvider shareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(shareItem);
            if (shareActionProvider != null) {
                final Intent i = getCommonActionIntent("I am using Locate Me!", "This app is cool. It lets me find where my friends" +
                        " and family members are. Give it a try! https://play.google.com/store/apps/details?id=com.letsdecode.locateme");
                shareActionProvider.setShareIntent(i);
                return true;
            }

        }
        if (item.getItemId() == R.id.send_code_option) {
            SharedPreferences sharedPreferences = mSharedpreferences;
            if (sharedPreferences != null) {
                MenuItem shareItem = item;
                final ShareActionProvider shareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(shareItem);
                final String code = sharedPreferences.getString(SECRET_CODE, "");
                if (shareActionProvider != null && code != null && code.isEmpty() == false) {
                    final Intent i = getCommonActionIntent("Track Me Using This Code - " + code, "Sms me the " + code + " if you want to track my current location");
                    shareActionProvider.setShareIntent(i);
                    return true;
                }
            }
        }

        return super.onOptionsItemSelected(item);
    }

    private Intent getCommonActionIntent(String subject, String message) {
        Intent i = new Intent(android.content.Intent.ACTION_SEND);
        //set the type
        i.setType("text/plain");
        //add a subject
        i.putExtra(android.content.Intent.EXTRA_SUBJECT,
                subject);
        i.putExtra(android.content.Intent.EXTRA_TEXT,
                message);
        return i;
    }


}

