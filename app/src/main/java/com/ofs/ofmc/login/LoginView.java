package com.ofs.ofmc.login;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
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
import com.ofs.ofmc.contracts.Arguments;
import com.ofs.ofmc.exceptions.EmptyTextException;
import com.ofs.ofmc.exceptions.InvalidFieldException;
import com.ofs.ofmc.home.Home;
import com.ofs.ofmc.toolbox.Constants;
import com.ofs.ofmc.toolbox.MetaballView;
import com.ofs.ofmc.toolbox.Progressbar;
import com.ofs.ofmc.toolbox.SharedPref;
import com.ofs.ofmc.toolbox.Utils;

import java.util.HashMap;

/**
 * Created by ${USER_NAME} on 2/16/17.
 */

public class LoginView extends BaseFragment implements OnboardingContract.Viewlogin,Arguments{

    OnboardingContract.Presenterlogin loginPresenter;
    private View rootView;
    private Button signIn;
    private EditText username;
    private EditText password;
    private SharedPref sharedPref;
    private HashMap<String,String> map;
    private Context context;
    private String user;
    private MetaballView progressBar;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView  = inflater.inflate(R.layout.login,container,false);
        sharedPref = new SharedPref();
        context = getActivity();
        map = new HashMap<>();
        signIn = (Button)rootView.findViewById(R.id.signIn);
        progressBar = (MetaballView) rootView.findViewById(R.id.metaball);
        username = (EditText) rootView.findViewById(R.id.userName);
        password = (EditText) rootView.findViewById(R.id.password);
        if(sharedPref.getString(context,SharedPref.PREFS_USERNAME) != null){
            username.setText(sharedPref.getString(getContext(),SharedPref.PREFS_USERNAME));
            password.setText(sharedPref.getString(getContext(),SharedPref.PREFS_PASSWORD));
        }
        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showProgress(true);
                try {
                    loginPresenter.login(username.getText().toString(),password.getText().toString());
                } catch (Exception e) {
                    showProgress(false);
                    Snackbar.make(rootView,e.getMessage(),Snackbar.LENGTH_LONG).show();
                }
            }
        });
        return rootView;
    }

    @Override
    public void startHome(final String username, final String password) {


        mAuth.signInWithEmailAndPassword(username,password).addOnCompleteListener(getActivity(),new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

            }
        }).addOnSuccessListener(getActivity(), new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                if(sharedPref.getString(getContext(),SharedPref.PREFS_USERNAME) == null){
                    String userId = authResult.getUser().getUid();
                    map.put(SharedPref.PREFS_USERNAME,username);
                    map.put(SharedPref.PREFS_USER,user);
                    map.put(SharedPref.PREFS_PASSWORD,password);
                    map.put(SharedPref.PREFS_USERID,userId);

                    //Toast.makeText(getActivity(),userId+ " " +sharedPref.getString(getContext(),SharedPref.PREFS_USERID),Toast.LENGTH_LONG).show();
                    sharedPref.save(getContext(),map);
                }
                showProgress(false);
                startActivity(Home.class,null);
            }
        });

    }

    @Override
    public boolean allFieldsValid() throws EmptyTextException,InvalidFieldException {
        if(username.getText().toString().isEmpty()  &&  password.getText().toString().isEmpty())
            throw new EmptyTextException("Fields are empty");
        if(Utils.validateEmail(username.getText().toString()) && Utils.validpassword(password.getText().toString()))
            return true;
        else
            throw new InvalidFieldException("Please enter Username and Password correctly");

    }

    @Override
    public void showProgress(boolean show) {
        if(show){
            progressBar.setVisibility(View.VISIBLE);
        }else if(!show){
            progressBar.setVisibility(View.GONE);
        }else
            progressBar.setVisibility(View.GONE);
    }


    @Override
    public void setPresenter(OnboardingContract.Presenterlogin presenter) {
        loginPresenter = presenter;
    }


    @Override
    public void setBundle(Bundle args) {
        username.setText(args.getString(Constants.EXTRA_EMAIL));
        user = args.getString(Constants.EXTRA_USERNAME);
    }
}
