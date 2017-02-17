package com.ofs.ofmc.login;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.ofs.ofmc.BaseFragment;
import com.ofs.ofmc.R;
import com.ofs.ofmc.abstracts.Arguments;
import com.ofs.ofmc.home.Home;
import com.ofs.ofmc.toolbox.Constants;

/**
 * Created by ${USER_NAME} on 2/16/17.
 */

public class LoginView extends BaseFragment implements OnboardingContract.Viewlogin,Arguments{

    OnboardingContract.Presenterlogin loginPresenter;
    private View rootView;
    private Button signIn;
    private EditText username;
    private EditText password;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView  = inflater.inflate(R.layout.login,container,false);
        signIn = (Button)rootView.findViewById(R.id.signIn);
        username = (EditText) rootView.findViewById(R.id.userName);
        password = (EditText) rootView.findViewById(R.id.password);
        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    loginPresenter.login(username.getText().toString(),password.getText().toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        return rootView;
    }

    @Override
    public void startHome(String username,String password) {


        mAuth.signInWithEmailAndPassword(username,password).addOnCompleteListener(getActivity(),new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

            }
        }).addOnSuccessListener(getActivity(), new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                startActivity(Home.class,null);
            }
        });

    }

    @Override
    public boolean allFieldsValid() {
        return false;
    }



    @Override
    public void setPresenter(OnboardingContract.Presenterlogin presenter) {
        loginPresenter = presenter;
    }


    @Override
    public void setBundle(Bundle args) {
        username.setText(args.getString(Constants.EXTRA_EMAIL));
    }
}
