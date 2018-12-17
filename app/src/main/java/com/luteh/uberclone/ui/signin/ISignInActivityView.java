package com.luteh.uberclone.ui.signin;

/**
 * Created by Luthfan Maftuh on 17/12/2018.
 * Email luthfanmaftuh@gmail.com
 */
public interface ISignInActivityView {

    void onRegisterSuccess();

    void onRegisterFailure(String errorMessage);

    void onLoginSuccess();

    void onLoginFailure(String message);

    void onFormNotValid(String message);
}
