package com.example.android.popularmovies.ui;

import java.util.List;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.popularmovies.R;
import com.example.android.popularmovies.data.Movie;


public class TrailerAdapter extends RecyclerView.Adapter<TrailerAdapter.TrailerHolder> {

    private List<Movie.Trailer> mItems;

    public TrailerAdapter(List<Movie.Trailer> list) {

        mItems = list;
    }

    @Override
    public TrailerHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new TrailerHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_trailer, parent, false));
    }

    @Override
    public void onBindViewHolder(TrailerHolder holder, int position) {

        final Movie.Trailer item = mItems.get(position);

        holder.trailer_title.setText(item.getName());

        holder.layout_root.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                Context context = view.getContext();

                try {

                    context.startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse("vnd.youtube:" + item.getSource())));

                } catch (ActivityNotFoundException ex) {

                    context.startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse(context.getString(R.string.youtube_root) + item.getSource())));
                }
            }
        });
    }

    @Override
    public int getItemCount() {

        return mItems.size();
    }

    public class TrailerHolder extends RecyclerView.ViewHolder{

        ViewGroup layout_root;
        TextView trailer_title;

        public TrailerHolder(View itemView) {

            super(itemView);

            layout_root = (ViewGroup) itemView;
            trailer_title = (TextView) itemView.findViewById(R.id.trailer_title);
        }
    }
}
