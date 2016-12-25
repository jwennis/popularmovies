package com.example.android.popularmovies.ui;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.popularmovies.R;

import java.util.List;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ReviewHolder> {

    private List<String> mItems;

    public ReviewAdapter(List<String> list) {

        mItems = list;
    }

    @Override
    public ReviewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new ReviewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_review, parent, false));
    }

    @Override
    public void onBindViewHolder(ReviewHolder holder, int position) {

        holder.review_text.setText(mItems.get(position));
        holder.divider.setVisibility(position > 0 ? View.VISIBLE : View.GONE);
    }

    @Override
    public int getItemCount() {

        return mItems.size();
    }

    public class ReviewHolder extends RecyclerView.ViewHolder{

        TextView review_text;
        View divider;

        public ReviewHolder(View itemView) {

            super(itemView);

            review_text = (TextView) itemView.findViewById(R.id.review_text);
            divider = itemView.findViewById(R.id.divider);
        }
    }
}
