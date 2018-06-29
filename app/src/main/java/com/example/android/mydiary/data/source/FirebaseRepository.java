package com.example.android.mydiary.data.source;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.example.android.mydiary.data.JournalEntry;
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

    private String mUserUId;

    public FirebaseRepository(String userUId) {
        mUserUId = userUId;
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mUserEntryReference = mFirebaseDatabase.getReference()
                .child("users")
                .child(mUserUId)
                .child("entries");
    }

    @Override
    public DatabaseReference getDatabaseReference() {
        return mUserEntryReference;

    }

    @Override
    public void getEntries( GetEntriesCallback getEntriesCallback) {

        final List<JournalEntry> entries = new ArrayList<>();

        mChildEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                JournalEntry journalEntry = dataSnapshot.getValue(JournalEntry.class);
                entries.add(journalEntry);
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

        mUserEntryReference.addChildEventListener(mChildEventListener);

        getEntriesCallback.onEntriesLoaded(entries);
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
    public void getJournalEntries() {

    }

    @Override
    public void deleteJournalEntry() {

    }

    @Override
    public void editJournalEntry() {

    }
}