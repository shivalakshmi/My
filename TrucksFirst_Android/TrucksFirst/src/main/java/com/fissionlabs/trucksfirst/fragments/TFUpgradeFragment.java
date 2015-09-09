package com.fissionlabs.trucksfirst.fragments;


import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.view.GravityCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.fissionlabs.trucksfirst.R;
import com.fissionlabs.trucksfirst.common.TFCommonFragment;
import com.fissionlabs.trucksfirst.util.TFUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class TFUpgradeFragment extends TFCommonFragment {
    private Activity mActivity;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_updates,container,false);
        mHomeActivity.isHomeFragment = false;
        mHomeActivity.mActionBar.setDisplayShowTitleEnabled(true);
        mHomeActivity.imageView.setVisibility(View.GONE);

        mHomeActivity.toolbar.setNavigationIcon(R.drawable.ic_back);
        mHomeActivity.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mHomeActivity.isHomeFragment == false)
                    mHomeActivity.onBackPressed();
                else
                    mHomeActivity.mDrawerLayout.openDrawer(GravityCompat.START);
            }
        });

        view.findViewById(R.id.send_mail_sms).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TFUtils.showProgressBar(mActivity,getResources().getString(R.string.downloading_wait));
                new SimpleTask().execute();
            }
        });

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mHomeActivity.toolbar.setNavigationIcon(R.drawable.ic_menu);
        mHomeActivity.isHomeFragment = true;
        mHomeActivity.mActionBar.setDisplayShowTitleEnabled(false);
        mHomeActivity.imageView.setVisibility(View.VISIBLE);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mActivity = activity;
    }
    class SimpleTask extends AsyncTask<Void,Void,Boolean> {
        @Override
        protected Boolean doInBackground(Void... params) {
//            URL url = new URL("http://52.76.55.8/rivigo/Rivigo.apk");
//        URL url = new URL("https://www.dropbox.com/s/50932rdsk9nwlhq/Rivigo.apk");
            try {
                URL url = new URL("https://s3-ap-southeast-1.amazonaws.com/rivigo-dev/Rivigo.apk");
                HttpURLConnection c = (HttpURLConnection) url.openConnection();
                c.setRequestMethod("GET");
//            c.setDoOutput(true);
                c.connect();

                String PATH = Environment.getExternalStorageDirectory() + "/download/";
                File file = new File(PATH);
                file.mkdirs();
                File outputFile = new File(file, "app.apk");
                FileOutputStream fos = new FileOutputStream(outputFile);
                int status = c.getResponseCode();
                Log.e("connection status", "" + status);
                InputStream is = c.getInputStream();

                byte[] buffer = new byte[1024];
                int len1 = 0;
                while ((len1 = is.read(buffer)) != -1) {
                    fos.write(buffer, 0, len1);
                }
                fos.close();
                is.close();

            } catch (FileNotFoundException ee) {
                Log.e("File", "FileNotFoundException! " + ee);
                ee.printStackTrace();
                return false;
            } catch (Exception e) {
                Log.e("UpdateAPP", "Exception " + e);
                e.printStackTrace();
                return false;
            }
            return true;
        }

        @Override
        protected void onPostExecute(Boolean flag) {
            super.onPostExecute(flag);
            TFUtils.hideProgressBar();
            if(flag) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setDataAndType(Uri.fromFile(new File(Environment.getExternalStorageDirectory() + "/download/" + "app.apk")), "application/vnd.android.package-archive");
                startActivity(intent);
            } else {
                Toast.makeText(mActivity,getString(R.string.no_updates_found),Toast.LENGTH_LONG).show();

            }
        }
    }

}


