package com.example.android.cabifymobilechallenge.data;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;

import com.example.android.cabifymobilechallenge.R;
import com.example.android.cabifymobilechallenge.data.pojo.Tutorial;

/**
 * Public class for getting static data to be loaded into the tutorial.
 */
public class CabifyTutorial {

    private static String[] mTitlesArray, mDescriptionsArray;
    private static TypedArray mImageViewArray;

    /**
     * Empty constructor.
     */
    private CabifyTutorial() {
    }

    /**
     * Public helper method to retrieve the static data to be loaded into the tutorial.
     *
     * @return a {@link Tutorial} object containing the static data to be loaded into the tutorial.
     */
    public static Tutorial getTutorial(Context context) {
        // Get titles, descriptions and images for tutorial from resources arrays.
        Resources res = context.getResources();
        mTitlesArray = res.getStringArray(R.array.tutorial_titles_array);
        mDescriptionsArray = res.getStringArray(R.array.tutorial_descriptions_array);
        mImageViewArray = res.obtainTypedArray(R.array.tutorial_images_array);

        // Compose Tutorial object and return it.
        return new Tutorial(mTitlesArray, mDescriptionsArray, mImageViewArray);
    }

    /**
     * Public helper method to return the number of tutorial items.
     *
     * @return the length of the titles array, which is the same than the number of tutorial items.
     */
    public static int getSize() {
        return mTitlesArray.length;
    }
}
