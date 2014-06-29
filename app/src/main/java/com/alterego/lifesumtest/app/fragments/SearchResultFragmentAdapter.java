package com.alterego.lifesumtest.app.fragments;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.alterego.lifesumtest.LifesumItem;
import com.alterego.lifesumtest.app.R;

import java.util.List;

public class SearchResultFragmentAdapter extends ArrayAdapter<LifesumItem> {

    private final List<LifesumItem> mLifesumItems;
    private final Activity mContext;

    public SearchResultFragmentAdapter(Activity context, int resource, List<LifesumItem> items) {
        super(context, resource);
        mContext = context;
        mLifesumItems = items;
    }

    static class ViewHolder {
        protected TextView title;
        protected TextView brand;
    }

    @Override
    public int getCount() {
        return mLifesumItems != null ? mLifesumItems.size() : 0;
    }

    @Override
    public boolean isEmpty() {
        return (mLifesumItems == null || mLifesumItems.size() == 0);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;

        if (convertView == null) {
            LayoutInflater inflater = mContext.getLayoutInflater();
            view = inflater.inflate(R.layout.fragment_searchresult_listitem, null);

            final ViewHolder viewHolder = new ViewHolder();
            viewHolder.title = (TextView) view.findViewById(R.id.ItemTitle);
            viewHolder.brand = (TextView) view.findViewById(R.id.ItemBrand);

            view.setTag(viewHolder);
        } else {
            view = convertView;
        }

        ViewHolder holder = (ViewHolder) view.getTag();
        holder.title.setText(mLifesumItems.get(position).getTitle());
        holder.brand.setText(mLifesumItems.get(position).getBrand());
        return view;
    }


}
