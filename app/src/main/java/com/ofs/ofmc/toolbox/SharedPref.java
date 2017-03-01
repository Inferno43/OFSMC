package com.ofs.ofmc.toolbox;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;

/**
 * Created by saravana.subramanian on 12/19/16.
 *
 * Shared preference class to store app information and settings
 */

public class SharedPref {


    private  Context context;
    private static SharedPref ourInstance = new SharedPref();

    public static SharedPref getInstance() {
        return ourInstance;
    }

    public static final String PREFS_OFMC = "OFMC";
    public static final String PREFS_USERNAME = "email";
    public static final String PREFS_USER = "user";
    public static final String PREFS_PASSWORD = "password";
    public static final String PREFS_USERID= "userId";
    public static final String PREFS_USER_IMAGE ="userImage";

    public static final String PREFS_IS_PROFILE_COMPLETE = "profile";
    public static final String PREFS_AUTOFILL = "autofill";
    public static final String PREFS_NOTIFICATION_INTERVAL = "notificationInterval";

    public static final String PREFS_COACHMARKS = "coachMarks";

    public static final String PREFS_FRESH_INSTALL = "freshInstall";


    public static final int PREFS_LIST = 1;
    public static final int PREFS_TIMELINE = 2;

    public SharedPref() {
        super();
    }
    public SharedPref(Context context) {
        this.context = context;
    }

    public void init(Context context) {
        SharedPreferences settings;
        SharedPreferences.Editor editor;

        settings = context.getSharedPreferences(PREFS_OFMC, Context.MODE_PRIVATE); //1
        editor = settings.edit(); //2

        editor.putBoolean(PREFS_FRESH_INSTALL,true);
        editor.putBoolean(PREFS_AUTOFILL,false);
        editor.apply(); //4
    }

    public void save(Context context, String text) {
        SharedPreferences settings;
        SharedPreferences.Editor editor;

        //settings = PreferenceManager.getDefaultSharedPreferences(context);
        settings = context.getSharedPreferences(PREFS_OFMC, Context.MODE_PRIVATE); //1
        editor = settings.edit(); //2

        //editor.putString(PREFS_KEY, text); //3

        editor.commit(); //4
    }

    public void saveProfile(Context context, HashMap<String,Integer> map) {
        SharedPreferences settings;
        SharedPreferences.Editor editor;

        //settings = PreferenceManager.getDefaultSharedPreferences(context);
        settings = context.getSharedPreferences(PREFS_OFMC, Context.MODE_PRIVATE); //1
        editor = settings.edit(); //2


        if(map.containsKey(PREFS_NOTIFICATION_INTERVAL))
            editor.putInt(PREFS_NOTIFICATION_INTERVAL, map.get(PREFS_NOTIFICATION_INTERVAL));
        //editor.putString(PREFS_KEY, String.valueOf(map.get("123"))); //3

        editor.apply(); //4
    }


    public void save(Context context, HashMap<String,?> map) {
        SharedPreferences settings;
        SharedPreferences.Editor editor;

        //settings = PreferenceManager.getDefaultSharedPreferences(context);
        settings = context.getSharedPreferences(PREFS_OFMC, Context.MODE_PRIVATE); //1
        editor = settings.edit(); //2


        if(map.containsKey(PREFS_USERNAME))
            editor.putString(PREFS_USERNAME,String.valueOf(map.get(PREFS_USERNAME)));
        if(map.containsKey(PREFS_PASSWORD))
            editor.putString(PREFS_PASSWORD,String.valueOf(map.get(PREFS_PASSWORD)));
        if(map.containsKey(PREFS_USERID))
            editor.putString(PREFS_USERID,String.valueOf(map.get(PREFS_USERID)));
        if(map.containsKey(PREFS_USER))
            editor.putString(PREFS_USER,String.valueOf(map.get(PREFS_USER)));


        editor.apply(); //4
    }

    public void save(Context context, String Key, boolean boole) {
        SharedPreferences settings;
        SharedPreferences.Editor editor;

        //settings = PreferenceManager.getDefaultSharedPreferences(context);
        settings = context.getSharedPreferences(PREFS_OFMC, Context.MODE_PRIVATE); //1
        editor = settings.edit(); //

        editor.putBoolean(Key, boole);

        //editor.putString(PREFS_KEY, String.valueOf(map.get("123"))); //3

        editor.apply(); //4
    }


    public void save(Context context, String Key, String string) {
        SharedPreferences settings;
        SharedPreferences.Editor editor;

        //settings = PreferenceManager.getDefaultSharedPreferences(context);
        settings = context.getSharedPreferences(PREFS_OFMC, Context.MODE_PRIVATE); //1
        editor = settings.edit(); //

        editor.putString(Key, string);

        //editor.putString(PREFS_KEY, String.valueOf(map.get("123"))); //3

        editor.apply(); //4
    }


    public void coachmarksShown(Context context, String className,boolean shown) {
        SharedPreferences settings;
        SharedPreferences.Editor editor;

        settings = context.getSharedPreferences(PREFS_OFMC, Context.MODE_PRIVATE); //1
        editor = settings.edit(); //2

        editor.putBoolean(className, shown);

        editor.apply(); //4
    }

    public boolean isCoachShown(Context context, String className) {
        SharedPreferences settings;
        SharedPreferences.Editor editor;

        settings = context.getSharedPreferences(PREFS_OFMC, Context.MODE_PRIVATE); //1

        return settings.getBoolean(className,false);
    }


    public String getString(Context context,String PREFS_KEY) {
        SharedPreferences settings;
        String text = null;

        //settings = PreferenceManager.getDefaultSharedPreferences(context);
        settings = context.getSharedPreferences(PREFS_OFMC, Context.MODE_PRIVATE);
        text = settings.getString(PREFS_KEY, null);
        return text;
    }

    public Boolean getBoolean(Context context,String PREFS_KEY) {
        SharedPreferences settings;
        boolean value;

        //settings = PreferenceManager.getDefaultSharedPreferences(context);
        settings = context.getSharedPreferences(PREFS_OFMC, Context.MODE_PRIVATE);
        value = settings.getBoolean(PREFS_KEY, false);
        return value;
    }

    public Integer getInteger(Context context,String PREFS_KEY) {
        SharedPreferences settings;
        int value;

        //settings = PreferenceManager.getDefaultSharedPreferences(context);
        settings = context.getSharedPreferences(PREFS_OFMC, Context.MODE_PRIVATE);
        value = settings.getInt(PREFS_KEY, 0);
        return value;
    }


    public HashMap<String,Integer> getMap(Context context) {
        SharedPreferences settings;
        HashMap<String,Integer>   map = new HashMap<>();
        settings = context.getSharedPreferences(PREFS_OFMC, Context.MODE_PRIVATE);
       // map.put(PREFS_FLOW,settings.getInt(PREFS_FLOW,1));
        return map;
    }

    public void clearSharedPreference(Context context) {
        SharedPreferences settings;
        SharedPreferences.Editor editor;

        //settings = PreferenceManager.getDefaultSharedPreferences(context);
        settings = context.getSharedPreferences(PREFS_OFMC, Context.MODE_PRIVATE);
        editor = settings.edit();
        editor.clear();
        editor.apply();
    }

    public void removeValue(Context context) {
        SharedPreferences settings;
        SharedPreferences.Editor editor;

        settings = context.getSharedPreferences(PREFS_OFMC, Context.MODE_PRIVATE);
        editor = settings.edit();

        //editor.remove(PREFS_KEY);
        editor.commit();
    }
}
