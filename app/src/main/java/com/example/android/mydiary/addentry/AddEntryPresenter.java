package com.example.android.mydiary.addentry;

import com.example.android.mydiary.data.JournalEntry;
import com.example.android.mydiary.data.source.FirebaseRepository;

/**
 * Created by Jalil on 28/06/2018.
 */

public class AddEntryPresenter implements AddEntryContract.Presenter {

    private AddEntryContract.View mAddEntryView;

    private FirebaseRepository mFirebaseRepository;


    public AddEntryPresenter(String userUId, AddEntryContract.View addEntryView) {
        mFirebaseRepository = new FirebaseRepository(userUId);

        mAddEntryView = addEntryView;
        mAddEntryView.setPresenter(this);
    }

    @Override
    public void start() {

    }

    @Override
    public void stop() {

    }

    @Override
    public void saveEntry(String title, String body) {
        JournalEntry newEntry = new JournalEntry(title, body);
        if (newEntry.isEmpty()) {
            mAddEntryView.showEmptyEntryError();
        } else {
            mFirebaseRepository.addEntry(newEntry);
            mAddEntryView.finishActivity();
        }

    }

    @Override
    public void showExistingEntry() {

    }
}
