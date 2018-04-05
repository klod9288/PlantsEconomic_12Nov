package srongklod_bangtamruat.plantseconomic.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
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
import android.widget.ImageView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.Random;

import srongklod_bangtamruat.plantseconomic.R;
import srongklod_bangtamruat.plantseconomic.utility.MyAlert;

public class AddShopFragment extends Fragment{

    private boolean aBoolean = true;
    private ImageView imageView;
    private Uri uri;
    private String nameString,descriptionString,priceString,
            stockString,displayString,uidLoginString;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

//        Find Login
        findLogin();

//        Image Controller
        imageController();

//        Button Controller
        buttonController();

    }//Main Method

    private void findLogin() {

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();

       displayString = firebaseUser.getDisplayName();
       uidLoginString = firebaseUser.getUid();

        Log.d("5AprilV1", "display ==>" + displayString);
        Log.d("5AprilV1", "uidLogin ==>" + uidLoginString);

    }

    private void buttonController() {

        Button button = getView().findViewById(R.id.btnAddShop);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText nameEditText = getView().findViewById(R.id.edtName);
                EditText descriptionEditText = getView().findViewById(R.id.edtDescription);
                EditText priceEditText = getView().findViewById(R.id.edtPrice);
                EditText stockEditText = getView().findViewById(R.id.edtStock);

                nameString = nameEditText.getText().toString().trim();
                descriptionString = descriptionEditText.getText().toString().trim();
                priceString = priceEditText.getText().toString().trim();
                stockString = stockEditText.getText().toString().trim();

                MyAlert myAlert = new MyAlert(getActivity());

                if (aBoolean) {
                    myAlert.nomalDialog(getString(R.string.title_Choose_image),
                            getString(R.string.message_choose_image));
                } else if (checkSpace()) {
                    myAlert.nomalDialog(getString(R.string.title_have_space),
                            getString(R.string.massage_have_space));
                } else {
//                    Upload Image
                    Random random = new Random();
                    int i = random.nextInt(10000);

                    FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
                    StorageReference storageReference = firebaseStorage.getReference()
                    .child("ShopSupplier/"+uidLoginString+"-"+Integer.toString(i));



                    storageReference.putFile(uri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            Log.d("5AprilV1", "Upload Image Success");

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                            Log.d("5AprilV1", "Cannot UpLoad Image e ==>" + e.toString());

                        }
                    });


                } //if

            }
        });

    }

    private boolean checkSpace() {
        return nameString.isEmpty() ||
                descriptionString.isEmpty()||
                priceString.isEmpty()||
                stockString.isEmpty();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode==getActivity().RESULT_OK) {

            aBoolean = false;

            uri = data.getData();


            try {

                Bitmap bitmap = BitmapFactory
                        .decodeStream(getActivity().getContentResolver().openInputStream(uri));
                imageView.setImageBitmap(bitmap);

            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }

    private void imageController() {


        imageView = getView().findViewById(R.id.imvAddShop);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(Intent.createChooser(intent,"Please Choose Image"), 1);

            }
        });

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_shop, container, false);

        return view;
    }
}//Main Class
