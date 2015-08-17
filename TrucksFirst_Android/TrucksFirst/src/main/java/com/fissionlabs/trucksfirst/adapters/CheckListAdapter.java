package com.fissionlabs.trucksfirst.adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.LabeledIntent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.fissionlabs.trucksfirst.R;
import com.fissionlabs.trucksfirst.home.TFHomeActivity;
import com.fissionlabs.trucksfirst.pojo.Checklist;
import com.fissionlabs.trucksfirst.pojo.ChecklistNew;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

public class CheckListAdapter extends BaseAdapter {

    private static final int TECHNICAL_HEADER = 0;
    private static final int OPERATIONAL_ITEM = 1;
    private static final int TECHNICAL_ITEM = 2;
    private static final int TYPE_MAX_COUNT = TECHNICAL_ITEM + 1;
    private ArrayList<Checklist> mChecklistArrayList = null;
    private final Activity context;

    private final LayoutInflater mInflater;
    private final ChecklistNew mChecklistNew;
    private ProgressDialog dialog = null;
    private String dwnload_file_path = "http://unec.edu.az/application/uploads/2014/12/pdf-sample.pdf";
    private String dest_file_path = "/sdcard/dwnloaded_file2.pdf";

    public CheckListAdapter(Activity context, ArrayList<Checklist> ChecklistArrayList, ChecklistNew checklistNew) {
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.mChecklistArrayList = ChecklistArrayList;
        this.context = context;
        this.mChecklistNew = checklistNew;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 12) {
            return TECHNICAL_HEADER;
        } else if (position <= 11) {
            return OPERATIONAL_ITEM;
        } else {
            return TECHNICAL_ITEM;
        }
    }

    @Override
    public int getViewTypeCount() {
        return TYPE_MAX_COUNT;
    }

    public int getCount() {
        return mChecklistArrayList.size();
    }

    public Object getItem(int position) {
        return mChecklistArrayList.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    @SuppressLint("InflateParams")
    public View getView(final int position, View convertView, final ViewGroup parent) {
        final ViewHolder holder;
        int type = getItemViewType(position);
        System.out.println("getView " + position + " " + convertView + " type = " + type);
        if (convertView == null) {
            holder = new ViewHolder();
            switch (type) {
                case TECHNICAL_HEADER:
                    convertView = mInflater.inflate(R.layout.technical_header, parent, false);
                    break;
                case OPERATIONAL_ITEM:
                    convertView = mInflater.inflate(R.layout.operational_item, parent, false);
                    holder.mTvOperational = (TextView) convertView.findViewById(R.id.operational);
                    holder.mTvChecklistItem = (TextView) convertView.findViewById(R.id.checklist_item);
                    holder.mRadioBtnYes = (RadioButton) convertView.findViewById(R.id.radio_btn_yes);
                    holder.mRadioBtnNo = (RadioButton) convertView.findViewById(R.id.radio_btn_no);
                    holder.mImgPrint = (ImageView) convertView.findViewById(R.id.print_img);
                    holder.mImgEmail = (ImageView) convertView.findViewById(R.id.email_img);
                    holder.mRadioGroup = (RadioGroup) convertView.findViewById(R.id.radio_group);

                    break;
                case TECHNICAL_ITEM:
                    convertView = mInflater.inflate(R.layout.technical_item, parent, false);
                    holder.mTvOperational = (TextView) convertView.findViewById(R.id.technical);
                    holder.mTvChecklistItem = (TextView) convertView.findViewById(R.id.checklist_technical);
                    break;
            }
            assert convertView != null;
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        switch (type) {

            case OPERATIONAL_ITEM:
                holder.mRadioBtnNo.setTag(holder);
                holder.mRadioBtnYes.setTag(holder);
                holder.mTvOperational.setText(mChecklistArrayList.get(position).getOperational());
                holder.mTvChecklistItem.setText(mChecklistArrayList.get(position).getChecklistItem());
                if (mChecklistArrayList.get(position).getStatus()) {
                    holder.mRadioBtnYes.setChecked(true);
                } else {
                    holder.mRadioBtnNo.setChecked(true);
                }
                holder.mImgEmail.setVisibility(View.INVISIBLE);
                if (mChecklistArrayList.get(position).getChecklistItem().equalsIgnoreCase(context.getResources().getString(R.string.grn_bilti))) {
                    holder.mImgEmail.setVisibility(View.VISIBLE);
                }
                if (position < 6 || position == 7) {
                    holder.mImgPrint.setVisibility(View.VISIBLE);
                }


                holder.mImgEmail.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        sendEmail();
                    }
                });

                holder.mImgPrint.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog = ProgressDialog.show(context, "", "Downloading file...", true);
                        new Thread(new Runnable() {
                            public void run() {
                                downloadFile(dwnload_file_path, dest_file_path);
                            }
                        }).start();

                    }
                });

                holder.mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                        TFHomeActivity.isChangesMade = true;
                        if (R.id.radio_btn_yes == checkedId) {
                            mChecklistArrayList.get(position).setStatus(true);
                            updateStatus(position, true);
                        } else {
                            mChecklistArrayList.get(position).setStatus(false);
                            updateStatus(position, false);
                        }
                        notifyDataSetChanged();
                    }
                });

                break;
            case TECHNICAL_ITEM:
                holder.mTvOperational.setText(mChecklistArrayList.get(position).getOperational());
                holder.mTvChecklistItem.setText(mChecklistArrayList.get(position).getChecklistItem());
                convertView.findViewById(R.id.cross).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        TFHomeActivity.isChangesMade = true;
                        ((ListView) parent).performItemClick(v, position, 0);
                    }
                });
                break;
        }

        return convertView;
    }

    private void updateStatus(int position, boolean flag) {
        if(mChecklistArrayList.get(position).getChecklistItem().equalsIgnoreCase(context.getResources().getString(R.string.registration_certificate))){
            mChecklistNew.setRegistrationCertificate(flag);
        }else if(mChecklistArrayList.get(position).getChecklistItem().equalsIgnoreCase(context.getResources().getString(R.string.fitness_certificate))){
            mChecklistNew.setFitnessCertificate(flag);
        }else if(mChecklistArrayList.get(position).getChecklistItem().equalsIgnoreCase(context.getResources().getString(R.string.national_permit))){
            mChecklistNew.setNationalPermit(flag);
        }else if(mChecklistArrayList.get(position).getChecklistItem().equalsIgnoreCase(context.getResources().getString(R.string.road_tax_booklet))){
            mChecklistNew.setRoadTaxBooklet(flag);
        }else if(mChecklistArrayList.get(position).getChecklistItem().equalsIgnoreCase(context.getResources().getString(R.string.pollution_certificate))){
            mChecklistNew.setPollutionCertificate(flag);
        }else if(mChecklistArrayList.get(position).getChecklistItem().equalsIgnoreCase(context.getResources().getString(R.string.insurance))){
            mChecklistNew.setInsurance(flag);
        }else if(mChecklistArrayList.get(position).getChecklistItem().equalsIgnoreCase(context.getResources().getString(R.string.toll_tax_receipt_and_cash_balance))){
            mChecklistNew.setTollTaxReceiptAndCashBalance(flag);
        }else if(mChecklistArrayList.get(position).getChecklistItem().equalsIgnoreCase(context.getResources().getString(R.string.grn_bilti))){
            mChecklistNew.setGrnOrBilti(flag);
        }else if(mChecklistArrayList.get(position).getChecklistItem().equalsIgnoreCase(context.getResources().getString(R.string.seal_intactness))){
            mChecklistNew.setSealIntactness(flag);
        }else if(mChecklistArrayList.get(position).getChecklistItem().equalsIgnoreCase(context.getResources().getString(R.string.tool_kit))){
            mChecklistNew.setToolKit(flag);
        }else if(mChecklistArrayList.get(position).getChecklistItem().equalsIgnoreCase(context.getResources().getString(R.string.others))){
            mChecklistNew.setToolKit(flag);
        }else if(mChecklistArrayList.get(position).getChecklistItem().equalsIgnoreCase(context.getResources().getString(R.string.cabin_cleanliness))){
            mChecklistNew.setCabinCleanliness(flag);
        }
    }

    public class ViewHolder {
        TextView mTvOperational;
        TextView mTvChecklistItem;
        RadioButton mRadioBtnYes;
        RadioButton mRadioBtnNo;
        ImageView mImgPrint;
        ImageView mImgEmail;
        public RadioGroup mRadioGroup;
    }

    private void sendEmail() {
        Intent emailIntent = new Intent();
        emailIntent.setAction(Intent.ACTION_SEND);
        emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Documents- GRN/Bilti");
        emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, "");
        emailIntent.setType("message/rfc822");

        PackageManager pm = context.getPackageManager();
        Intent sendIntent = new Intent(Intent.ACTION_SEND);
        sendIntent.setType("text/plain");

        Intent openInChooser = Intent.createChooser(emailIntent, "Share Via");

        List<ResolveInfo> resInfo = pm.queryIntentActivities(sendIntent, 0);
        List<LabeledIntent> intentList = new ArrayList<>();
        for (int i = 0; i < resInfo.size(); i++) {
            ResolveInfo ri = resInfo.get(i);
            String packageName = ri.activityInfo.packageName;
            if (packageName.contains("android.email")) {
                emailIntent.setPackage(packageName);
            } else if (packageName.contains("android.gm")) {
                Intent intent = new Intent();
                intent.setComponent(new ComponentName(packageName, ri.activityInfo.name));
                intent.setAction(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_TEXT, "");
                intent.putExtra(Intent.EXTRA_SUBJECT, "Documents- GRN/Bilti");
                intent.setType("message/rfc822");
                intentList.add(new LabeledIntent(intent, packageName, ri.loadLabel(pm), ri.icon));
            }
        }

        LabeledIntent[] extraIntents = intentList.toArray(new LabeledIntent[intentList.size()]);

        openInChooser.putExtra(Intent.EXTRA_INITIAL_INTENTS, extraIntents);
        context.startActivity(openInChooser);
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
            final Uri printFileUri = Uri.parse("file:///sdcard/dwnloaded_file2.pdf");
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setPackage("com.hp.android.print");
            i.setDataAndType(printFileUri, "text/plain");
            context.startActivity(i);

        } catch(FileNotFoundException e) {
            hideProgressIndicator();
            return;
        } catch (IOException e) {
            hideProgressIndicator();
            return;
        }
    }

    private void hideProgressIndicator() {
            context.runOnUiThread(new Runnable() {
                public void run() {
                    dialog.dismiss();
                }
            });
    }

}