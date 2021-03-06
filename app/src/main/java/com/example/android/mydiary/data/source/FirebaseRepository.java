package com.example.android.mydiary.data.source;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.example.android.mydiary.data.JournalEntry;
import com.example.android.mydiary.journal.JournalPresenter;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jalil on 29/06/2018.
 */

public class FirebaseRepository implements FirebaseContract {

    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mUserEntryReference;
    private ChildEventListener mChildEventListener;

    private JournalPresenter mPresenter;


    private List<JournalEntry> mEntries = new ArrayList<>();

    public FirebaseRepository(String userUId, JournalPresenter presenter) {
        
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mUserEntryReference = mFirebaseDatabase.getReference()
                .child("users")
                .child(userUId)
                .child("entries");

        mPresenter = presenter;

    }

    @Override
    public void getEntries( GetEntriesCallback getEntriesCallback) {
        getEntriesCallback.onEntriesLoaded(mEntries);
    }


    @Override
    public void attachDatabaseReadListener() {
        mChildEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                JournalEntry journalEntry = dataSnapshot.getValue(JournalEntry.class);
                try {
                    journalEntry.setUniqueId(dataSnapshot.getKey());
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
                mEntries.add(journalEntry);
                mPresenter.loadEntries();
            }
            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                //reload
                mPresenter.loadEntries();
            }
            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                //reload
                mPresenter.loadEntries();

            }
            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };

        mUserEntryReference.addChildEventListener(mChildEventListener);
    }

    @Override
    public void detachDatabaseReadListener() {
        mUserEntryReference.removeEventListener(mChildEventListener);
    }

    @Override
    public void addEntry(JournalEntry newEntry) {
        mUserEntryReference.push().setValue(newEntry);
    }

    @Override
    public void updateEntry(JournalEntry updatedEntry, String entryUId) {
        mUserEntryReference.child(entryUId).setValue(updatedEntry);
    }

    @Override
    public void deleteJournalEntry(String entryUId) {
        mUserEntryReference.child(entryUId).removeValue();

    }

    @Override
    public void deleteAllEntries() {
        mUserEntryReference.removeValue();
        mPresenter.loadEntries();
    }

}
