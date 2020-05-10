package com.SevenDigITs.Solutions.justTap.listview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import com.SevenDigITs.Solutions.justTap.R;


import java.util.List;

public class UserMiniCardAdapter extends RecyclerView.Adapter<UserMiniCardAdapter.MyViewHolder> {
    private Context mContext;
    private List<UserMiniCardClass> mainList;
    private UserMiniCardAdapter.onItemClickListener mListner;

    /**
     * this interface for calling in main activity to do on click
     */
    public interface onItemClickListener {
        void onItemClick(View view, int position);
    }

    /**
     * this constractor define on click item
     */
    public void setOnItemClickListener(UserMiniCardAdapter.onItemClickListener listener) {
        this.mListner = listener;

    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public Button button;

        public MyViewHolder(final View view, final UserMiniCardAdapter.onItemClickListener listener) {
            super(view);
            title = (TextView) view.findViewById(R.id.txt_news);
            button = (Button) view.findViewById(R.id.bttn_news);

            /** this on click for every card in list */
            button.setOnClickListener(new View.OnClickListener() {
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



    public UserMiniCardAdapter(Context mContext, List<UserMiniCardClass> List) {
        this.mContext = mContext;
        this.mainList = List;

    }

    @Override
    public UserMiniCardAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_news_paper_design, parent, false);
/** we add in this return mlistner  */
        return new UserMiniCardAdapter.MyViewHolder(itemView, mListner);
    }

    @Override
    public void onBindViewHolder(UserMiniCardAdapter.MyViewHolder holder, int position) {
        UserMiniCardClass userMiniCardClass = mainList.get(position);
        holder.title.setText(userMiniCardClass.getTitle());



    }

    @Override
    public int getItemCount() {
        return mainList.size();
    }
}
