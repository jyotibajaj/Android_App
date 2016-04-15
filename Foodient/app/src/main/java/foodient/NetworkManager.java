package foodient;

import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * NetworkManager accepts each network request and returns the result response success/failure
 * through the provided callback object;
 */
public class NetworkManager {

    public final static String TAG = "NetworkManager";

    /**
     * Submit method receives the general network request and executes it on a separate thread
     * @param req Object that represents network request
     * @param obj2 Callback object that should be called to return the result of the network request
     */
    public void submit(final Request req, final ResultData obj2) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                HttpClient client = new DefaultHttpClient();
                //Fetch Url from the request
                String url = req.getUrl();
                HttpGet request = new HttpGet(url);
                HttpResponse response = null;
                try {
                    response = client.execute(request);
                } catch (ClientProtocolException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                BufferedReader rd = null;
                try {
                    rd = new BufferedReader(new InputStreamReader(response
                            .getEntity().getContent()));
                } catch (IllegalStateException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                StringBuilder content = new StringBuilder();
                String line = "";
                //Read the network response as String
                try {
                    while (null != (line = rd.readLine())) {
                        content.append(line);
                    }
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

                try {
                    //Convert string into JSON objects
                    JSONObject obj = new JSONObject(content.toString());
                    System.out.println(obj);
                    //Send Response to the caller
                    if (obj.isNull("error_code") == false) {
                        obj2.onFail(obj);
                    } else {
                        obj2.onSuccess(obj);
                    }
                } catch (JSONException e) {
                    Log.e(TAG, e.toString());
                }

                Log.d(TAG, content.toString());

            }
        });
        thread.start();

    }


}
