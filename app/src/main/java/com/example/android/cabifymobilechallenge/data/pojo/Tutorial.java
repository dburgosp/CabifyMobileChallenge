package com.example.android.cabifymobilechallenge.data.pojo;

import android.content.res.TypedArray;

/**
 * Public class for instantiating elements to be loaded in the Tutorial ViewPager.
 */
public class Tutorial {

    /* ************************ */
    /* Private member variables */
    /* ************************ */

    private String[] mTitlesArray;
    private String[] mDescriptionsArray;
    private TypedArray mImageViewArray;

    /* ************ */
    /* Constructors */
    /* ************ */

    /**
     * Constructor for this class.
     *
     * @param titlesArray       is an String array containing the titles of the ViewPager items.
     * @param descriptionsArray is an String array containing the descriptions of the ViewPager
     *                          items.
     * @param imageViewArray    is an TypedArray containing the image resource identifiers of the
     *                          ViewPager items.
     */
    public Tutorial(String[] titlesArray, String[] descriptionsArray, TypedArray imageViewArray) {
        mTitlesArray = titlesArray;
        mDescriptionsArray = descriptionsArray;
        mImageViewArray = imageViewArray;
    }

    /* ******************* */
    /* Getters and Setters */
    /* ******************* */

    public String[] getTitlesArray() {
        return mTitlesArray;
    }

    public void setTitlesArray(String[] titlesArray) {
        mTitlesArray = titlesArray;
    }

    public String[] getDescriptionsArray() {
        return mDescriptionsArray;
    }

    public void setDescriptionsArray(String[] descriptionsArray) {
        mDescriptionsArray = descriptionsArray;
    }

    public TypedArray getImageViewArray() {
        return mImageViewArray;
    }

    public void setImageViewArray(TypedArray imageViewArray) {
        mImageViewArray = imageViewArray;
    }
}
