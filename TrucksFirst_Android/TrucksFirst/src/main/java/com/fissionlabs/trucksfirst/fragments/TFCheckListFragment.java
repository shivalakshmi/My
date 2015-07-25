package com.fissionlabs.trucksfirst.fragments;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.fissionlabs.trucksfirst.R;
import com.fissionlabs.trucksfirst.common.TFCommonFragment;
import com.fissionlabs.trucksfirst.pojo.Checklist;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
@SuppressWarnings({"ALL", "CanBeFinal"})
public class TFCheckListFragment extends TFCommonFragment {


    private ArrayList<Checklist> mChecklistArrayList = new ArrayList();
    private ArrayList<String> mDocumentStatusList = new ArrayList();
    private ArrayList<String> mDocumentChecklist = new ArrayList();
    private ArrayList<String> mKitsStatusList = new ArrayList();
    private ArrayList<String> mKitsChecklist = new ArrayList();
    private ArrayList<String> mCleanlinessStatusList = new ArrayList();
    private ArrayList<String> mCleanlinessChecklist = new ArrayList();

    private ArrayList<String> mDriverChecklist = new ArrayList();
    private ArrayList<String> mTyreOilChecklist = new ArrayList();
    private ArrayList<String> mElectrical = new ArrayList();
    private ArrayList<String> mScratch = new ArrayList();

    @Override
    public void onCreate(Bundle savedInstanceState) {
       super.onCreate(savedInstanceState);
         setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_check_list, container, false);

