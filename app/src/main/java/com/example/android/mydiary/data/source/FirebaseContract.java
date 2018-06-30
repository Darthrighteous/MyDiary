package com.example.android.mydiary.data.source;

import com.example.android.mydiary.data.JournalEntry;

import java.util.List;

/**
 * Created by Jalil on 29/06/2018.
 */

public interface FirebaseContract {
    interface GetEntriesCallback {
        void onEntriesLoaded(List<JournalEntry> entries);

        void onLoadComplete();

    }

    void getEntries (GetEntriesCallback callback);

    void attachDatabaseReadListener ();

    void detachDatabaseReadListener ();

    void addEntry (JournalEntry newEntry);

    void updateEntry (JournalEntry updatedEntry, String entryUId);

    void deleteJournalEntry(String entryUId);
}
