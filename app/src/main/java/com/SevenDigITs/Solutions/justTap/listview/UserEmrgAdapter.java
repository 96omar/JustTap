package com.SevenDigITs.Solutions.justTap.listview;
import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.SevenDigITs.Solutions.justTap.R;
import com.bumptech.glide.Glide;

import java.util.List;

//import com.bumptech.glide.Glide;

public class UserEmrgAdapter extends RecyclerView.Adapter<UserEmrgAdapter.MyViewHolder> {
    private Context mContext;
    private List<UserEmrgClass> mainList;
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
        public Button button;

        public MyViewHolder(final View view, final onItemClickListener listener) {
            super(view);
            title = (TextView) view.findViewById(R.id.txt_emr);
            content = (TextView) view.findViewById(R.id.txt_emr_num);
            button = (Button) view.findViewById(R.id.bttn_emr);

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

    public UserEmrgAdapter(Context mContext, List<UserEmrgClass> List) {
        this.mContext = mContext;
        this.mainList = List;
    }

    @Override
    public UserEmrgAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_emargency_design, parent, false);
/** we add in this return mlistner  */
        return new MyViewHolder(itemView, mListner);
    }

    @Override
    public void onBindViewHolder(UserEmrgAdapter.MyViewHolder holder, int position) {
        UserEmrgClass userEmrgClass = mainList.get(position);
        holder.title.setText(userEmrgClass.getTitle());
        holder.content.setText(userEmrgClass.getContent());


    }

    @Override
    public int getItemCount() {
        return mainList.size();
    }
}
