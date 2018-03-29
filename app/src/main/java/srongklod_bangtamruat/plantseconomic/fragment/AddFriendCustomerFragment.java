package srongklod_bangtamruat.plantseconomic.fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.sql.Array;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;
import srongklod_bangtamruat.plantseconomic.R;
import srongklod_bangtamruat.plantseconomic.utility.AddFriendAdapter;
import srongklod_bangtamruat.plantseconomic.utility.CustomerModel;
import srongklod_bangtamruat.plantseconomic.utility.MyAlert;
import srongklod_bangtamruat.plantseconomic.utility.MyChangeArrayListToArray;
import srongklod_bangtamruat.plantseconomic.utility.PostModel;

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
                final String[] nameStrings = new String[timeAInt];
                final String[] surNameStrings = new String[timeAInt];
                final String[] imageStrings = new String[timeAInt];
                final String[] uidFriendStrings = new String[timeAInt];

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
                    uidFriendStrings[timeInts[0]] = customerModel1.getUidUserString();

                    timeInts[0] += 1;

                }//for

//                Set Adapter Here
                AddFriendAdapter addFriendAdapter = new AddFriendAdapter(getActivity(),
                        nameStrings, surNameStrings, imageStrings);
                listView.setAdapter(addFriendAdapter);

                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        Log.d("29MarchV1", "Click Name ==>" + nameStrings[position]);
                        confirmAddFriend(
                                nameStrings[position],
                                surNameStrings[position],
                                imageStrings[position],
                                uidFriendStrings[position],
                                "Customer");

                    }
                });


            }//OnDataChange

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    private void confirmAddFriend(String nameString,
                                  String surNameString,
                                  String imageString,
                                  final String uidFriendString,
                                  String positionString) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setCancelable(false);
        builder.setIcon(R.drawable.ic_action_add_friend);
        builder.setTitle("Confirm Add Friend");

        LayoutInflater layoutInflater = getActivity().getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.alert_confirm_frind, null);
        builder.setView(view);

        TextView nameTextView = view.findViewById(R.id.txtName);
        TextView surTextView = view.findViewById(R.id.txtSurName);
        TextView positionTextView = view.findViewById(R.id.txtPosition);
        CircleImageView circleImageView = view.findViewById(R.id.imvAvata);

        nameTextView.setText(nameString);
        surTextView.setText(surNameString);
        positionTextView.setText(positionString);

        Picasso.with(getActivity()).load(imageString).into(circleImageView);

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.dismiss();

            }
        });
        builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                updateFirendToFirebase(uidFriendString);

                dialog.dismiss();
            }
        });
        builder.show();


    }//Confirm Add Friend

    private void updateFirendToFirebase(String uidFriendString) {

//        Find uid Current Login
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("LoginFile", Context.MODE_PRIVATE);
        String resultFormSharePreferenc = sharedPreferences.getString("Login", null);
        Log.d("29MarchV2", "Result" + resultFormSharePreferenc);

        MyChangeArrayListToArray myChangeArrayListToArray = new MyChangeArrayListToArray(getActivity());
        String[] loginStrings = myChangeArrayListToArray.myArray(resultFormSharePreferenc);
        Log.d("29MarchV2", "uid Logined ==> " + loginStrings[3]);

        if (uidFriendString.equals(loginStrings[3])) {
//            Choose mySalfe
            MyAlert myAlert = new MyAlert(getActivity());
            myAlert.nomalDialog("Choose My Salfe","Please Choose Another Friend");


        } else {

            Calendar calendar = Calendar.getInstance();
            DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm");
            String dateTimeString = dateFormat.format(calendar.getTime());
            String postString = "Start Add Friend";

            PostModel postModel = new PostModel(dateTimeString, postString);

//            Process add friend
            FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
            DatabaseReference databaseReference = firebaseDatabase.getReference().child("FriendCustomer")
                    .child("Friend-" + loginStrings[3]);

            databaseReference.child(uidFriendString).setValue(postModel);


        }//if


    }//update friend

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_friend, container, false);
        return view;
    }
}//main Class
