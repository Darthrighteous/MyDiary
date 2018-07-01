package com.example.android.mydiary.addentry;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.example.android.mydiary.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddEntryFragment extends Fragment implements AddEntryContract.View {
    private AddEntryContract.Presenter mPresenter;

    private EditText mEditTextTitle;
    private EditText mEditTextBody;

    public AddEntryFragment() {
        // Required empty public constructor
    }

    //return new instance of fragment
    public static AddEntryFragment newInstance() {
         return new AddEntryFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_entry_detail, container, false);

        //set the edit text variables
        mEditTextTitle = rootView.findViewById(R.id.edit_add_entry_title);
        mEditTextBody = rootView.findViewById(R.id.edit_add_entry_body);

        //Set focus to edit text and open key board
        mEditTextTitle.requestFocus();
        try {
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(mEditTextTitle, InputMethodManager.SHOW_IMPLICIT);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }


        mPresenter.start();
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        try{
            FloatingActionButton fab = getActivity().findViewById(R.id.fab_add_entry_done);
            fab.setImageResource(R.drawable.ic_fab_done);
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String title = mEditTextTitle.getText().toString();
                    String body = mEditTextBody.getText().toString();


                    mPresenter.saveEntry(title, body);
                }
            });
        } catch (NullPointerException e){
            e.printStackTrace();
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
    }

    @Override
    public void setPresenter(AddEntryContract.Presenter presenter) {
        mPresenter = presenter;

    }

    @Override
    public void setTitle(String title) {
        mEditTextTitle.setText(title);
    }

    @Override
    public void setBody(String body) {
        mEditTextBody.setText(body);
    }

    @Override
    public void showExistingEntry(String title, String body) {
        mEditTextTitle.setText(title);
        mEditTextBody.setText(body);
    }

    @Override
    public void showEmptyEntryError() {
        Snackbar.make(mEditTextBody, getString(R.string.msg_entry_message), Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void finishActivity() {
        try{
            getActivity().setResult(Activity.RESULT_OK);
        } catch (NullPointerException e){
            e.printStackTrace();
        }

        getActivity().finish();
    }
}
