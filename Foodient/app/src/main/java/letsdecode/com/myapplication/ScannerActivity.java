package letsdecode.com.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import me.dm7.barcodescanner.zxing.*;
import me.dm7.barcodescanner.zxing.ZXingScannerView;

/**
 * Activity responsible to capture the UPC code
 */
public class ScannerActivity extends Activity implements ZXingScannerView.ResultHandler {
    /**
     * ScannerView object
     */
    private ZXingScannerView mScannerView;

    /**
     * Diplay the camera
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mScannerView = new ZXingScannerView(this);   // Programmatically initialize the scanner view
        setContentView(mScannerView);
    }

    @Override
    public void onResume() {
        super.onResume();
        mScannerView.setResultHandler(this); // Register ourselves as a handler for scan results.
        mScannerView.startCamera();          // Start camera on resume
    }

    @Override
    public void onPause() {
        super.onPause();
        mScannerView.stopCamera();           // Stop camera on pause
    }

    /**
     * Called by the ZXing lib as soon as UPCCODE is captured through ZXingScannerView
     * @param rawResult
     */
    @Override
    public void handleResult(com.google.zxing.Result rawResult) {
        // Do something with the result here
        Log.v("result", rawResult.getText()); // Prints scan results
        Log.v("Scanner", rawResult.getBarcodeFormat().toString()); // Prints the scan format (qrcode, pdf417 etc.)
        Intent data = new Intent();
        String upcCodeValue = rawResult.getText();
        data.putExtra("UPC_CODE", upcCodeValue);
        setResult(RESULT_OK, data);
        //Job done, end the activity so that result could be returned
        finish();
    }
}
