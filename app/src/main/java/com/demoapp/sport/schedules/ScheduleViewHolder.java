package com.demoapp.sport.schedules;

import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

import com.demoapp.sport.BR;
import com.demoapp.sport.data.remote.Schedules;

public class ScheduleViewHolder extends RecyclerView.ViewHolder {
    ViewDataBinding mDataBinding;

    public ScheduleViewHolder(ViewDataBinding dataBinding) {
        super(dataBinding.getRoot());
        this.mDataBinding = dataBinding;
    }

    public void bind(Schedules schedules) {
        mDataBinding.setVariable(BR.schedule, schedules);
        mDataBinding.executePendingBindings();
    }
}
