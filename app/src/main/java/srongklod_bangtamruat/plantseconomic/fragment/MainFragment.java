package srongklod_bangtamruat.plantseconomic.fragment;

import android.content.Intent;
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
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import srongklod_bangtamruat.plantseconomic.R;
import srongklod_bangtamruat.plantseconomic.ServiceActivity;
import srongklod_bangtamruat.plantseconomic.utility.MyAlert;

/**
 * Created by Administrator on 5/11/2560.
 */

public class MainFragment extends Fragment{

//    Explicit
    private String emailString, passwordString;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;


//    Main Method
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

//        Check Status
        checkStatusLogin();


//        Register Control
        registerControll();

//        SignIn Controller
        signInController();

    }//Main Method

    private void checkStatusLogin() {

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        if (firebaseAuth.getCurrentUser() !=null) {

            intentToService();

        } else {

        }
    }

    @Override
    public void onResume() {
        super.onResume();

        try {

            FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
            firebaseAuth.signOut();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void signInController() {

        Button button = getView().findViewById(R.id.btnSignIn);
        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

//                Initial Edittext

                EditText emailEditText = getView().findViewById(R.id.edtEmail);
                EditText passwordEditText = getView().findViewById(R.id.edtPassword);

//                Get Value From Edittext
                emailString = emailEditText.getText().toString().trim();
                passwordString = passwordEditText.getText().toString().trim();

//                Check Space

                if (emailString.isEmpty()||passwordString.isEmpty()) {
//                    Have Space
                    MyAlert myAlert = new MyAlert(getActivity());
                    myAlert.nomalDialog(getString(R.string.title_have_space),
                            getString(R.string.massage_have_space));

                } else {
//                    No Space
                    checkEmailAndPass();

                }


            }//OnClick
        });

    }

    private void checkEmailAndPass() {

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.signInWithEmailAndPassword(emailString, passwordString)
        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()) {

//                    Sign In Success
                    Toast.makeText(getActivity(),"SignIn Success",
                            Toast.LENGTH_SHORT).show();

//                    Find UserUid
                    firebaseUser = firebaseAuth.getCurrentUser();
                    String uidString = firebaseUser.getUid();
                    Log.d("30DecV1", "uidString ==>" + uidString);

//                    Intent to ServiceActivity
                    intentToService();


                } else {
//                    Sign In Non Success
                    String result = task.getException().getMessage();
                    MyAlert myAlert = new MyAlert(getActivity());
                    myAlert.nomalDialog("SignIn False",result);
                }

            }
        });



    }

    private void intentToService() {
        Intent intent = new Intent(getActivity(), ServiceActivity.class);
        startActivity(intent);
        getActivity().finish();//close
    }

    private void registerControll() {
        TextView textView = getView().findViewById(R.id.txtRegister);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                Replace fragment
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.contentMainFragment, new RegisterFragment())
                        .addToBackStack(null)
                        .commit();

            }//OnClick
        });

    }

    //    Create View of Fragment
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        return view;
    }
}//Main Class
