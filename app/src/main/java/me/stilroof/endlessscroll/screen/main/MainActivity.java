package me.stilroof.endlessscroll.screen.main;

import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.stilroof.endlessscroll.R;
import me.stilroof.endlessscroll.model.Product;
import me.stilroof.endlessscroll.screen.main.adapter.DividerItemDecoration;
import me.stilroof.endlessscroll.screen.main.adapter.MainAdapter;
import rx.Observable;
import rx.subjects.PublishSubject;


/**
 * @author Sergei Riabov 2016
 */

public class MainActivity extends AppCompatActivity implements MainContract.View {

    @BindView(R.id.productsRecyclerView)
    RecyclerView mProductRecyclerView;

    @BindView(R.id.fab)
    FloatingActionButton fab;

    private MainContract.Presenter mPresenter;
    private MainAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ButterKnife.bind(this);

        fab.setOnClickListener(view1 -> Snackbar.make(mProductRecyclerView,getString(R.string.app_name),Snackbar.LENGTH_LONG).show());

        mProductRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mProductRecyclerView.addItemDecoration(new DividerItemDecoration(this));
        mAdapter = new MainAdapter();
        mProductRecyclerView.setAdapter(mAdapter);

        mPresenter = new MainPresenter(this);
        mPresenter.init();

        RecyclerScrollListener scrollListener = new RecyclerScrollListener();
        mProductRecyclerView.addOnScrollListener(scrollListener);
        scrollListener.getScrolledToTheEndObservable()
                .throttleFirst(300, TimeUnit.MILLISECONDS)
                .subscribe(dy -> mPresenter.scrolledToTheEnd());
    }

    @Override
    public void setData(List<Product> productList) {
        mAdapter.setData(productList);
    }

    @Override
    public void addData(List<Product> newProductList) {
        Handler handler = new Handler();
        Runnable r = () -> mAdapter.addData(newProductList);
        handler.post(r);
    }

    class RecyclerScrollListener extends RecyclerView.OnScrollListener {
        private final PublishSubject<Integer> scrollBus = PublishSubject.create();
        int lastVisibleItem;
        int totalItem;

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            if (dy > 0) {
                fab.hide();
            } else if (dy < 0) {
                fab.show();
            }

            lastVisibleItem = ((LinearLayoutManager) recyclerView.getLayoutManager()).findLastVisibleItemPosition();
            totalItem = recyclerView.getAdapter().getItemCount();
            scrollBus.onNext(dy);
        }
        Observable<Integer> getScrolledToTheEndObservable() {
            return scrollBus.onBackpressureDrop()
                    .filter(dy -> dy>0)
                    .filter(dy -> lastVisibleItem > totalItem - 2)
                    .asObservable();
        }
    }
}
