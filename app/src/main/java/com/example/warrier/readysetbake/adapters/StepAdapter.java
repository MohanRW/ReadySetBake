package com.example.warrier.readysetbake.adapters;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.example.warrier.readysetbake.R;
import com.example.warrier.readysetbake.databinding.ListStepItemBinding;
import com.example.warrier.readysetbake.model.Step;
import com.example.warrier.readysetbake.utils.OnItemClickListener;

import java.util.ArrayList;



public class StepAdapter extends RecyclerView.Adapter<StepAdapter.StepHolder> {
    private ArrayList<Step> mStepList;
    private OnItemClickListener mStepOnItemClickListener;
    private int mSelectedPosition = 0;

    public StepAdapter(ArrayList<Step> incomingStepSet,
                       OnItemClickListener<Step> StepOnItemClickListener) {
        this.mStepList = incomingStepSet;
        this.mStepOnItemClickListener = StepOnItemClickListener;
    }
    @Override
    public StepHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ListStepItemBinding binding = DataBindingUtil.inflate(inflater, R.layout.list_step_item, parent, false);
        return new StepHolder(binding);
    }

    @Override
    public void onBindViewHolder(StepHolder holder, int position) {
        holder.mStep = mStepList.get(position);
        holder.mBinding.stepItemHolder.setSelected(mSelectedPosition == position);

        if(holder.mStep.getVideoURL()!=null && !holder.mStep.getVideoURL().matches("")) {
            Glide.with(holder.itemView.getContext())
                    .load(holder.mStep.getThumbnailURL())
                    .placeholder(R.drawable.video_no_thumb)
                    .error(R.drawable.video_no_thumb)
                    .dontAnimate()
                    .into(holder.mBinding.stepItemVideoThumb);
        } else {
            holder.mBinding.stepItemVideoThumb.setImageResource(R.drawable.no_video);
        }
        holder.mBinding.stepListStepNumber.setText(String.valueOf(holder.mStep.getId() + 1) + ": ");
        holder.mBinding.stepListStepShortDesc.setText(holder.mStep.getShortDescription());


    }

    @Override
    public int getItemCount() {
        return (mStepList == null) ? 0 : mStepList.size();
    }

    public class StepHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final ListStepItemBinding mBinding;
        private Step mStep;

        public StepHolder(ListStepItemBinding binding) {
            super(binding.getRoot());
            this.mBinding = binding;

            binding.stepItemHolder.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            notifyItemChanged(mSelectedPosition);
            mSelectedPosition = getLayoutPosition();
            notifyItemChanged(mSelectedPosition);
            mStepOnItemClickListener.onClick(mStep, v);
        }
    }
    public int getStepAdapterCurrentPosition(){
        return mSelectedPosition;
    }
    public void setStepAdapterCurrentPosition(int savedPosition){
        mSelectedPosition = savedPosition;
    }

}
