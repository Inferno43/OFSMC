package com.ofs.ofmc.login;

/**
 * Created by ${USER_NAME} on 2/16/17.
 */

public class SignUpPresenter implements OnboardingContract.PresenterSignUp {

    private OnboardingContract.ViewSignUp viewSignUp;

    public SignUpPresenter(OnboardingContract.ViewSignUp viewSignUp) {
        this.viewSignUp = viewSignUp;
        this.viewSignUp.setPresenter(this);
    }

    @Override
    public void signUp(String userName,String email,String password) throws Exception {
        if(viewSignUp.allFieldsValid())
            viewSignUp.showLogin(userName,email,password);
    }

    @Override
    public void start() {

    }
}
