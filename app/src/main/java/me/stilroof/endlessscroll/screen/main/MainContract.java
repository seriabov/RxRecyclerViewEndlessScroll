package me.stilroof.endlessscroll.screen.main;

import java.util.List;

import me.stilroof.endlessscroll.model.Product;

/**
 * @author Sergei Riabov 2016
 */

interface MainContract {

    interface View {

        void setData(List<Product> productList);

        void addData(List<Product> newProductList);

    }

    interface Presenter {

        void init();

        void scrolledToTheEnd();

    }
}
