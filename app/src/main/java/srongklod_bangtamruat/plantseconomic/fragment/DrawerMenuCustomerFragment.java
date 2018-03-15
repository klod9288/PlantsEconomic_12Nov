package srongklod_bangtamruat.plantseconomic.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import srongklod_bangtamruat.plantseconomic.R;
import srongklod_bangtamruat.plantseconomic.utility.DrawerListViewAdapter;
import srongklod_bangtamruat.plantseconomic.utility.Myconstan;

/**
 * Created by Administrator on 31/12/2560.
 */

public class DrawerMenuCustomerFragment extends Fragment{

    private String[] loginStrings;

    public static DrawerMenuCustomerFragment drawerMenuCustomerInstance(String[] loginStrings) {

        DrawerMenuCustomerFragment drawerMenuCustomerFragment = new DrawerMenuCustomerFragment();
        Bundle bundle = new Bundle();
        bundle.putStringArray("Login",loginStrings);
        drawerMenuCustomerFragment.setArguments(bundle);
        return drawerMenuCustomerFragment;

    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

//        Get Value From Argument
        loginStrings = getArguments().getStringArray("Login");


//        Create ListView
        createListView();


    }

    private void createListView() {

        ListView listView = getView().findViewById(R.id.listViewCustomerDrawer);
        Myconstan myconstan = new Myconstan();

//        Icon
        int[] ints = myconstan.getIconCustomerInts();

//        Title
        String[] strings = myconstan.getTitleCustomerStrings();

        DrawerListViewAdapter drawerListViewAdapter = new DrawerListViewAdapter(getActivity()
        ,ints,strings);
        listView.setAdapter(drawerListViewAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                switch (i) {

                    case 0:

                        getActivity().getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.contentServiceFragment, CustomerShowFragment.customerShowInstance(loginStrings))
                                .commit();

                        break;
                    case 1:

                        getActivity().getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.contentServiceFragment, new AddFriendCustomerFragment())
                                .commit();

                        break;
                }//switch


            }
        });

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_drawer_menu_customer, container, false);

        return view;
    }
}
