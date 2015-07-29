package com.fissionlabs.trucksfirst.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
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
import android.widget.Toast;

import com.fissionlabs.trucksfirst.R;
import com.fissionlabs.trucksfirst.pojo.Checklist;

import java.util.ArrayList;

/**
 * Created by Lakshmi on 27-07-2015.
 */
public class CheckListAdapter extends BaseAdapter {

    private static final int TECHNICAL_HEADER = 0;
    private static final int OPERATIONAL_ITEM = 1;
    private static final int TECHNICAL_ITEM = 2;
    private static final int TYPE_MAX_COUNT = TECHNICAL_ITEM + 1;
    private ArrayList<Checklist> mChecklistArrayList = new ArrayList();
    private static Context context;

    private LayoutInflater mInflater;

    public CheckListAdapter(Context context,ArrayList<Checklist> ChecklistArrayList) {
        mInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.mChecklistArrayList = ChecklistArrayList;
        this.context = context;
    }

    @Override
    public int getItemViewType(int position) {
        if(position==12)
        {
            return TECHNICAL_HEADER;
        }
        else if(position<=11)
        {
            return OPERATIONAL_ITEM;
        }else {
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
                    convertView = mInflater.inflate(R.layout.technical_header,parent, false);
                    break;
                case OPERATIONAL_ITEM:
                    convertView = mInflater.inflate(R.layout.operational_item, parent, false);
                    holder.mTVoperational = (TextView) convertView.findViewById(R.id.operational);
                    holder.mTVchecklistTtem = (TextView) convertView.findViewById(R.id.checklist_item);
                    holder.mRadioBtnYes = (RadioButton) convertView.findViewById(R.id.radio_btn_yes);
                    holder.mRadioBtnNo = (RadioButton) convertView.findViewById(R.id.radio_btn_no);
                    holder.mImgPrint = (ImageView) convertView.findViewById(R.id.print_img);
                    holder.mImgEmail = (ImageView) convertView.findViewById(R.id.email_img);
                    holder.mRadioGroup = (RadioGroup)convertView.findViewById(R.id.radio_group);

                    break;
                case TECHNICAL_ITEM:
                    convertView = mInflater.inflate(R.layout.technical_item, parent, false);
                    holder.mTVoperational = (TextView) convertView.findViewById(R.id.technical);
                    holder.mTVchecklistTtem = (TextView) convertView.findViewById(R.id.checklist_technical);
                    break;
            }
            assert convertView != null;
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder)convertView.getTag();
        }

        switch (type) {

            case OPERATIONAL_ITEM:
                holder.mRadioBtnNo.setTag(holder);
                holder.mRadioBtnYes.setTag(holder);
                holder.mTVoperational.setText(mChecklistArrayList.get(position).getOperational());
                holder.mTVchecklistTtem.setText(mChecklistArrayList.get(position).getChecklistItem());
                if (mChecklistArrayList.get(position).getStatus()) {
                    holder.mRadioBtnYes.setChecked(true);
                } else {
                    holder.mRadioBtnNo.setChecked(true);
                }
                holder.mImgEmail.setVisibility(View.INVISIBLE);
                if(mChecklistArrayList.get(position).getChecklistItem().equalsIgnoreCase(context.getResources().getString(R.string.grn_bilti)))
                {
                    holder.mImgEmail.setVisibility(View.VISIBLE);
                }
                if(position<6 || position==7)
                {
                    holder.mImgPrint.setVisibility(View.VISIBLE);
                }


                holder.mImgEmail.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        sendEmail();
                    }
                });


                holder.mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
                {
                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                        if(R.id.radio_btn_yes == checkedId)
                        {
                            mChecklistArrayList.get(position).setStatus(true);
                        }
                        else
                        {
                            mChecklistArrayList.get(position).setStatus(false);
                        }
                        notifyDataSetChanged();
                    }
                });

                break;
            case TECHNICAL_ITEM:
                holder.mTVoperational.setText(mChecklistArrayList.get(position).getOperational());
                holder.mTVchecklistTtem.setText(mChecklistArrayList.get(position).getChecklistItem());
                convertView.findViewById(R.id.cross).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ((ListView) parent).performItemClick(v, position, 0);
                    }
                });
                break;
        }

        return convertView;
    }

    public class ViewHolder {
        TextView mTVoperational;
        TextView mTVchecklistTtem;
        RadioButton mRadioBtnYes;
        RadioButton mRadioBtnNo;
        ImageView mImgPrint;
        ImageView mImgEmail;
        public RadioGroup mRadioGroup;
    }
    static void sendEmail() {
        String[] TO = {""};
        String[] CC = {""};

        Intent emailIntent = new Intent(Intent.ACTION_SEND);

        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.setType("text/plain");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
        emailIntent.putExtra(Intent.EXTRA_CC, CC);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Your subject");
        emailIntent.putExtra(Intent.EXTRA_TEXT, "Email message goes here");
        emailIntent.setType("message/rfc822");

        try {

            context.startActivity(Intent.createChooser(emailIntent, "Send mail..."));
        }
        catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(context, "There is no email client installed.", Toast.LENGTH_SHORT).show();
        }
    }

}