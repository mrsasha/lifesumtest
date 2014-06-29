package com.alterego.lifesumtest.app.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.SparseBooleanArray;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.Toast;

import com.alterego.lifesumtest.LifesumItem;
import com.alterego.lifesumtest.app.MainApplication;
import com.alterego.lifesumtest.app.OnFragmentInteractionListener;
import com.alterego.lifesumtest.app.R;
import com.alterego.lifesumtest.app.SettingsManager;

import java.util.ArrayList;
import java.util.List;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class SavedItemsFragment extends Fragment {

    private static final String FRAGMENT_TITLE = "Saved items";

    private SettingsManager mSettingsManager;
    private OnFragmentInteractionListener mListener;
    private List<LifesumItem> mLifesumItems = new ArrayList<LifesumItem>();

    private AbsListView mListView;
    private LifesumItemListAdapter mAdapter;
    private ActionMode mActionMode;


    public static SavedItemsFragment newInstance() {
        SavedItemsFragment fragment = new SavedItemsFragment();
        return fragment;
    }

    public SavedItemsFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mSettingsManager = MainApplication.getMainApplication().getSettingsManager();
        mSettingsManager.getLogger().info("SearchResultFragment onCreate");

        mLifesumItems = mSettingsManager.getLifesumItemDao().loadAll();
        mAdapter = new LifesumItemListAdapter(mSettingsManager.getParentActivity(), R.layout.fragment_searchresult_listitem, mLifesumItems);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_lifesumitem, container, false);

        mListView = (AbsListView) view.findViewById(android.R.id.list);
        ((AdapterView<ListAdapter>) mListView).setAdapter(mAdapter);

        mListView.setChoiceMode(AbsListView.CHOICE_MODE_MULTIPLE_MODAL);
        mListView.setMultiChoiceModeListener(mListViewMultiChoiceModeListener);

        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
            mListener.setActionBarTitle(FRAGMENT_TITLE);
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    private AbsListView.MultiChoiceModeListener mListViewMultiChoiceModeListener = new AbsListView.MultiChoiceModeListener() {

        @Override
        public void onItemCheckedStateChanged(ActionMode mode, int position,
        long id, boolean checked) {
            // Here you can do something when items are selected/de-selected,
            // such as update the title in the CAB
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            // Respond to clicks on the actions in the CAB
            switch (item.getItemId()) {
                case R.id.action_delete:
                    deleteSelectedItems();
                    mode.finish(); // Action picked, so close the CAB
                    return true;
                default:
                    return false;
            }
        }

        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            // Inflate the menu for the CAB
            MenuInflater inflater = mode.getMenuInflater();
            inflater.inflate(R.menu.delete, menu);
            return true;
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {
            // Here you can make any necessary updates to the activity when
            // the CAB is removed. By default, selected items are deselected/unchecked.
            mActionMode = null;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            // Here you can perform updates to the CAB due to
            // an invalidate() request
            return false;
        }
    };

    private void deleteSelectedItems() {

        mSettingsManager.getLogger().info("SearchResultFragment deleteSelectedItems, adapter size = " + mAdapter.getCount());
        SparseBooleanArray checkedArray = mListView.getCheckedItemPositions();
        ArrayList<LifesumItem> selectedItems = new ArrayList<LifesumItem>();
        for (int i = 0; i < checkedArray.size(); i++) {
            // Item position in adapter
            int position = checkedArray.keyAt(i);
            // Add sport if it is checked i.e.) == TRUE!
            if (checkedArray.valueAt(i))
                selectedItems.add(mAdapter.getItem(position));
        }

        mSettingsManager.deleteLifesumItems(selectedItems).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(new Observer<Boolean>() {
            @Override
            public void onCompleted() {
                //do nothing
            }

            @Override
            public void onError(Throwable e) {
                Toast.makeText(mSettingsManager.getParentActivity(), "Problem deleting items!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNext(Boolean aBoolean) {
                Toast.makeText(mSettingsManager.getParentActivity(), "Deleting items successful!", Toast.LENGTH_SHORT).show();
                refreshAdapter();
            }
        });
        mSettingsManager.getLogger().info("SearchResultFragment deleteSelectedItems size = " + selectedItems.size());

    }

    private void refreshAdapter() {
        mLifesumItems = mSettingsManager.getLifesumItemDao().loadAll();
        mAdapter = new LifesumItemListAdapter(mSettingsManager.getParentActivity(), R.layout.fragment_searchresult_listitem, mLifesumItems);
        ((AdapterView<ListAdapter>) mListView).setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
    }
}
