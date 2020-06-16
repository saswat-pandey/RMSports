package com.demoapp.sport.schedules;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.demoapp.sport.data.remote.SearchService;

import java.lang.reflect.InvocationTargetException;

public class ScheduleViewModelFactory implements ViewModelProvider.Factory {
    private SearchService mService;

    public ScheduleViewModelFactory(SearchService service){this.mService = service;}
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        try {
            return modelClass.getConstructor(SearchService.class).newInstance(mService);
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Can not create instance" + e.getLocalizedMessage(), e);
        } catch (InstantiationException e) {
            throw new RuntimeException("Can not create instance" + e.getLocalizedMessage(), e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException("Can not create instance" + e.getLocalizedMessage(), e);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException("Can not create instance" + e.getLocalizedMessage(), e);
        }
    }
}
