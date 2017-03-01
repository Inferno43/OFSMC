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


        boolean allFieldsValid() throws Exception;
        void showMessage(String message);
        void showProfile(Employee employee);


    }
    interface PresenterProfile extends BasePresenter{

        void loadProfile(String id);
        void editProfile(Employee employee) throws Exception;

        void saveProfile(Employee employee) throws Exception;
        void profileCompleted(boolean isComplete);
        void setContext(Context context);
    }

    interface ViewDashboard extends BaseView<PresenterDashboard>{

    }

    interface PresenterDashboard extends BasePresenter{

    }

    interface ViewHolidays extends BaseView<PresenterHolidays>{

    }

    interface PresenterHolidays extends BasePresenter{

    }

    interface ViewEmployeeSeating extends BaseView<PresenterEmployeeSeating>{

    }

    interface PresenterEmployeeSeating extends BasePresenter{

    }
}
