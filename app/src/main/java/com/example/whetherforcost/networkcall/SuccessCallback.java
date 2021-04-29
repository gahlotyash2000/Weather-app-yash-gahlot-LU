package com.example.whetherforcost.networkcall;

import android.app.ProgressDialog;
import android.content.Context;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

abstract class BaseCallBackRetrofit<T> {

  public abstract void onSuccess(Response<T> response);
  public abstract void onFailure(Response<T> response);

}

public abstract class SuccessCallback<T> extends  BaseCallBackRetrofit<T> implements Callback<T> {

    private ProgressDialog progressDialog;
    private Context context;

    public SuccessCallback(boolean showProgressBar, Context context) {
        this.showProgressBar = showProgressBar;
        this.context=context;


            if(showProgressBar){
                progressDialog = new ProgressDialog(context);
                progressDialog.setMessage("Please wait...");
                progressDialog.show();
            }


    }

    private boolean showProgressBar;

    @Override
    public void onResponse(Call<T> call, Response<T> response) {
        if(showProgressBar) {
            if (progressDialog != null && progressDialog.isShowing())
                progressDialog.dismiss();
        }

        if(response.isSuccessful()) {
            if (response.body() != null)
            onSuccess(response);
            else
                onFailure(response);
        }else{
            // error case
            switch (response.code()) {
                case 404:
                    Toast.makeText( context, "not found", Toast.LENGTH_SHORT).show();
                    break;
                case 500:
                    Toast.makeText(context, "server broken", Toast.LENGTH_SHORT).show();
                    break;
                default:
                    Toast.makeText(context, "unknown error", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
/*
        if(response.code()>= 400 && response.code() < 599){
            onFailure(response);
        }
        else {
            onSuccess(response);
        }*/

    }

    @Override
    public void onFailure(Call<T> call, Throwable t){
        if(showProgressBar) {
            if (progressDialog != null && progressDialog.isShowing())
                progressDialog.dismiss();
        }
        Toast.makeText(context, t.getMessage(), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onSuccess(Response<T> response) {

    }

    @Override
    public void onFailure(Response<T> response) {

    }
}
