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
import srongklod_bangtamruat.plantseconomic.utility.Myconstan;

public class ShowShopCustomerFragment extends Fragment{

    private TextView hub0TextView,hub1TextView,hub2TextView,hub3TextView, hub4TextView;


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

//        InitialView
        initialView();

//        ShowView
        showView();


//        SetController
        setController();


    }//Main Method

    private void showView() {
        Myconstan myconstan = new Myconstan();
        String[] strings = myconstan.getCategoryShopStrings();

        hub0TextView.setText(strings[0]);
        hub1TextView.setText(strings[1]);
        hub2TextView.setText(strings[2]);
        hub3TextView.setText(strings[3]);
        hub4TextView.setText(strings[4]);

    }

    private void setController() {


        hub0TextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showListShop(0);


            }
        });
        hub1TextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showListShop(1);

            }
        });
        hub2TextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showListShop(2);

            }
        });
        hub3TextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showListShop(3);

            }
        });
        hub4TextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showListShop(4);

            }
        });
    }//SetController

    private void showListShop(int index) {

        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.contentServiceFragment,ListShopWhereByCategoryFragment
                        .listShopWhereByCategoryInstance(index))
                .commit();

    }

    private void initialView() {
        hub0TextView = getView().findViewById(R.id.txtHub0);
        hub1TextView = getView().findViewById(R.id.txtHub1);
        hub2TextView = getView().findViewById(R.id.txtHub2);
        hub3TextView = getView().findViewById(R.id.txtHub3);
        hub4TextView = getView().findViewById(R.id.txtHub4);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_show_shop_customer, container, false);
        return view;
    }
}//Main Class
