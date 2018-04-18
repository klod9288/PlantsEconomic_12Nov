package srongklod_bangtamruat.plantseconomic.utility;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import srongklod_bangtamruat.plantseconomic.R;

public class MessageListAdapter extends RecyclerView.Adapter<MessageListAdapter.MyViewHolder>{

    private Context context;
    private List<String> dateStringList, nameAnSurStringList;
    private LayoutInflater layoutInflater;

    public MessageListAdapter(Context context, List<String> dateStringList, List<String> nameAnSurStringList) {
        this.layoutInflater = LayoutInflater.from(context);
        this.dateStringList = dateStringList;
        this.nameAnSurStringList = nameAnSurStringList;
    }//Constructor

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = layoutInflater.inflate(R.layout.recycleview_message, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        String dateString = dateStringList.get(position);
        String nameAnSurString = nameAnSurStringList.get(position);

        holder.dateTextView.setText(dateString);
        holder.nameAnSurTextView.setText(nameAnSurString);


    }

    @Override
    public int getItemCount() {
        return dateStringList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {


        private TextView dateTextView, nameAnSurTextView;

        public MyViewHolder(View itemView) {
            super(itemView);

//            Initial View
            dateTextView = itemView.findViewById(R.id.txtDate);
            nameAnSurTextView = itemView.findViewById(R.id.txtNameAnSur);

        }//Constructor
    }//MyViewHolder


}//Main Class
