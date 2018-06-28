package com.example.android.mydiary.journal;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.example.android.mydiary.data.JournalEntry;
import com.firebase.ui.auth.AuthUI;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Jalil on 26/06/2018.
 */

public class JournalPresenter implements JournalContract.Presenter{

    private JournalContract.View mJournalView;

    private DatabaseReference mDatabaseReference;

    private ChildEventListener mChildEventListener;

    // Choose authentication providers
    List<AuthUI.IdpConfig> providers = Arrays.asList(
            new AuthUI.IdpConfig.EmailBuilder().build(),
            new AuthUI.IdpConfig.GoogleBuilder().build() );



    public JournalPresenter(DatabaseReference databaseReference, JournalContract.View journalView) {
        mDatabaseReference = databaseReference;

        mJournalView = journalView;

        mJournalView.setPresenter(this);
    }

    @Override
    public void start() {
        attachDatabaseReadListener();
    }

    @Override
    public void attachDatabaseReadListener() {
        //only create and attach listener if it is null i.e. it has been detached
        if(mChildEventListener == null) {
            mChildEventListener = new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                    JournalEntry journalEntry = dataSnapshot.getValue(JournalEntry.class);
                    mJournalView.addEntry(journalEntry);
                }

                @Override
                public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                }

                @Override
                public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

                }

                @Override
                public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            };
            mDatabaseReference.addChildEventListener(mChildEventListener);
        }
    }


    @Override
    public void detachDatabaseReadListener() {
        if(mChildEventListener != null) {
            mDatabaseReference.removeEventListener(mChildEventListener);
            mChildEventListener = null;
        }
    }

    @Override
    public void signIn(String username, String emailAddress) {
        mJournalView.displayUserInfo(username, emailAddress);

    }

    @Override
    public void signOut() {
        mJournalView.clearAdapter();
        detachDatabaseReadListener();
        mJournalView.showSignInFlow(providers);
    }


    @Override
    public void openEntryDetails() {
        //TODO diary entry selected functionality

    }

    @Override
    public void openSettings() {
        //TODO settings selected functionality

    }

    @Override
    public void addNewEntry() {
        mJournalView.showAddEntry();
    }

}
