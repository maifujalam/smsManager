package com.example.messagemanager;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import static com.example.messagemanager.MessageManager.TAG;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder>
{
    public ArrayList<item_Adapter> alist;
    OnSMSOpener onSMSOpener;
    public ListAdapter(ArrayList<item_Adapter> alist,OnSMSOpener onSMSOpener)
    {
        this.alist=alist;
        this.onSMSOpener=onSMSOpener;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v=LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_layout,null);
        return new ViewHolder(v,onSMSOpener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Log.d(TAG, "onBindViewHolder: Now Binding");
        viewHolder.tv1.setText(alist.get(i).getTitle1());
        viewHolder.tv2.setText(alist.get(i).getBody1());
        viewHolder.tv3.setText(alist.get(i).getTime1());
        viewHolder.iv.setImageResource(alist.get(i).getImg());
        if(alist.get(i).getTitle1().equals("+919554478794")) {
            viewHolder.itemView.setVisibility(View.GONE);
            //itemView.setLayoutParams(new RecyclerView.LayoutParams(0, 0));
            viewHolder.itemView.setLayoutParams(new RecyclerView.LayoutParams(0,0));
        }

    }
    @Override
    public int getItemCount() {
        return alist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder  implements View.OnClickListener{
        TextView tv1,tv2,tv3;
        ImageView iv;
        public OnSMSOpener onSMSOpener;
        public ViewHolder(@NonNull View itemView,OnSMSOpener onSMSOpener) {
            super(itemView);
            tv1=itemView.findViewById(R.id.title1);
            tv2=itemView.findViewById(R.id.message1);
            tv3=itemView.findViewById(R.id.time1);
            iv=itemView.findViewById(R.id.imageView);
            this.onSMSOpener=onSMSOpener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onSMSOpener.onSMSClick(getAdapterPosition());
        }
    }
    public interface OnSMSOpener
    {
        void onSMSClick(int position);
    }
}
