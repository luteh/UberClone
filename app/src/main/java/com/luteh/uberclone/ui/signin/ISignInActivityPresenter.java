package com.luteh.uberclone.ui.signin;

/**
 * Created by Luthfan Maftuh on 17/12/2018.
 * Email luthfanmaftuh@gmail.com
 */
public interface ISignInActivityPresenter {
    /**
     * Register user using firebase auth
     *
     * @param email    the user email
     * @param password the user password
     * @param name     the user name
     * @param phone    the user phone number
     */
    void submitRegister(String email, String password, String name, String phone);

    /**
     * Login using firebase auth
     *
     * @param email    user email
     * @param password user password
     */
    void submitLogin(String email, String password);
}
