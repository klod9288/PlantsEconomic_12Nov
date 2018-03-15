package srongklod_bangtamruat.plantseconomic.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;

import srongklod_bangtamruat.plantseconomic.R;

/**
 * Created by Administrator on 11/2/2561.
 */

public class AddFriendCustomerFragment extends Fragment{
    //    Explicit
    private DatabaseReference databaseReference;
    private FirebaseDatabase firebaseDatabase;
    private String uidLoginString,displayString;
    private ArrayList<String>uidStringArrayList,displayStringArrayList, avataStringArrayList;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

//        Find uidLoginString
        findUidLoginString();

        Log.d("15MarchV2", "uidString ==>" + uidLoginString);


//        Read all child Customer
        readAllChildCustomer();

    }//Main Method


    private ArrayList<String> collectItem(Map<String,Object>objectMap,String chilString) {

        ArrayList<String> stringArrayList = new ArrayList<>();
        for (Map.Entry<String, Object> entry : objectMap.entrySet()) {
            Map map = (Map) entry.getValue();
            stringArrayList.add((String) map.get(chilString));

        }

        return stringArrayList;
    }

    private void collectDisplayName(Map<String,Object>displayNameMap) {
        ArrayList<String> stringArrayList = new ArrayList<>();
        for (Map.Entry<String, Object> entry : displayNameMap.entrySet()) {
            Map map = (Map) entry.getValue();
            stringArrayList.add((String) map.get("nameString"));
        }
        Log.d("15MarchV2", "Display All Before ==>" + stringArrayList.toString());
        stringArrayList.remove(displayString);
        Log.d("15MarchV2", "Display All After ==>" + stringArrayList.toString());

    }



    private void findUidLoginString() {
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        uidLoginString = firebaseUser.getUid();
        displayString = firebaseUser.getDisplayName();
        Log.d("15MarchV2", "uidLoginString ==>" + uidLoginString.toString());
        Log.d("15MarchV2", "displayString ==>" + displayString.toString());

    }

    private void collectUIDcustomer(Map<String, Object> customerMap) {

        ArrayList<String> stringArrayList = new ArrayList<>();
        for (Map.Entry<String, Object> entry : customerMap.entrySet()) {
            Map map = (Map) entry.getValue();
            stringArrayList.add((String) map.get("uidUserString"));

        }
        Log.d("15MarchV2", "stringArrayList Before ==>" + stringArrayList.toString());

        stringArrayList.remove(uidLoginString);

        Log.d("15MarchV2", "stringArrayList After ==>" + stringArrayList.toString());

    }


    private void readAllChildCustomer() {
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference().child("Customer");
        final String tag= "15MarchVV3";

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                uidStringArrayList = collectItem((Map<String, Object>) dataSnapshot.getValue(), "nameString");
                uidStringArrayList.remove(uidLoginString);
                Log.d(tag,"uidStrinArrayList ==> " +uidStringArrayList.toString());

                displayStringArrayList = collectItem((Map<String, Object>) dataSnapshot.getValue(), "nameString");
                displayStringArrayList.remove(displayString);
                Log.d(tag,"displayStrinArrayList ==> " +displayStringArrayList.toString());

                avataStringArrayList = collectItem((Map<String, Object>) dataSnapshot.getValue(), "avataString");
                Log.d(tag,"avataStrinArrayList ==> " +avataStringArrayList.toString());



            }//onData change

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
