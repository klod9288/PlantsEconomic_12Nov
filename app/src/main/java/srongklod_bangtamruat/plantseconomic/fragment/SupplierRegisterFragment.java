package srongklod_bangtamruat.plantseconomic.fragment;

import android.app.ProgressDialog;
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

import de.hdodenhof.circleimageview.CircleImageView;
import srongklod_bangtamruat.plantseconomic.R;
import srongklod_bangtamruat.plantseconomic.ServiceActivity;
import srongklod_bangtamruat.plantseconomic.utility.MyAlert;
import srongklod_bangtamruat.plantseconomic.utility.SupplierModel;

/**
 * Created by Administrator on 12/11/2560.
 */

public class SupplierRegisterFragment extends Fragment {
    //    Explicit
    private String companyString, addressString, faxString,
            telephoneString, businessString, emailString,
            passwordString, headQuartersString,uidUserString,pathUrlImageString,
            statusString;//0 ==> Free, 1 ==> Wait ,2 ==> VIP
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    private DatabaseReference databaseReference;
    private ProgressDialog progressDialog;
    private SupplierModel supplierModel;
    private boolean avataABoolean = true;
    private Uri uri;
    private CircleImageView circleImageView;



    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

//        Avata Controller
        avataController();


//        Save Controller
        saveController();


    }

    private void avataController() {

        circleImageView = getView().findViewById(R.id.imvAvata);
        circleImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(Intent.createChooser(intent,"Please Choose App"),1);

            }
        });

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode==getActivity().RESULT_OK) {

            avataABoolean = false;
            uri = data.getData();


            try {

                Bitmap bitmap = BitmapFactory
                        .decodeStream(getActivity().getContentResolver()
                        .openInputStream(uri));
                circleImageView.setImageBitmap(bitmap);

            } catch (Exception e) {
                e.printStackTrace();
            }

        }


    }

    private void saveController() {

        Button button = getView().findViewById(R.id.btnSaveSupplier);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                EditText companyNameEditText = getView().findViewById(R.id.edtCompanyName);
                EditText addressEditText = getView().findViewById(R.id.edtAddress);
                EditText faxEditText = getView().findViewById(R.id.edtFax);
                EditText telephoneEditText = getView().findViewById(R.id.edtTelephone);
                EditText businessEditText = getView().findViewById(R.id.edtBusiness);
                EditText emailEditText = getView().findViewById(R.id.edtEmail);
                EditText passwordEditText = getView().findViewById(R.id.edtPassword);
                EditText headQuartersEditText = getView().findViewById(R.id.edtHead);

                companyString = companyNameEditText.getText().toString().trim();
                addressString = addressEditText.getText().toString().trim();
                faxString = faxEditText.getText().toString().trim();
                telephoneString = telephoneEditText.getText().toString().trim();
                businessString = businessEditText.getText().toString().trim();
                emailString = emailEditText.getText().toString().trim();
                passwordString = passwordEditText.getText().toString().trim();
                headQuartersString = headQuartersEditText.getText().toString().trim();

                MyAlert myAlert = new MyAlert(getActivity());

                if (avataABoolean) {

                    myAlert.nomalDialog(getResources().getString(R.string.title_Choose_image),
                            getResources().getString(R.string.message_choose_image));


                }else if (checkSpace()) {

//                    Have Space

                    myAlert.nomalDialog(getResources().getString(R.string.title_have_space),
                            getResources().getString(R.string.massage_have_space));

                } else {

//                    No Space
                    confirmValue();

                }//if


            }
        });

    }//on Click


    private void confirmValue() {


        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setIcon(R.drawable.ic_action_upload);
        builder.setCancelable(false);
        builder.setTitle("please Confirm Value");
        builder.setMessage("Company = "+companyString+"\n"+
        "Address = "+addressString+"\n"+
        "Fax = "+faxString+"\n"+
        "Telephone = "+telephoneString+"\n"+
        "Business = "+businessString+"\n"+
        "Email = "+emailString+"\n"+
        "Password = "+passwordString+"\n"+
        "Head Quarters = "+headQuartersString+"\n"+"\n"+
                "Please Choose Status");


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

            }//Onclick

        });
        builder.show();

    }//ConfirmValue

    private void uploadValueFirebase() {

//        Setup Progress
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setTitle("Please Wait few Minus...");
        progressDialog.show();


//        UpDate Authentications
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.createUserWithEmailAndPassword(emailString,passwordString)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {

//                            Register Success
                            registerSuccess();

                        } else {

//                            Register Error
                            String resultError = task.getException().getMessage();
                            MyAlert myAlert = new MyAlert(getActivity());
                            myAlert.nomalDialog("Cannot Register",resultError);

                        }

                    }//onComplete
                });


//        UploadDatabase


    }//UploadValueFierbase

    private void registerSuccess() {

//        ================================================================================================================


//        Update Image to Data


//        ================================================================================================================


        FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
        StorageReference storageReference=firebaseStorage.getReference()
                .child("LogoSupplier/"+companyString);

        storageReference.putFile(uri)
                .addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {


                        if (task.isSuccessful()) {

                            Log.d("13MayV1", "Upload Image Success");
                            findPath();

                        } else {

                            Log.d("13MayV1", "Cannot upload ==> " + task.getException().getMessage().toString());

                        }

                    }
                });




    }//Register Success

    private void findPath() {

        FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
        StorageReference storageReference = firebaseStorage.getReference()
                .child("LogoSupplier/" + companyString);


        final String[] strings=new String[]{""};

        storageReference.getDownloadUrl()
                .addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {

                        strings[0] = uri.toString();
                        pathUrlImageString = strings[0];
                        updateTextToFirebase();

                    }
                });

    }

    private void updateTextToFirebase() {
//        ================================================================================================================


//        Update Value to Database


//        ================================================================================================================

        final String tag = "27DecV2";

//        for Database
        firebaseUser = firebaseAuth.getCurrentUser();
        uidUserString = firebaseUser.getUid();
        Log.d(tag, "uid Current User ==> " + uidUserString);

//        Set upModel
        supplierModel = new SupplierModel(uidUserString,companyString,addressString,faxString
        ,telephoneString,businessString,headQuartersString,"0", pathUrlImageString);

        UserProfileChangeRequest userProfileChangeRequest = new UserProfileChangeRequest
                .Builder()
                .setDisplayName(companyString)
                .build();

        firebaseUser.updateProfile(userProfileChangeRequest).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {

                Log.d(tag, "onSuccess Work");
                databaseReference = FirebaseDatabase.getInstance().getReference().child("Supplier");
                databaseReference.child(uidUserString).setValue(supplierModel);

                progressDialog.dismiss();

                startActivity(new Intent(getActivity(), ServiceActivity.class));
                getActivity().finish();



            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d(tag, "Failure ==> " + e.toString());
            }
        });


//        For Authentication
        Toast.makeText(getActivity(),"Register Success",
                Toast.LENGTH_SHORT).show();

        progressDialog.dismiss();
    }

    private boolean checkSpace() {
        return companyString.equals("")||addressString.equals("")||faxString.equals("")||
                telephoneString.equals("")||
                businessString.equals("")||
                emailString.equals("")||
                passwordString.equals("")||
                headQuartersString.equals("");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_supplier_register, container, false);
        return view;

    }

}//Main Class
