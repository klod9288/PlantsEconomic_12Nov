package srongklod_bangtamruat.plantseconomic.utility;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;
import srongklod_bangtamruat.plantseconomic.R;

/**
 * Created by Administrator on 11/2/2561.
 */

public class AddFriendAdapter extends BaseAdapter{

    private Context context;
    private String[] nameStrings,surNameStrings,imageStrings;


    public AddFriendAdapter(Context context, String[] nameStrings, String[] surNameStrings, String[] imageStrings) {
        this.context = context;
        this.nameStrings = nameStrings;
        this.surNameStrings = surNameStrings;
        this.imageStrings = imageStrings;
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
        View view1 = layoutInflater.inflate(R.layout.listview_add_friend, viewGroup, false);

        TextView nameTextView= view1.findViewById(R.id.txtName);
        nameTextView.setText(nameStrings[i]);


        TextView surTextView = view1.findViewById(R.id.txtsurName);
        surTextView.setText(surNameStrings[i]);

        CircleImageView circleImageView = view1.findViewById(R.id.cirImvIcon);
        Picasso.with(context).load(imageStrings[i]).into(circleImageView);

        return view1;
    }
}//Main Class
