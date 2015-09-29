package com.fissionlabs.trucksfirst.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.GravityCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fissionlabs.trucksfirst.R;
import com.fissionlabs.trucksfirst.common.TFCommonFragment;
import com.fissionlabs.trucksfirst.common.TFConst;
import com.fissionlabs.trucksfirst.model.checklist.NewChecklist;

import java.util.ArrayList;


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
        NewChecklist c = (NewChecklist) args.getSerializable("checklist");

        View view = inflater.inflate(R.layout.fragment_prev_checklist, container, false);
        header = (TextView) view.findViewById(R.id.header);
        header.setText(String.format(getResources().getString(R.string.previous_checklist_header),vehNumber));
        listView = (RecyclerView) view.findViewById(R.id.list);
        listView.setLayoutManager(new LinearLayoutManager(getActivity()));
        listView.setAdapter(new ListAdapter(makeMapFromChecklist(c)));
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

    ChecklistStringData makeMapFromChecklist(NewChecklist c) {
        ChecklistStringData ret = new ChecklistStringData();
        String s;
        ret.add(mHomeActivity.getString(R.string.title_screen1), "");
        s = ((c.data.screen1.leftWiper)?"OK":"Not OK");
        ret.add(mHomeActivity.getString(R.string.item_l_wiper), s);
        s = ((c.data.screen1.rightWiper)?"OK":"Not OK");
        ret.add(mHomeActivity.getString(R.string.item_r_wiper), s);
        s = ((c.data.screen1.lowBeamHeadlight)?"OK":"Not OK");
        ret.add(mHomeActivity.getString(R.string.item_l_headlight), s);
        s = ((c.data.screen1.highBeamHeadlight)?"OK":"Not OK");
        ret.add(mHomeActivity.getString(R.string.item_r_headlight), s);
        s = ((c.data.screen1.leftSideIndicator)?"OK":"Not OK");
        ret.add(mHomeActivity.getString(R.string.item_left_indicator), s);
        s = ((c.data.screen1.rightSideIndicator)?"OK":"Not OK");
        ret.add(mHomeActivity.getString(R.string.item_right_indicator), s);
        s = ((c.data.screen1.brakeLight)?"OK":"Not OK");
        ret.add(mHomeActivity.getString(R.string.item_brake_light), s);
        s = ((c.data.screen1.horn)?"OK":"Not OK");
        ret.add(mHomeActivity.getString(R.string.item_horn), s);

        ret.add(mHomeActivity.getString(R.string.title_screen_2), "");
        if (c.data.screen2.mechanicalIssue) {
            ret.add(mHomeActivity.getString(R.string.item_mech_issue), "Yes");
            ret.add("",c.data.screen2.mechanicalIssueList);
        } else ret.add(mHomeActivity.getString(R.string.item_mech_issue), "No");
        if (c.data.screen2.tyreOilIssue) {
            ret.add(mHomeActivity.getString(R.string.item_tyre_oil_issue), "Yes");
            ret.add("",c.data.screen2.tyreOilIssueList);
        } else ret.add(mHomeActivity.getString(R.string.item_tyre_oil_issue), "No");
        if (c.data.screen2.electricalIssue) {
            ret.add(mHomeActivity.getString(R.string.item_elec_issue), "Yes");
            ret.add("",c.data.screen2.electricalIssueList);
        } else ret.add(mHomeActivity.getString(R.string.item_elec_issue), "No");
        if (c.data.screen2.engineIssue) {
            ret.add(mHomeActivity.getString(R.string.item_engine_issue), "Yes");
            ret.add("",c.data.screen2.engineIssueList);
        } else ret.add(mHomeActivity.getString(R.string.item_engine_issue), "No");

        ret.add(mHomeActivity.getString(R.string.title_screen_3),"");
        s = ((c.data.screen3.registrationCertificate)?"Yes":"No");
        ret.add(mHomeActivity.getString(R.string.item_reg_cert), s);
        s = ((c.data.screen3.nationalPermit)?"Yes":"No");
        ret.add(mHomeActivity.getString(R.string.item_nat_perm), s);
        s = ((c.data.screen3.insurance)?"Yes":"No");
        ret.add(mHomeActivity.getString(R.string.item_insurance), s);
        s = ((c.data.screen3.pollutionCertificate)?"Yes":"No");
        ret.add(mHomeActivity.getString(R.string.item_poll_cert), s);
        s = ((c.data.screen3.goodsTaxRecipiet)?"Yes":"No");
        ret.add(mHomeActivity.getString(R.string.item_good_tax), s);
        s = ((c.data.screen3.dharamKaantaParchi)?"Yes":"No");
        ret.add(mHomeActivity.getString(R.string.item_d_parch), s);

        ret.add(mHomeActivity.getString(R.string.title_screen_4), "");
        s = ((c.data.screen4.invoice)?"Yes":"No");
        ret.add(mHomeActivity.getString(R.string.item_invoice), s);
        s = ((c.data.screen4.lr)?"Yes":"No");
        ret.add(mHomeActivity.getString(R.string.item_lr), s);
        s = ((c.data.screen4.tp)?"Yes":"No");
        ret.add(mHomeActivity.getString(R.string.item_tp), s);
        s = ((c.data.screen4.gatiDocBag)?"Yes":"No");
        ret.add(mHomeActivity.getString(R.string.item_gat_doc), s);
        s = ((c.data.screen4.areDocsAvailable)?"Yes":"No");
        ret.add(mHomeActivity.getString(R.string.item_docs), s);
        s = ((c.data.screen4.docsGivenToDriver)?"Yes":"No");
        ret.add(mHomeActivity.getString(R.string.item_docs_driver), s);

        ret.add(mHomeActivity.getString(R.string.title_screen_5),"");
        s = ((c.data.screen5.jack)?"Yes":"No");
        ret.add(mHomeActivity.getString(R.string.item_jack), s);
        s = ((c.data.screen5.jackRod)?"Yes":"No");
        ret.add(mHomeActivity.getString(R.string.item_jackrod), s);
        s = ((c.data.screen5.tyreLever)?"Yes":"No");
        ret.add(mHomeActivity.getString(R.string.item_tyre_lever), s);
        s = ((c.data.screen5.stepeneyRemover)?"Yes":"No");
        ret.add(mHomeActivity.getString(R.string.item_step_remo), s);

        ret.add(mHomeActivity.getString(R.string.title_screen_6),"");
        ret.add(mHomeActivity.getString(R.string.item_toll_cash),""+c.data.screen6.tollCashWithDriver);
        s = ((c.data.screen6.tollReceipts)?"Yes":"No");
        ret.add(mHomeActivity.getString(R.string.item_toll_rece), s);
        s = ((c.data.screen6.isCashWithDriverOk)?"Yes":"No");
        ret.add(mHomeActivity.getString(R.string.item_cash_ok), s);
        if (!c.data.screen6.cashShortageReason.equals("")) ret.add("", mHomeActivity.getString(R.string.item_cash_short_reason)
                +c.data.screen6.cashShortageReason);
        s = ((c.data.screen6.isTopUpDone)?"Yes":"No");
        ret.add(mHomeActivity.getString(R.string.item_topup), s);
        if (c.data.screen6.inputAmount > 0) ret.add("", mHomeActivity.getString(R.string.item_topup_amount_label)
                +c.data.screen6.inputAmount);

        ret.add(mHomeActivity.getString(R.string.title_screen_7),"");
        s = ((c.data.screen7.touchingDamage)?"Yes":"No");
        ret.add(mHomeActivity.getString(R.string.item_touc_dama), s);
        s = ((c.data.screen7.sealIntactness)?"Yes":"No");
        ret.add(mHomeActivity.getString(R.string.item_seal), s);
        s = ((c.data.screen7.stepeneyInPlace)?"Yes":"No");
        ret.add(mHomeActivity.getString(R.string.item_stepney), s);
        s = ((c.data.screen7.tyrePuncture)?"Yes":"No");
        ret.add(mHomeActivity.getString(R.string.item_puncture), s);
        s = ((c.data.screen7.tyrePressure)?"OK":"Not OK");
        ret.add(mHomeActivity.getString(R.string.item_tyre_pres), s);

        ret.add(mHomeActivity.getString(R.string.title_screen_8),"");
        s = ((c.data.screen8.isCabinClean)?"Yes":"No");
        ret.add(mHomeActivity.getString(R.string.item_cabin), s);

        ret.add(mHomeActivity.getString(R.string.title_screen_9),"");
        s = ((c.data.screen9.engineOilLeakage)?"Yes":"No");
        ret.add(mHomeActivity.getString(R.string.item_engi_oil_leak), s);
        s = ((c.data.screen9.steeringOilLeakage)?"Yes":"No");
        ret.add(mHomeActivity.getString(R.string.item_stee_oil_leak), s);
        s = ((c.data.screen9.temperatureCheck)?"OK":"Not OK");
        ret.add(mHomeActivity.getString(R.string.item_temp), s);
        s = ((c.data.screen9.coolantLevel)?"OK":"Not OK");
        ret.add(mHomeActivity.getString(R.string.item_coolant), s);
        s = ((c.data.screen9.visualInspection)?"OK":"Not OK");
        ret.add(mHomeActivity.getString(R.string.item_visual), s);

        ret.add(mHomeActivity.getString(R.string.title_screen_10),"");
        s = ((c.data.screen10.driverOnBoarded)?"Yes":"No");
        ret.add(mHomeActivity.getString(R.string.item_driver_on_board), s);
        s = ((c.data.screen10.readyToMove)?"Yes":"No");
        ret.add(mHomeActivity.getString(R.string.item_ready_move), s);

        return ret;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mHomeActivity.toolbar.setNavigationIcon(R.drawable.ic_menu);
        mHomeActivity.isHomeFragment = true;
        mHomeActivity.mActionBar.setDisplayShowTitleEnabled(false);
    }
}
