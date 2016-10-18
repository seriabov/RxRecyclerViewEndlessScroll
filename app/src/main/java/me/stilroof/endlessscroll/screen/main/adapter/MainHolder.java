package me.stilroof.endlessscroll.screen.main.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import java.text.NumberFormat;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.stilroof.endlessscroll.R;
import me.stilroof.endlessscroll.model.Product;

/**
 * @author Sergei Riabov 2016
 */

class MainHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.product_name)
    TextView mNameText;

    @BindView(R.id.product_price)
    TextView mPriceText;

    @BindView(R.id.product_amount)
    TextView mAmountText;

    MainHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    void bind(@NonNull Product product) {
        mNameText.setText(product.getName());
        mPriceText.setText(mPriceText.getResources()
                .getString(R.string.currency_usd_prefix,
                        NumberFormat.getInstance().format(product.getPrice())));
        mAmountText.setText(mAmountText.getResources()
                .getString(R.string.product_amount, product.getAmount()));
    }
}
