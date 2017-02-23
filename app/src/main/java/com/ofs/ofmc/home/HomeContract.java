package com.ofs.ofmc.home;

import android.app.Activity;
import android.content.Context;

import com.ofs.ofmc.BasePresenter;
import com.ofs.ofmc.BaseView;
import com.ofs.ofmc.model.Employee;

/**
 * Created by ${USER_NAME} on 2/17/17.
 */

public interface HomeContract {

    interface ViewHome extends BaseView<PresenterHome>{

    }
    interface PresenterHome extends BasePresenter{

    }

    interface ViewProfile extends BaseView<PresenterProfile>{

        void showMessage(String message);
        void showProfile(Employee employee);


    }
    interface PresenterProfile extends BasePresenter{

        void loadProfile(String id);
        void editProfile(Employee employee);

        void saveProfile(Employee employee);
        void profileCompleted(boolean isComplete);
        void setContext(Context context);
    }
}
