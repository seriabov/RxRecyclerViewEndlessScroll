package me.stilroof.endlessscroll.screen.main;

import android.util.Log;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Random;

import me.stilroof.endlessscroll.model.Product;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * @author Sergei Riabov 2016
 */

class MainPresenter implements MainContract.Presenter {

    private static final int PAGE_LIMIT = 30;

    private MainContract.View mView;

    MainPresenter(MainContract.View view) {
        mView = view;
    }

    @Override
    public void init() {
        loadData();
    }

    @Override
    public void scrolledToTheEnd() {
        Log.d("TAG", "scrolled to the end");
        setDummyData()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(addedList -> mView.addData(addedList),
                        Throwable::printStackTrace);
    }

    private void loadData() {
       setDummyData()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(dataList -> mView.setData(dataList),
                            Throwable::printStackTrace);
    }

    private Observable<List<Product>> setDummyData() {
        Random random = new Random();
        return Observable.range(0,PAGE_LIMIT)
                .map(integer -> {
                    Product p = new Product();
                    p.setName("Item #" + random.nextInt(1000));
                    p.setAmount(random.nextInt(100));
                    p.setPrice(new BigDecimal(random.nextInt(999)).divide(new BigDecimal(10),2, RoundingMode.HALF_UP));
                    Log.d("TAG", "generate " + p.getName());
                    return p;
                })
                .toList();
    }
}
