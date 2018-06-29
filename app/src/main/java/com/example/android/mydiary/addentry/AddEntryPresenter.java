package com.example.android.mydiary.addentry;

import com.example.android.mydiary.data.JournalEntry;
import com.example.android.mydiary.data.source.FirebaseRepository;

/**
 * Created by Jalil on 28/06/2018.
 */

public class AddEntryPresenter implements AddEntryContract.Presenter {

    private AddEntryContract.View mAddEntryView;

    private FirebaseRepository mFirebaseRepository;

    private String mEntryUId;
    private String mTitle;
    private String mBody;


    //Add entry constructor
    public AddEntryPresenter(String userUId, AddEntryContract.View addEntryView) {
        mFirebaseRepository = new FirebaseRepository(userUId);

        mEntryUId = null;

        mAddEntryView = addEntryView;
        mAddEntryView.setPresenter(this);

    }

    //Edit entry constructor
    public AddEntryPresenter(String userUId, String entryUId, String title, String body, AddEntryContract.View addEntryView) {
        mFirebaseRepository = new FirebaseRepository(userUId);

        mEntryUId = entryUId;
        mTitle = title;
        mBody = body;

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
        if (mEntryUId != null) {
            JournalEntry newEntry = new JournalEntry(title, body);
            if (newEntry.isEmpty()) {
                mAddEntryView.showEmptyEntryError();
            } else {
                mFirebaseRepository.updateEntry(newEntry, mEntryUId);
                mAddEntryView.finishActivity();
            }
        } else {
            JournalEntry newEntry = new JournalEntry(title, body);
            if (newEntry.isEmpty()) {
                mAddEntryView.showEmptyEntryError();
            } else {
                mFirebaseRepository.addEntry(newEntry);
                mAddEntryView.finishActivity();
            }
        }

    }
}
