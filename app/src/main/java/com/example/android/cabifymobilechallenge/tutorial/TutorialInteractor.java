package com.example.android.cabifymobilechallenge.tutorial;

import android.content.Context;

import com.example.android.cabifymobilechallenge.common.model.BaseInteractor;
import com.example.android.cabifymobilechallenge.data.CabifyTutorial;
import com.example.android.cabifymobilechallenge.data.pojo.Tutorial;

public class TutorialInteractor
        extends BaseInteractor
        implements TutorialContract.Interactor {

    /**
     * Public constructors
     *
     * @param context is the Context of the calling View.
     */
    public TutorialInteractor(Context context) {
        super(context);
    }

    /* ****************************************** */
    /* TutorialContract.Interactor implementation */
    /* ****************************************** */

    @Override
    public Tutorial getTutorial() {
        return CabifyTutorial.getTutorial(mContext);

/*        Observable<Tutorial> tutorialObservable =
                Observable.create((Observable.OnSubscribe<Tutorial>) subscriber -> {
                    subscriber.onNext(CabifyTutorial.getTutorial(mContext));
                    subscriber.onCompleted();
                })
                        .subscribeOn(Schedulers.io())               // subscribeOn the I/O thread.
                        .observeOn(AndroidSchedulers.mainThread()); // observeOn the UI Thread.
        tutorialObservable.subscribe(tutorialSubscriber);*/
    }
}
