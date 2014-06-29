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

import com.alterego.lifesumtest.LifesumItem;
import com.alterego.lifesumtest.app.MainApplication;
import com.alterego.lifesumtest.app.OnFragmentInteractionListener;
import com.alterego.lifesumtest.app.R;
import com.alterego.lifesumtest.app.SettingsManager;
import com.alterego.lifesumtest.app.data.LifesumResponse;

import java.util.ArrayList;
import java.util.List;

public class SearchResultFragment extends Fragment
        //implements AbsListView.OnItemClickListener, AdapterView.OnItemLongClickListener
{

    private static final String LIFESUM_SEARCH_RESULT = "lifesum_search_result";

    private SettingsManager mSettingsManager;
    private OnFragmentInteractionListener mListener;
    private List<LifesumItem> mLifesumItems = new ArrayList<LifesumItem>();

    private AbsListView mListView;
    private SearchResultFragmentAdapter mAdapter;
    private android.view.ActionMode mActionMode;


    public static SearchResultFragment newInstance(String search_result) {
        SearchResultFragment fragment = new SearchResultFragment();
        Bundle args = new Bundle();
        args.putString(LIFESUM_SEARCH_RESULT, search_result);
        fragment.setArguments(args);
        return fragment;
    }

    public SearchResultFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mSettingsManager = MainApplication.getMainApplication().getSettingsManager();
        mSettingsManager.getLogger().info("SearchResultFragment onCreate");

        if (getArguments() != null) {
            String searchResultJSON = getArguments().getString(LIFESUM_SEARCH_RESULT);
            LifesumResponse searchResultObject = mSettingsManager.getGson().fromJson(searchResultJSON, LifesumResponse.class);
            mLifesumItems = searchResultObject.getResponse().getList();
        }

        mAdapter = new SearchResultFragmentAdapter(mSettingsManager.getParentActivity(), R.layout.fragment_searchresult_listitem, mLifesumItems);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_lifesumitem, container, false);

        mListView = (AbsListView) view.findViewById(android.R.id.list);
        ((AdapterView<ListAdapter>) mListView).setAdapter(mAdapter);

        //mListView.setOnItemClickListener(this);
        //mListView.setOnItemLongClickListener(this);
        mListView.setChoiceMode(AbsListView.CHOICE_MODE_MULTIPLE_MODAL);
        mListView.setMultiChoiceModeListener(mListViewMultiChoiceModeListener);

        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
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


//    @Override
//    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//        if (null != mListener) {
//            //TODO open details fragment
//            // Notify the active callbacks interface (the activity, if the
//            // fragment is attached to one) that an item has been selected.
//            //mListener.onFragmentInteraction(DummyContent.ITEMS.get(position).id);
//        }
//    }

//    @Override
//    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
//        //TODO select item to save!
//        if (mActionMode != null) {
//            return false;
//        }
//
//        mActionMode = getActivity().startActionMode(mActionModeCallback);
//        view.setSelected(true);
//        return true;
//    }

//    private ActionMode.Callback mActionModeCallback = new ActionMode.Callback() {
//
//        @Override
//        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
//            // Inflate a menu resource providing context menu items
//            MenuInflater inflater = mode.getMenuInflater();
//            inflater.inflate(R.menu.save, menu);
//            return true;
//        }
//
//        @Override
//        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
//            return false; // Return false if nothing is done
//        }
//
//        @Override
//        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
//            switch (item.getItemId()) {
//                case R.id.action_save:
//                    saveSelectedItems();
//                    mode.finish(); // Action picked, so close the CAB
//                    return true;
//                default:
//                    return false;
//            }
//        }
//
//        // Called when the user exits the action mode
//        @Override
//        public void onDestroyActionMode(ActionMode mode) {
//            mActionMode = null;
//        }
//    };

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
                case R.id.action_save:
                    saveSelectedItems();
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
            inflater.inflate(R.menu.save, menu);
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

    private void saveSelectedItems() {
        //TODO
        mSettingsManager.getLogger().info("SearchResultFragment saveSelectedItems, adapter size = " + mAdapter.getCount());
        SparseBooleanArray checkedArray = mListView.getCheckedItemPositions();
        ArrayList<LifesumItem> selectedItems = new ArrayList<LifesumItem>();
        for (int i = 0; i < checkedArray.size(); i++) {
            // Item position in adapter
            int position = checkedArray.keyAt(i);
            // Add sport if it is checked i.e.) == TRUE!
            if (checkedArray.valueAt(i))
                selectedItems.add(mAdapter.getItem(position));
        }

        mSettingsManager.getLogger().info("SearchResultFragment saveSelectedItems size = " + selectedItems.size());

    }
}
