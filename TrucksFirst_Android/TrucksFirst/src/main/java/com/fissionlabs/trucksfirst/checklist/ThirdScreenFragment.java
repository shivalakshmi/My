package com.fissionlabs.trucksfirst.checklist;

import android.app.ProgressDialog;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.fissionlabs.trucksfirst.R;
import com.fissionlabs.trucksfirst.common.TFConst;
import com.fissionlabs.trucksfirst.model.checklist.NewChecklist;
import com.fissionlabs.trucksfirst.util.TFUtils;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @author ashok on 9/23/15.
 */
public class ThirdScreenFragment extends CheckListCommonFragment implements View.OnClickListener {

    private Button btnNext;
    private TextView mTvTime;
    private int count = 20;
    private boolean timeOver;
    private int timeTaken = 0;
    private RadioGroup radio_group_rc,radioBtnNationalPermit,radioBtnInsurance,radioBtnPollutionCerti,radioBtnDharamKaat,
            radioBtnRoadTax,radioBtnGoodsTax;
    private NewChecklist newChecklist = FirstScreenFragment.newChecklist;
    private ImageView rcDoc,npDoc,insuranceDoc,gtDoc,rtDoc,pcDoc;
    private String vehicleNumber;
    private String download_file_path = "";
    private String dest_file_path = "/sdcard/dwnloaded_file2.pdf";
    private ProgressDialog dialog = null;
    public static Uri printFileUri;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_third_screen_checklist, container, false);
        vehicleNumber =  TFUtils.getStringFromSP(getActivity(), "vehicle_number");
        btnNext = (Button) view.findViewById(R.id.btnNext);

        mTvTime = (TextView) view.findViewById(R.id.tvTime);
        TextView tvPageNumber = (TextView) view.findViewById(R.id.tvPageNumber);


        radio_group_rc = (RadioGroup)view.findViewById(R.id.radio_group_rc);
        radioBtnNationalPermit = (RadioGroup)view.findViewById(R.id.radioBtnNationalPermit);
        radioBtnInsurance = (RadioGroup)view.findViewById(R.id.radioBtnInsurance);
        radioBtnPollutionCerti = (RadioGroup)view.findViewById(R.id.radioBtnPollutionCerti);
        radioBtnDharamKaat = (RadioGroup)view.findViewById(R.id.radioBtnDharamKaat);
        radioBtnRoadTax = (RadioGroup)view.findViewById(R.id.radioBtnRoadTax);
        radioBtnGoodsTax = (RadioGroup)view.findViewById(R.id.radioBtnGoodsTax);
        rcDoc = (ImageView)view.findViewById(R.id.rc_doc);
        npDoc = (ImageView)view.findViewById(R.id.np_doc);
        insuranceDoc = (ImageView)view.findViewById(R.id.insurance_doc);
        gtDoc = (ImageView)view.findViewById(R.id.gt_docs);
        rtDoc = (ImageView)view.findViewById(R.id.rt_doc);
        pcDoc = (ImageView)view.findViewById(R.id.pc_doc);
        rcDoc.setOnClickListener(this);
        npDoc.setOnClickListener(this);
        insuranceDoc.setOnClickListener(this);
        gtDoc.setOnClickListener(this);
        rtDoc.setOnClickListener(this);
        pcDoc.setOnClickListener(this);

        tvPageNumber.setText(String.format(getResources().getString(R.string.page_number), 3, 11));
        mTvTime.setText(String.format(getResources().getString(R.string.timer), count));

        ((TextView) view.findViewById(R.id.tvScreenName)).setText(getResources().getString(R.string.truck_docs));

        radio_group_rc.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb = (RadioButton) group.findViewById(checkedId);
                if (null != rb && checkedId > -1) {
                    if (rb.getText().toString().equalsIgnoreCase("OK")) {
                        newChecklist.data.screen3.registrationCertificate = true;
                    } else {
                        newChecklist.data.screen3.registrationCertificate = false;
                    }
                }

            }
        });

        radioBtnNationalPermit.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb = (RadioButton) group.findViewById(checkedId);
                if (null != rb && checkedId > -1) {
                    if (rb.getText().toString().equalsIgnoreCase("OK")) {
                        newChecklist.data.screen3.nationalPermit = true;
                    } else {
                        newChecklist.data.screen3.nationalPermit = false;
                    }
                }

            }
        });

        radioBtnInsurance.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb = (RadioButton) group.findViewById(checkedId);
                if (null != rb && checkedId > -1) {
                    if (rb.getText().toString().equalsIgnoreCase("OK")) {
                        newChecklist.data.screen3.insurance = true;
                    } else {
                        newChecklist.data.screen3.insurance = false;
                    }
                }

            }
        });

        radioBtnGoodsTax.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb = (RadioButton) group.findViewById(checkedId);
                if (null != rb && checkedId > -1) {
                    if (rb.getText().toString().equalsIgnoreCase("OK")) {
                        newChecklist.data.screen3.goodsTaxRecipiet= true;
                    } else {
                        newChecklist.data.screen3.goodsTaxRecipiet = false;
                    }
                }

            }
        });

        radioBtnRoadTax.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb = (RadioButton) group.findViewById(checkedId);
                if (null != rb && checkedId > -1) {
                    if (rb.getText().toString().equalsIgnoreCase("OK")) {
                        newChecklist.data.screen3.roadTaxBooklet = true;
                    } else {
                        newChecklist.data.screen3.roadTaxBooklet = false;
                    }
                }

            }
        });

        radioBtnPollutionCerti.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb = (RadioButton) group.findViewById(checkedId);
                if (null != rb && checkedId > -1) {
                    if (rb.getText().toString().equalsIgnoreCase("OK")) {
                        newChecklist.data.screen3.pollutionCertificate = true;
                    } else {
                        newChecklist.data.screen3.pollutionCertificate = false;
                    }
                }

            }
        });

        radioBtnDharamKaat.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb = (RadioButton) group.findViewById(checkedId);
                if (null != rb && checkedId > -1) {
                    if (rb.getText().toString().equalsIgnoreCase("OK")) {
                        newChecklist.data.screen3.dharamKaantaParchi = true;
                    } else {
                        newChecklist.data.screen3.dharamKaantaParchi = false;
                    }
                }

            }
        });


        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                newChecklist.data.screen3.timeTaken = timeTaken;
                CheckListBaseFragment.moveToNext();
            }
        });

        return view;
    }

    @Override
    public void onClick(View v) {

        String temp = vehicleNumber.substring(0, 4) + "-" + vehicleNumber.substring(4, 5) + "-" + vehicleNumber.substring(5);

        switch(v.getId())
        {
            case R.id.rc_doc:
                temp = temp + "/" + temp + "_RC";
                break;
            case R.id.np_doc:
                temp = temp + "/" + temp + "_NP";
                break;
            case R.id.insurance_doc:
                temp = temp + "/" + temp + "_Insurance";
                break;
            case R.id.gt_docs:
                temp = temp + "/" + temp + "_GT";
                break;
            case R.id.rt_doc:
                temp = temp + "/" + temp + "_RT";
                break;
            case R.id.pc_doc:
                temp = temp + "/" + temp + "_PUC";
                break;


        }

        download_file_path = TFConst.URL_PRINT_DOCUMENT + temp + ".pdf";
        dialog = ProgressDialog.show(getActivity(), "", "Downloading file...", true);
        dest_file_path = "/sdcard/" + temp.split("/")[1] + ".pdf";
        new Thread(new Runnable() {
            public void run() {
                downloadFile(download_file_path, dest_file_path);
            }
        }).start();


        Bundle bundle = new Bundle();
        bundle.putString("pdf", download_file_path);
        bundle.putString("file", dest_file_path);
        bundle.putString("vehicle_number", vehicleNumber);
        this.startFragment(R.layout.pdf_viewer, bundle);

    }

    public void downloadFile(String url, String dest_file_path) {

        try {
            File dest_file = new File(dest_file_path);
            URL u = new URL(url);
            URLConnection conn = u.openConnection();
            int contentLength = conn.getContentLength();
            DataInputStream stream = new DataInputStream(u.openStream());
            byte[] buffer = new byte[contentLength];
            stream.readFully(buffer);
            stream.close();
            DataOutputStream fos = new DataOutputStream(new FileOutputStream(dest_file));
            fos.write(buffer);
            fos.flush();
            fos.close();
            hideProgressIndicator();

            printFileUri = Uri.parse("file://" + dest_file_path);
            /*Intent i = new Intent(Intent.ACTION_VIEW);
            i.setPackage("com.hp.android.print");
            i.setDataAndType(printFileUri, "text/plain");
            getActivity().startActivity(i);*/

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            hideProgressIndicator();
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.printing_file_not_found), Toast.LENGTH_SHORT).show();
                }
            });
            return;
        } catch (IOException e) {
            e.printStackTrace();
            hideProgressIndicator();
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.printing_issue_with_download), Toast.LENGTH_SHORT).show();
                }
            });
            return;
        } catch (Exception e) {
            e.printStackTrace();
            hideProgressIndicator();
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.printing_issue_with_download2), Toast.LENGTH_SHORT).show();
                }
            });
            return;
        }
    }

    private void hideProgressIndicator() {
        getActivity().runOnUiThread(new Runnable() {
            public void run() {
                dialog.dismiss();
            }
        });
    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if (isVisibleToUser) {
            startTimer();
        }
    }

    private void startTimer() {
        Timer T = new Timer();
        T.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {

                if (mHomeActivity == null) {
                    return;
                }

                mHomeActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        if (count == 0) {
                            timeOver = true;
                        }

                        if (timeOver) {
                            mTvTime.setTextColor(mHomeActivity.getResources().getColor(R.color.red));
                            mTvTime.setText(String.format(mHomeActivity.getResources().getString(R.string.timer), count));
                            count++;
                        } else {
                            mTvTime.setTextColor(mHomeActivity.getResources().getColor(R.color.black));
                            mTvTime.setText(String.format(mHomeActivity.getResources().getString(R.string.timer), count));
                            count--;
                        }

                        timeTaken++;
                    }
                });
            }
        }, 20, 1000);
    }


}
