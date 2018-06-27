package com.example.android.mydiary.journal;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.mydiary.R;
import com.example.android.mydiary.data.JournalEntry;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.Query;

/**
 * A simple {@link Fragment} subclass.
 */
public class JournalFragment extends Fragment implements JournalViewContract {

    private JournalPresenterContract mPresenterContract;

    private FirebaseRecyclerAdapter mAdapter;

    private RecyclerView mRecyclerView;

    public JournalFragment() {
        // Required empty public constructor
    }

    public static JournalFragment newInstance() {
        return new JournalFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_journal, container, false);

        mRecyclerView = rootView.findViewById(R.id.recycler_view_journal);
        LinearLayoutManager layoutManager =
                new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(mAdapter);

        return rootView;
    }

    @Override
    public void showJournalEntries(Query query) {
        
        FirebaseRecyclerOptions<JournalEntry> options =
                new FirebaseRecyclerOptions.Builder<JournalEntry>()
                        .setQuery(query, JournalEntry.class)
                        .build();

        mAdapter = new FirebaseRecyclerAdapter<JournalEntry, JournalViewHolder>(options) {
            @Override
            public JournalViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                // Create a new instance of the ViewHolder
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_journal_entry, parent, false);

                return new JournalViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(JournalViewHolder holder, int position, JournalEntry entry) {
                // Bind the JournalEntry object to the JournalViewHolder
                holder.titleTextView.setText(entry.getTitle());
//                TODO convert date created from long to string
//                holder.dateCreatedView.setText(entry.getDateCreated());
                holder.bodyTextView.setText(entry.getBody());
            }
        };


    }

    @Override
    public void showEditEntry() {

    }

    @Override
    public void showAddEntry() {

    }

    @Override
    public void setPresenter(JournalPresenter presenter) {
        mPresenterContract = presenter;
    }

    class JournalViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView titleTextView;
        TextView dateCreatedView;
        TextView bodyTextView;

        JournalViewHolder(View itemView) {
            super(itemView);

            titleTextView = itemView.findViewById(R.id.text_title);
            dateCreatedView = itemView.findViewById(R.id.text_date_created);
            bodyTextView = itemView.findViewById(R.id.text_body);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            //TODO onclick journal entry functionality

        }
    }
}

