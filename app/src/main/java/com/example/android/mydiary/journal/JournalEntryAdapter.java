package com.example.android.mydiary.journal;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.android.mydiary.R;
import com.example.android.mydiary.data.JournalEntry;

import java.util.List;

/**
 * Created by Jalil on 28/06/2018.
 */

public class JournalEntryAdapter extends ArrayAdapter<JournalEntry> {

    private List<JournalEntry> mList;
    private EntryClickListener entryClickListener;


    public JournalEntryAdapter(@NonNull Context context, int resource,
                               @NonNull List<JournalEntry> objects, EntryClickListener listener) {
        super(context, resource, objects);
        setList(objects);
        entryClickListener = listener;

    }

    public void setList(List<JournalEntry> list) {
        mList = list;
    }

    public void updateData (List<JournalEntry> list) {
        setList(list);
        notifyDataSetChanged();
    }

    @Nullable
    @Override
    public JournalEntry getItem(int position) {
        return mList.get(position);
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View newView =  convertView;
        if (newView == null) {
            newView = ((Activity) getContext()).getLayoutInflater()
                    .inflate(R.layout.item_journal_entry, parent, false);
        }
        TextView title = newView.findViewById(R.id.text_title);
        TextView body = newView.findViewById(R.id.text_body);
        TextView date = newView.findViewById(R.id.text_date_created);

        final JournalEntry journalEntry = getItem(position);

        title.setText(journalEntry.getTitle());
        body.setText(journalEntry.getBody());

        String dateString = "Created - " + journalEntry.getDateCreated() +
                " Last modified - " + journalEntry.getDateModified();
        date.setText(dateString);

        newView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                entryClickListener.onEntryClick(journalEntry);
            }
        });

        return newView;
    }

    public interface EntryClickListener {
         void onEntryClick (JournalEntry clickedEntry);
    }
}
