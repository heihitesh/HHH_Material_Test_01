package Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.itshiteshverma.hhh_material_test_01.R;
import com.itshiteshverma.hhh_material_test_01.extras.Movie;
import com.itshiteshverma.hhh_material_test_01.fragments.Constant;
import com.itshiteshverma.hhh_material_test_01.network.VolleySingleton;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


/**
 * Created by Nilesh Verma on 9/11/2015.
 */
public class AdpterBoxOffice extends RecyclerView.Adapter<AdpterBoxOffice.viewHolderBoxOffice>{

    private ArrayList<Movie> listMovies = new ArrayList<>();
    private LayoutInflater layoutInflater;

    //for image
    private VolleySingleton volleySingleton;
    private ImageLoader imageLoader;
    private DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");

    public AdpterBoxOffice(Context context){
        layoutInflater = LayoutInflater.from(context);
        volleySingleton = VolleySingleton.getsInstance();
        imageLoader = volleySingleton.getImageLoader();

    }

    public void setMoviesList(ArrayList<Movie> listMovies){
        this.listMovies = listMovies;
        notifyItemRangeChanged(0 , listMovies.size()); //to notify if the change is  happen or not


    }
    @Override
    public viewHolderBoxOffice onCreateViewHolder(ViewGroup parent, int viewType) {
       View view =  layoutInflater.inflate(R.layout.custom_movie_option, parent, false);
        viewHolderBoxOffice viewHolderBoxOffice = new viewHolderBoxOffice(view);
        return viewHolderBoxOffice;
    }

    @Override
    public void onBindViewHolder( viewHolderBoxOffice holder, int position) {
        Movie currentMovie = listMovies.get(position);
        holder.Title.setText(currentMovie.getTitle());
            Date movieReleaseDate = currentMovie.getDate();
        if(movieReleaseDate != null){
            String formattedDate = dateFormatter.format(movieReleaseDate);
            holder.Date.setText(formattedDate);


        }else {
            holder.Date.setText(Constant.NA);

        }
        int score = currentMovie.getVote();
        if(score==-1){
            holder.Rating.setRating(0.0F);
            holder.Rating.setAlpha(0.5F);  //only half visible
        }else {
            holder.Rating.setRating(currentMovie.getVote() / 2.0F);

        }
        String Poster = currentMovie.geturlPoster();
        loadImage(Poster ,holder);



    }

    private void loadImage(String poster, final viewHolderBoxOffice holder) {
        if(!poster.equals(Constant.NA)){
            imageLoader.get(poster, new ImageLoader.ImageListener() {
                @Override
                public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
                    holder.Poster.setImageBitmap(response.getBitmap());
                }

                @Override
                public void onErrorResponse(VolleyError error) {

                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return listMovies.size();
    }

    static class viewHolderBoxOffice extends RecyclerView.ViewHolder{

        private ImageView Poster;
        private TextView Title;
        private TextView Date;
        private RatingBar Rating;


        public viewHolderBoxOffice(View itemView) {
            super(itemView);
            Poster=(ImageView) itemView.findViewById(R.id.moviePoster);
            Title = (TextView) itemView.findViewById(R.id.title);
            Date = (TextView) itemView.findViewById(R.id.date);
            Rating = (RatingBar) itemView.findViewById(R.id.ratingBar);
        }
    }
}
