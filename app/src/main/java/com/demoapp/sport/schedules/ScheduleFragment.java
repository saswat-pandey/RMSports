package com.demoapp.sport.schedules;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.demoapp.sport.R;
import com.demoapp.sport.data.remote.Api;
import com.demoapp.sport.data.remote.SearchService;
import com.demoapp.sport.databinding.FragmentScheduleBinding;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

public class ScheduleFragment extends Fragment {
    private static final String TAG = "ScheduleFragment";

    private ScheduleViewModel mViewModel;
    private FragmentScheduleBinding mBinding;
    private SearchService mService;
    private String mTeamId;
    private AdView mAdView;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_schedule, container, false);
        mService = Api.getInstance().getService();

        mViewModel = new ViewModelProvider(this, new ScheduleViewModelFactory(mService)).get(ScheduleViewModel.class);
        mBinding.setViewModel(mViewModel);

        mTeamId = ScheduleFragmentArgs.fromBundle(getArguments()).getId();

        mAdView = mBinding.adView;
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        mBinding.setLifecycleOwner(this);
        return mBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel.showErrorMessage.observe(getActivity(), s -> Toast.makeText(getActivity(), s, Toast.LENGTH_LONG).show());
        mViewModel.getSchedule(mTeamId).observe(getActivity(), schedules -> {
            ScheduleAdapter scheduleAdapter = new ScheduleAdapter(schedules);
            mBinding.scheduleRecyclerView.setAdapter(scheduleAdapter);
        });

    }


}