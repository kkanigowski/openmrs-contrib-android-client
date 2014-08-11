package org.openmrs.client.activities;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.openmrs.client.R;
import org.openmrs.client.applications.ACApplication;
import org.openmrs.client.tasks.UrlAuthenticationTask;

public class LoginActivity extends Activity implements View.OnClickListener {

    private Context ctx;
    private EditText mLoginEditText;
    private EditText mPasswordEditText;
    private Button mValidationButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ctx = LoginActivity.this;

        mLoginEditText = (EditText) findViewById(R.id.loginEditText);
        mPasswordEditText = (EditText) findViewById(R.id.passwordEditText);
        mValidationButton = (Button) findViewById(R.id.validationButton);

        mValidationButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.validationButton :

                if (!stringIsEmpty(mLoginEditText.getText().toString()) &&
                        !stringIsEmpty(mPasswordEditText.getText().toString())) {

                    ACApplication.getInstance().setUsernamePreference(mLoginEditText.getText().toString());

                    UrlDialog urlDialog = new UrlDialog(ctx);
                    urlDialog.show();
                } else {
                    Toast.makeText(ctx, getString(R.string.login_or_password_empty), Toast.LENGTH_SHORT).show();
                }
                break;

            default:
                break;
        }
    }

    private boolean stringIsEmpty(String sEditText) {

        boolean isEmpty = false;
        if (sEditText.matches("")) {
            isEmpty = true;
        }
        return isEmpty;
    }

    private class UrlDialog extends Dialog implements android.view.View.OnClickListener {

        private Context ctx;
        private Button mOkButton;
        private Button mCancelButton;
        private EditText mServerUrlEditText;

        public UrlDialog(Context ctx) {
            super(ctx);
            this.ctx = ctx;
        }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            requestWindowFeature(Window.FEATURE_NO_TITLE);                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                //requestWindowFeature(Window.FEATURE_NO_TITLE);
            setContentView(R.layout.url_dialog);
            mOkButton = (Button) findViewById(R.id.okButton);
            mCancelButton = (Button) findViewById(R.id.cancelButton);
            mOkButton.setOnClickListener(this);
            mCancelButton.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {

                case R.id.okButton :
                    mServerUrlEditText = (EditText) findViewById(R.id.serverUrlEditText);
                    if (!stringIsEmpty(mServerUrlEditText.getText().toString())) {
                        dismiss();
                        ACApplication.getInstance().setServerUrlPreference(mServerUrlEditText.getText().toString());
                        UrlAuthenticationTask task = new UrlAuthenticationTask(ctx,
                                mPasswordEditText.getText().toString());
                        task.execute((Void[]) null);
                    } else {
                        Toast.makeText(ctx, getString(R.string.server_url_empty_error), Toast.LENGTH_SHORT).show();
                    }
                    break;
                case R.id.cancelButton:
                    dismiss();
                    break;
                default:
                    break;
            }
        }
    }
}
