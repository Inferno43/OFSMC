package com.ofs.ofmc.contracts;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

/**
 * Created by ${USER_NAME} on 2/16/17.
 */

public interface AbstractFragmentCallback {

    void replaceFragment(FragmentManager fragmentManager, Fragment fragment, boolean addToBackstack, Bundle args);
}
