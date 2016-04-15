package foodient;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.HashMap;

import letsdecode.com.myapplication.R;

/*
 * Defines and initialize the variables.
 * keys in case of JSONObject
 */
public class UPCModel implements Serializable {

    /**
     * Parsed data for the request kept in hash map for the faster access
     */
    public HashMap<Integer, String> mData = new HashMap<Integer, String>();
    private static HashMap<String, Integer> map = new HashMap<String, Integer>();

    public static final String BRAND = "brand_name";
    public static final String SODIUM = "nf_sodium";
    public static final String CALORIES_FROM_FAT = "nf_calories_from_fat";
    public static final String PROTEIN = "nf_protein";
    public static final String CARBOHYDRATE = "nf_total_carbohydrate";
    public static final String SUGAR = "nf_sugars";
    public static final String VITAMIN_C = "nf_vitamin_c_dv";
    public static final String VITAMIN_A = "nf_vitamin_a_dv";
    public static final String TOTAL_CALORIES = "nf_calories";
    public static final String ITEM_NAME = "item_name";
    public static final String ITEM_DESCRIPTION = "item_description";
    public static final String INGREDIENTS = "nf_ingredient_statement";
    public static final String WATER = "nf_water_grams";
    public static final String TOTAL_FAT = "nf_total_fat";
    public static final String SATURATED_FAT = "nf_saturated_fat";
    public static final String TRANS__FATTY_ACID = "nf_trans_fatty_acid";
    public static final String POLYUNSATURATED_FAT = "nf_polyunsaturated_fat";
    public static final String MONOUNSATURATED_FAT = "nf_monounsaturated_fat";
    public static final String CHOLESTROL = "nf_cholesterol";
    public static final String DIETERY_FIBER = "nf_dietary_fiber";
    public static final String IRON = "nf_iron_dv";
    public static final String SERVING_PER_CONTAINER = "nf_servings_per_container";
    public static final String SERVING_SIZE_UNIT = "nf_serving_size_unit";
    public static final String SERVING_WEIGHT_GRAMS = "nf_serving_weight_grams";
    public static final String CONTAINS_MILK = "allergen_contains_milk";
    public static final String CONTAINS_EGGS = "allergen_contains_eggs";
    public static final String CONTAINS_FISH = "allergen_contains_fish";
    public static final String CONTAINS_SHELLFISH = "allergen_contains_shellfish";
    public static final String CONTAINS_TREE_NUTS = "allergen_contains_tree_nuts";
    public static final String CONTAINS_PEANUTS = "allergen_contains_peanuts";
    public static final String CONTAINS_WHEAT = "allergen_contains_wheat";
    public static final String CONTAINS_SOYABEANS = "allergen_contains_soybeans";
    public static final String CONTAINS_GLUTEN = "allergen_contains_gluten";

    /**
     * Build keys to android string resource as map
     */
    static {
        map.put("brand_name", R.string.brand_name);
        map.put("nf_sodium", R.string.nf_sodium);
        map.put("nf_calories_from_fat", R.string.nf_calories_from_fat);
        map.put("nf_protein", R.string.nf_protein);
        map.put("nf_total_carbohydrate", R.string.nf_total_carbohydrate);
        map.put("nf_sugars", R.string.nf_sugars);
        map.put("nf_vitamin_c_dv", R.string.nf_vitamin_c_dv);
        map.put("nf_vitamin_a_dv", R.string.nf_vitamin_a_dv);
        map.put("nf_calories", R.string.nf_calories);
        map.put("item_name", R.string.item_name);
        map.put("item_description", R.string.item_description);
        map.put("nf_ingredient_statement", R.string.nf_ingredient_statement);
        map.put("nf_water_grams", R.string.nf_water_grams);
        map.put("nf_total_fat", R.string.nf_total_fat);
        map.put("nf_saturated_fat", R.string.nf_saturated_fat);
        map.put("nf_trans_fatty_acid", R.string.nf_trans_fatty_acid);
        map.put("nf_polyunsaturated_fat", R.string.nf_polyunsaturated_fat);
        map.put("nf_monounsaturated_fat", R.string.nf_monounsaturated_fat);
        map.put("nf_cholesterol", R.string.nf_cholesterol);
        map.put("nf_dietary_fiber", R.string.nf_dietary_fiber);
        map.put("nf_iron_dv", R.string.nf_iron_dv);
        map.put("nf_servings_per_container", R.string.nf_servings_per_container);
        map.put("nf_serving_size_unit", R.string.nf_serving_size_unit);
        map.put("nf_serving_weight_grams", R.string.nf_serving_weight_grams);
        map.put("allergen_contains_milk", R.string.allergen_contains_milk);
        map.put("allergen_contains_eggs", R.string.allergen_contains_eggs);
        map.put("allergen_contains_fish", R.string.allergen_contains_fish);
        map.put("allergen_contains_shellfish", R.string.allergen_contains_shellfish);
        map.put("allergen_contains_tree_nuts", R.string.allergen_contains_tree_nuts);
        map.put("allergen_contains_peanuts", R.string.allergen_contains_peanuts);
        map.put("allergen_contains_wheat", R.string.allergen_contains_wheat);
        map.put("allergen_contains_soybeans", R.string.allergen_contains_soybeans);
        map.put("allergen_contains_gluten", R.string.allergen_contains_gluten);
    }

    /**
     * Constructor
     * Parse the server response from json into the hah map
     * @param obj server response in json obj form
     */
    public UPCModel(JSONObject obj) {
        try {
            putValue(BRAND, obj);
            putValue(SODIUM, obj);
            putValue(CALORIES_FROM_FAT, obj);
            putValue(PROTEIN, obj);
            putValue(CARBOHYDRATE, obj);
            putValue(SUGAR, obj);
            putValue(VITAMIN_C, obj);
            putValue(VITAMIN_A, obj);
            putValue(TOTAL_CALORIES, obj);
            putValue(ITEM_NAME, obj);
            putValue(ITEM_DESCRIPTION, obj);
            putValue(INGREDIENTS, obj);
            putValue(WATER, obj);
            putValue(TOTAL_FAT, obj);
            putValue(SATURATED_FAT, obj);
            putValue(TRANS__FATTY_ACID, obj);
            putValue(POLYUNSATURATED_FAT, obj);
            putValue(MONOUNSATURATED_FAT, obj);
            putValue(CHOLESTROL, obj);
            putValue(DIETERY_FIBER, obj);
            putValue(IRON, obj);
            putValue(SERVING_PER_CONTAINER, obj);
            putValue(SERVING_SIZE_UNIT, obj);
            putValue(SERVING_WEIGHT_GRAMS, obj);
            putValue(CONTAINS_MILK, obj);
            putValue(CONTAINS_EGGS, obj);
            putValue(CONTAINS_SHELLFISH, obj);
            putValue(CONTAINS_FISH, obj);
            putValue(CONTAINS_TREE_NUTS, obj);
            putValue(CONTAINS_PEANUTS, obj);
            putValue(CONTAINS_WHEAT, obj);
            putValue(CONTAINS_SOYABEANS, obj);
            putValue(CONTAINS_GLUTEN, obj);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    /**
     * Helper method to put key and value into the hash map after looking into the json object
     * @param key
     * @param obj
     * @throws JSONException
     */
    private void putValue(final String key, final JSONObject obj) throws JSONException{
        String value = obj.get(key).toString();
        mData.put(map.get(key), value);
    }

}
