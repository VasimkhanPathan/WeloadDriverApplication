package com.weload.driverapplication.util;

import android.app.ProgressDialog;
import android.content.Context;

public class CustomeProgressDialog {
    private ProgressDialog progressDialog;
    public void showProgress(Context context) {
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Loading...");
        progressDialog.show();
    }

    public void hideProgress() {
        if (progressDialog != null) {
            if (progressDialog.isShowing())
                progressDialog.cancel();
        }
    }
}
