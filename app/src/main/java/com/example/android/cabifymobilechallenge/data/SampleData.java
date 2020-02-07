package com.example.android.cabifymobilechallenge.data;

import android.content.Context;

import com.example.android.cabifymobilechallenge.R;
import com.example.android.cabifymobilechallenge.data.pojo.discounts.Discount;
import com.example.android.cabifymobilechallenge.data.pojo.discounts.DiscountTypeMxN;
import com.example.android.cabifymobilechallenge.data.pojo.discounts.DiscountTypeNorMore;
import com.example.android.cabifymobilechallenge.data.pojo.payment.PaymentMethod;
import com.example.android.cabifymobilechallenge.data.pojo.products.Product;
import com.example.android.cabifymobilechallenge.data.pojo.products.ProductDetails;
import com.example.android.cabifymobilechallenge.data.pojo.user.Address;
import com.example.android.cabifymobilechallenge.data.pojo.user.User;

import java.util.ArrayList;

public class SampleData {

    /**
     * Private empty constructor.
     */
    private SampleData() {
    }

    /**
     * Public static method for getting a sample small image depending of a product code. This
     * information should come from an API call.
     *
     * @param productCode is the {@link Product} code.
     * @return a drawable resource identifier for loading the corresponding Drawable into an
     * ImageView.
     */
    public static int getSampleImageSmall(String productCode) {
        switch (productCode) {
            case "VOUCHER":
                return R.drawable.cabify;

            case "TSHIRT":
                return R.drawable.t_shirt;

            case "MUG":
            default:
                return R.drawable.coffee_mug;
        }
    }

    /**
     * Public static method for getting a sample big image depending of a product code. This
     * information  should come from an API call.
     *
     * @param productCode is the {@link Product} code.
     * @return a drawable resource identifier for loading the corresponding Drawable into an
     * ImageView.
     */
    public static int getSampleImageBig(String productCode) {
        switch (productCode) {
            case "VOUCHER":
                return R.drawable.cabify_big;

            case "TSHIRT":
                return R.drawable.t_shirt_big;

            case "MUG":
            default:
                return R.drawable.coffee_mug_big;
        }
    }

    /**
     * Public static method for knowing if a product has discount or not. This information should
     * come from an API call.
     *
     * @param productCode is the {@link Product} code.
     * @return true if the product has discount, false otherwise.
     */
    public static boolean getSampleHasDiscount(String productCode) {
        switch (productCode) {
            case "VOUCHER":
            case "TSHIRT":
                return true;

            default:
                return false;
        }
    }

    /**
     * Public static method for getting a sample discount depending of a product code. This
     * information should come from an API call.
     *
     * @param productCode is the {@link Product} code.
     * @return a {@link Discount} object with the discount applied to the product, or null if there
     * is no discount.
     */
    public static Discount<android.os.Parcelable> getSampleDiscount(String productCode) {
        switch (productCode) {
            case "VOUCHER":
                DiscountTypeMxN voucherDiscount = new DiscountTypeMxN(2, 1);
                return new Discount<>(Discount.TYPE_M_X_N, voucherDiscount);

            case "TSHIRT":
                DiscountTypeNorMore tshirtDiscount = new DiscountTypeNorMore(3, 19.0);
                return new Discount<>(Discount.TYPE_N_OR_MORE, tshirtDiscount);

            default:
                return null;
        }
    }

    /**
     * Public static method for getting a sample discount depending of a product code. This
     * information should come from an API call.
     *
     * @param context     is the {@link Context} for getting the string resource.
     * @param productCode is the {@link Product} code.
     * @return a {@link Discount} object with the discount applied to the product, or null if there
     * is no discount.
     */
    public static ProductDetails getProductDetails(Context context, String productCode) {
        String sampleText = context.getString(R.string.sample_text);

        switch (productCode) {
            case "VOUCHER":
                return new ProductDetails(productCode, "Cabify Voucher", 5.0,
                        getSampleImageBig(productCode), sampleText, getSampleDiscount(productCode));

            case "TSHIRT":
                return new ProductDetails(productCode, "Cabify T-Shirt", 20.0,
                        getSampleImageBig(productCode), sampleText, getSampleDiscount(productCode));

            case "MUG":
            default:
                return new ProductDetails(productCode, "Cabify Coffee Mug", 7.5,
                        getSampleImageBig(productCode), sampleText, getSampleDiscount(productCode));
        }
    }

    /**
     * @return a sample {@link User} object.
     */
    public static User getUser() {
        // Create sample addresses.
        ArrayList<Address> addresses = new ArrayList<>();
        addresses.add(new Address("Casa", "Calle Mayor", "2", null,
                "Madrid", "Madrid", "28000", "España"));
        addresses.add(new Address("Casa de mamá", "Avenida de la Paz", "47",
                "Portal 23, bajo B", "Madrid", "Madrid",
                "28010", "España"));

        return new User("12345678", "David", "Burgos Prieto", "123456",
                "665895175", "david.burgos.prieto@gmail.com",
                "ABCDEFGHIJKLMNOPQ12345", addresses);
    }

    /**
     * @return a list of {@link PaymentMethod} objects.
     */
    public static ArrayList<PaymentMethod> getPaymentMethods() {
        ArrayList<PaymentMethod> paymentMethods = new ArrayList<>();
        paymentMethods.add(new PaymentMethod(
                "PayPal", PaymentMethod.PAYMENT_PAYPAL));
        paymentMethods.add(new PaymentMethod(
                "Tarjeta bancaria", PaymentMethod.PAYMENT_CREDIT_CARD));
        return paymentMethods;
    }
}
