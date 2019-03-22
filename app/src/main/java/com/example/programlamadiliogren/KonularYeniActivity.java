
package com.example.programlamadiliogren;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class KonularYeniActivity extends AppCompatActivity {
  /*  ExpandableListView list;
    HashMap<String, List<String>> ülkeler;
    List<String> şehirler;
AdaptörSınıfı adapter;*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_konular_yeni);
       /* list = (ExpandableListView) findViewById(R.id.elist);
        ülkeler=bilgial();
        şehirler=new ArrayList<>(ülkeler.keySet());
    }

    public static HashMap<String, List<String>> bilgial() {
        HashMap<String, List<String>> detay = new HashMap<>();
        List<String> türkiye = new ArrayList<>();
        türkiye.add("Kırşehir");
        türkiye.add("Yozgat");
        List<String> amerika = new ArrayList<>();
        amerika.add("New York");
        detay.put("Türkiye", türkiye);
        detay.put("Amerika",amerika);
        return detay;

    }
}

class AdaptörSınıfı extends BaseExpandableListAdapter {

    Context icerik;
    HashMap<String,List<String>> ülkeDetay;
    List<String> ülkelistesi;

    public AdaptörSınıfı(Context icerik,HashMap ülkeDetay, List ülkelistesi){
        this.icerik=icerik;
        this.ülkeDetay=ülkeDetay;
        this.ülkelistesi=ülkelistesi;
    }



    public int getGroupCount() {
        return ülkelistesi.size();
    }


    public int getChildrenCount(int groupPosition) {
        return ülkeDetay.get(ülkelistesi.get(groupPosition)).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return ülkelistesi.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return ülkeDetay.get(ülkelistesi.get(groupPosition)).get(childPosition);
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
        String parent_text=(String)getGroup(groupPosition);
        if (convertView==null)
        {
            LayoutInflater acici=(LayoutInflater) icerik.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView=acici.inflate(R.layout.layout_parent,parent,false);
        }
        TextView text=(TextView)convertView.findViewById(R.id.tv_parent_name);
        text.setText(parent_text);
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        String parent_text=(String)getChild(groupPosition,childPosition);
        if (convertView==null)
        {
            LayoutInflater acici=(LayoutInflater) icerik.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView=acici.inflate(R.layout.layout_child,parent,false);
        }
        TextView text=(TextView)convertView.findViewById(R.id.tv_chiid_name);
        text.setText(parent_text);
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }*/
}
}
