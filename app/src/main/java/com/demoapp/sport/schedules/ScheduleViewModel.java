package com.demoapp.sport.schedules;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.demoapp.sport.data.remote.Event;
import com.demoapp.sport.data.remote.ScheduleResponse;
import com.demoapp.sport.data.remote.Schedules;
import com.demoapp.sport.data.remote.SearchService;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ScheduleViewModel extends ViewModel {
    public MutableLiveData<String> showErrorMessage = new MutableLiveData();
    public MutableLiveData<Boolean> showLoading = new MutableLiveData();
    public MutableLiveData<Boolean> showNoData = new MutableLiveData();
    public MutableLiveData<ArrayList<Schedules>> schedule = new MutableLiveData<>();
    public SearchService mSearchService;

    public ScheduleViewModel(SearchService service){this.mSearchService = service;}


    MutableLiveData<ArrayList<Schedules>> getSchedule(String query) {
        showLoading.postValue(true);
        mSearchService.getSchedules(query).enqueue(new Callback<ScheduleResponse>() {
            @Override
            public void onResponse(Call<ScheduleResponse> call, Response<ScheduleResponse> response) {
                if (response.isSuccessful() && response.body().isNullOrEmpty()){
                    showNoData.postValue(true);
                }else{
                    schedule.postValue((ArrayList<Schedules>) response.body().getSchedules());
                }
                showNoData.postValue(false);
            }

            @Override
            public void onFailure(Call<ScheduleResponse> call, Throwable t) {
                showLoading.postValue(false);
                showErrorMessage.postValue(t.getLocalizedMessage());
            }
        });
        return schedule;
    }
}
