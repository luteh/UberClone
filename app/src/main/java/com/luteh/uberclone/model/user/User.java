package com.luteh.uberclone.model.user;

import android.text.TextUtils;
import android.util.Patterns;

/**
 * Created by Luthfan Maftuh on 17/12/2018.
 * Email luthfanmaftuh@gmail.com
 */
public class User implements IUser {
    private String email, password, name, phone;

    public User() {
    }

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public User(String email, String password, String name, String phone) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }


    @Override
    public int isValidRegisterData() {
        if (TextUtils.isEmpty(getEmail())) return 0;
        else if (!Patterns.EMAIL_ADDRESS.matcher(getEmail()).matches()) return 1;
        else if (TextUtils.isEmpty(getPassword())) return 2;
        else if (getPassword().length() < 6) return 3;
        else if (TextUtils.isEmpty(getName())) return 4;
        else if (TextUtils.isEmpty(getPhone())) return 5;
        else return -1;
    }

    @Override
    public int isValidLoginData() {
        if (TextUtils.isEmpty(getEmail())) return 0;
        else if (!Patterns.EMAIL_ADDRESS.matcher(getEmail()).matches()) return 1;
        else if (TextUtils.isEmpty(getPassword())) return 2;
        else if (getPassword().length() < 6) return 3;
        else return -1;
    }
}
