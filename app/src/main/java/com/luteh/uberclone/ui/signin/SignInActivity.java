package com.luteh.uberclone.ui.signin;


import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import androidx.appcompat.app.AlertDialog;
import butterknife.Unbinder;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.luteh.uberclone.common.BaseActivity;
import com.luteh.uberclone.R;
import com.luteh.uberclone.common.Common;
import com.luteh.uberclone.ui.signin.dialog.RegisterDialogViews;
import com.luteh.uberclone.ui.signin.dialog.SignInDialogViews;

public class SignInActivity extends BaseActivity implements ISignInActivityView {

    @BindView(R.id.btnRegister)
    Button btnRegister;
    @BindView(R.id.btnSignIn)
    Button btnSignIn;
    @BindView(R.id.rootLayout)
    RelativeLayout rootLayout;

    @BindString(R.string.label_register)
    String labelRegister;
    @BindString(R.string.label_message_use_email)
    String labelMessageUseEmail;
    @BindString(R.string.label_cancel)
    String labelCancel;
    @BindString(R.string.label_msg_enter_email)
    String labelMessageEnterEmail;

    private ISignInActivityPresenter iSignInActivityPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onInit() {
        super.onInit();

        iSignInActivityPresenter = new SignInActivityPresenterImp(this, this);
    }

    @OnClick(R.id.btnRegister)
    public void onClickRegister() {
        showRegisterDialog();
    }

    @OnClick(R.id.btnSignIn)
    public void onClickSignIn() {
        showSignInDialog();
    }

    private void showSignInDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        final View view = LayoutInflater.from(this).inflate(R.layout.sign_in_dialog, null);

        final SignInDialogViews signInDialogViews = new SignInDialogViews();
        Unbinder unbinderDialog = ButterKnife.bind(signInDialogViews, view);

        builder.setTitle(getResources().getText(R.string.label_sign_in))
                .setMessage(labelMessageUseEmail)
                .setView(view)
                .setPositiveButton(getResources().getText(R.string.label_sign_in), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();

                        // Check Validation
                        iSignInActivityPresenter.submitLogin(
                                signInDialogViews.etEmail.getText().toString(),
                                signInDialogViews.etPassword.getText().toString()
                        );
                        unbinderDialog.unbind();
                    }
                })
                .setNegativeButton(labelCancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        unbinderDialog.unbind();
                    }
                });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void showRegisterDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        final View view = LayoutInflater.from(this).inflate(R.layout.register_dialog, null);

        final RegisterDialogViews registerDialogViews = new RegisterDialogViews();
        Unbinder unbinderDialog = ButterKnife.bind(registerDialogViews, view);

        builder.setTitle(labelRegister)
                .setMessage(labelMessageUseEmail)
                .setView(view)
                .setPositiveButton(labelRegister, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();

                        // Check Validation
                        iSignInActivityPresenter.submitRegister(
                                registerDialogViews.etEmail.getText().toString(),
                                registerDialogViews.etPassword.getText().toString(),
                                registerDialogViews.etName.getText().toString(),
                                registerDialogViews.etPhone.getText().toString()
                        );
                        unbinderDialog.unbind();
                    }
                })
                .setNegativeButton(labelCancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        unbinderDialog.unbind();
                    }
                });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    public void onRegisterSuccess() {
        Common.showSnackBar(rootLayout, getResources().getText(R.string.label_msg_register_succes).toString());
    }

    @Override
    public void onRegisterFailure(String errorMessage) {
        Common.showSnackBar(rootLayout, getResources().getText(R.string.label_msg_fail) + errorMessage);
    }

    @Override
    public void onLoginSuccess() {
        Common.showSnackBar(rootLayout, "Login Success " + FirebaseAuth.getInstance().getCurrentUser());
    }

    @Override
    public void onLoginFailure(String message) {
        Common.showSnackBar(rootLayout, message);
    }

    @Override
    public void onFormNotValid(String message) {
        Common.showSnackBar(rootLayout, message);
    }
}
