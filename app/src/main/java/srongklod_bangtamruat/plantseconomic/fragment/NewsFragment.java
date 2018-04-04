package srongklod_bangtamruat.plantseconomic.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import srongklod_bangtamruat.plantseconomic.R;
import srongklod_bangtamruat.plantseconomic.utility.MyAlert;
import srongklod_bangtamruat.plantseconomic.utility.MyChangeArrayListToArray;
import srongklod_bangtamruat.plantseconomic.utility.NewsModel;

public class NewsFragment extends Fragment{

    private String newsString,dateString;
    private String[] loginStrings;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

//        FindDate
        findDate();

//        Find Login
        findLogin();

//        Post Controller
        postController();


    }//Main Method

    private void findDate() {

        Calendar calendar = Calendar.getInstance();
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        dateString = dateFormat.format(calendar.getTime());

    }

    private void findLogin() {

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("LoginFile", Context.MODE_PRIVATE);
        String s = sharedPreferences.getString("Login", null);
        Log.d("4AprilV3", "result ==>" + s);

        MyChangeArrayListToArray myChangeArrayListToArray = new MyChangeArrayListToArray(getActivity());
        loginStrings = myChangeArrayListToArray.myArray(s);


    }

    private void postController() {

        Button button = getView().findViewById(R.id.btnPost);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                findDate();

                EditText editText = getView().findViewById(R.id.edtNews);
                newsString = editText.getText().toString().trim();

                if (newsString.isEmpty()) {
                    MyAlert myAlert = new MyAlert(getActivity());
                    myAlert.nomalDialog(getString(R.string.title_have_space), getString(R.string.massage_have_space));
                } else {
                    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                    DatabaseReference databaseReference = firebaseDatabase.getReference()
                            .child("FriendCustomer")
                            .child("News-"+loginStrings[3]);

                    NewsModel newsModel = new NewsModel(newsString);



                    Map<String, Object> stringObjectMap = new HashMap<String, Object>();
                    stringObjectMap.put(dateString, newsString);
                    databaseReference.updateChildren(stringObjectMap);

                    Toast.makeText(getActivity(),"Post Your News Success",Toast.LENGTH_SHORT).show();

                    editText.setText("");


                } //if

            }
        });

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news, container, false);
        return view;
    }
}//Main Class
