package com.example.android.mydiary.journal;

import com.example.android.mydiary.data.JournalEntry;
import com.example.android.mydiary.data.source.FirebaseContract;
import com.example.android.mydiary.data.source.FirebaseRepository;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DatabaseReference;

import java.util.List;

/**
 * Created by Jalil on 26/06/2018.
 */

public class JournalPresenter implements JournalContract.Presenter{

    private JournalContract.View mJournalView;

    private DatabaseReference mDatabaseReference;

    private ChildEventListener mChildEventListener;

    private FirebaseUser mUser;

    private FirebaseRepository mFirebaseRepository;


    public JournalPresenter(FirebaseUser user, JournalContract.View journalView) {
        mFirebaseRepository = new FirebaseRepository(user.getUid());

        mDatabaseReference = mFirebaseRepository.getDatabaseReference();

        mJournalView = journalView;

        mUser = user;

        mJournalView.setPresenter(this);
    }

    @Override
    public void start() {
        loadEntries();
    }

    @Override
    public void stop() {
        mFirebaseRepository.detachDatabaseReadListener();
    }

    @Override
    public void loadEntries() {
        mFirebaseRepository.getEntries(new FirebaseContract.GetEntriesCallback() {
            @Override
            public void onEntriesLoaded(List<JournalEntry> entries) {
                processEntries(entries);
            }
        });
    }

    @Override
    public void processEntries(List<JournalEntry> entries) {
        mJournalView.showJournalEntries(entries);
    }


    @Override
    public void openEntryDetails() {
        //TODO diary entry selected functionality

    }

    @Override
    public void openSettings() {
        //TODO settings selected functionality

    }
}
