package com.example.android.mydiary.data;

/**
 * Created by Jalil on 26/06/2018.
 */

public class JournalEntry {
    private String mTitle;
    private long mDateCreated;
    private long mLastModified;
    private String mBody;

    public JournalEntry(String title, String body) {
        mTitle = title;
        mBody = body;

    }

    public String getTitle() {
        return mTitle;
    }

    public String getBody() {
        return mBody;
    }

    public long getDateCreated() {
        return mDateCreated;
    }

    public void setDateCreated(long mDateCreated) {
        this.mDateCreated = mDateCreated;
    }

    public long getLastModified() {
        return mLastModified;
    }

    public void setLastModified(long mLastModified) {
        this.mLastModified = mLastModified;
    }



}
