package com.example.whetherforcost;

import android.content.Context;
import android.net.ConnectivityManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Utils {




    //Check internet connection.
    public static boolean isNetworkConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        boolean result = cm.getActiveNetworkInfo() != null;

        if (!result) {
            showToast(context, "Please connect to internet!");
        }

        return result;
    }

    public static void showToast(Context argContext, String msg) {
        try {
            View toastView = LayoutInflater.from(argContext).inflate(R.layout.custom_toast_view, null);
            TextView txtMsg = toastView.findViewById(R.id.customToastText);
            txtMsg.setText(msg);
            Toast toast = new Toast(argContext);
            toast.setView(toastView);
            toast.setDuration(Toast.LENGTH_LONG);
            toast.setGravity(Gravity.BOTTOM, 0, 100);
            toast.show();
        } catch (NullPointerException e) {
        }
    }




    public static String convertDate(String mDate, String mFormat, String returnFormat) throws Exception {
        String strCurrentDate = mDate;
        SimpleDateFormat inputFormat = new SimpleDateFormat(mFormat, Locale.US);
        SimpleDateFormat outputFormat = new SimpleDateFormat(returnFormat, Locale.US);
        Date newDate = null;
        try {
            newDate = inputFormat.parse(strCurrentDate);
            String date = outputFormat.format(newDate);
            return date;
        } catch (ParseException e) {
            Log.e("ParseException", "convertDate: ", e);
        }
        return "";
    }


    public static int calculateNoOfColumns(Context context, float columnWidthDp) { // For example columnWidthdp=180
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        float screenWidthDp = displayMetrics.widthPixels / displayMetrics.density;
        int noOfColumns = (int) (screenWidthDp / columnWidthDp + 0.5); // +0.5 for correct rounding to int.
        return noOfColumns;
    }

    public static  boolean validateDateTime(String dateTime){
        boolean flag= false;

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        Date strDate = null;
        try {
            strDate = sdf.parse(dateTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if ((System.currentTimeMillis()+3600000) > strDate.getTime()) {
            flag = false;
        }
        else{
            flag = true;
        }
        return flag;
    }


    //celsius = kelvin - 273.0;
    //         System.out.println ("\n" + kelvin + "K = "+ celsius + "C");
    //         fahrenhiet = (celsius * 9.0/5.0) + 32.0;

    public static String getCalTemp(String temp){
        String deg= "";
        try {
            deg = String.valueOf((Double.parseDouble(temp)- 273.0));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return deg;
    }

    public static String dateOnly(String dateTime){
        String date= "";
        try {
            SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date dates = fmt.parse(dateTime);

            SimpleDateFormat fmtOut = new SimpleDateFormat("dd\nEEEE");
            date=  fmtOut.format(dates);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return date;

    }

    public static String timeOnly(String dateTime){
        String date= "";
        try {
            SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date dates = fmt.parse(dateTime);

            SimpleDateFormat fmtOut = new SimpleDateFormat("hh:mm a");
            date=  fmtOut.format(dates);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return date;
    }

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }

    public static String getDateTimeFromMilisecond(String milisecond){
        String dateTime="";

        try {
            SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy hh:mm a");
            dateTime = formatter.format(new Date(Long.parseLong(milisecond)));
        } catch (Exception e) {
            e.printStackTrace();
        }


        return dateTime;
    }

}
