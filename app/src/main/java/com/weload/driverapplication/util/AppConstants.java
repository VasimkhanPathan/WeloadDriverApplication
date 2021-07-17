package com.weload.driverapplication.util;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.preference.PreferenceManager;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class AppConstants {


    public static NetworkInfo getNetworkInfo(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo();
    }

    /**
     * Check if there is any connectivity
     *
     * @param context
     * @return
     */
    public static boolean isConnected(Context context) {
        NetworkInfo info = AppConstants.getNetworkInfo(context);
        return (info != null && info.isConnected());
    }

    /**
     * Check if there is fast connectivity
     *
     * @param context
     * @return
     */
    public static boolean isConnectedFast(Context context) {
        NetworkInfo info = AppConstants.getNetworkInfo(context);
        return (info != null && info.isConnected() && AppConstants.isConnectionFast(info.getType(), info.getSubtype()));
    }

    /**
     * Check if the connection is fast
     *
     * @param type
     * @param subType
     * @return
     */
    public static boolean isConnectionFast(int type, int subType) {
        if (type == ConnectivityManager.TYPE_WIFI) {
            return true;
        } else if (type == ConnectivityManager.TYPE_MOBILE) {
            switch (subType) {
                case TelephonyManager.NETWORK_TYPE_1xRTT:
                    return false; // ~ 50-100 kbps
                case TelephonyManager.NETWORK_TYPE_CDMA:
                    return false; // ~ 14-64 kbps
                case TelephonyManager.NETWORK_TYPE_EDGE:
                    return false; // ~ 50-100 kbps
                case TelephonyManager.NETWORK_TYPE_EVDO_0:
                    return true; // ~ 400-1000 kbps
                case TelephonyManager.NETWORK_TYPE_EVDO_A:
                    return true; // ~ 600-1400 kbps
                case TelephonyManager.NETWORK_TYPE_GPRS:
                    return false; // ~ 100 kbps
                case TelephonyManager.NETWORK_TYPE_HSDPA:
                    return true; // ~ 2-14 Mbps
                case TelephonyManager.NETWORK_TYPE_HSPA:
                    return true; // ~ 700-1700 kbps
                case TelephonyManager.NETWORK_TYPE_HSUPA:
                    return true; // ~ 1-23 Mbps
                case TelephonyManager.NETWORK_TYPE_UMTS:
                    return true; // ~ 400-7000 kbps
                /*
                 * Above API level 7, make sure to set android:targetSdkVersion
                 * to appropriate level to use these
                 */
                case TelephonyManager.NETWORK_TYPE_EHRPD: // API level 11
                    return true; // ~ 1-2 Mbps
                case TelephonyManager.NETWORK_TYPE_EVDO_B: // API level 9
                    return true; // ~ 5 Mbps
                case TelephonyManager.NETWORK_TYPE_HSPAP: // API level 13
                    return true; // ~ 10-20 Mbps
                case TelephonyManager.NETWORK_TYPE_IDEN: // API level 8
                    return false; // ~25 kbps
                case TelephonyManager.NETWORK_TYPE_LTE: // API level 11
                    return true; // ~ 10+ Mbps
                // Unknown
                case TelephonyManager.NETWORK_TYPE_UNKNOWN:
                default:
                    return false;
            }
        } else {
            return false;
        }
    }

    public static class URL {
        public static final String PRIVACY_POLICY = "https://www.coincode.in/privacy-policy.php";
        public static String URL = "";  //  api url
    }

    public static class IntentKeys {
        public static String AGENT = "AGENT";
        public static String STATION_LIST = "STATION_LIST";
    }

    private static class ApiConfiguration {
        private static final String SERVER_URL = "https://rahuls73.sg-host.com/";
    }

    public static class ApiNames {
        public static final String API_URL = ApiConfiguration.SERVER_URL;
       // public static String OWNER_ID = "51";
        public static final String LOGIN = "driver-login/";
        public static final String REGISTER_FCM_TOKEN = "weload-fmc-token-update/";
        public static final String FORGOT_PASSWORD = "driver-forgot-password/";
        public static final String RESET_PASSWORD = "driver-reset-password/";
        public static final String GET_ALL_JOBS = "get-all-jobs/";
        public static final String GET_JOB_INFO= "get-driver-job/";
        public static final String START_JOB= "updater-job/";
        public static final String Job_COMPLETE= "complete-weload-job/";
        public static final String JOB_HISTORY= "weload-job-history/";
        public static final String JOB_HISTORY_FILTER= "weload-job-history-filter/";
        public static final String JOB_TRANSCATION= "weload-transaction-history-filter/";
        public static final String JOB_COUNT= "weload-driver-job-counts/";
        public static final String LEAVE_APPLICATION= "weload-driver-jleave-request/";

    }

    public static class Status {
        public static int SUCCESS = 200;
        public static int NO_DATA = 203;
        public static String FAILED = "404";
        public static String TRUE = "0";
        public static String FALSE = "1";
        public static String SYNCED = "0";
        public static String NOT_SYNCED = "1";
    }

    public static class Keys {  //  Shared Preferences Keys
        public static String DRIVE_ID = "com.driver.driver_id";
        public static String DRIVE_NAME = "com.driver.driver_name";
        public static String DRIVE_MOBILE = "com.driver.driver_mobile";
        public static String API_KEY="com.driver.api_key";
        public static String SESSION_TOKEN="com.driver.session_key";
        public static String FIREBASE_TOKEN="com.driver.firebase_token";

    }

    public static boolean validateMobile(String strMobileNo){
        if(strMobileNo!=null && strMobileNo!=""){
            if(strMobileNo.length()>=8 && strMobileNo.length()<=10){
                return  true;
            }else {

                return false;
            }
        }else{

            return false;
        }

    }
    public static boolean validatePassword(String strPassword){
        if(strPassword!=null && !strPassword.isEmpty()){
            return true;
        }else

        return  false;
    }
    public static class Formats {
        public static String DATE_TIME_FOR_IMAGE = "dd-MMM-yyyy_HHmmss";
        public static String PASSWORD_PATTERN = "((?=.*\\d)(?=.*[@#$%])(?=.*[a-z])(?=.*[A-Z]).{8,20})";
        public static String DATE_FORMAT = "yyyy-MM-dd";
        public static String TIME_FORMAT = "hh:mm:ss a";
        public static String TIME_FORMAT_24 = "HH:mm:ss";
        public static String DATE_TIME_FORMAT = "dd-MM-yyyy hh:mm:ss a";
        public static String DATE_TIME_FORMAT_SERVER = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
        public static String DATE_TIME_MONTH = "E, dd MMM yyyy HH:mm:ss";


        /*
         *   Password Pattern Details
         *   (            Start of group
         *   (?=.*\d)	 must contains one digit from 0-9
         *   (?=.*[a-z])  must contains one lowercase characters
         *   (?=.*[A-Z])  must contains one uppercase characters
         *   (?=.*[@#$%]) must contains one special symbols in the list "@#$%"    //  Not Used
         *   .            match anything with previous condition checking
         *   {8,20}       length at least 8 characters and maximum of 20
         *   )            End of group
         *
         */

        public static String getStringInNumberFormat(double dNumber) {
            NumberFormat formatter = NumberFormat.getNumberInstance();
            formatter.setMinimumFractionDigits(3);
            formatter.setMaximumFractionDigits(3);
            return formatter.format(dNumber);
        }

        public static String getStringInCCNumberFormat(double dNumber) {
            NumberFormat formatter = NumberFormat.getNumberInstance();
            formatter.setMinimumFractionDigits(5);
            formatter.setMaximumFractionDigits(5);
            return formatter.format(dNumber);
        }

        public static String getDate(String sDate) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATE_TIME_FORMAT_SERVER);
            simpleDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
            Date date = null;
            String sConvertDate = "";
            try {
                date = simpleDateFormat.parse(sDate);
                simpleDateFormat = new SimpleDateFormat(DATE_TIME_MONTH);
                sConvertDate = simpleDateFormat.format(date);
            } catch (ParseException e1) {
                e1.printStackTrace();
                Log.d("Log","error "+e1.getMessage());
            }
            return sConvertDate;
        }

        public static String getTime(String sDate) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATE_TIME_FORMAT_SERVER);
            simpleDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
            String sTime = "";
            Date date = null;
            try {
                date = simpleDateFormat.parse(sDate);
                simpleDateFormat = new SimpleDateFormat(TIME_FORMAT);
                sTime = simpleDateFormat.format(date);
            } catch (ParseException e1) {
                e1.printStackTrace();
            }
            return sTime;
        }

        public static String getDateTime(String sDate) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATE_TIME_FORMAT_SERVER);
            simpleDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
            Date date = null;
            String sConvertDate = "";
            try {
                date = simpleDateFormat.parse(sDate);
                simpleDateFormat = new SimpleDateFormat(DATE_TIME_FORMAT);
                sConvertDate = simpleDateFormat.format(date);
            } catch (ParseException e1) {
                e1.printStackTrace();
            }
            return sConvertDate;
        }
    }

    //  Get & Set Values in Shared Preferences
    public static class Preferences {

        // Boolean Preferences (Checkbox)
        public static boolean getBooleanPreference(Context context, String key, boolean defaultValue) {
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
            return preferences.getBoolean(key, defaultValue);
        }

        public static void setBooleanPreferences(Context context, String key, boolean value) {
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
            SharedPreferences.Editor preferenceEditor = preferences.edit();
            preferenceEditor.putBoolean(key, value);
            preferenceEditor.apply();
        }

        public static String getStringPreference(Context context, String key,String defaultValue) {
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
            return preferences.getString(key, defaultValue);
        }

        public static String getRecentRatePreference(Context context, String key) {
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
            return preferences.getString(key, "0");
        }

        public static void setStringPreferences(Context context, String key, String value) {
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());

            SharedPreferences.Editor preferenceEditor = preferences.edit();
            preferenceEditor.putString(key, value);
            preferenceEditor.apply();
        }

        public static int getIntPreference(Context context, String key, int defValue) {
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
            return preferences.getInt(key, defValue);
        }

        public static void setIntPreferences(Context context, String key, int value) {
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
            SharedPreferences.Editor preferenceEditor = preferences.edit();
            preferenceEditor.putInt(key, value);
            preferenceEditor.apply();
        }
    }

    //  copy wallet address
    public static class Copy {
        public static void copyText(Context context, String sText) {
            ClipboardManager clipboard = (ClipboardManager) context.getSystemService(context.CLIPBOARD_SERVICE);
            ClipData clip = ClipData.newPlainText("WalletAddress", sText);
            clipboard.setPrimaryClip(clip);
            Toast.makeText(context, "Wallet address has been copied",
                    Toast.LENGTH_LONG).show();
        }
    }

    //  copy Key
    public static class CopyKey {
        public static void copyText(Context context, String sText) {
            ClipboardManager clipboard = (ClipboardManager) context.getSystemService(context.CLIPBOARD_SERVICE);
            ClipData clip = ClipData.newPlainText("Key", sText);
            clipboard.setPrimaryClip(clip);
            Toast.makeText(context, "Authentication key has been copied, Please paste this key in Google Authenticator app", Toast.LENGTH_LONG).show();
        }
    }

    public static class Paste {

        public static String pasteText(Context context) {
            String pasteText = "";
            ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
            if (clipboard.hasPrimaryClip() == true) {
                ClipData.Item item = clipboard.getPrimaryClip().getItemAt(0);
                pasteText = item.getText().toString();
            } else {
                Toast.makeText(context, "Nothing to Paste", Toast.LENGTH_SHORT).show();
            }
            return pasteText;
        }

    }
}
