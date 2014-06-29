package com.alterego.lifesumtest.app.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListAdapter;

import com.alterego.lifesumtest.app.MainApplication;
import com.alterego.lifesumtest.app.OnFragmentInteractionListener;
import com.alterego.lifesumtest.app.R;
import com.alterego.lifesumtest.app.SettingsManager;
import com.alterego.lifesumtest.app.data.LifesumItem;
import com.alterego.lifesumtest.app.data.LifesumResponse;

import java.util.ArrayList;
import java.util.List;

public class SearchResultFragment extends Fragment implements AbsListView.OnItemClickListener, AdapterView.OnItemLongClickListener {

    private static final String LIFESUM_SEARCH_RESULT = "lifesum_search_result";

    private SettingsManager mSettingsManager;
    private OnFragmentInteractionListener mListener;
    private List<LifesumItem> mLifesumItems = new ArrayList<LifesumItem>();

    private AbsListView mListView;
    private ListAdapter mAdapter;



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

        mListView.setOnItemClickListener(this);
        mListView.setOnItemLongClickListener(this);

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


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (null != mListener) {
            //TODO open details fragment
            // Notify the active callbacks interface (the activity, if the
            // fragment is attached to one) that an item has been selected.
            //mListener.onFragmentInteraction(DummyContent.ITEMS.get(position).id);
        }
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        //TODO select item to save!
        return false;
    }
}
