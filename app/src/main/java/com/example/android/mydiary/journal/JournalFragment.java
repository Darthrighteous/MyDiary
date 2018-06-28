package com.example.android.mydiary.journal;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.example.android.mydiary.R;
import com.example.android.mydiary.addentry.AddEntryActivity;
import com.example.android.mydiary.data.JournalEntry;
import com.firebase.ui.auth.AuthUI;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class JournalFragment extends Fragment implements JournalContract.View {

    private JournalContract.Presenter mPresenter;

    private JournalEntryAdapter mAdapter;


    public JournalFragment() {
        // Required empty public constructor
    }

    public static JournalFragment newInstance() {
        return new JournalFragment();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        mPresenter.result();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mAdapter = new JournalEntryAdapter(getContext(),
                R.layout.item_journal_entry,
                new ArrayList<JournalEntry>(0));

        mPresenter.start();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_journal, container, false);

        //listview setup
        ListView journalList = rootView.findViewById(R.id.list_view_journal);
        journalList.setAdapter(mAdapter);

        //fab setup
        FloatingActionButton fab = getActivity().findViewById(R.id.fab_open_new_entry);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.addNewEntry();
            }
        });

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
    }

    @Override
    public void onPause() {
        super.onPause();
        mPresenter.detachDatabaseReadListener();
        mAdapter.clear();
    }

    @Override
    public void displayUserInfo(String user, String emailAddress) {
        NavigationView nav = getActivity().findViewById(R.id.nav_view);
        TextView username = nav.getHeaderView(0).findViewById(R.id.text_username);
        username.setText(user);
        TextView email = nav.getHeaderView(0).findViewById(R.id.text_email_address);
        email.setText(emailAddress);
    }

    @Override
    public void showSignInFlow(List<AuthUI.IdpConfig> providers) {
        startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setIsSmartLockEnabled(false)
                        .setAvailableProviders(providers)
                        .build(),
                JournalContract.RC_SIGN_IN);
    }

    @Override
    public void clearAdapter() {
        mAdapter.clear();
    }

    @Override
    public void addEntry(JournalEntry journalEntry) {
        mAdapter.add(journalEntry);
    }

    @Override
    public void showJournalEntries() {


    }

    @Override
    public void showEditEntry() {

    }

    @Override
    public void showAddEntry() {
        Intent addEntryIntent = new Intent(getContext(), AddEntryActivity.class);
        startActivityForResult(addEntryIntent, AddEntryActivity.REQUEST_ADD_ENTRY);
    }

    @Override
    public void setPresenter(JournalContract.Presenter presenter) {
        mPresenter = presenter;
    }

}

