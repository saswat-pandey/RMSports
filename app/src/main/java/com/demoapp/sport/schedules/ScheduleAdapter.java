package com.demoapp.sport.schedules;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

import com.demoapp.sport.R;
import com.demoapp.sport.data.remote.Schedules;

import java.util.ArrayList;


public class ScheduleAdapter extends RecyclerView.Adapter<ScheduleViewHolder> {

    private ArrayList<Schedules> mSchedules;

    public  ScheduleAdapter(ArrayList<Schedules> schedules){this.mSchedules = schedules;}

    @NonNull
    @Override
    public ScheduleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ViewDataBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_schedule, parent, false);
        return new ScheduleViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ScheduleViewHolder holder, int position) {
        holder.bind(mSchedules.get(position));
    }

    @Override
    public int getItemCount() {
        return mSchedules.size();
    }
}
