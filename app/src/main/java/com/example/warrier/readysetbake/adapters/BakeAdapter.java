package com.example.warrier.readysetbake.adapters;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.example.warrier.readysetbake.R;
import com.example.warrier.readysetbake.databinding.ListItemBinding;
import com.example.warrier.readysetbake.model.Bake;
import com.example.warrier.readysetbake.utils.OnItemClickListener;

import java.util.ArrayList;


public class BakeAdapter extends RecyclerView.Adapter<BakeAdapter.bakeHolder> {
    private ArrayList<Bake> mBake;
    private OnItemClickListener mOnItemClickListener;

    public BakeAdapter(ArrayList<Bake> incomingRecipeSet,
                       OnItemClickListener<Bake> recipeOnItemClickListener) {
        this.mBake = incomingRecipeSet;
        this.mOnItemClickListener = recipeOnItemClickListener;
    }

    @Override
    public bakeHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ListItemBinding binding = DataBindingUtil.inflate(inflater, R.layout.list_item, parent, false);
        return new bakeHolder(binding);
    }

    @Override
    public void onBindViewHolder(bakeHolder holder, int position) {
        holder.mRecipe = mBake.get(position);
        Glide.with(holder.itemView.getContext())
                .load(holder.mRecipe.getImage())
                .placeholder(R.drawable.forkandknife)
                .error(R.drawable.forkandknife)
                .dontAnimate()
                .into(holder.mBinding.recipeImage);
        holder.mBinding.recipeName.setText(holder.mRecipe.getName());
        holder.mBinding.recipeName.setSelected(true);
        holder.mBinding.recipeName.setHorizontallyScrolling(true);
        holder.mBinding.ingredientCount.setText(String.valueOf(holder.mRecipe.getIngredients().size()));
        holder.mBinding.servingCount.setText(String.valueOf(holder.mRecipe.getServings()));
        holder.mBinding.stepCount.setText(String.valueOf(holder.mRecipe.getSteps().size()));

    }

    @Override
    public int getItemCount() {return (mBake == null) ? 0 : mBake.size();}

    public class bakeHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final ListItemBinding mBinding;
        private Bake mRecipe;

        public bakeHolder(ListItemBinding binding) {
            super(binding.getRoot());
            this.mBinding = binding;

            mBinding.recipeList.setOnClickListener(this);
            }
        @Override
        public void onClick(View v) {
                mOnItemClickListener.onClick(mRecipe, v);
            }
        }


    }

