package com.example.android.mydiary.data;

import com.example.android.mydiary.utilities.dateTimeUtils;

/**
 * Created by Jalil on 26/06/2018.
 */

public class JournalEntry {
    private String mTitle;
    private String mDateCreated;
    private String mDateModified;
    private String mBody;
    private String mUniqueId;

    //no argument constructor for firebase
    public JournalEntry() {

    }

    public JournalEntry(String title, String body) {
        mTitle = title;
        mBody = body;
        mDateCreated = dateTimeUtils.getDateString();
        mDateModified = dateTimeUtils.getDateString();

    }

    public void setTitle (String title) {
        mTitle = title;
    }

    public void setBody (String body) {
        mBody = body;
    }

    public void setUniqueId (String uniqueId) {
        mUniqueId = uniqueId;
    }

    public void setDateCreated (String dateCreated) {
        mDateCreated = dateCreated;
    }

    public void setDateModified (String dateModified) {
        mDateModified = dateModified;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getBody() {
        return mBody;
    }

    public String getUniqueId() {
        return mUniqueId;
    }

    public String getDateCreated () {
        return mDateCreated;
    }

    public String getDateModified() {
        return mDateModified;
    }

    public boolean isEmpty() {
        return (mTitle.isEmpty() || mTitle == null)
                && (mBody.isEmpty() || mBody == null);
    }

//    public long getDateCreated() {
//        return mDateCreated;
//    }
//
//    public void setDateCreated(long mDateCreated) {
//        this.mDateCreated = mDateCreated;
//    }
//
//    public long getLastModified() {
//        return mLastModified;
//    }
//
//    public void setLastModified(long mLastModified) {
//        this.mLastModified = mLastModified;
//    }



}
