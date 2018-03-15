package srongklod_bangtamruat.plantseconomic.fragment;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.Random;

import de.hdodenhof.circleimageview.CircleImageView;
import srongklod_bangtamruat.plantseconomic.R;
import srongklod_bangtamruat.plantseconomic.utility.CustomerModel;
import srongklod_bangtamruat.plantseconomic.utility.MyAlert;

/**
 * Created by Administrator on 12/11/2560.
 */

public class CustomerRegisterFragment extends Fragment {
    //    Explicit
    private String nameString, surNameString, emailString,
            passwordString, phoneString, uidUserString;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;
    private CustomerModel customerModel;
    private FirebaseUser firebaseUser;
    private UserProfileChangeRequest userProfileChangeRequest;
    private ProgressDialog progressDialog;
    private CircleImageView circleImageView;
    private Uri uri;
    private boolean circleImageABoolean = true;


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

//        Save Controller
        saveController();

//        CircleImageView Controller
        circleImageController();



    }//Main Method

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode==getActivity().RESULT_OK) {

            uri = data.getData();
            circleImageABoolean = false;

//            Setup Image to CircleImageView
            try {

                Bitmap bitmap = BitmapFactory.decodeStream(getActivity().getContentResolver().openInputStream(uri));
                circleImageView.setImageBitmap(bitmap);



            } catch (Exception e) {
                e.printStackTrace();


            }


        }//if


    }//onActivityResult

    private void circleImageController() {
        circleImageView = getView().findViewById(R.id.imvAvatar);
        circleImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(Intent.createChooser(intent, "Please Choose App"),1);




            }
        });
    }

    private void saveController() {

        Button button = getView().findViewById(R.id.btnSaveCustomer);
        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

//                Initial EditText
                EditText nameEditText = getView().findViewById(R.id.edtName);
                EditText surNameEditText = getView().findViewById(R.id.edtSurname);
                EditText emailEditText = getView().findViewById(R.id.edtEmail);
                EditText passwordEditText = getView().findViewById(R.id.edtPassword);
                EditText phoneEditText = getView().findViewById(R.id.edtPhone);

//                Get Value from EditText
                nameString = nameEditText.getText().toString().trim();
                surNameString = surNameEditText.getText().toString().trim();
                emailString = emailEditText.getText().toString().trim();
                passwordString = passwordEditText.getText().toString().trim();
                phoneString = phoneEditText.getText().toString().trim();

//                Check Space
                if (checkSpace()) {

//                    HaveSpec
                    MyAlert myAlert = new MyAlert(getActivity());
                    myAlert.nomalDialog(getResources().getString(R.string.title_have_space),
                            getResources().getString(R.string.massage_have_space));

                } else if (circleImageABoolean) {
//                    Non Choose Image
                    MyAlert myAlert = new MyAlert(getActivity());
                    myAlert.nomalDialog(getResources().getString(R.string.title_Choose_image),
                            getResources().getString(R.string.message_choose_image));

                } else {
                    confirmValue();
                }


            }//On Click
        });

    }

    private void confirmValue() {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setIcon(R.drawable.ic_action_upload);
        builder.setCancelable(false);
        builder.setTitle("please Confirm Value");
        builder.setMessage("Name = " + nameString + "\n" +
                "Surname = " + surNameString + "\n" +
                "Email = " + emailString + "\n" +
                "Password = " + passwordString + "\n" +
                "Phone = " + phoneString);
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                uploadValueFirebase();
                dialogInterface.dismiss();
            }
        });
        builder.show();

    }//ConfirmValue

    private void uploadValueFirebase() {

        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setTitle("Please Wait few Minus...");
        progressDialog.show();

//        Add to Authentication
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.createUserWithEmailAndPassword(emailString, passwordString)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {
//                            Register Success
                            registerSuccess();

                        } else {
//                            Something Error
                            String resultError = task.getException().getMessage();
                            MyAlert myAlert = new MyAlert(getActivity());
                            myAlert.nomalDialog(getString(R.string.title_connot_register), resultError);

                        }

                    }   // onComplete
                });

    }   // uploadValueFiebase

    private void registerSuccess() {

        final String tag = "15MarchV1";

//        Find uid of Current User
        firebaseUser = firebaseAuth.getCurrentUser();
        uidUserString = firebaseUser.getUid();
        Log.d(tag, "uidUserString from getCurrentUser ==> " + uidUserString);

//        Create Name of Image
        Random random = new Random();
        int i = random.nextInt(1000);
        String nameImageString = uidUserString + "_" + Integer.toString(i);
        Log.d(tag, "nameImageString ==>" + nameImageString);


//        Upload Image to Firebase
        FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
        StorageReference storageReference = firebaseStorage.getReference();
        StorageReference storageReference1=storageReference.child("Avata/"+nameImageString);
        storageReference1.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {




            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                Log.d(tag, "e OnFailure ==>" + e.toString());

            }
        });


//        Setup Model
        customerModel = new CustomerModel(
                uidUserString,
                nameString,
                surNameString,
                phoneString,
                nameImageString);


        userProfileChangeRequest = new UserProfileChangeRequest
                .Builder()
                .setDisplayName(nameString)
                .build();

        firebaseUser.updateProfile(userProfileChangeRequest).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {

                Log.d(tag, "onSuccess Work");

//                Add to Database
                databaseReference = FirebaseDatabase.getInstance().getReference().child("Customer");
                databaseReference.child(uidUserString).setValue(customerModel);

            }
        });

        progressDialog.dismiss();

        Toast.makeText(getActivity(), "Register Success",
                Toast.LENGTH_SHORT).show();


        getActivity().getSupportFragmentManager().popBackStack();


    }//Register Success


    private boolean checkSpace() {
        return nameString.equals("")
                || surNameString.equals("")
                || emailString.equals("")
                || passwordString.equals("")
                || phoneString.equals("");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_customer_register, container, false);
        return view;

    }
}//Main Class