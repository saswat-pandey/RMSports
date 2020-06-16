package com.demoapp.sport.detail;

import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

import com.demoapp.sport.BR;


class LeagueViewHolder extends RecyclerView.ViewHolder {
    public ViewDataBinding dataBinding;

    public LeagueViewHolder(ViewDataBinding dataBinding) {
        super(dataBinding.getRoot()); // The super class contructor takes view as the parameter so we pass the root view of the binding class
        this.dataBinding = dataBinding;
    }

    public void bind(String league) {
        dataBinding.setVariable(BR.league, league);
        dataBinding.executePendingBindings();
    }
}