package letsdecode.com.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import foodient.Item;
import foodient.MyAdapter;
import foodient.UPCModel;

/**
 * Activity that shows the detail of the product nutrients
 */
public class DetailActivity extends Activity {
    /**
     * List view to display the data
     */
    private ListView mListView;
    /**
     * Data to display
     */
    private List<Item> listData;
    /**
     * Network response containing json data
     */
    private UPCModel mUpcModel;
    /**
     * Filter to allow only selected items in the list
     */
    private static ArrayList<Integer> listAllowedItem = new ArrayList<Integer>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity2);

        mListView = (ListView) findViewById(R.id.list);
        //Get the message from the intent defined in main activity.
        Intent intent = getIntent();
        mUpcModel = (UPCModel) intent.getSerializableExtra("data");

        listData = buildContent(mUpcModel.mData);

        ListView listView = (ListView) findViewById(R.id.list);

        listView.setAdapter(new MyAdapter(this, listData));

    }

    /**
     * Build the filter
     */
    static {
        listAllowedItem.add(R.string.brand_name);
        listAllowedItem.add(R.string.item_name);
        listAllowedItem.add(R.string.nf_serving_weight_grams);

        listAllowedItem.add(R.string.nf_calories);
        listAllowedItem.add(R.string.nf_calories_from_fat);

        listAllowedItem.add(R.string.nf_total_fat);
        listAllowedItem.add(R.string.nf_saturated_fat);
        listAllowedItem.add(R.string.nf_trans_fatty_acid);

        listAllowedItem.add(R.string.nf_total_carbohydrate);
        listAllowedItem.add(R.string.nf_sugars);

        listAllowedItem.add(R.string.nf_protein);
        listAllowedItem.add(R.string.nf_vitamin_c_dv);
        listAllowedItem.add(R.string.nf_vitamin_a_dv);

        listAllowedItem.add(R.string.nf_cholesterol);
        listAllowedItem.add(R.string.nf_dietary_fiber);
        listAllowedItem.add(R.string.nf_iron_dv);
        listAllowedItem.add(R.string.allergen_contains_eggs);
        listAllowedItem.add(R.string.allergen_contains_fish);
        listAllowedItem.add(R.string.allergen_contains_peanuts);
        listAllowedItem.add(R.string.allergen_contains_gluten);
    }

    /**
     * Applies the filter on the data received.
     * @param map data received
     * @return list of item to display
     */
    private List<Item> buildContent(HashMap<Integer, String> map) {
        String keyAsString;
        Integer key;
        ArrayList<Item> list = new ArrayList<Item>();
        for (Integer listItem : listAllowedItem) {
            for (Map.Entry<Integer, String> entry : map.entrySet()) {
                if (map.get(listItem) != null) {
                    String value = map.get(listItem);
                    key = listItem;
                    keyAsString = getResources().getString(key);
                    if (value.equals("null")) {
                        value = "-";
                    } else {
                        String suffix = getMeasureText(key);
                        value += " " + suffix;
                    }

                    list.add(new Item(keyAsString, value));
                    break;
                }
            }
        }
        return list;
    }

    /**
     * Provides the unit of the quantity for each nutrient based on the nutrient type
     * @param res Nutrient type
     * @return Unit suffix e.g. mg, g, %
     */
    private String getMeasureText(int res) {
        switch (res) {

            case R.string.nf_calories_from_fat:
            case R.string.nf_total_fat:
            case R.string.nf_saturated_fat:
            case R.string.nf_monounsaturated_fat:
            case R.string.nf_polyunsaturated_fat:
            case R.string.nf_trans_fatty_acid:
            case R.string.nf_total_carbohydrate:
            case R.string.nf_dietary_fiber:
            case R.string.nf_sugars:
            case R.string.nf_protein:
            case R.string.nf_serving_weight_grams:
                return "g";

            case R.string.nf_cholesterol:
            case R.string.nf_sodium:
                return "mg";

            case R.string.nf_vitamin_a_dv:
            case R.string.nf_vitamin_c_dv:
            case R.string.nf_iron_dv:
                return "% DV";
//            case R.string.nf_calcium_dv:

//                return "%";
//            case R.string.nf_serving_size_qty:
            case R.string.nf_calories:
            case R.string.nf_serving_size_unit:
            default:
                return "";
        }
    }
}

