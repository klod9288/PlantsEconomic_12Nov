package srongklod_bangtamruat.plantseconomic.utility;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import srongklod_bangtamruat.plantseconomic.R;

public class ShopSupplierAdapter extends BaseAdapter{

    private Context context;
    private String[] nameStrings,descriptionStrings,priceStrings,stockStrings, urlImageStrings;

    public ShopSupplierAdapter(Context context,
                               String[] nameStrings,
                               String[] descriptionStrings,
                               String[] priceStrings,
                               String[] stockStrings,
                               String[] urlImageStrings) {
        this.context = context;
        this.nameStrings = nameStrings;
        this.descriptionStrings = descriptionStrings;
        this.priceStrings = priceStrings;
        this.stockStrings = stockStrings;
        this.urlImageStrings = urlImageStrings;
    }

    @Override
    public int getCount() {
        return nameStrings.length;
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
        View view = layoutInflater.inflate(R.layout.listview_shop_supplier, parent, false);

        ImageView imageView = view.findViewById(R.id.imvProduct);
        Picasso.with(context).load(urlImageStrings[position]).resize(180,180).into(imageView);

        TextView nameTextView = view.findViewById(R.id.txtName);
        TextView descriptionTextView = view.findViewById(R.id.txtDescription);
        TextView priceTextView = view.findViewById(R.id.txtPrice);
        TextView stockTextView = view.findViewById(R.id.txtStock);

        nameTextView.setText(nameStrings[position]);
        descriptionTextView.setText(descriptionStrings[position]);
        priceTextView.setText(priceStrings[position]);
        stockTextView.setText(stockStrings[position]);

        return view;
    }
}
