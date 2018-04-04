package srongklod_bangtamruat.plantseconomic.utility;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import de.hdodenhof.circleimageview.CircleImageView;
import srongklod_bangtamruat.plantseconomic.R;

public class ShowTimeLineAdapter extends BaseAdapter {

    private Context context;
    private String[] imageString, nameString, surNmaeString, messageString, showDateString;

    public ShowTimeLineAdapter(Context context, String[] imageString, String[] nameString, String[] surNmaeString, String[] messageString, String[] showDateString) {
        this.context = context;
        this.imageString = imageString;
        this.nameString = nameString;
        this.surNmaeString = surNmaeString;
        this.messageString = messageString;
        this.showDateString = showDateString;
    }

    @Override
    public int getCount() {
        return imageString.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.listview_show_time_line_friend, parent, false);
        CircleImageView circleImageView = view.findViewById(R.id.cirImvIcon);
        Picasso.with(context).load(imageString[position]).into(circleImageView);
        TextView nameTextView = view.findViewById(R.id.txtName);
        TextView surnameTextView = view.findViewById(R.id.txtsurName);
        TextView messageTextView = view.findViewById(R.id.txtMessage);
        TextView showDateTextView = view.findViewById(R.id.txtShowDate);

        nameTextView.setText(nameString[position]);
        surnameTextView.setText(surNmaeString[position]);
        messageTextView.setText(messageString[position]);
        showDateTextView.setText(showDateString[position]);


        return view;
    }
}//Main Class
