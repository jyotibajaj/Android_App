package foodient;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import letsdecode.com.myapplication.R;


 /* MyAdapter sets the view of each row to be displayed.
 */

public class MyAdapter extends BaseAdapter {
    //context name
    private final Context context;
    // list name
    private List<Item> list;

    /*
    Constructor initializes the context and list
    @param context Context name
    @param list
     */

    public MyAdapter(Context context, List<Item> listData) {
        this.context = context;
        this.list = listData;
    }


    /**
     * Returns size of list
     *
     * @return list size.
     */

    @Override
    public int getCount() {
        return list.size();
    }

    /**
     * Returns object at given position
     *
     * @param position of the object in the list.
     * @return object.
     */

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    /**
     * Returns object at given position
     *
     * @param position of the object in the list.
     * @return object.
     */

    @Override
    public long getItemId(int position) {
        return position;
    }

    /*This method will be called to get the View for each row
    The fist argument passed to getView is the listview item position ie row number.
    The second parameter is recycled view reference
    Third parameter is the parent to which this view will get attached to.
     */
    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        //Check if view is already inflated
        if (view == null) {
            //Inflate the view
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.list_row_item, null);
        }

        TextView key = (TextView) view.findViewById(R.id.textView1);
        TextView value = (TextView) view.findViewById(R.id.textView2);
        Item item = list.get(position);

        //fill views with the data
        key.setText(item.getKeyFromItem());
        String valueString = item.getValueFromItem();
        if (valueString != null && valueString.equals("null") == false) {
            value.setText(valueString);
        } else {
            value.setText("-");
        }

        return view;
    }
}
