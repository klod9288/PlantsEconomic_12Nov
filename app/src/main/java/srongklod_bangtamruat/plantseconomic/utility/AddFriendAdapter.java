package srongklod_bangtamruat.plantseconomic.utility;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import srongklod_bangtamruat.plantseconomic.R;

/**
 * Created by Administrator on 11/2/2561.
 */

public class AddFriendAdapter extends BaseAdapter{

    private Context context;
    private String[] nameStrings;
    private int[] iconInts;

    public AddFriendAdapter(Context context,
                            String[] nameStrings,
                            int[] iconInts) {
        this.context = context;
        this.nameStrings = nameStrings;
        this.iconInts = iconInts;
    }

    @Override
    public int getCount() {
        return nameStrings.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view1 = layoutInflater.inflate(R.layout.layout_listview_add_friend, viewGroup, false);

        TextView textView= view1.findViewById(R.id.txtShowName);
        textView.setText(nameStrings[i]);

        ImageView imageView = view1.findViewById(R.id.imvIcon);
        imageView.setImageResource(iconInts[i]);



        return view1;
    }
}//Main Class
