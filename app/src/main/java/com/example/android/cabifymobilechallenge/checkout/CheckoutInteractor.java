package com.example.android.cabifymobilechallenge.checkout;

import android.annotation.SuppressLint;
import android.content.Context;

import com.example.android.cabifymobilechallenge.common.model.BaseInteractor;
import com.example.android.cabifymobilechallenge.common.presenter.Callback;
import com.example.android.cabifymobilechallenge.data.SampleData;
import com.example.android.cabifymobilechallenge.data.pojo.payment.Checkout;
import com.example.android.cabifymobilechallenge.data.pojo.payment.PaymentMethod;
import com.example.android.cabifymobilechallenge.data.pojo.payment.TransactionDataResult;
import com.example.android.cabifymobilechallenge.data.pojo.payment.TransactionData;

import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

import static com.example.android.cabifymobilechallenge.data.pojo.payment.TransactionDataResult.CODE_1;
import static com.example.android.cabifymobilechallenge.data.pojo.payment.TransactionDataResult.DESC_CODE_1;

public class CheckoutInteractor
        extends BaseInteractor
        implements CheckoutContract.Interactor {

    /**
     * Public constructors
     *
     * @param context is the Context of the calling View.
     */
    public CheckoutInteractor(Context context) {
        super(context);
    }

    /* ************************************** */
    /* UserContract.Interactor implementation */
    /* ************************************** */

    /**
     * Create an {@link Single} Observable for retrieving the available payment methods.
     *
     * @param callback is the {@link Callback<ArrayList>} object that is going to receive the result
     *                 of this API call.
     */
    @SuppressLint("CheckResult")
    @Override
    public void getPaymentMethods(Callback<ArrayList<PaymentMethod>> callback) {
        // Create the Observable.
        Single<ArrayList<PaymentMethod>> getPaymentMethodsList = Single.create(emitter -> {
            Thread thread = new Thread(() -> {
                try {
                    // The API call doesn't exist, so we return some sample data.
                    emitter.onSuccess(SampleData.getPaymentMethods());
                } catch (Exception e) {
                    emitter.onError(e);
                }
            });
            thread.start();
        });

        // Observe for results.
        getPaymentMethodsList.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<ArrayList<PaymentMethod>>() {
                    @Override
                    public void onSuccess(ArrayList<PaymentMethod> paymentMethods) {
                        callback.returnResult(paymentMethods);
                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.returnError(e.getMessage());
                    }
                });
    }

    /**
     * Create an {@link Single} Observable for completing the payment process. This should typically
     * connect to the selected payment method platform (PayPal or bank virtual TPV), send the
     * required transaction data, wait for the result and send it back to the Presenter.
     *
     * @param checkout is the {@link Checkout} object containing the final shopping process,
     *                 containing all the required data for the payment transaction.
     * @param callback is the {@link Callback<TransactionData>} object that is going to receive the
     *                 result of this API call.
     */
    @SuppressLint("CheckResult")
    @Override
    public void processPayment(Checkout checkout, Callback<TransactionData> callback) {
        // Create the Observable.
        Single<TransactionData> beginPaymentTransaction = Single.create(emitter -> {
            Thread thread = new Thread(() -> {
                try {
                    // The connection to PayPal or bank payment platforms don't exist, so we return
                    // some sample data.
                    TransactionData transaction =
                            new TransactionData(checkout.getPaymentMethod().getName(),
                                    "A1B2C3D4E5",  "000056789",
                                    getAmountToPay(checkout.getShoppingCart()),
                                    checkout.getUserName(), new Date(),
                                    new TransactionDataResult(CODE_1, DESC_CODE_1));

                    Thread.sleep(2000);
                    emitter.onSuccess(transaction);
                } catch (Exception e) {
                    emitter.onError(e);
                }
            });
            thread.start();
        });

        // Observe for results.
        beginPaymentTransaction.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<TransactionData>() {
                    @Override
                    public void onSuccess(TransactionData result) {
                        callback.returnResult(result);
                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.returnError(e.getMessage());
                    }
                });
    }
}
