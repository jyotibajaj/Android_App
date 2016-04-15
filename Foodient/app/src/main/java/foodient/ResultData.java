package foodient;


import org.json.JSONObject;

/**
 * Class to define network response callback methods
 */
abstract public class ResultData {
    /**
     * Should be called with parse json response object
     * @param jObj json response object
     */
    public void onSuccess(JSONObject jObj) {
        always();

    }

    /**
     * Should be called when network call results in failure
     * @param jObj error response
     */
    public void onFail(JSONObject jObj) {
        always();
    }
    /**
     * Should be call in either case;
     * Should be used to perform tasks that should happen irrespective of the result e.g. hiding
     * the spinner
     */
    public void always() {
    }
}
