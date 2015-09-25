package com.fissionlabs.trucksfirst.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.GravityCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.Toast;

import com.fissionlabs.trucksfirst.R;
import com.fissionlabs.trucksfirst.adapters.CheckListAdapter;
import com.fissionlabs.trucksfirst.common.TFCommonFragment;
import com.fissionlabs.trucksfirst.common.TFConst;

/**
 * Created by Lakshmi on 15-09-2015.
 */
public class TFpdfViewer extends TFCommonFragment implements TFConst {

    private WebView webView;
    private Button print, email;
    private Bundle bundle;
    private String pdf;
    private String file, vehicleNum;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.pdf_viewer, container, false);
        webView = (WebView) view.findViewById(R.id.webView);
        print = (Button) view.findViewById(R.id.print);
        email = (Button) view.findViewById(R.id.email);
        bundle = this.getArguments();


        mHomeActivity.isHomeFragment = false;

        mHomeActivity.mActionBar.setDisplayShowTitleEnabled(true);
        mHomeActivity.imageView.setVisibility(View.GONE);

        mHomeActivity.toolbar.setNavigationIcon(R.drawable.ic_back);
        mHomeActivity.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mHomeActivity.isHomeFragment == false)
                    mHomeActivity.onBackPressed();
                else
                    mHomeActivity.mDrawerLayout.openDrawer(GravityCompat.START);
            }
        });

        vehicleNum = bundle.getString("vehicle_number");

        if (bundle.getString("pdf") != null) {
            pdf = bundle.getString("pdf");
            file = bundle.getString("file");
            webView.getSettings().setJavaScriptEnabled(true);
            webView.setWebViewClient(new WebViewClient());
            if (savedInstanceState == null) {
                webView.loadUrl("http://docs.google.com/gview?embedded=true&url=" + pdf);
            }

        }


        print.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setPackage("com.hp.android.print");
                    i.setDataAndType(Uri.parse("file://" + file), "text/plain");
                    getActivity().startActivity(i);
                } catch (Exception e) {
                    e.printStackTrace();
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.printing_issue_with_download2), Toast.LENGTH_SHORT).show();
                        }
                    });
                    return;
                }
            }
        });

        email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckListAdapter.sendEmail(getActivity());
            }
        });

        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        // Save the state of the WebView
        webView.saveState(outState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mHomeActivity.mActionBar.setTitle(vehicleNum);
        mHomeActivity.toolbar.setNavigationIcon(R.drawable.ic_back);
        mHomeActivity.isHomeFragment = false;
        mHomeActivity.mActionBar.setDisplayShowTitleEnabled(false);
        mHomeActivity.imageView.setVisibility(View.GONE);
    }
}

