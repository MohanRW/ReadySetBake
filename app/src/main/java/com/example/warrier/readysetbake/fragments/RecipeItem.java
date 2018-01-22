package com.example.warrier.readysetbake.fragments;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.warrier.readysetbake.Network.NetworkService;
import com.example.warrier.readysetbake.R;
import com.example.warrier.readysetbake.adapters.BakeAdapter;
import com.example.warrier.readysetbake.databinding.RecipeItemsFragBinding;
import com.example.warrier.readysetbake.model.Bake;
import com.example.warrier.readysetbake.presenter.BakeItemPresenter;
import com.example.warrier.readysetbake.presenter.BakeItemPresenterContract;
import com.example.warrier.readysetbake.utils.OnItemClickListener;

import java.util.ArrayList;


public class RecipeItem extends Fragment
        implements BakeItemPresenterContract.View {
    private final String INSTANCE_KEY_RECIPE_LIST = "instance_key_recipe_list";

    private RecipeItemsFragBinding binding;
    private RecyclerView mRecipeRecyclerView;
    private BakeAdapter mAdapter;
    private ArrayList<Bake> mBakeList = new ArrayList<>();
    private BakeItemPresenter mBakeListPresenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey(INSTANCE_KEY_RECIPE_LIST)) {
                mBakeList = savedInstanceState.getParcelableArrayList(INSTANCE_KEY_RECIPE_LIST);
            }
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.recipe_items_frag, container, false);
        final View view = binding.getRoot();
         binding.tbToolbar.toolbar.setTitle("Ready Set Bake");




        mRecipeRecyclerView = (RecyclerView) view.findViewById(R.id.recipe_recycler_view);
        mRecipeRecyclerView.setHasFixedSize(true);
        mRecipeRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(),
                getResources().getInteger(R.integer.recipe_list_columns)));

        mBakeListPresenter = new BakeItemPresenter(this, new NetworkService());

        mAdapter = new BakeAdapter(mBakeList, new OnItemClickListener<Bake>() {
            @Override
            public void onClick(Bake bake, View view) {
                mBakeListPresenter.recipeClicked(bake, view);
            }
        });
        mRecipeRecyclerView.setAdapter(mAdapter);


        if(mBakeList==null || mBakeList.size()==0) {
            mBakeListPresenter.fetchRecipes();
        }
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mBakeListPresenter.viewDestroy();
    }

    @Override
    public void updateAdapter(ArrayList<Bake> recipeList) {
        mBakeList.clear();
        mBakeList.addAll(recipeList);
        mAdapter.notifyDataSetChanged();
    }



    @Override
    public void displaySnackbarMessage(int stringResId) {
        Snackbar snackbar = Snackbar.make(getActivity().findViewById(R.id.list_container),
                getString(stringResId),
                Snackbar.LENGTH_LONG);
        View snackbarView = snackbar.getView();
        snackbarView.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorAccent));
        snackbar.show();
    }

    @Override
    public boolean isActive() {
        return isAdded();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList(INSTANCE_KEY_RECIPE_LIST, mBakeList);
        super.onSaveInstanceState(outState);
    }
}