        ListView mLVChecklist = (ListView) view.findViewById(R.id.listView);
        mChecklistArrayList.clear();
        checklistData();
        mLVChecklist.setAdapter(new MyCustomAdapter(getActivity()));
        View headerView = inflater.inflate(R.layout.operational_header,null);
        mLVChecklist.addHeaderView(headerView);
        View footerView = inflater.inflate(R.layout.checklist_footer,null);
        mLVChecklist.addFooterView(footerView);
        return view;
    }

    class MyCustomAdapter extends BaseAdapter {

        private static final int TECHNICAL_HEADER = 0;
        private static final int TYPE_OPERATIONAL = 1;
        private static final int TYPE_TECHNICAL = 2;
        private static final int TYPE_MAX_COUNT = TYPE_TECHNICAL + 1;

        private LayoutInflater mInflater;

        public MyCustomAdapter(Context context) {
            mInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getItemViewType(int position) {
            if(position==12)
            {
                return TECHNICAL_HEADER;
            }
            else if(position<=11)
            {
                return TYPE_OPERATIONAL;
            }else {
                return TYPE_TECHNICAL;
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
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            int type = getItemViewType(position);
            System.out.println("getView " + position + " " + convertView + " type = " + type);
            if (convertView == null) {
                holder = new ViewHolder();
                switch (type) {
                    case TECHNICAL_HEADER:
                        convertView = mInflater.inflate(R.layout.technical_header,parent, false);
                        break;
                    case TYPE_OPERATIONAL:
                        convertView = mInflater.inflate(R.layout.operational_item, parent, false);
                        holder.mTVoperational = (TextView) convertView.findViewById(R.id.operational);
                        holder.mTVchecklistTtem = (TextView) convertView.findViewById(R.id.checklist_item);
                        holder.mRadioBtnYes = (RadioButton) convertView.findViewById(R.id.radio_btn_yes);
                        holder.mRadioBtnNo = (RadioButton) convertView.findViewById(R.id.radio_btn_no);
                        holder.mImgPrint = (ImageView) convertView.findViewById(R.id.print_img);
                        holder.mImgEmail = (ImageView) convertView.findViewById(R.id.email_img);

                        break;
                    case TYPE_TECHNICAL:
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

                case TYPE_OPERATIONAL:
                    holder.mTVoperational.setText(mChecklistArrayList.get(position).getOperational());
                    holder.mTVchecklistTtem.setText(mChecklistArrayList.get(position).getChecklistItem());
                    if (mChecklistArrayList.get(position).getStatus().equalsIgnoreCase("true")) {
                        holder.mRadioBtnYes.setChecked(true);
                    } else {
                        holder.mRadioBtnNo.setChecked(true);
                    }
                    break;
                case TYPE_TECHNICAL:
                    holder.mTVoperational.setText(mChecklistArrayList.get(position).getOperational());
                    holder.mTVchecklistTtem.setText(mChecklistArrayList.get(position).getChecklistItem());
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
        }


    }

    private void checklistData()
    {
        mDocumentStatusList.add("true");
        mDocumentStatusList.add("true");
        mDocumentStatusList.add("false");
        mDocumentStatusList.add("true");
        mDocumentStatusList.add("true");
        mDocumentStatusList.add("false");
        mDocumentStatusList.add("true");
        mDocumentStatusList.add("true");
        mDocumentStatusList.add("true");

        mKitsStatusList.add("true");
        mKitsStatusList.add("false");

        mCleanlinessStatusList.add("false");

        Checklist mChecklist;

        String[] documentList = getResources().getStringArray(R.array.document_checklist);
        for (int i = 0; i < documentList.length; i++) {
            if (i == 0) {
                mChecklist = new Checklist(getResources().getString(R.string.documents), documentList[i], mDocumentStatusList.get(i), "true", "true");
                mChecklistArrayList.add(mChecklist);
            } else {
                mChecklist = new Checklist("", documentList[i], mDocumentStatusList.get(i), "true", "true");
                mChecklistArrayList.add(mChecklist);
            }
        }

        String[] kitsList = getResources().getStringArray(R.array.kits_checklist);
        for (int j = 0; j < kitsList.length; j++) {
            if (j == 0) {
                mChecklist = new Checklist(getResources().getString(R.string.kits), kitsList[j], mKitsStatusList.get(j), "true", "true");
                mChecklistArrayList.add(mChecklist);
            } else {
                mChecklist = new Checklist("",kitsList[j], mKitsStatusList.get(j), "true", "true");
                mChecklistArrayList.add(mChecklist);
            }
        }
        mChecklist = new Checklist(getResources().getString(R.string.cleanliness), getResources().getString(R.string.cabin_cleanliness), mCleanlinessStatusList.get(0), "true", "true");
        mChecklistArrayList.add(mChecklist);
        mChecklist = new Checklist(null, null, null, "true", "true");
        mChecklistArrayList.add(mChecklist);

        String[] driverList = getResources().getStringArray(R.array.driver_checklist);

        for (int k = 0; k < driverList.length; k++) {
            if (k == 0) {
                mChecklist = new Checklist(getResources().getString(R.string.driver), driverList[k], "false", "true", "true");
                mChecklistArrayList.add(mChecklist);
            } else {
                mChecklist = new Checklist("", driverList[k], "true", "true", "true");
                mChecklistArrayList.add(mChecklist);
            }
        }

        String[] tyreOilChecklist = getResources().getStringArray(R.array.tyre_oil_checklist);
        for (int l = 0; l < tyreOilChecklist.length; l++) {
            if (l == 0) {
                mChecklist = new Checklist(getResources().getString(R.string.tyre_oil), tyreOilChecklist[l], "false", "true", "true");
                mChecklistArrayList.add(mChecklist);
            } else {
                mChecklist = new Checklist("", tyreOilChecklist[l], "true", "true", "true");
                mChecklistArrayList.add(mChecklist);
            }
        }
        String[] electricalChecklist = getResources().getStringArray(R.array.electrical_checklist);
        for (int m = 0; m < electricalChecklist.length; m++) {
            if (m == 0) {
                mChecklist = new Checklist(getResources().getString(R.string.electrical), electricalChecklist[m], "false", "true", "true");
                mChecklistArrayList.add(mChecklist);
            } else {
                mChecklist = new Checklist("",electricalChecklist[m], "true", "true", "true");
                mChecklistArrayList.add(mChecklist);
            }
        }
        String[] scratchChecklist = getResources().getStringArray(R.array.scratch_checklist);
        for (int n = 0; n < scratchChecklist.length; n++) {
            if (n == 0) {
                mChecklist = new Checklist(getResources().getString(R.string.scratch_dent), scratchChecklist[n], "false", "true", "true");
                mChecklistArrayList.add(mChecklist);
            } else {
                mChecklist = new Checklist("", scratchChecklist[n], "true", "true", "true");
                mChecklistArrayList.add(mChecklist);
            }
        }

    }


}
