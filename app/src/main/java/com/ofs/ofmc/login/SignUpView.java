package com.ofs.ofmc.login;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
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
import com.ofs.ofmc.BaseFragment;
import com.ofs.ofmc.R;
import com.ofs.ofmc.abstracts.Arguments;
import com.ofs.ofmc.exceptions.EmptyTextException;
import com.ofs.ofmc.toolbox.Constants;
import com.ofs.ofmc.toolbox.Utils;

/**
 * Created by ${USER_NAME} on 2/16/17.
 */

public class SignUpView extends BaseFragment implements OnboardingContract.ViewSignUp,Arguments {

    OnboardingContract.PresenterSignUp presenterSignUp;
    private Context context;
    private View rootView;
    private EditText userName;
    private EditText password;
    private EditText email;
    private EditText employeeId;
    private EditText department;
    private EditText phase;
    private EditText extension;
    private Button register;

    Arguments arguments;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
            arguments = (Arguments) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnHeadlineSelectedListener");
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.signup, container, false);
        context = getActivity();
        userName = (EditText) rootView.findViewById(R.id.userName);
        password = (EditText) rootView.findViewById(R.id.password);
        email = (EditText) rootView.findViewById(R.id.email);
//        employeeId = (EditText)rootView.findViewById(R.id.emp_id);
//        department = (EditText)rootView.findViewById(R.id.department);
//        phase = (EditText)rootView.findViewById(R.id.phase);
//        extension = (EditText)rootView.findViewById(R.id.extension);
        register = (Button) rootView.findViewById(R.id.register);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    presenterSignUp.signUp(userName.getText().toString(), email.getText().toString(),
                            password.getText().toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {

                } else {

                }
            }
        };
        return rootView;
    }

    @Override
    public void showLogin(String userName, String email, String password) {
        createAccount(userName, email, password);
    }

    @Override
    public boolean allFieldsValid() {
        return false;
    }



    @Override
    public void setPresenter(OnboardingContract.PresenterSignUp presenter) {
        this.presenterSignUp = presenter;
    }

    private void createAccount(final String userName, final String email, String password) {

        if (Utils.validateEmail(email) && Utils.validpassword(password)) {
            //replaceActivity(LoginActivity.class,true,null);
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (!task.isSuccessful()) {

                                //Toast.makeText(context,"Could not create your account, please try later",Toast.LENGTH_LONG).show();
                            } else {
                                task.addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                                    @Override
                                    public void onSuccess(AuthResult authResult) {
                                        if (authResult.getUser() != null) {

                                            Snackbar.make(getView(), "Hello " + userName, Snackbar.LENGTH_LONG).show();
                                            Onboarding onboarding = (Onboarding) getActivity();
                                            Bundle args = new Bundle();
                                            args.putString(Constants.EXTRA_EMAIL,email);
                                            arguments.setBundle(args);
                                            onboarding.getViewPager().setCurrentItem(0);

                                        }
                                    }
                                })
                                        .addOnFailureListener(getActivity(), new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
                                            }
                                        });
                            }
                        }
                    });
        } else
            Toast.makeText(context, "Invalid email or password", Toast.LENGTH_LONG).show();
    }

    @Override
    public void setBundle(Bundle args) {

    }
}
