package com.weload.driverapplication.JobDescription.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.weload.driverapplication.JobDescription.model.JobDescriptionRespons;
import com.weload.driverapplication.R;

import java.util.HashMap;
import java.util.List;

public class ExpandableListAdapter extends BaseExpandableListAdapter {
    private Context context;
    private List<String> _listDataHeader;
    private HashMap<String,List<JobDescriptionRespons.Data.Product.Option>> _listDataChild;

    public ExpandableListAdapter(Context context, List<String> _listDataHeader, HashMap<String, List<JobDescriptionRespons.Data.Product.Option>> _listDataChild) {
        this.context = context;
        this._listDataHeader = _listDataHeader;
        this._listDataChild = _listDataChild;
    }

    @Override
    public int getGroupCount() {
        return this._listDataHeader.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return this._listDataChild.get(this._listDataHeader.get(groupPosition)).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this._listDataHeader.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return this._listDataChild.get(this._listDataHeader.get(groupPosition)).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        String headerTitle= (String) getGroup(groupPosition);
        if(convertView==null){
            LayoutInflater infaInflator = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView= infaInflator.inflate(R.layout.list_group,null);

        }
        TextView lblListHeader = convertView.findViewById(R.id.listTitle);
        lblListHeader.setTypeface(null, Typeface.BOLD);
        lblListHeader.setText(Html.fromHtml(headerTitle));
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
     //   final  String childText= (String) getChild(groupPosition,childPosition);
        final JobDescriptionRespons.Data.Product.Option option= (JobDescriptionRespons.Data.Product.Option) getChild(groupPosition,childPosition);
        if(convertView==null){
            LayoutInflater infaInflater= (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infaInflater.inflate(R.layout.list_item,null);
        }
        TextView txtListChild=convertView.findViewById(R.id.tvName);
        txtListChild.setText(Html.fromHtml(option.name+":"+option.value));
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
