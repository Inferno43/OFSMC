package com.ofs.ofmc.abstracts;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by ${USER_NAME} on 2/16/17.
 */

public interface AbstractActivityCallback {

    void startActivity(Class<? extends AppCompatActivity> activity,Bundle args);
}
