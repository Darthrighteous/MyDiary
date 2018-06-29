package com.example.android.mydiary.data.source;

import com.example.android.mydiary.data.JournalEntry;
import com.google.firebase.database.DatabaseReference;

import java.util.List;

/**
 * Created by Jalil on 29/06/2018.
 */

public interface FirebaseContract {
    interface GetEntriesCallback {
        void onEntriesLoaded(List<JournalEntry> entries);

    }

    DatabaseReference getDatabaseReference();

    void getEntries(GetEntriesCallback callback);

    void detachDatabaseReadListener();

    void addEntry(JournalEntry newEntry);

    void getJournalEntries();

    void deleteJournalEntry();

    void editJournalEntry();
}
