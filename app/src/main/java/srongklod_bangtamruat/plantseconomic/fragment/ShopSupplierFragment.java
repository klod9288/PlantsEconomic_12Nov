package srongklod_bangtamruat.plantseconomic.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import srongklod_bangtamruat.plantseconomic.R;

public class ShopSupplierFragment extends Fragment{

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        tryAddShop();

    }//Main Method

    private void tryAddShop() {
        TextView textView = getView().findViewById(R.id.txtAddShop);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addShopSupplier();
            }
        });
    }

    private void addShopSupplier() {

        getActivity()
                .getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.contentServiceFragment,new AddShopFragment())
                .commit();


    }//addShopSupplier

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shop_supplier, container, false);
        return view;
    }


}//Main Class
