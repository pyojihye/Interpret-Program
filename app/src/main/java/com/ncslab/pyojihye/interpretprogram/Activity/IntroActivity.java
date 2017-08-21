package com.ncslab.pyojihye.interpretprogram.Activity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.view.Window;

import com.ncslab.pyojihye.interpretprogram.R;

import static com.ncslab.pyojihye.interpretprogram.ECT.Const.FIRSTRUN;

/**
 * Created by PYOJIHYE on 2017-07-11.
 */

public class IntroActivity extends Activity {
    //    private final String TAG = "IntroActivity";
    Handler h;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_intro);

        final SharedPreferences settings = getSharedPreferences(FIRSTRUN, MODE_PRIVATE);
        if (settings.getBoolean("isFirstRun", true)) {
            AlertDialog.Builder d = new AlertDialog.Builder(this);
            d.setTitle(getString(R.string.dialog_title_log));
            d.setMessage(getString(R.string.dialog_contents_log));
            d.setIcon(R.mipmap.ic_launcher);

            d.setPositiveButton(getString(R.string.dialog_yes), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    h = new Handler();
                    h.postDelayed(irun, 2000);
//                    Log.d(TAG, "onCreate()");
                }

                Runnable irun = new Runnable() {
                    public void run() {
                        SharedPreferences.Editor editor = settings.edit();
                        editor.putBoolean("isFirstRun", false);
                        editor.commit();

                        transition();
//                        Log.d(TAG, "Runnable()");
                    }
                };
            });

            d.setNegativeButton(getString(R.string.dialog_no), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                    finish();
                }
            });

            d.show();
        } else {
            Runnable irun = new Runnable() {
                public void run() {
                    transition();
//                    Log.d(TAG, "Runnable()");
                }
            };

            h = new Handler();
            h.postDelayed(irun, 2000);
        }
    }

    private void transition(){
        Intent i = new Intent(IntroActivity.this, LoginActivity.class);
        startActivity(i);
        finish();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }
}
