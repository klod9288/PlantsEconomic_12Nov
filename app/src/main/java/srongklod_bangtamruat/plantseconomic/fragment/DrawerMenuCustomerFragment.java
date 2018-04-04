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
import srongklod_bangtamruat.plantseconomic.ServiceActivity;
import srongklod_bangtamruat.plantseconomic.utility.DrawerListViewAdapter;
import srongklod_bangtamruat.plantseconomic.utility.Myconstan;

/**
 * Created by Administrator on 31/12/2560.
 */

public class DrawerMenuCustomerFragment extends Fragment{

    private String[] loginStrings;


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


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
//                        for Home
                        getActivity().getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.contentServiceFragment, new CustomerShowFragment())
                                .commit();

                        break;
                    case 1:
//                        for Friend
                        getActivity().getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.contentServiceFragment, new AddFriendCustomerFragment())
                                .commit();

                        break;
                    case 2:
//                        for Message
                        break;
                    case 3:
//                        for News
                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.contentServiceFragment,
                                new NewsFragment()).commit();

                        break;
                }//switch

                ((ServiceActivity)getActivity()).myCloseDrawer();

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
