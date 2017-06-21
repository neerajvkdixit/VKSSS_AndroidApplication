package com.vksss.org.adapters;

import java.util.ArrayList;

import com.vksss.org.R;

import android.app.Activity;
import android.content.ClipData.Item;
import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CustumAdapter extends BaseAdapter {
    private Activity context;
    private ArrayList<ListModel> data;
    private static LayoutInflater inflater=null;
    int i=0;
    public CustumAdapter(Activity a,ArrayList<ListModel> data) {
            context = a;
            this.data = data;
             inflater = ( LayoutInflater )context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);       
    }
 
    public int getCount() {
         
        if(data.size()<=0)
            return 1;
        return data.size();
    }
 
    public Object getItem(int position) {
        return position;
    }
 
    public long getItemId(int position) {
        return position;
    }
    
    public static class ViewHolder{
        
        public TextView text;
        public ImageView image;
 
    }
    
    public View getView(int position, View convertView, ViewGroup parent) {
        
    	

        View v = convertView;
        ViewHolder holder = null;
        if (v == null) {
            v = inflater.inflate(R.layout.listviewnav, null);
            holder = new ViewHolder();
            holder.text = (TextView) v.findViewById(R.id.title);
            holder.image=(ImageView)v.findViewById(R.id.imageIcon);
            v.setTag(holder);
        }
        holder = (ViewHolder) v.getTag();
        
        
        if(data.size()<=0){
            holder.text.setText("No Data");
        }else{
            /***** Get each Model object from Arraylist ********/
        	ListModel tempValues=null;
            tempValues = ( ListModel ) data.get( position );
            holder.text.setText( tempValues.getTitle() );
            holder.image.setImageResource(tempValues.getIcon());
              
             /******** Set Item Click Listner for LayoutInflater for each row *******/

            // vi.setOnClickListener(new OnItemClickListener( position ));
        }
        
//        TextView tv = (TextView) v.findViewById(R.id.title);
//        ImageView iv = (ImageView) v.findViewById(R.id.icon);
//
//        ListModel tempListModel = data.get(position);
//        
//        tv.setText(tempListModel.getTitle());
//        iv.setImageResource(tempListModel.getIcon());

        return v;
    }
     
     
}
