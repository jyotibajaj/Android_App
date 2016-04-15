package foodient;

public class Request {
    String upcCode;

    public Request(String upcCode) {
        this.upcCode = upcCode;
    }
	/*
	 * set upc take the input from user.
	 */

    // concatenate the upc and create the url
    public String getUrl() {
//Old
//        String url = "https://api.nutritionix.com/v1_1/item?upc=" + upcCode
//                + "&appId=5ed436d0&appKey=4052568eda5a051afe585e6811962773";

// new
        String url = "https://api.nutritionix.com/v1_1/item?upc=" + upcCode
                + "&appId=17cefcfc&appKey=5b25754c0ef75e60047c21077a5833f5";


        return url;
    }

}
