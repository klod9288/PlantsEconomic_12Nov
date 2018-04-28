package srongklod_bangtamruat.plantseconomic.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import srongklod_bangtamruat.plantseconomic.R;
import srongklod_bangtamruat.plantseconomic.utility.Myconstan;

public class ListShopWhereByCategoryFragment extends Fragment{

    private int indexAnInt;

    public static ListShopWhereByCategoryFragment listShopWhereByCategoryInstance(int indexInt) {

        ListShopWhereByCategoryFragment listShopWhereByCategoryFragment = new ListShopWhereByCategoryFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("Index",indexInt);
        listShopWhereByCategoryFragment.setArguments(bundle);
        return listShopWhereByCategoryFragment;

    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

//        Show Category
        showCategory();



    }//MainMethod

    private void showCategory() {

        indexAnInt = getArguments().getInt("Index", 0);
        Log.d("28AprilV1", "Receive Index ==> " + indexAnInt);

        TextView textView = getView().findViewById(R.id.txtTitle);
        Myconstan myconstan = new Myconstan();
        String[] strings = myconstan.getCategoryShopStrings();
        textView.setText(strings[indexAnInt]);


    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_shop_category, container, false);
        return view;


    }//CreateView




}//Main Class
