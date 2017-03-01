package com.ofs.ofmc.home;

import android.animation.Animator;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.ofs.ofmc.BaseFragment;
import com.ofs.ofmc.R;
import com.ofs.ofmc.exceptions.EmptyTextException;
import com.ofs.ofmc.exceptions.InvalidFieldException;
import com.ofs.ofmc.model.Employee;
import com.ofs.ofmc.toolbox.Constants;
import com.ofs.ofmc.toolbox.SharedPref;
import com.ofs.ofmc.toolbox.Utils;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.io.IOException;

/**
 * Created by saravana.subramanian on 2/21/17.
 */

public class ProfileView extends BaseFragment implements HomeContract.ViewProfile {

    HomeContract.PresenterProfile presenter;
    private View rootView;
    private String userId;
    private ImageView imageView;
    private EditText UserName;
    private EditText Email;
    private EditText EmployeeId;
    private EditText Department;
    private EditText Location;
    private EditText Extension;
    private Button   submit;
    private ImageView locationChooser;
    private ImageView departmentChooser;
    Employee employee;
    Context context;
    SharedPref sharedPref;
    StorageReference storageReference;

    PopupMenu locationMenu;
    PopupMenu departmentMenu;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.profile,container,false);
        sharedPref = new SharedPref();
        context = getActivity();

        getActivity().setTitle(getString(R.string.ofs_profile));
        storageReference = FirebaseStorage.getInstance().getReference();
        imageView = (ImageView) rootView.findViewById(R.id.emp_Image);
        UserName = (EditText) rootView.findViewById(R.id.userName);
        Email = (EditText) rootView.findViewById(R.id.email);
        EmployeeId = (EditText) rootView.findViewById(R.id.emp_id);
        Department = (EditText) rootView.findViewById(R.id.department);
        Location = (EditText) rootView.findViewById(R.id.phase);
        Extension = (EditText) rootView.findViewById(R.id.extension);
        locationChooser =(ImageView)rootView.findViewById(R.id.locationSelector);
        departmentChooser =(ImageView)rootView.findViewById(R.id.departmentSelector);
        submit = (Button) rootView.findViewById(R.id.register);

        locationMenu = new PopupMenu(context, locationChooser);
        departmentMenu = new PopupMenu(context, departmentChooser);
        userId = getArguments().getString(Constants.EXTRA_USERID)!= null ?
                getArguments().getString(Constants.EXTRA_USERID):sharedPref.getString(context,SharedPref.PREFS_USERID);
        rootView.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {

            @TargetApi(Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                v.removeOnLayoutChangeListener(this);
                int cx = getArguments().getInt(Constants.MOTION_X_ARG);
                int cy = getArguments().getInt(Constants.MOTION_Y_ARG);
                float finalRadius   = (int) Math.hypot(left, bottom);
                Animator anim = ViewAnimationUtils.createCircularReveal(v, cx, cy, 0, finalRadius);
                anim.setDuration(375);
                anim.start();
            }
        });



        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        locationMenu.getMenuInflater().inflate(R.menu.location, locationMenu.getMenu());
        departmentMenu.getMenuInflater().inflate(R.menu.department_menu, departmentMenu.getMenu());
        try{
            presenter.setContext(context);

            presenter.loadProfile(userId);
        }catch(Exception e){

        }
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFileChooser();
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                employee = new Employee(sharedPref.getString(context,SharedPref.PREFS_USERID),
                        EmployeeId.getText().toString(),UserName.getText().toString(),Department.getText().toString(),
                        "",Email.getText().toString(),Location.getText().toString(),Extension.getText().toString()
                        ,Constants.FIREBASE_IMAGE_PATH);
                try {
                    presenter.saveProfile(employee);
                } catch (Exception e) {
                    Snackbar.make(rootView,e.getMessage(),Snackbar.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }
        });
        locationChooser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                //registering popup with OnMenuItemClickListener
                locationMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        Location.setText(item.getTitle());
                        return true;
                    }
                });

                locationMenu.show();
            }
        });
        departmentChooser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //registering popup with OnMenuItemClickListener
                departmentMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        Department.setText(item.getTitle());
                        return true;
                    }
                });


                departmentMenu.show();
            }
        });



    }

    @Override
    public boolean allFieldsValid() throws EmptyTextException,InvalidFieldException {
        if(UserName.getText().toString().isEmpty()  ||  Email.getText().toString().isEmpty() ||
                EmployeeId.getText().toString().isEmpty()||  Department.getText().toString().isEmpty()
                ||  Location.getText().toString().isEmpty() ||  Extension.getText().toString().isEmpty())
            throw new EmptyTextException("Fields are empty");
        if(Utils.validateEmail(Email.getText().toString()) && Utils.validUserName(UserName.getText().toString()) &&
                !EmployeeId.getText().toString().isEmpty() &&  !Department.getText().toString().isEmpty()
                &&  !Location.getText().toString().isEmpty() &&  !Extension.getText().toString().isEmpty())
            return true;
        else
            throw new InvalidFieldException("Please enter all fields correctly");
    }

    @Override
    public void showMessage(String message) {
        Snackbar.make(rootView,message,Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void showProfile(Employee employee) {
        if(!sharedPref.getString(context,SharedPref.PREFS_USERID).equals(userId)){
            rootView.setFocusable(false);
            UserName.setFocusable(false);
            Email.setFocusable(false);
            EmployeeId.setFocusable(false);
            Extension.setFocusable(false);
            departmentChooser.setClickable(false);
            locationChooser.setClickable(false);
            submit.setVisibility(View.GONE);
        }

        UserName.setText(employee.getEmployeeName());
        Email.setText(employee.getEmployeeEmail());
        EmployeeId.setText(employee.getEmployeeId());
        Department.setText(employee.getEmployeeDepartment());
        Location.setText(employee.getEmployeePhase());
        Extension.setText(employee.getEmployeeExtension());
        String sx = employee.getEmployeeImage()+employee.getUserId();
        storageReference.child(employee.getEmployeeImage()+employee.getUserId()+".jpg")
                .getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.with(context).load(uri.toString())
                        .placeholder(R.mipmap.ic_launcher)
                        .resize(100, 100).into(imageView, new Callback() {
                    @Override
                    public void onSuccess() {
                        Animation fadeOut = new AlphaAnimation(0, 1);
                        fadeOut.setInterpolator(new AccelerateInterpolator());
                        fadeOut.setDuration(500);
                        imageView.startAnimation(fadeOut);

                    }

                    @Override
                    public void onError() {

                    }
                });

            }
        });
    }


    @Override
    public void setPresenter(HomeContract.PresenterProfile presenter) {
        this.presenter = presenter;
    }


    //method to show file chooser
    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), Constants.PICK_IMAGE_REQUEST);
    }

    //handling the image chooser activity result
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constants.PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null && data.getData() != null) {
            Uri filePath = data.getData();
            try {
                final Bitmap bitmap = MediaStore.Images.Media.getBitmap(context.getContentResolver(), filePath);
                imageView.setImageBitmap(bitmap);
                if (filePath != null) {
                    //displaying a progress dialog while upload is going on
                    final ProgressDialog progressDialog = new ProgressDialog(context);
                    progressDialog.setTitle("Uploading");
                    progressDialog.show();

                    StorageReference riversRef = storageReference.child("images/"+userId+".jpg");
                    riversRef.putFile(filePath)
                            .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                    progressDialog.dismiss();

                                    sharedPref.save(context,SharedPref.PREFS_USER_IMAGE,String.valueOf("images/"));
                                    //and displaying a success toast
                                    Toast.makeText(context, "File Uploaded "+taskSnapshot.getDownloadUrl(), Toast.LENGTH_LONG).show();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception exception) {
                                    //if the upload is not successfull
                                    //hiding the progress dialog
                                    progressDialog.dismiss();

                                    //and displaying error message
                                    Toast.makeText(context, exception.getMessage(), Toast.LENGTH_LONG).show();
                                }
                            })
                            .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                                    //calculating progress percentage
                                    double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();

                                    //displaying percentage in progress dialog
                                    progressDialog.setMessage("Uploaded " + ((int) progress) + "%...");
                                }
                            });
                }
                //if there is not any file
                else {
                    //you can display an error toast
                }


            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }



}
