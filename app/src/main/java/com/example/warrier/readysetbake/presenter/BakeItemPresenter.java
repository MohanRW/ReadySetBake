package com.example.warrier.readysetbake.presenter;

import android.view.View;

import com.example.warrier.readysetbake.Network.NetworkService;
import com.example.warrier.readysetbake.R;
import com.example.warrier.readysetbake.model.Bake;

import java.util.ArrayList;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;



public class BakeItemPresenter implements BakeItemPresenterContract.Presenter{
    private final BakeItemPresenterContract.View mView;
    private final NetworkService mNetworkService;
    private CompositeDisposable mCompositeDisposable = new CompositeDisposable();

    public BakeItemPresenter(BakeItemPresenterContract.View mView, NetworkService mNetworkService) {
        this.mView = mView;
        this.mNetworkService = mNetworkService;
    }
    public interface Callbacks {
        void recipeSelected(Bake recipe);
    }


    @Override
    public void fetchRecipes() {
        Observable<ArrayList<Bake>> retrofitObserver;

        retrofitObserver = this.mNetworkService.networkApiRequestRecipes();

        retrofitObserver.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(networkApiRecipeObserver());

    }

    @Override
    public void recipeClicked(Bake recipe, View view) {
        ((Callbacks) view.getContext()).recipeSelected(recipe);

    }

    @Override
    public void viewDestroy() {
        mCompositeDisposable.clear();
    }


    private Observer<ArrayList<Bake>> networkApiRecipeObserver(){
        return new Observer<ArrayList<Bake>>() {
            @Override
            public void onSubscribe(Disposable d) {
                mCompositeDisposable.add(d);
            }
            @Override
            public void onNext(ArrayList<Bake> networkRecipeResult) {
                ArrayList<Bake> recipeList = new ArrayList<>();
                recipeList.addAll(networkRecipeResult);
                if(mView.isActive()) {
                    mView.updateAdapter(recipeList);
                }
            }
            @Override
            public void onError(Throwable e) {
                if(mView.isActive()) {
                    mView.displaySnackbarMessage(R.string.network_error_recipes);
                }
            }
            @Override
            public void onComplete() {}
        };
    }

}
