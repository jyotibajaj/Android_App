package foodient;



/**
 * Class representing each row in nutrients table
 */
public class Item {
    /**
     * Name for the Nutrient
     */
    public String key;
    /**
     * Quantity of the Nutrient
     */
    public String value;

    /**
     * Ctr
     * @param key Nutrient
     * @param value Quantity
     */
    public Item(String key, String value){
        this.key = key;
        this.value = value;

    }

    /**
     * Returns Nutrient name
     * @return Nutrient name
     */
    public String getKeyFromItem() {
        return key;
    }

    /**
     * Quantity of the Nutrient
     * @return Quantity
     */
    public String getValueFromItem() {
        return value;
    }
}
