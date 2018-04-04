package srongklod_bangtamruat.plantseconomic.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import srongklod_bangtamruat.plantseconomic.R;
import srongklod_bangtamruat.plantseconomic.ServiceActivity;
import srongklod_bangtamruat.plantseconomic.utility.MyAlert;
import srongklod_bangtamruat.plantseconomic.utility.MyChangeArrayListToArray;
import srongklod_bangtamruat.plantseconomic.utility.PostModel;
import srongklod_bangtamruat.plantseconomic.utility.ShowTimeLineAdapter;

/**
 * Created by Administrator on 30/12/2560.
 */

public class CustomerShowFragment extends Fragment {

    //    Explicit

    private String[] loginStrings,nameFriendStrings,surNameFriendStrings,imageFriendStrings;
    private String[] strings=new String[3];
    private ArrayList<String>nameStringArrayList,surStringArrayList, imageStringArrayList;
    private int amountFriendAnInt,timesAnInt=0;


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

//        Setup ArrayList
        setupArrayList();

//            Find uid Login
        findUidLogin();

//        Create listFriend
        createListFriend();


    }//Main Method

    private void setupArrayList() {

        nameStringArrayList = new ArrayList<>();
        surStringArrayList = new ArrayList<>();
        imageStringArrayList = new ArrayList<>();

    }

    private void createListFriend() {

        ListView listView = getView().findViewById(R.id.listViewShowFriend);
        final int[] countInts = new int[]{0};

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference().child("FriendCustomer")
                .child("Friend-"+loginStrings[3]);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                int i = (int) dataSnapshot.getChildrenCount();
                amountFriendAnInt = i;
                List list = new ArrayList();

                nameFriendStrings = new String[i];
                surNameFriendStrings = new String[i];
                imageFriendStrings = new String[i];


                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {

                    try {

                        PostModel postModel = dataSnapshot1.getValue(PostModel.class);
                        list.add(postModel);

                        PostModel postModel1 = (PostModel) list.get(countInts[0]);
                        String uidFriend = postModel1.getUidFriendString();
                        Log.d("29MarchV3", "uidFriend ==> " + uidFriend);

                        findNameSurnameImage(uidFriend);


                        countInts[0] += 1;

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }//for

                Log.d("4AprilV1", "NameArrayList ==>" + nameStringArrayList.toString());

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {


            }
        });



    }//CreateList Friend

    private void findNameSurnameImage(String uidFriend) {

        final String[] strings = new String[3];

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference()
                .child("Customer").child(uidFriend.trim());
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Map map = (Map) dataSnapshot.getValue();
                strings[0] = String.valueOf(map.get("nameString"));
                strings[1] = String.valueOf(map.get("lastNameString"));
                strings[2] = String.valueOf(map.get("urlImageString"));

                Log.d("29MarchV3", "map ==>" + map.toString());

                nameStringArrayList.add(strings[0]);
                surStringArrayList.add(strings[1]);
                imageStringArrayList.add(strings[2]);

                MyChangeArrayListToArray myChangeArrayListToArray = new MyChangeArrayListToArray(getActivity());
                String[] showNameStrings = myChangeArrayListToArray.myArray(nameStringArrayList.toString());
                String[] showSurNameStrings = myChangeArrayListToArray.myArray(surStringArrayList.toString());
                String[] showImageStrings = myChangeArrayListToArray.myArray(imageStringArrayList.toString());
                String[] showMessageString = new String[nameStringArrayList.size()];
                String[] showDateString = new String[nameStringArrayList.size()];



                if (timesAnInt<amountFriendAnInt) {


                 for (int i=0;i<showNameStrings.length;i+=1) {

                     Log.d("4AprilV2", "Name["+i+"] ==> "+showNameStrings[i]);
                     Log.d("4AprilV2", "SurName["+i+"] ==> "+showSurNameStrings[i]);
                     Log.d("4AprilV2", "Image[" + i + "] ==> " + showImageStrings[i]);

                     showMessageString[i] = "Test Message";
                     showDateString[i] = "4-04-2018 09:56";

                 }

                    ListView listView = getView().findViewById(R.id.listViewShowFriend);
                    ShowTimeLineAdapter showTimeLineAdapter = new ShowTimeLineAdapter(
                            getActivity(),showImageStrings,
                            showNameStrings,showSurNameStrings,
                            showMessageString,showDateString);
                    listView.setAdapter(showTimeLineAdapter);


                } else {
                    timesAnInt += 1;
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

                Log.d("29MarchV3", "Error on findNameSurnameImage ==>" + databaseError.toString());

            }
        });



    }

    private void findUidLogin() {

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("LoginFile", Context.MODE_PRIVATE);
        String s = sharedPreferences.getString("Login", null);
        MyChangeArrayListToArray myChangeArrayListToArray = new MyChangeArrayListToArray(getActivity());
        loginStrings = myChangeArrayListToArray.myArray(s);
        Log.d("29MarchV3", "uid Login ==>" + loginStrings[3]);

    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_customer_show, container, false);
        return view;

    }
}//Main Class
