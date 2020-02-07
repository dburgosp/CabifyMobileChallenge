package com.example.android.cabifymobilechallenge.user;

import android.annotation.SuppressLint;
import android.content.Context;

import com.example.android.cabifymobilechallenge.common.model.BaseInteractor;
import com.example.android.cabifymobilechallenge.common.presenter.Callback;
import com.example.android.cabifymobilechallenge.data.SampleData;
import com.example.android.cabifymobilechallenge.data.pojo.user.User;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class UserInteractor
        extends BaseInteractor
        implements UserContract.Interactor {

    private static final int MIN_PASSWORD_LENGTH = 8;
    private static final int RECOVERY_PASSWORD_CODE_LENGTH = 6;

    /**
     * Public constructors
     *
     * @param context is the Context of the calling View.
     */
    public UserInteractor(Context context) {
        super(context);
    }

    /* ************************************** */
    /* UserContract.Interactor implementation */
    /* ************************************** */

    /**
     * Create an {@link Single} Observable for checking if an user e-mail is registered on this app.
     *
     * @param email    is the user's email.
     * @param callback is the {@link Callback<Boolean>} object that is going to receive the result
     *                 of this API call.
     */
    @SuppressLint("CheckResult")
    @Override
    public void checkEmail(String email, Callback<Boolean> callback) {
        // Create the Observable.
        Single<Boolean> isValidEmail = Single.create(emitter -> {
            Thread thread = new Thread(() -> {
                try {
                    // The API call doesn't exist, so we return some sample value (true if the typed
                    // e-mail is the same than the stored one).
                    Boolean result = getEmail().equals(email);

                    // Simulate a short delay before returning the result.
                    Thread.sleep(2000);
                    emitter.onSuccess(result);
                } catch (Exception e) {
                    emitter.onError(e);
                }
            });
            thread.start();
        });

        // Observe for results.
        isValidEmail.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<Boolean>() {
                    @Override
                    public void onSuccess(Boolean isValid) {
                        callback.returnResult(isValid);
                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.returnError(e.getMessage());
                    }
                });
    }

    /**
     * Create an {@link Single} Observable for log in an user.
     *
     * @param email    is the registered user's email.
     * @param password is the password.
     * @param callback is the {@link Callback<String>} object that is going to receive the result
     *                 of this API call.
     */
    @SuppressLint("CheckResult")
    @Override
    public void userLogin(String email, String password, Callback<User> callback) {
        // Create the Observable.
        Single<User> isLoginCorrect = Single.create(emitter -> {
            Thread thread = new Thread(() -> {
                try {
                    // The API call doesn't exist, so we return a sample logged User.
                    User user = SampleData.getUser();

                    // Simulate a short delay before returning the result.
                    Thread.sleep(2000);
                    emitter.onSuccess(user);
                } catch (Exception e) {
                    emitter.onError(e);
                }
            });
            thread.start();
        });

        // Observe for results.
        isLoginCorrect.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<User>() {
                    @Override
                    public void onSuccess(User user) {
                        callback.returnResult(user);
                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.returnError(e.getMessage());
                    }
                });
    }

    /**
     * Create an {@link Single} Observable for creating a new password recovery code and send it to
     * a given e-mail.
     *
     * @param email    is the registered user's email.
     * @param callback is the {@link Callback<Boolean>} object that is going to receive the result
     *                 of this API call.
     */
    @SuppressLint("CheckResult")
    @Override
    public void generateRecoveryCode(String email, Callback<Boolean> callback) {
        // Create the Observable.
        Single<Boolean> getNewPasswordRecoveryCode = Single.create(emitter -> {
            Thread thread = new Thread(() -> {
                try {
                    // The Interactor should execute here an API call for asking to the server to
                    // generate the new code. That new code should be sent later from the server to
                    // the user's e-mail. The API call doesn't exist so far, so we simulate a short
                    // delay before returning a sample result.
                    Thread.sleep(2000);
                    emitter.onSuccess(true);
                } catch (Exception e) {
                    emitter.onError(e);
                }
            });
            thread.start();
        });

        // Observe for results.
        getNewPasswordRecoveryCode.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<Boolean>() {
                    @Override
                    public void onSuccess(Boolean res) {
                        callback.returnResult(res);
                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.returnError(e.getMessage());
                    }
                });
    }

    /**
     * Create an {@link Single} Observable for checking if a typed password recovery code is valid
     *
     * @param code     is the password recovery code.
     * @param callback is the {@link Callback<Boolean>} object that is going to receive the result
     *                 of this API call.
     */
    @SuppressLint("CheckResult")
    @Override
    public void checkRecoveryCode(String code, Callback<Boolean> callback) {
        // Create the Observable.
        Single<Boolean> checkNewPasswordRecoveryCode = Single.create(emitter -> {
            Thread thread = new Thread(() -> {
                try {
                    // The API call doesn't exist, so we simulate a short delay before returning a
                    // sample result (always true).
                    Thread.sleep(2000);
                    emitter.onSuccess(true);
                } catch (Exception e) {
                    emitter.onError(e);
                }
            });
            thread.start();
        });

        // Observe for results.
        checkNewPasswordRecoveryCode.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<Boolean>() {
                    @Override
                    public void onSuccess(Boolean isValid) {
                        callback.returnResult(isValid);
                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.returnError(e.getMessage());
                    }
                });
    }

    /**
     * Create an {@link Single} Observable for validating an user's phone and sending the validation
     * code via WhatsApp.
     *
     * @param phone    is the user mobile phone.
     * @param callback is the {@link Callback<Boolean>} object that is going to receive the result
     *                 of this API call.
     */
    @SuppressLint("CheckResult")
    @Override
    public void whatsAppValidation(String phone, Callback<Boolean> callback) {
        // Create the Observable.
        Single<Boolean> validateAndSendWhatsApp = Single.create(emitter -> {
            Thread thread = new Thread(() -> {
                try {
                    // The API call doesn't exist, so we simulate a short delay before returning a
                    // sample result (always true).
                    Thread.sleep(2000);
                    emitter.onSuccess(true);
                } catch (Exception e) {
                    emitter.onError(e);
                }
            });
            thread.start();
        });

        // Observe for results.
        validateAndSendWhatsApp.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<Boolean>() {
                    @Override
                    public void onSuccess(Boolean isValid) {
                        callback.returnResult(isValid);
                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.returnError(e.getMessage());
                    }
                });
    }

    /**
     * Create an {@link Single} Observable for validating an user's phone and sending the validation
     * code via SMS.
     *
     * @param phone    is the user mobile phone.
     * @param callback is the {@link Callback<Boolean>} object that is going to receive the result
     *                 of this API call.
     */
    @SuppressLint("CheckResult")
    @Override
    public void smsValidation(String phone, Callback<Boolean> callback) {
        // Create the Observable.
        Single<Boolean> validateAndSendSms = Single.create(emitter -> {
            Thread thread = new Thread(() -> {
                try {
                    // The API call doesn't exist, so we simulate a short delay before returning a
                    // sample result (always true).
                    Thread.sleep(2000);
                    emitter.onSuccess(true);
                } catch (Exception e) {
                    emitter.onError(e);
                }
            });
            thread.start();
        });

        // Observe for results.
        validateAndSendSms.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<Boolean>() {
                    @Override
                    public void onSuccess(Boolean isValid) {
                        callback.returnResult(isValid);
                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.returnError(e.getMessage());
                    }
                });
    }

    /**
     * Create an {@link Single} Observable for checking if a validation code is valid
     *
     * @param code     is the password recovery code.
     * @param callback is the {@link Callback<Boolean>} object that is going to receive the result
     *                 of this API call.
     */
    @SuppressLint("CheckResult")
    @Override
    public void checkValidationCode(String code, Callback<Boolean> callback) {
        // Create the Observable.
        Single<Boolean> checkValidationCode = Single.create(emitter -> {
            Thread thread = new Thread(() -> {
                try {
                    // The API call doesn't exist, so we simulate a short delay before returning a
                    // sample result (always true).
                    Thread.sleep(2000);
                    emitter.onSuccess(true);
                } catch (Exception e) {
                    emitter.onError(e);
                }
            });
            thread.start();
        });

        // Observe for results.
        checkValidationCode.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<Boolean>() {
                    @Override
                    public void onSuccess(Boolean isValid) {
                        callback.returnResult(isValid);
                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.returnError(e.getMessage());
                    }
                });
    }

    /**
     * @return {@link UserInteractor#MIN_PASSWORD_LENGTH} value.
     */
    @Override
    public int getMinPasswordLength() {
        return MIN_PASSWORD_LENGTH;
    }

    /**
     * @return {@link UserInteractor#RECOVERY_PASSWORD_CODE_LENGTH} value.
     */
    @Override
    public int getCodeLength() {
        return RECOVERY_PASSWORD_CODE_LENGTH;
    }
}
