package com.SevenDigITs.Solutions.justTap.listview;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.SevenDigITs.Solutions.justTap.R;
import com.bumptech.glide.Glide;

import java.util.List;

//import com.bumptech.glide.Glide;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.MyViewHolder> {
    private Context mContext;
    private List<MainLancherClass> mainList;
    private onItemClickListener mListner;

    /**
     * this interface for calling in main activity to do on click
     */
    public interface onItemClickListener {
        void onItemClick(View view, int position);
    }

    /**
     * this constractor define on click item
     */
    public void setOnItemClickListener(onItemClickListener listener) {
        this.mListner = listener;

    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, content;
        public ImageView icon;
        public CardView cardView;

        public MyViewHolder(final View view, final onItemClickListener listener) {
            super(view);
            title = (TextView) view.findViewById(R.id.main_title);
            content = (TextView) view.findViewById(R.id.main_content);
            icon = (ImageView) view.findViewById(R.id.main_icon);
            cardView = (CardView) view.findViewById(R.id.main_card_view);

            /** this on click for every card in list */
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    // Toast.makeText(itemView.getContext(), "Position:" + Integer.toString(getPosition()), Toast.LENGTH_SHORT).show();
                    if (listener != null) {
                        listener.onItemClick(v, getAdapterPosition());
                    }
                }
            });
        }
    }

    public MainAdapter(Context mContext, List<MainLancherClass> List) {
        this.mContext = mContext;
        this.mainList = List;
    }

    @Override
    public MainAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.main_luncher_design, parent, false);
/** we add in this return mlistner  */
        return new MyViewHolder(itemView, mListner);
    }

    @Override
    public void onBindViewHolder(MainAdapter.MyViewHolder holder, int position) {
        MainLancherClass mainLancherClass = mainList.get(position);
        holder.title.setText(mainLancherClass.getTitle());
        holder.content.setText(mainLancherClass.getContent());

        // loading album cover using Glide library
        Glide.with(mContext).load(mainLancherClass.getImage()).into(holder.icon);


    }

    @Override
    public int getItemCount() {
        return mainList.size();
    }
}
