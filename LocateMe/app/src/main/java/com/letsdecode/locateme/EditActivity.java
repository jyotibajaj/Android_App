package com.letsdecode.locateme;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.content.Intent;


public class EditActivity extends Activity {
    public EditText secretCodeEdit;
    TextView enterSecretCode;
    public Button saveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_activity);
        enterSecretCode = (TextView) findViewById(R.id.enterSecretCode);
        enterSecretCode.setText("Set your secret code");
        saveButton = (Button) findViewById(R.id.save);
        secretCodeEdit = (EditText) findViewById(R.id.secretCodeEdit);
         /* Set Text Watcher listener */
        secretCodeEdit.addTextChangedListener(secretCodeWatcher);
        ActionBar actionBar = getActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                 onBackHomePressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private final TextWatcher secretCodeWatcher = new TextWatcher() {
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        public void onTextChanged(CharSequence s, int start, int before, int count) {
            secretCodeEdit.setVisibility(View.VISIBLE);
        }

        public void afterTextChanged(Editable s) {
            final String secretCode = secretCodeEdit.getText().toString();
            if (secretCode.length() == 4) {
                saveButton.setEnabled(true);
                saveButton.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        finishOnDone();
                    }
                });

            } else {
                saveButton.setEnabled(false);
            }
        }
    };

    private void finishOnDone() {
        String secretCodeValue = secretCodeEdit.getText().toString();
        if (secretCodeValue != null && secretCodeValue.isEmpty() == false) {
            Intent codeIntent = new Intent(getApplicationContext(), DisplayActivity.class);
            codeIntent.putExtra("codeValue", secretCodeValue);
            setResult(RESULT_OK, codeIntent);
        } else {
            setResult(RESULT_CANCELED);
        }
        finish();
    }


    private void onBackHomePressed() {

    String secretCode = DisplayActivity.mSharedpreferences.getString(DisplayActivity.SECRET_CODE, "");
    DisplayActivity.savedSecretCode.setText("saved code "+ secretCode);
        Intent codeIntent = new Intent(getApplicationContext(), DisplayActivity.class);
        codeIntent.putExtra("codeValue", secretCode);
        setResult(RESULT_OK, codeIntent);
        finish();
    }


}
