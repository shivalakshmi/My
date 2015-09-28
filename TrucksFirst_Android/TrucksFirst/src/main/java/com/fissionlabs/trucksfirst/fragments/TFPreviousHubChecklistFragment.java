package com.fissionlabs.trucksfirst.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.GravityCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fissionlabs.trucksfirst.R;
import com.fissionlabs.trucksfirst.common.TFCommonFragment;
import com.fissionlabs.trucksfirst.common.TFConst;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Kanj on 28-09-2015.
 */
public class TFPreviousHubChecklistFragment  extends TFCommonFragment implements TFConst {
    private RecyclerView listView;
    private TextView header;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle bundle) {
        mHomeActivity.isHomeFragment = false;
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
        Bundle args = getArguments();
        String vehNumber = args.getString("vehicle_number");
        String tid = args.getString("tracking_id");
        //String json = args.getString("json");
        //Log.v("Kanj", json);
        //NewChecklist c = args.getSerializable("checklist");

        View view = inflater.inflate(R.layout.fragment_prev_checklist, container, false);
        header = (TextView) view.findViewById(R.id.header);
        header.setText(String.format(getResources().getString(R.string.previous_checklist_header),vehNumber));
        listView = (RecyclerView) view.findViewById(R.id.list);
        listView.setLayoutManager(new LinearLayoutManager(getActivity()));
        listView.setAdapter(new ListAdapter(makeMapFromChecklist()));
        return view;
    }

    class TitleViewHolder extends RecyclerView.ViewHolder {
        public TextView tv;

        public TitleViewHolder(View itemView) {
            super(itemView);
            tv = (TextView)itemView.findViewById(R.id.screen_title);
        }
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {
        public TextView attribute;
        public TextView value;

        public ItemViewHolder(View itemView) {
            super(itemView);
            attribute = (TextView)itemView.findViewById(R.id.tv_attribute);
            value = (TextView)itemView.findViewById(R.id.tv_value);
        }
    }

    class DescriptionViewHolder extends RecyclerView.ViewHolder {
        public TextView desc;

        public DescriptionViewHolder(View itemView) {
            super(itemView);
            desc = (TextView)itemView.findViewById(R.id.tv_desc);
        }
    }

    class ListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
        private final int TITLE=0, INFO=1, DESC=2;
        private ChecklistStringData data;

        public ListAdapter(ChecklistStringData data) {
            Log.v("Kanj", "adapter constuctor - size="+data.size);
            this.data = data;
        }

        @Override
        public int getItemViewType(int position) {
            if (data.val.get(position).equals("")) return TITLE;
            else if (data.attr.get(position).equals("")) return DESC;
            else return INFO;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            RecyclerView.ViewHolder holder;
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            switch (viewType) {
                case TITLE:
                    View v1 = inflater.inflate(R.layout.checklist_display_header, parent, false);
                    holder = new TitleViewHolder(v1);
                    break;
                case DESC:
                    View v2 = inflater.inflate(R.layout.checklist_display_description, parent, false);
                    holder = new DescriptionViewHolder(v2);
                    break;
                // Default will handle INFO case.
                default:
                    View v3 = inflater.inflate(R.layout.checklist_display_item, parent, false);
                    holder = new ItemViewHolder(v3);
                    break;
            }
            return holder;
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            switch(holder.getItemViewType()){
                case TITLE:
                    TitleViewHolder tvh = (TitleViewHolder)holder;
                    tvh.tv.setText(data.attr.get(position));
                    break;
                case INFO:
                    ItemViewHolder ivh = (ItemViewHolder) holder;
                    ivh.attribute.setText(data.attr.get(position));
                    ivh.value.setText(data.val.get(position));
                    break;
                case DESC:
                    DescriptionViewHolder dvh = (DescriptionViewHolder)holder;
                    dvh.desc.setText(data.val.get(position));
                    break;
            }
        }

        @Override
        public int getItemCount() {
            //return 69; items+screen titles
            return data.size;
        }
    }

    class ChecklistStringData{
        public ArrayList<String> attr;
        public ArrayList<String> val;
        public int size;

        public ChecklistStringData() {
            attr = new ArrayList<String>();
            val = new ArrayList<String>();
            size = 0;
        }

        public void add(String a, String v) {
            attr.add(a);
            val.add(v);
            size++;
        }
    }

    ChecklistStringData makeMapFromChecklist() {
        ChecklistStringData ret = new ChecklistStringData();
        ret.add("Screen 1", "");
        ret.add("Driver", "OK");
        ret.add("Deisel", "OK");
        ret.add("Screen 2", "");
        ret.add("License", "OK");
        ret.add("","License is damaged.");
        return ret;
    }

    /*    ChecklistStringData makeMapFromChecklist(NewChecklist c) {
        ChecklistStringData ret = new ChecklistStringData();
        String s;
        ret.add("Screen 1", "");
        s = ((c.data.screen1.leftWiper)?"OK":"Not OK");
        ret.add("Left Wiper", s);
        s = ((c.data.screen1.rightWiper)?"OK":"Not OK");
        ret.add("Right Wiper", s);
        s = ((c.data.screen1.lowBeamHeadlight)?"OK":"Not OK");
        ret.add("Low Beam Headlight", s);
        s = ((c.data.screen1.highBeamHeadlight)?"OK":"Not OK");
        ret.add("High Beam Headlight", s);
        s = ((c.data.screen1.leftSideIndicator)?"OK":"Not OK");
        ret.add("Left Side Indicator", s);
        s = ((c.data.screen1.rightSideIndicator)?"OK":"Not OK");
        ret.add("Right Side Indicator", s);
        s = ((c.data.screen1.brakeLight)?"OK":"Not OK");
        ret.add("Brake Light", s);
        s = ((c.data.screen1.horn)?"OK":"Not OK");
        ret.add("Horn", s);

        ret.add("Screen 2", "");
        if (c.data.screen2.mechanicalIssue) {
            ret.add("Mechanical issue", "Yes");
            ret.add("",c.data.screen2.mechanicalIssueList);
        } else ret.add("Mechanical issue", "No");
        if (c.data.screen2.tyreOilIssue) {
            ret.add("Tyre/oil issue", "Yes");
            ret.add("",c.data.screen2.tyreOilIssueList);
        } else ret.add("Tyre/oil issue", "No");
        if (c.data.screen2.electricalIssue) {
            ret.add("Electrical issue", "Yes");
            ret.add("",c.data.screen2.electricalIssueList);
        } else ret.add("Electrical issue", "No");
        if (c.data.screen2.engineIssue) {
            ret.add("Engine issue", "Yes");
            ret.add("",c.data.screen2.engineIssueList);
        } else ret.add("Engine issue", "No");

        ret.add("Screen 3","");
        s = ((c.data.screen3.registrationCertificate)?"Yes":"No");
        ret.add("Registration Certificate", s);
        s = ((c.data.screen3.nationalPermit)?"Yes":"No");
        ret.add("National Permit", s);
        s = ((c.data.screen3.insurance)?"Yes":"No");
        ret.add("Insurance", s);
        s = ((c.data.screen3.pollutionCertificate)?"Yes":"No");
        ret.add("Pollution Certificate", s);
        s = ((c.data.screen3.goodsTaxRecipiet)?"Yes":"No");
        ret.add("Goods Tax Receipt", s);
        s = ((c.data.screen3.dharamKaantaParchi)?"Yes":"No");
        ret.add("Dharamkaanta Parchi", s);

        ret.add("Screen 4", "");
        s = ((c.data.screen4.invoice)?"Yes":"No");
        ret.add("Invoice", s);
        s = ((c.data.screen4.lr)?"Yes":"No");
        ret.add("LR", s);
        s = ((c.data.screen4.tp)?"Yes":"No");
        ret.add("TP", s);
        s = ((c.data.screen4.gatiDocBag)?"Yes":"No");
        ret.add("Gati Documents Bag", s);
        s = ((c.data.screen4.areDocsAvailable)?"Yes":"No");
        ret.add("Are documents available?", s);
        s = ((c.data.screen4.docsGivenToDriver)?"Yes":"No");
        ret.add("Documents given to driver?", s);

        ret.add("Screen 5","");
        s = ((c.data.screen5.jack)?"Yes":"No");
        ret.add("Is jack in order?", s);
        s = ((c.data.screen5.jackRod)?"Yes":"No");
        ret.add("Is jackrod in order?", s);
        s = ((c.data.screen5.tyreLever)?"Yes":"No");
        ret.add("Is tyre lever in order?", s);
        s = ((c.data.screen5.stepeneyRemover)?"Yes":"No");
        ret.add("Is stepney remover in order?", s);

        ret.add("Screen 6","");
        ret.add("Toll cash with Pilot",c.data.screen6.tollCashWithDriver);
        s = ((c.data.screen6.tollReceipts)?"Yes":"No");
        ret.add("Are toll receipts in order?", s);
        if (!c.data.screen6.cashShortageReason.equals("")) ret.add("", "Cash shortage reason: "
                +c.data.screen6.cashShortageReason);
        s = ((c.data.screen6.isTopUpDone)?"Yes":"No");
        ret.add("Is top up done?", s);
        if (!c.data.screen6.inputAmount.equals("")) ret.add("", "Amount: "
                +c.data.screen6.inputAmount);

        ret.add("Screen 7","");
        s = ((c.data.screen7.touchingDamage)?"Yes":"No");
        ret.add("Touching Damage", s);
        s = ((c.data.screen7.sealIntactness)?"Yes":"No");
        ret.add("Seat Intact", s);
        s = ((c.data.screen7.stepeneyInPlace)?"Yes":"No");
        ret.add("Is stepney in place?", s);
        s = ((c.data.screen7.tyrePuncture)?"Yes":"No");
        ret.add("Tyre Puncture", s);
        s = ((c.data.screen7.tyrePressure)?"OK":"Not OK");
        ret.add("Tyre Pressure", s);

        ret.add("Screen 8","");
        s = ((c.data.screen8.isCabinClean)?"Yes":"No");
        ret.add("Is cabin clean?", s);

        ret.add("Screen 9","");
        s = ((c.data.screen9.engineOilLeakage)?"Yes":"No");
        ret.add("Engine Oil Leakage", s);
        s = ((c.data.screen9.steeringOilLeakage)?"Yes":"No");
        ret.add("Steering Oil Leakage", s);
        s = ((c.data.screen9.temperatureCheck)?"OK":"Not OK");
        ret.add("Temperature Check", s);
        s = ((c.data.screen9.coolantLevel)?"OK":"Not OK");
        ret.add("Coolant Level", s);
        s = ((c.data.screen9.visualInspection)?"OK":"Not OK");
        ret.add("Visual Inspection", s);

        ret.add("Screen 10","");
        s = ((c.data.screen10.driverOnBoarded)?"Yes":"No");
        ret.add("Driver on board?", s);
        s = ((c.data.screen10.readyToMove)?"Yes":"No");
        ret.add("Ready to move", s);

        return ret;
    }
    */

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mHomeActivity.toolbar.setNavigationIcon(R.drawable.ic_menu);
        mHomeActivity.isHomeFragment = true;
        mHomeActivity.mActionBar.setDisplayShowTitleEnabled(false);
    }
}
