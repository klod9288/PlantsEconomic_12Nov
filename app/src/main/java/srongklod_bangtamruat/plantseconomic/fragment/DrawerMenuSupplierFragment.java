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
 * Created by Administrator on 14/1/2561.
 */

public class DrawerMenuSupplierFragment extends Fragment {

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

//        Create ListView
        createListView();


    }

    private void createListView() {

        ListView listView = getView().findViewById(R.id.listViewSupplierDrawer);
        Myconstan myconstan = new Myconstan();

//        Icon
        int[] ints = myconstan.getIconSupplier();

//        Title
        String[] strings = myconstan.getTitleSupplierStrings();

        DrawerListViewAdapter drawerListViewAdapter = new DrawerListViewAdapter(
                getActivity(),
                ints,
                strings);
        listView.setAdapter(drawerListViewAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                switch (position) {

                    case 0:
//                        Home

                        getActivity().getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.contentServiceFragment, new SupplierShowFragment())
                                .commit();

                        break;
                    case 1:
//                        Message
                        break;
                    case 2:
//                        News
                        break;
                    case 3:
//                        Shop

                        getActivity().getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.contentServiceFragment, new ShopSupplierFragment())
                                .commit();

                        break;


                }//switch

                ((ServiceActivity) getActivity()).myCloseDrawer();

            }//onItemClick
        });


    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_drawer_menu_supplier, container, false);

        return view;
    }
}
