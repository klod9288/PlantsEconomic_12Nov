package srongklod_bangtamruat.plantseconomic.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import srongklod_bangtamruat.plantseconomic.R;

public class EditShopFragment extends Fragment{

    private String childEditString;



    public static EditShopFragment editShopInstance(String keyString) {

        EditShopFragment editShopFragment = new EditShopFragment();
        Bundle bundle = new Bundle();
        bundle.putString("KeyString",keyString);
        editShopFragment.setArguments(bundle);
        return editShopFragment;


    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

//        Get Value From Argument
        childEditString = getArguments().getString("KeyString");
        Log.d("9AprilV3", "childEditString ==>" + childEditString);


    }//Main Method

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_shop, container, false);

        return view;
    }
}//Main Class
