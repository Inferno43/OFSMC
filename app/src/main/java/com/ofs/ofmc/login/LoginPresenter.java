package com.ofs.ofmc.login;

/**
 * Created by ${USER_NAME} on 2/16/17.
 */

public class LoginPresenter implements OnboardingContract.Presenterlogin {

    private OnboardingContract.Viewlogin view;

    public LoginPresenter(OnboardingContract.Viewlogin view) {
        this.view = view;
        this.view.setPresenter(this);
    }


    @Override
    public void login(String userName,String password) throws Exception {
        if(view.allFieldsValid())
            view.startHome(userName,password);
    }

    @Override
    public void savePreference() {

    }

    @Override
    public void start() {

    }
}
