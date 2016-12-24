package com.example.android.popularmovies;

import android.graphics.Typeface;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.bumptech.glide.Glide;

import com.example.android.popularmovies.async.MovieApiTask;
import com.example.android.popularmovies.data.Movie;

import java.util.List;

public class DetailActivity extends AppCompatActivity {

    private Movie mMovie;
    private boolean mIsInitialized;

    @BindString(R.string.backdrop_root)
    String BACKDROP_ROOT;

    @BindString(R.string.poster_root)
    String POSTER_ROOT;

    @BindView(R.id.detail_backdrop)
    ImageView backdrop;

    @BindView(R.id.detail_poster)
    ImageView poster;

    @BindView(R.id.detail_title)
    TextView title;

    @BindView(R.id.detail_tagline)
    TextView tagline;

    @BindView(R.id.detail_runtime)
    TextView runtime;

    @BindView(R.id.detail_rating)
    TextView rating;

    @BindView(R.id.detail_budget)
    TextView budget;

    @BindView(R.id.detail_revenue)
    TextView revenue;

    @BindView(R.id.detail_synopsis)
    TextView synopsis;

    @BindView(R.id.detail_genres)
    TextView genres;

    @BindView(R.id.detail_reviews)
    RecyclerView reviews;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_detail);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if(savedInstanceState != null) {

            mMovie = savedInstanceState.getParcelable(Movie.PARAM_MOVIE_PARCEL);

            mIsInitialized = true;

        } else {

            mMovie = getIntent().getExtras().getParcelable(Movie.PARAM_MOVIE_PARCEL);

            mIsInitialized = false;
        }

        bindMovie();
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {

        outState.putParcelable(Movie.PARAM_MOVIE_PARCEL, mMovie);

        super.onSaveInstanceState(outState);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId() == android.R.id.home) {

            super.onBackPressed();

            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    private void bindMovie() {

        ButterKnife.bind(this);

        Glide.with(this)
                .load(BACKDROP_ROOT + mMovie.getBackdropPath())
                .centerCrop()
                .into(backdrop);

        Glide.with(this)
                .load(POSTER_ROOT + mMovie.getPosterPath())
                .fitCenter()
                .into(poster);

        title.setText(String.format("%s (%d)", mMovie.getTitle(), mMovie.getYear()));

        genres.setText(buildLabel("Genres", mMovie.getGenres()));
        synopsis.setText(buildLabel("Synopsis", mMovie.getSynopsis()));
        rating.setText(buildLabel("Rating", String.format("%.1f (%d votes)",
                mMovie.getRating(), mMovie.getNumVotes())));

        if(mIsInitialized) {

            tagline.setText(mMovie.getTagline());

            runtime.setText(buildLabel("Runtime", mMovie.getRuntime() + " min"));
            budget.setText(buildLabel("Budget", format$(mMovie.getBudget())));
            revenue.setText(buildLabel("Revenue", format$(mMovie.getRevenue())));

            bindReviews();

            // TODO: link to IMDB + list trailers

        } else {

            fetchMovie();
        }
    }


    private SpannableStringBuilder buildLabel(String label, String value) {

        SpannableStringBuilder builder =
                new SpannableStringBuilder(label + " " + value);

        builder.setSpan(new StyleSpan(Typeface.BOLD),
                0, label.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        return builder;
    }


    private String format$(int value) {

        return "$" + String.format("%,d", value);
    }


    private void fetchMovie() {

        MovieApiTask task = new MovieApiTask(Integer.toString(mMovie.getId())) {

            @Override
            protected void onPostExecute(List<Movie> movies) {

                mMovie.mergeDetails(movies.get(0));

                mIsInitialized = true;

                bindMovie();
            }
        };
    }

    private void bindReviews() {

        final List<String> list = mMovie.getReviews();

        reviews.setLayoutManager(new LinearLayoutManager(this));
        reviews.setItemAnimator(new DefaultItemAnimator());

        reviews.setAdapter(new RecyclerView.Adapter<ReviewHolder>() {

            @Override
            public ReviewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

                return new ReviewHolder(LayoutInflater.from(DetailActivity.this)
                        .inflate(android.R.layout.simple_list_item_1, parent, false));
            }

            @Override
            public void onBindViewHolder(ReviewHolder holder, int position) {

                holder.text.setText(list.get(position));
            }

            @Override
            public int getItemCount() {

                return list.size();
            }
        });
    }

    public class ReviewHolder extends RecyclerView.ViewHolder {

        TextView text;

        public ReviewHolder(View itemView) {

            super(itemView);

            text = (TextView) itemView;

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                text.setTextAppearance(R.style.detail_normal_text);
            }
        }
    }
}
