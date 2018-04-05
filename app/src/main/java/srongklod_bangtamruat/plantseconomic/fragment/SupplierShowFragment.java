package srongklod_bangtamruat.plantseconomic.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import srongklod_bangtamruat.plantseconomic.R;
import srongklod_bangtamruat.plantseconomic.utility.MyChangeArrayListToArray;

/**
 * Created by Administrator on 30/12/2560.
 */

public class SupplierShowFragment extends Fragment{

    private String[] supplierStrings;


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

//        Get Value From sharedPreferences
        getValueFromSharePreferece();

//        Show Text

        showText();


    }//Mani Activity

    private void getValueFromSharePreferece() {

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("LoginFile", Context.MODE_PRIVATE);
        String result = sharedPreferences.getString("Login", null);

        MyChangeArrayListToArray myChangeArrayListToArray = new MyChangeArrayListToArray(getActivity());
        supplierStrings = myChangeArrayListToArray.myArray(result);

    }

    private void showText() {

        TextView textView = getView().findViewById(R.id.txtShowSupplier);
        textView.setText(supplierStrings[2]);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_supplier_show, container, false);


        return view;
    }
}//Main Class
