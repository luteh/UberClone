package com.luteh.uberclone.ui.signin;

import android.content.Context;
import android.util.Log;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.luteh.uberclone.model.user.User;
import com.luteh.uberclone.R;


import durdinapps.rxfirebase2.RxFirebaseAuth;
import durdinapps.rxfirebase2.RxFirebaseDatabase;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Luthfan Maftuh on 17/12/2018.
 * Email luthfanmaftuh@gmail.com
 */
public class SignInActivityPresenterImp implements ISignInActivityPresenter {

    private static final String TAG = SignInActivity.class.getSimpleName();

    private ISignInActivityView iSignInActivityView;
    private Context context;

    public SignInActivityPresenterImp(Context context, ISignInActivityView iSignInActivityView) {
        this.context = context;
        this.iSignInActivityView = iSignInActivityView;
    }

    @Override
    public void submitRegister(String email, String password, String name, String phone) {
        User user = new User(email, password, name, phone);

        switch (user.isValidRegisterData()) {
            case 0:
                iSignInActivityView.onFormNotValid(context.getResources().getString(R.string.label_msg_enter_email));
                break;
            case 1:
                iSignInActivityView.onFormNotValid(context.getResources().getString(R.string.label_msg_invalid_email));
                break;
            case 2:
                iSignInActivityView.onFormNotValid(context.getResources().getString(R.string.label_msg_enter_password));
                break;
            case 3:
                iSignInActivityView.onFormNotValid(context.getResources().getString(R.string.label_msg_password_too_short));
                break;
            case 4:
                iSignInActivityView.onFormNotValid(context.getResources().getString(R.string.label_msg_enter_name));
                break;
            case 5:
                iSignInActivityView.onFormNotValid(context.getResources().getString(R.string.label_msg_enter_phone));
                break;
            case -1:
                RxFirebaseAuth.createUserWithEmailAndPassword(FirebaseAuth.getInstance(), user.getEmail(), user.getPassword())
                        .map(authResult -> authResult.getUser().getUid())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(uid -> {
                            insertUserIntoDatabase(user, uid);
                        }, throwable -> iSignInActivityView.onRegisterFailure(throwable.getMessage()));
                break;
        }
    }

    @Override
    public void submitLogin(String email, String password) {
        User user = new User(email, password);

        switch (user.isValidLoginData()) {
            case 0:
                iSignInActivityView.onFormNotValid(context.getResources().getString(R.string.label_msg_enter_email));
                break;
            case 1:
                iSignInActivityView.onFormNotValid(context.getResources().getString(R.string.label_msg_invalid_email));
                break;
            case 2:
                iSignInActivityView.onFormNotValid(context.getResources().getString(R.string.label_msg_enter_password));
                break;
            case 3:
                iSignInActivityView.onFormNotValid(context.getResources().getString(R.string.label_msg_password_too_short));
                break;
            case -1:
                RxFirebaseAuth.signInWithEmailAndPassword(FirebaseAuth.getInstance(), user.getEmail(), user.getPassword())
                        .map(authResult -> authResult.getUser().getUid() )
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(authResult -> {
                            Log.d(TAG, "onSuccess: " + authResult);
                            iSignInActivityView.onLoginSuccess();
                        }, throwable -> iSignInActivityView.onLoginFailure(throwable.getMessage()));
                break;
        }

    }

    private void insertUserIntoDatabase(User user, String uid) {
        // Save user to db
        RxFirebaseDatabase.setValue(FirebaseDatabase.getInstance().getReference("users").child(uid), user)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> iSignInActivityView.onRegisterSuccess(),
                        throwable -> iSignInActivityView.onRegisterFailure(throwable.getMessage()));
    }
}
