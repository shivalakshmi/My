package com.fissionlabs.trucksfirst.fragments;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_check_list, container, false);

        ListView mLVChecklist = (ListView) view.findViewById(R.id.listView);
        checklistData();
        mLVChecklist.setAdapter(new MyCustomAdapter(getActivity()));
        View footerView = inflater.inflate(R.layout.checklist_footer,null);
        mLVChecklist.addFooterView(footerView);
        return view;
    }

    class MyCustomAdapter extends BaseAdapter {

        private static final int OPERATION_HEADER = 2;
        private static final int TECHNICAL_HEADER = 0;
        private static final int TYPE_OPERATIONAL = 1;
        private static final int TYPE_TECHNICAL = 3;
        private static final int TYPE_MAX_COUNT = TYPE_TECHNICAL + 1;

        private LayoutInflater mInflater;

        public MyCustomAdapter(Context context) {
            mInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getItemViewType(int position) {
            if(position==0)
            {
                return OPERATION_HEADER;
            }
            else if(position==11)
            {
                return TECHNICAL_HEADER;
            }
            else if(position<=10)
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
                    case OPERATION_HEADER:
                        convertView = mInflater.inflate(R.layout.operational_header,parent, false);
                        break;
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
        mDocumentChecklist.add(getResources().getString(R.string.registration_certificate));
        mDocumentChecklist.add(getResources().getString(R.string.fitness_certificate));
        mDocumentChecklist.add(getResources().getString(R.string.national_permit));
        mDocumentChecklist.add(getResources().getString(R.string.road_tax_booklet));
        mDocumentChecklist.add(getResources().getString(R.string.pollution_certificate));
        mDocumentChecklist.add(getResources().getString(R.string.insurance));
        mDocumentChecklist.add(getResources().getString(R.string.toll_tax_receipt_and_cash_balance));
        mDocumentChecklist.add(getResources().getString(R.string.grn_bilti));
        mDocumentChecklist.add(getResources().getString(R.string.seal_intactness));

        mDocumentStatusList.add("true");
        mDocumentStatusList.add("true");
        mDocumentStatusList.add("false");
        mDocumentStatusList.add("true");
        mDocumentStatusList.add("true");
        mDocumentStatusList.add("false");
        mDocumentStatusList.add("true");
        mDocumentStatusList.add("true");
        mDocumentStatusList.add("true");

        mKitsChecklist.add(getResources().getString(R.string.tool_kit));
        mKitsChecklist.add(getResources().getString(R.string.others));

        mKitsStatusList.add("true");
        mKitsStatusList.add("false");

        mCleanlinessChecklist.add(getResources().getString(R.string.cleanliness));

        mCleanlinessStatusList.add("false");

        mDriverChecklist.add(getResources().getString(R.string.engine_starting));
        mDriverChecklist.add(getResources().getString(R.string.engine_sound));
        mDriverChecklist.add(getResources().getString(R.string.exhaust_emission));
        mDriverChecklist.add(getResources().getString(R.string.clutch_working));
        mDriverChecklist.add(getResources().getString(R.string.gear_movement));
        mDriverChecklist.add(getResources().getString(R.string.brake_effectiveness));

        mTyreOilChecklist.add(getResources().getString(R.string.tyres));
        mTyreOilChecklist.add(getResources().getString(R.string.coolant_leakage));
        mTyreOilChecklist.add(getResources().getString(R.string.engine_oil_leakage));
        mTyreOilChecklist.add(getResources().getString(R.string.gear_oil_leakage));
        mTyreOilChecklist.add(getResources().getString(R.string.fuel_diesel_leakage));
        mTyreOilChecklist.add(getResources().getString(R.string.differential_oil_leakage));

        mElectrical.add(getResources().getString(R.string.headlight));
        mElectrical.add(getResources().getString(R.string.indicator_and_hazard));
        mElectrical.add(getResources().getString(R.string.horn));
        mElectrical.add(getResources().getString(R.string.Wiper));
        mElectrical.add(getResources().getString(R.string.temperature_on_temperature_gauge));
        mElectrical.add(getResources().getString(R.string.alternator_charger_light));
        mElectrical.add(getResources().getString(R.string.oil_pressure_warning_light));

        mScratch.add(getResources().getString(R.string.front));
        mScratch.add(getResources().getString(R.string.left));
        mScratch.add(getResources().getString(R.string.rear));
        mScratch.add(getResources().getString(R.string.right));

        Checklist mChecklist;
        for (int i = 0; i < mDocumentStatusList.size(); i++) {
            if (i == 0) {
                mChecklist = new Checklist(getResources().getString(R.string.documents), mDocumentChecklist.get(i), mDocumentStatusList.get(i), "true", "true");
                mChecklistArrayList.add(mChecklist);
            } else {
                mChecklist = new Checklist("", mDocumentChecklist.get(i), mDocumentStatusList.get(i), "true", "true");
                mChecklistArrayList.add(mChecklist);
            }
        }
        for (int j = 0; j < mKitsStatusList.size(); j++) {
            if (j == 0) {
                mChecklist = new Checklist(getResources().getString(R.string.kits), mKitsChecklist.get(j), mKitsStatusList.get(j), "true", "true");
                mChecklistArrayList.add(mChecklist);
            } else {
                mChecklist = new Checklist("", mKitsChecklist.get(j), mKitsStatusList.get(j), "true", "true");
                mChecklistArrayList.add(mChecklist);
            }
        }
        mChecklist = new Checklist(getResources().getString(R.string.cleanliness), mCleanlinessChecklist.get(0), mCleanlinessStatusList.get(0), "true", "true");
        mChecklistArrayList.add(mChecklist);

        for (int k = 0; k < mDriverChecklist.size(); k++) {
            if (k == 0) {
                mChecklist = new Checklist(getResources().getString(R.string.driver), mDriverChecklist.get(k), "false", "true", "true");
                mChecklistArrayList.add(mChecklist);
            } else {
                mChecklist = new Checklist("", mDriverChecklist.get(k), "true", "true", "true");
                mChecklistArrayList.add(mChecklist);
            }
        }

        for (int l = 0; l < mTyreOilChecklist.size(); l++) {
            if (l == 0) {
                mChecklist = new Checklist(getResources().getString(R.string.tyre_oil), mTyreOilChecklist.get(l), "false", "true", "true");
                mChecklistArrayList.add(mChecklist);
            } else {
                mChecklist = new Checklist("", mTyreOilChecklist.get(l), "true", "true", "true");
                mChecklistArrayList.add(mChecklist);
            }
        }

        for (int m = 0; m < mElectrical.size(); m++) {
            if (m == 0) {
                mChecklist = new Checklist(getResources().getString(R.string.electrical), mElectrical.get(m), "false", "true", "true");
                mChecklistArrayList.add(mChecklist);
            } else {
                mChecklist = new Checklist("", mElectrical.get(m), "true", "true", "true");
                mChecklistArrayList.add(mChecklist);
            }
        }

        for (int n = 0; n < mScratch.size(); n++) {
            if (n == 0) {
                mChecklist = new Checklist(getResources().getString(R.string.scratch_dent), mScratch.get(n), "false", "true", "true");
                mChecklistArrayList.add(mChecklist);
            } else {
                mChecklist = new Checklist("", mScratch.get(n), "true", "true", "true");
                mChecklistArrayList.add(mChecklist);
            }
        }

    }


}
