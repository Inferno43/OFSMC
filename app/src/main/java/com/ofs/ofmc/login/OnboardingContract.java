package com.ofs.ofmc.login;

import android.os.Bundle;

import com.ofs.ofmc.BasePresenter;
import com.ofs.ofmc.BaseView;

/**
 * Created by ${USER_NAME} on 2/16/17.
 */

public interface OnboardingContract {

    interface Viewlogin extends BaseView<Presenterlogin>{
        void startHome(String username,String password);
        boolean allFieldsValid();
    }

    interface Presenterlogin extends BasePresenter{
        void login(String username,String password) throws Exception;
        void savePreference();
    }

    interface ViewSignUp extends BaseView<PresenterSignUp>{

        void showLogin(String userName,String email,String password);
        boolean allFieldsValid();

    }

    interface PresenterSignUp extends BasePresenter{

        void signUp(String userName,String email,String password) throws Exception;
    }
}
