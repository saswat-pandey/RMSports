package com.demoapp.sport.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import com.demoapp.sport.R;
import com.demoapp.sport.data.remote.Api;
import com.demoapp.sport.data.remote.SearchService;
import com.demoapp.sport.databinding.FragmentMainBinding;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.util.ArrayList;

public class MainFragment extends Fragment implements SearchView.OnQueryTextListener {

    FragmentMainBinding mBinding;
    private MainViewModel mViewModel;
    private SearchView mSearchView;
    private TeamAdapter mTeamAdapter;
    private SearchService mService;
    private AdView mAdView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        //binding the layout
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_main, container, false);

        //getting the service
        mService = Api.getInstance().getService();

        //setting the view model using factory to in order for dependency injection
        mViewModel = new ViewModelProvider(this, new MainViewModelFactory(mService)).get(MainViewModel.class);
        mBinding.setViewModel(mViewModel);

        //the lifecycle methods of the fragment are binded with the model
        mBinding.setLifecycleOwner(this);

        //setting, requesting and loading as ad
        mAdView = mBinding.adView;
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        //If true, the fragment has menu items to contribute.
        setHasOptionsMenu(true);

        //Returns the outermost View in the layout file associated with the Binding.
        // If this binding is for a merge layout file, this will return the first root in the merge tag.
        return mBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setupRecyclerView();
        mViewModel.showErrorMessage.observe(getActivity(), s -> Toast.makeText(getActivity(), s, Toast.LENGTH_LONG).show());
    }

    private void setupRecyclerView() {
        //Setting up team in the teams adapter and
        // a listener that will use nav graph to send the argument team from main fragment to the details fragment
        mTeamAdapter = new TeamAdapter(new ArrayList<>(), team -> NavHostFragment.findNavController(MainFragment.this).navigate(MainFragmentDirections.actionMainFragmentToDetailFragment(team)));
        //setting adapter
        mBinding.teamRecycler.setAdapter(mTeamAdapter);
        //observing teams from MainViewModel  and when there is a change loading up the adapter
        mViewModel.teams.observe(getActivity(), teams -> mTeamAdapter.addData(teams));
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.main_menu, menu);
        MenuItem menuItem = menu.findItem(R.id.search);
        mSearchView = (SearchView) menuItem.getActionView();
        mSearchView.setOnQueryTextListener(this);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        mSearchView.clearFocus();
        mViewModel.searchTeam(query.trim());
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }
}
