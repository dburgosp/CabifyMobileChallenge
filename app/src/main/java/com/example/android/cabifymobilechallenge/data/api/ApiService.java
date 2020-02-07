package com.example.android.cabifymobilechallenge.data.api;

import com.example.android.cabifymobilechallenge.data.pojo.products.Products;
import com.example.android.cabifymobilechallenge.data.pojo.products.Product;

import io.reactivex.Single;
import retrofit2.http.GET;

/**
 * Public interface that define the HTTP operations / API calls for performing requests to the
 * remote Cabify webserver.
 */
public interface ApiService {

    /**
     * API call for retrieving the list of Cabify products from the server.
     *
     * @return a list of {@link Product} objects.
     */
    @GET("4bwec")
    Single<Products> getProducts();

    // Add more HTTP operations (GET, POST...) here when required.

}
