package com.demoapp.sport.events;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.demoapp.sport.R;
import com.demoapp.sport.data.remote.Api;
import com.demoapp.sport.data.remote.SearchService;
import com.demoapp.sport.databinding.EventFragmentBinding;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

public class EventFragment extends Fragment {
    private EventViewModel mViewModel;
    private EventFragmentBinding mBinding;
    private SearchService mService;
    private String mTeamId;
    private AdView mAdView;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.event_fragment, container, false);
        mService = Api.getInstance().getService();

        mViewModel = new ViewModelProvider(this, new EventViewModelFactory(mService)).get(EventViewModel.class);
        mBinding.setViewModel(mViewModel);

        mTeamId = EventFragmentArgs.fromBundle(getArguments()).getId();
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
        mViewModel.getEvents(mTeamId).observe(getActivity(), events -> {
            EventAdapter eventAdapter = new EventAdapter(events);
            mBinding.eventsRecycler.setAdapter(eventAdapter);
        });

    }
}