package com.example.warrier.readysetbake.fragments;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.ShareActionProvider;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.warrier.readysetbake.R;
import com.example.warrier.readysetbake.adapters.StepAdapter;
import com.example.warrier.readysetbake.databinding.RecipeFragBinding;
import com.example.warrier.readysetbake.model.Bake;
import com.example.warrier.readysetbake.model.Ingredient;
import com.example.warrier.readysetbake.model.Step;
import com.example.warrier.readysetbake.presenter.RecipeDetailPresenter;
import com.example.warrier.readysetbake.presenter.RecipeDetailPresenterContract;
import com.example.warrier.readysetbake.utils.OnItemClickListener;

import java.util.ArrayList;



public class Recipe extends Fragment implements RecipeDetailPresenterContract.View  {
    private static final String BUNDLE_RECIPE_DATA =
            "com.example.warrier.readysetbake.recipe_data";
    private final String INSTANCE_KEY_ADAPTER_POSITION = "instance_key_adapter_position";
    private final String INSTANCE_KEY_RECIPE = "instance_key_recipe";
    private final String INSTANCE_KEY_INGREDIENTS_ID = "instance_key_ingredients_id";
    private final String INSTANCE_KEY_INGREDIENTS_COUNT = "instance_key_ingredients_count";
    private final String INSTANCE_KEY_STEPS = "instance_key_steps";

    private ShareActionProvider mShareActionProvider;
    private RecipeFragBinding binding;
    private Bake mBake;
    private RecipeDetailPresenter mRecipeDetailPresenter;
    private RecyclerView mStepRecyclerView;
    private StepAdapter mStepAdapter;
    private int mStepAdapterSavedPosition = 0;
    private ArrayList<Step> mStepList = new ArrayList<>();
    private ArrayList<TextView> mIngredientList = new ArrayList<>();

    public static Recipe newInstance(Bake recipe) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(BUNDLE_RECIPE_DATA, recipe);
        Recipe fragment = new Recipe();
        fragment.setArguments(bundle);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();

        if ((arguments != null) && (arguments.containsKey(BUNDLE_RECIPE_DATA))) {
            mBake = arguments.getParcelable(BUNDLE_RECIPE_DATA);
        }
        if (savedInstanceState != null && savedInstanceState.containsKey(INSTANCE_KEY_ADAPTER_POSITION)) {
            mStepAdapterSavedPosition = savedInstanceState.getInt(INSTANCE_KEY_ADAPTER_POSITION);
        }
        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey(INSTANCE_KEY_RECIPE)) {
                mBake = savedInstanceState.getParcelable(INSTANCE_KEY_RECIPE);
            }
            if (savedInstanceState.containsKey(INSTANCE_KEY_STEPS)) {
                mStepList = savedInstanceState.getParcelableArrayList(INSTANCE_KEY_STEPS);
            }
            if (savedInstanceState.containsKey(INSTANCE_KEY_INGREDIENTS_COUNT)) {
                int ingredientCount = savedInstanceState.getInt(INSTANCE_KEY_INGREDIENTS_COUNT);
                for (int i = 0; i < ingredientCount; i++) {
                    TextView textView = new TextView(this.getContext());
                    textView.setTextColor(getResources().getColor(R.color.white));
                    textView.setPadding(10,12,10,12);
                    textView.setText("- "+String.valueOf(mBake.getIngredients().get(i).getQuantity()) +
                            String.valueOf(mBake.getIngredients().get(i).getMeasure()) + " " + mBake.getIngredients().get(i).getIngredient());
                    //textView.setChecked(savedInstanceState.getBoolean(INSTANCE_KEY_INGREDIENTS_ID + String.valueOf(i)));
                    mIngredientList.add(textView);
                }
            }


        }
        mRecipeDetailPresenter = new RecipeDetailPresenter(this);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);

        binding = DataBindingUtil.inflate(inflater, R.layout.recipe_frag, container, false);
        final View view = binding.getRoot();

        binding.tbToolbar.toolbar.setTitle(mBake.getName());
        ((AppCompatActivity) getActivity()).setSupportActionBar(binding.tbToolbar.toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        if(mIngredientList.size()>0) {
            for(TextView cbIngredientView : mIngredientList) {
                binding.ingredientChecklist.addView(cbIngredientView);
            }
        } else {
            if (mBake.getIngredients() != null && mBake.getIngredients().size() > 0) {
                for (Ingredient ingredient : mBake.getIngredients()) {
                    TextView textView = new TextView(this.getContext());
                    textView.setTextColor(getResources().getColor(R.color.white));
                    textView.setPadding(10,12,10,12);
                    textView.setText("- "+String.valueOf(ingredient.getQuantity()) +
                            String.valueOf(ingredient.getMeasure()) + " " + ingredient.getIngredient());
                    binding.ingredientChecklist.addView(textView);
                    mIngredientList.add(textView);
                }
            }
        }
        if (mBake.getSteps() != null && mBake.getSteps().size() > 0 && mStepList.size()==0) {
            mStepList.addAll(mBake.getSteps());
        }

        mStepRecyclerView = (RecyclerView) view.findViewById(R.id.recipe_detail_steps_recycler_view);
        mStepRecyclerView.setHasFixedSize(true);
        mStepRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),
                LinearLayoutManager.VERTICAL, false));
        mStepAdapter = new StepAdapter(mStepList, new OnItemClickListener<Step>() {
            @Override
            public void onClick(Step step, View view) {
                mRecipeDetailPresenter.stepClicked(mStepList, step.getId(), mBake.getName(), view);

            }
        });
        mStepAdapter.setStepAdapterCurrentPosition(mStepAdapterSavedPosition);
        mStepRecyclerView.setAdapter(mStepAdapter);

        return view;

    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        super.onCreateOptionsMenu(menu, menuInflater);
        menuInflater.inflate(R.menu.recipe_detail_menu, menu);
        MenuItem item = menu.findItem(R.id.menu_item_detail_share);
        mShareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(item);
        setShareIntent(createShareIntent());
    }
    private void setShareIntent(Intent shareIntent) {
        if (mShareActionProvider != null) {
            mShareActionProvider.setShareIntent(shareIntent);
        }
    }
    private Intent createShareIntent() {
        String msg = mBake.getName() + "\n" + "----\n" +
                getString(R.string.ingredients_title) + ":\n" + "----\n";
        for(TextView ingredient : mIngredientList){
            msg += ingredient.getText() + "\n";
        }
        msg += getString(R.string.steps_title) + ":\n" + "----\n";
        for(Step step : mStepList){
            msg += step.getShortDescription() + "\n";
        }
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT, msg);
        return shareIntent;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(INSTANCE_KEY_ADAPTER_POSITION, mStepAdapter.getStepAdapterCurrentPosition());
        outState.putParcelable(INSTANCE_KEY_RECIPE, mBake);
        outState.putParcelableArrayList(INSTANCE_KEY_STEPS, mStepList);
        outState.putInt(INSTANCE_KEY_INGREDIENTS_COUNT, mIngredientList.size());
    }


}