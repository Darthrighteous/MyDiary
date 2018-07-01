package com.example.android.mydiary.addentry;

import com.example.android.mydiary.data.JournalEntry;
import com.example.android.mydiary.data.source.FirebaseRepository;
import com.example.android.mydiary.utilities.dateTimeUtils;

/**
 * Created by Jalil on 28/06/2018.
 */

public class AddEntryPresenter implements AddEntryContract.Presenter {

    private AddEntryContract.View mAddEntryView;

    private FirebaseRepository mFirebaseRepository;

    private String mEntryUId;
    private String mTitle;
    private String mBody;
    private String mDateCreated;


    //Add entry constructor
    public AddEntryPresenter(String userUId, AddEntryContract.View addEntryView) {
        mFirebaseRepository = new FirebaseRepository(userUId, null);

        mEntryUId = null;

        mAddEntryView = addEntryView;
        mAddEntryView.setPresenter(this);

    }

    //Edit entry constructor
    public AddEntryPresenter(String userUId, String entryUId, String title, String body,
                             String dateCreated, AddEntryContract.View addEntryView) {
        mFirebaseRepository = new FirebaseRepository(userUId, null);

        mEntryUId = entryUId;
        mTitle = title;
        mBody = body;
        mDateCreated = dateCreated;

        mAddEntryView = addEntryView;
        mAddEntryView.setPresenter(this);
    }

    @Override
    public void start() {
        mAddEntryView.showExistingEntry(mTitle, mBody);
    }

    @Override
    public void stop() {

    }

    @Override
    public void saveEntry(String title, String body) {
        //existing entry
        if (mEntryUId != null) {
            JournalEntry newEntry = new JournalEntry(title, body);
            //set the date modified and date created
            newEntry.setDateCreated(mDateCreated);
            newEntry.setDateModified(dateTimeUtils.getDateString());
            if (newEntry.isEmpty()) {
                mAddEntryView.showEmptyEntryError();
            } else {
                mFirebaseRepository.updateEntry(newEntry, mEntryUId);
                mAddEntryView.finishActivity();
            }
        }
        //new entry
        else {
            JournalEntry newEntry = new JournalEntry(title, body);
            if (newEntry.isEmpty()) {
                mAddEntryView.showEmptyEntryError();
            } else {
                mFirebaseRepository.addEntry(newEntry);
                mAddEntryView.finishActivity();
            }
        }

    }

    @Override
    public void deleteEntry() {
        //if an entry was selected
        if (mEntryUId != null) {
            mFirebaseRepository.deleteJournalEntry(mEntryUId);
        }
    }
}
