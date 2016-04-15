package letsdecode.com.myapplication;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONObject;

import foodient.NetworkManager;
import foodient.Request;
import foodient.ResultData;
import foodient.UPCModel;


public class MainActivity extends Activity  {
    public static final String Extra_Message = "letsdecode.com.myapplication.MESSAGE";
    EditText editText;

    public void alertMessage() {
        new AlertDialog.Builder(this)
                .setTitle("Invalid upcCode")
                .setMessage("Please enter valid upcCode")
                .setNeutralButton("OK", null)
                .show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText = (EditText) findViewById(R.id.edit_message);
    }

    /**
     * Called when search button is clicked
     * @param view
     */
    public void onClickSendButton(View view) {

        final String upcCode = editText.getText().toString();
        if (upcCode.length()<= 0) {
            Toast.makeText(this, "Enter the upc Code", Toast.LENGTH_SHORT).show();

        } else {
            overOnSubmit(upcCode);
        }
    }

    /**
     * Submit to the network manager
     * @param upcCode UPC Code
     * @param result Result object
     */
    public void onSubmit(String upcCode, ResultData result) {
        Request req = new Request(upcCode);
        NetworkManager network = new NetworkManager();
        network.submit(req, result);
    }

    /**
     * Start the spinner and create the ResultData object before submitting to the NetworkManager
     * @param upcCode UPC Code
     */
    public void overOnSubmit(String upcCode) {
        final ProgressDialog dialog = ProgressDialog.show(this, "Searching", "Please wait...", true);
        onSubmit(upcCode, new ResultData() {
            @Override
            public void onSuccess(JSONObject jObj) {
                super.onSuccess(jObj);
                final UPCModel upcModel = new UPCModel(jObj);
                final Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                intent.putExtra(Extra_Message, jObj.toString());
                intent.putExtra("data", upcModel);
                MainActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //Start the activity
                        startActivity(intent);
                    }
                });
            }

            @Override
            public void onFail(JSONObject obj) {
                super.onFail(obj);
                MainActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //Error occurred; show the error message
                        alertMessage();
                    }
                });

            }

            @Override
            public void always() {
                super.always();
                dialog.dismiss();
            }

        });
    }

    /**
     * Starts the scanner activity
     * @param view
     */
    public void onClickScanButton(View view) {
        Intent scannerIntent = new Intent(MainActivity.this, ScannerActivity.class);
        startActivityForResult(scannerIntent, 2);
    }

    /**
     * called when scanner activity return the UPC code
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 2) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                // ScannerActivity upc = new ScannerActivity();
                final String upcCode = data.getStringExtra("UPC_CODE");
                //send UPC code to get server response
                overOnSubmit(upcCode);

            }
        }
    }


}

