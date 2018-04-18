package srongklod_bangtamruat.plantseconomic.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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

import java.util.ArrayList;
import java.util.List;

import srongklod_bangtamruat.plantseconomic.R;
import srongklod_bangtamruat.plantseconomic.utility.MessageListAdapter;
import srongklod_bangtamruat.plantseconomic.utility.MessageModel;

public class MessageListFragment extends Fragment{

    private String uidLoginString;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

//        Find UidLoginString
        findUidLoginString();


//        Create RecyclerView
        createRecyclerView();

    }//MainMethod

    private void findUidLoginString() {

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        uidLoginString = firebaseUser.getUid();
        Log.d("18AprilV3", "uidLoginString ==> " + uidLoginString);


    }

    private void createRecyclerView() {

        final RecyclerView recyclerView = getView().findViewById(R.id.recyclerViewMessage);

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference()
                .child("Supplier")
                .child(uidLoginString)
                .child("Message");

        final int[] timesInts = new int[]{0};

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                List list = new ArrayList();
                ArrayList<String> dateStringArrayList = new ArrayList<>();
                ArrayList<String> nameAnSurStringArrayList = new ArrayList<>();

                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {

                    MessageModel messageModel = dataSnapshot1.getValue(MessageModel.class);
                    list.add(messageModel);

                    MessageModel messageModel1 = (MessageModel) list.get(timesInts[0]);

                    dateStringArrayList.add(messageModel1.getCurrentDateString());
                    nameAnSurStringArrayList.add(messageModel1.getNameAnSurnameString());

                    timesInts[0] += 1;
                }//for


                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(),
                        LinearLayoutManager.VERTICAL,false);
                recyclerView.setLayoutManager(linearLayoutManager);


                MessageListAdapter messageListAdapter = new MessageListAdapter(getActivity(),
                        dateStringArrayList, nameAnSurStringArrayList);
                recyclerView.setAdapter(messageListAdapter);


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_message_list, container, false);
        return view;
    }
}
