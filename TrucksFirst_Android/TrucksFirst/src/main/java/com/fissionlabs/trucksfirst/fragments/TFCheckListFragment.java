package com.fissionlabs.trucksfirst.fragments;

import android.app.Fragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.support.v7.app.AlertDialog;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.fissionlabs.trucksfirst.R;
import com.fissionlabs.trucksfirst.adapters.CheckListAdapter;
import com.fissionlabs.trucksfirst.common.TFCommonFragment;
import com.fissionlabs.trucksfirst.common.TFConst;
import com.fissionlabs.trucksfirst.home.TFHomeActivity;
import com.fissionlabs.trucksfirst.pojo.Checklist;
import com.fissionlabs.trucksfirst.pojo.ChecklistNew;
import com.fissionlabs.trucksfirst.util.TFUtils;
import com.fissionlabs.trucksfirst.webservices.WebServices;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
@SuppressWarnings({"ALL", "CanBeFinal"})
public class TFCheckListFragment extends TFCommonFragment implements TFConst{

    private ArrayList<Checklist> mChecklistArrayList = new ArrayList();
    private ArrayList<Boolean> mDocumentStatusList = new ArrayList();
    private ArrayList<String> mDocumentChecklist = new ArrayList();
    private ArrayList<Boolean> mKitsStatusList = new ArrayList();
    private ArrayList<String> mKitsChecklist = new ArrayList();
    private ArrayList<Boolean> mCleanlinessStatusList = new ArrayList();
    private ArrayList<String> mCleanlinessChecklist = new ArrayList();

    private ArrayList<String> mDriverChecklist = new ArrayList();
    private ArrayList<String> mTyreOilChecklist = new ArrayList();
    private ArrayList<String> mElectrical = new ArrayList();
    private ArrayList<String> mScratch = new ArrayList();

    private ArrayList<Boolean> mDriverChecklistStatus = new ArrayList();
    private ArrayList<Boolean> mTyreOilChecklistStatus = new ArrayList();
    private ArrayList<Boolean> mElectricalStatus = new ArrayList();
    private ArrayList<Boolean> mScratchStatus = new ArrayList();
    private ChecklistNew mChecklistNew;
    private WebServices mWebServices;
    private Button saveBtn;
    private Button cancelBtn;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_check_list, container, false);
        Bundle bundle = this.getArguments();

        mHomeActivity.mActionBar.setTitle(bundle.getString("vehicle_number"));
        final ListView mLVChecklist = (ListView) view.findViewById(R.id.listView);
        mChecklistArrayList.clear();
        mWebServices = new WebServices();
