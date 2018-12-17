package com.luteh.uberclone.model.user;

/**
 * Created by Luthfan Maftuh on 17/12/2018.
 * Email luthfanmaftuh@gmail.com
 */
public interface IUser {
    /**
     * validate the input fields of register form
     */
    int isValidRegisterData();

    /**
     * validate the input fields of sign in form
     */
    int isValidLoginData();
}
