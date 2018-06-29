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


    public JournalEntryAdapter(@NonNull Context context, int resource, @NonNull List<JournalEntry> objects) {
        super(context, resource, objects);
        setList(objects);

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
        if (convertView == null) {
            convertView = ((Activity) getContext()).getLayoutInflater()
                    .inflate(R.layout.item_journal_entry, parent, false);
        }
        TextView title = convertView.findViewById(R.id.text_title);
        TextView body = convertView.findViewById(R.id.text_body);

        JournalEntry journalEntry = getItem(position);

        title.setText(journalEntry.getTitle());
        body.setText(journalEntry.getBody());

        return convertView;
    }
}