//        mChecklistNew =new ChecklistNew();
        mWebServices.getVehicleChecklistDetails(bundle.getString("vehicle_number"), new ResultReceiver(null) {
            @Override
            protected void onReceiveResult(int resultCode, Bundle resultData) {
                if (resultCode == SUCCESS) {

                    String responseStr = resultData.getString("response");

                    if (responseStr != null) {
                        mChecklistNew = new Gson().fromJson(responseStr, ChecklistNew.class);
                        checklistData();
                        mLVChecklist.setAdapter(new CheckListAdapter(getActivity(), mChecklistArrayList, mChecklistNew));
                    }
                } else {
                    Toast.makeText(getActivity(), "" + getResources().getString(R.string.issue_parsing_data), Toast.LENGTH_SHORT).show();
                }
                TFUtils.hideProgressBar();
            }
        });


        View headerView = inflater.inflate(R.layout.operational_header, null);
        mLVChecklist.addHeaderView(headerView);
        View footerView = inflater.inflate(R.layout.checklist_footer, null);
        saveBtn = (Button)footerView.findViewById(R.id.save);
        cancelBtn = (Button)footerView.findViewById(R.id.cancel);
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    mHomeActivity.onBackPressed();
            }
        });

        mLVChecklist.addFooterView(footerView);

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TFHomeActivity.isChangesMade = false;
                String jsonObject = new Gson().toJson(mChecklistNew, ChecklistNew.class);
                try {
                    mWebServices.updateVehicleChecklist(new JSONObject(jsonObject));
                }
                catch(Exception e) {
                    e.printStackTrace();
                }
                mHomeActivity.onBackPressed();
            }
        });

        mLVChecklist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                long viewId = view.getId();

                if (viewId == R.id.cross) {
                    showStatusAlertDialog("HR55V1234");
                }


            }
        });
        return view;
    }


    private void checklistData() {
        mDocumentStatusList.clear();
        mKitsStatusList.clear();
        mCleanlinessStatusList.clear();
        mDriverChecklistStatus.clear();
        mTyreOilChecklistStatus.clear();
        mElectricalStatus.clear();
        mScratchStatus.clear();
        mDocumentStatusList.add(mChecklistNew.isRegistrationCertificate());
        mDocumentStatusList.add(mChecklistNew.isFitnessCertificate());
        mDocumentStatusList.add(mChecklistNew.isNationalPermit());
        mDocumentStatusList.add(mChecklistNew.isRoadTaxBooklet());
        mDocumentStatusList.add(mChecklistNew.isPollutionCertificate());
        mDocumentStatusList.add(mChecklistNew.isInsurance());
        mDocumentStatusList.add(mChecklistNew.isTollTaxReceiptAndCashBalance());
        mDocumentStatusList.add(mChecklistNew.isGrnOrBilti());
        mDocumentStatusList.add(mChecklistNew.isSealIntactness());

        mKitsStatusList.add(mChecklistNew.isToolKit());
        mKitsStatusList.add(mChecklistNew.isToolKit());

        mCleanlinessStatusList.add(mChecklistNew.isCabinCleanliness());

        mDriverChecklistStatus.add(mChecklistNew.isEngineStarting());
        mDriverChecklistStatus.add(mChecklistNew.isEngineSound());
        mDriverChecklistStatus.add(mChecklistNew.isExhaustEmission());
        mDriverChecklistStatus.add(mChecklistNew.isClutchWorking());
        mDriverChecklistStatus.add(mChecklistNew.isGearMovement());
        mDriverChecklistStatus.add(mChecklistNew.isBrakeEffectiveness());

        mTyreOilChecklistStatus.add(mChecklistNew.isTyres());
        mTyreOilChecklistStatus.add(mChecklistNew.isCoolantLeakage());
        mTyreOilChecklistStatus.add(mChecklistNew.isEngineOilLeakage());
        mTyreOilChecklistStatus.add(mChecklistNew.isGearOilLeakage());
        mTyreOilChecklistStatus.add(mChecklistNew.isFuelDieselLeakage());
        mTyreOilChecklistStatus.add(mChecklistNew.isDifferentialOilLeakage());

        mElectricalStatus.add(mChecklistNew.isHeadlight());
        mElectricalStatus.add(mChecklistNew.isIndicatorAndHazard());
        mElectricalStatus.add(mChecklistNew.isHorn());
        mElectricalStatus.add(mChecklistNew.isWiper());
        mElectricalStatus.add(mChecklistNew.isTemperatureOnTemperatureGauge());
        mElectricalStatus.add(mChecklistNew.isAlternatorChargerLight());
        mElectricalStatus.add(mChecklistNew.isOilPressureWarningLight());

        mScratchStatus.add(mChecklistNew.isFront());
        mScratchStatus.add(mChecklistNew.isLeft());
        mScratchStatus.add(mChecklistNew.isRear());
        mScratchStatus.add(mChecklistNew.isRight());



        Checklist mChecklist;

        String[] documentList = getResources().getStringArray(R.array.document_checklist);
        for (int i = 0; i < documentList.length; i++) {
            if (i == 0) {
                mChecklist = new Checklist(getResources().getString(R.string.documents), documentList[i], mDocumentStatusList.get(i));
                mChecklistArrayList.add(mChecklist);
            } else {
                mChecklist = new Checklist("", documentList[i], mDocumentStatusList.get(i));
                mChecklistArrayList.add(mChecklist);
            }

        }

        String[] kitsList = getResources().getStringArray(R.array.kits_checklist);
        for (int j = 0; j < kitsList.length; j++) {
            if (j == 0) {
                mChecklist = new Checklist(getResources().getString(R.string.kits), kitsList[j], mKitsStatusList.get(j));
                mChecklistArrayList.add(mChecklist);
            } else {
                mChecklist = new Checklist("", kitsList[j], mKitsStatusList.get(j));
                mChecklistArrayList.add(mChecklist);
            }
        }
        mChecklist = new Checklist(getResources().getString(R.string.cleanliness), getResources().getString(R.string.cabin_cleanliness), mCleanlinessStatusList.get(0));
        mChecklistArrayList.add(mChecklist);
        mChecklist = new Checklist(null, null, false);
        mChecklistArrayList.add(mChecklist);

        String[] driverList = getResources().getStringArray(R.array.driver_checklist);

        for (int k = 0; k < driverList.length; k++) {
            if (k == 0) {
                mChecklist = new Checklist(getResources().getString(R.string.driver), driverList[k], mDriverChecklistStatus.get(k));
                mChecklistArrayList.add(mChecklist);
            } else {
                mChecklist = new Checklist("", driverList[k], mDriverChecklistStatus.get(k));
                mChecklistArrayList.add(mChecklist);
            }
        }

        String[] tyreOilChecklist = getResources().getStringArray(R.array.tyre_oil_checklist);
        for (int l = 0; l < tyreOilChecklist.length; l++) {
            if (l == 0) {
                mChecklist = new Checklist(getResources().getString(R.string.tyre_oil), tyreOilChecklist[l], mTyreOilChecklistStatus.get(l));
                mChecklistArrayList.add(mChecklist);
            } else {
                mChecklist = new Checklist("", tyreOilChecklist[l], mTyreOilChecklistStatus.get(l));
                mChecklistArrayList.add(mChecklist);
            }
        }
        String[] electricalChecklist = getResources().getStringArray(R.array.electrical_checklist);
        for (int m = 0; m < electricalChecklist.length; m++) {
            if (m == 0) {
                mChecklist = new Checklist(getResources().getString(R.string.electrical), electricalChecklist[m], mElectricalStatus.get(m));
                mChecklistArrayList.add(mChecklist);
            } else {
                mChecklist = new Checklist("", electricalChecklist[m], mElectricalStatus.get(m));
                mChecklistArrayList.add(mChecklist);
            }
        }
        String[] scratchChecklist = getResources().getStringArray(R.array.scratch_checklist);
        for (int n = 0; n < scratchChecklist.length; n++) {
            if (n == 0) {
                mChecklist = new Checklist(getResources().getString(R.string.scratch_dent), scratchChecklist[n], mScratchStatus.get(n));
                mChecklistArrayList.add(mChecklist);
            } else {
                mChecklist = new Checklist("", scratchChecklist[n], mScratchStatus.get(n));
                mChecklistArrayList.add(mChecklist);
            }
        }

    }

    public void showStatusAlertDialog(String title) {

        final CharSequence items[] = {getString(R.string.next_hub),
                getString(R.string.ashok_leyland)};

        final ArrayList<Integer> selectedItems = new ArrayList();
        boolean checkedItems[] = new boolean[items.length];

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());

        dialogBuilder.setTitle(Html.fromHtml("<b>" + title + "\t\t" + "</b>" + getResources().getString(R.string.notification_send_to)));
        dialogBuilder.setMultiChoiceItems(items, checkedItems, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                if (isChecked) {
                    selectedItems.add(which);
                } else if (selectedItems.contains(which)) {
                    selectedItems.remove(Integer.valueOf(which));
                }
            }
        });
        dialogBuilder.setPositiveButton(getResources().getString(R.string.save), null);
        dialogBuilder.setNegativeButton(getResources().getString(R.string.cancel), null);

        AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.getWindow().setLayout(500, LinearLayout.LayoutParams.WRAP_CONTENT);

        alertDialog.show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mHomeActivity.mActionBar.setTitle(getResources().getString(R.string.app_name));
    }
}
