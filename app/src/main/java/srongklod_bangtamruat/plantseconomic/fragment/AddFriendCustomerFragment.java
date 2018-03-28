package srongklod_bangtamruat.plantseconomic.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import srongklod_bangtamruat.plantseconomic.R;
import srongklod_bangtamruat.plantseconomic.utility.AddFriendAdapter;
import srongklod_bangtamruat.plantseconomic.utility.CustomerModel;

/**
 * Created by Administrator on 11/2/2561.
 */

public class AddFriendCustomerFragment extends Fragment{

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

//        Create ListView
        createListView();


    }//Main Method

    private void createListView() {

        final ListView listView = getView().findViewById(R.id.lisViewAddFriend);
        final int[] timeInts = new int[]{0};

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference().child("Customer");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                int timeAInt = (int) dataSnapshot.getChildrenCount();

                Log.d("28MarchV1", "timeAInt ==>" + timeAInt);
                String[] nameStrings = new String[timeAInt];
                String[] surNameStrings = new String[timeAInt];
                String[] imageStrings = new String[timeAInt];

                ArrayList<String> stringArrayList = new ArrayList<>();
                List list = new ArrayList();


                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {

                    CustomerModel customerModel = dataSnapshot1.getValue(CustomerModel.class);
                    list.add(customerModel);

                    CustomerModel customerModel1 = (CustomerModel) list.get(timeInts[0]);
                    Log.d("28MarchV1", "Name ==>" + customerModel1.getNameString());

                    nameStrings[timeInts[0] ]= customerModel1.getNameString();
                    surNameStrings[timeInts[0]] = customerModel1.getLastNameString();
                    imageStrings[timeInts[0]] = customerModel1.getUrlImageString();

                    timeInts[0] += 1;

                }//for

//                Set Adapter Here
                AddFriendAdapter addFriendAdapter = new AddFriendAdapter(getActivity(),
                        nameStrings, surNameStrings, imageStrings);
                listView.setAdapter(addFriendAdapter);


            }//OnDataChange

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_friend, container, false);
        return view;
    }
}//main Class
