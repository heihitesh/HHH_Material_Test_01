package com.itshiteshverma.hhh_material_test_01.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.itshiteshverma.hhh_material_test_01.MainActivity;
import com.itshiteshverma.hhh_material_test_01.R;
import com.itshiteshverma.hhh_material_test_01.extras.Movie;
import com.itshiteshverma.hhh_material_test_01.network.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import Adapter.AdpterBoxOffice;

public class FragmentBoxOffice extends Fragment {

    private VolleySingleton volleySingleton;
    private ImageLoader imageLoader;
    private RequestQueue requestQueue;
    private static final String LINKOFTHEMOVIEDB = "http://api.themoviedb.org/3/discover/movie?api_key=";

    //array list to handel the movies
    private ArrayList<Movie> listMovies = new ArrayList<>();

    //this will set the date format for our JSOn to check for the fromat in release date
    private DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");//year-Month(in caps)-day
    //this defines the type of date format we will be using

    //for the recycler View
    private RecyclerView listofHITMOVIES;

    private AdpterBoxOffice adapterBoxOffice;

    private TextView VOlleyErrorMessages;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_fragment_box_office, container, false);
        listofHITMOVIES = (RecyclerView) rootView.findViewById(R.id.listmovies);
        listofHITMOVIES.setLayoutManager(new LinearLayoutManager(getActivity()));
        VOlleyErrorMessages = (TextView) rootView.findViewById(R.id.textforErrorMessages);
        //
        adapterBoxOffice = new AdpterBoxOffice(getActivity());


        sendJSON();

        listofHITMOVIES.setAdapter(adapterBoxOffice);
        return rootView;
    }

    private void sendJSON() {


        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, getTheRequestusingAPI(1)
                , (String) null, new Response.Listener<JSONObject>() { ///very IMPORTANT TO ADD THE (STRING) BEFORE NULL OTHER WISE IT WILL BE AMBIGUOUS
            @Override
            public void onResponse(JSONObject response) {
                listMovies = parseJsonRequest(response);
                //  L.toast(getActivity(), response.toString());
                adapterBoxOffice.setMoviesList(listMovies);
                //hide the error textview if not an error
                VOlleyErrorMessages.setVisibility(View.GONE);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                handleVolleyErrors(error);

            }
        });

        requestQueue.add(request);

    }

    private void handleVolleyErrors(VolleyError error) {

        VOlleyErrorMessages.setVisibility(View.VISIBLE);


        if (error instanceof TimeoutError || error instanceof NoConnectionError) {

            VOlleyErrorMessages.setText(R.string.error_timeout);

        } else if (error instanceof AuthFailureError) {
            VOlleyErrorMessages.setText(R.string.auth_fail);

        } else if (error instanceof ServerError) {
            VOlleyErrorMessages.setText(R.string.server_error);


        } else if (error instanceof NetworkError) {
            VOlleyErrorMessages.setText(R.string.network_error);

        } else if (error instanceof ParseError) {
            VOlleyErrorMessages.setText(R.string.parse_error);

        }


    }

    public static String getTheRequestusingAPI(int pagesize) {
        return LINKOFTHEMOVIEDB + MainActivity.themoviedbAPI + "&" + "page=" + pagesize;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        volleySingleton = VolleySingleton.getsInstance();
        requestQueue = volleySingleton.getmRequestQueue();


    }

    private ArrayList<Movie> parseJsonRequest(JSONObject response) {
        ArrayList<Movie> listMovies = new ArrayList<>();
        if (response != null && response.length() > 0) {


            try {

                //  StringBuilder data = new StringBuilder();
                //  if (response.has("results")) { //has check that does it have a map or not may be null
                JSONArray arrayMovie = response.getJSONArray("results");

                for (int i = 0; i < arrayMovie.length(); i++) { //getting all the objects

                    long id = -1;
                    String title = Constant.NA;
                    String releasedate = Constant.NA;
                    int voteavg = -1;
                    String poster = Constant.NA;
                    String overview = Constant.NA;


                    JSONObject currentMovie = arrayMovie.getJSONObject(i);
                    //getting the ID of the Movie for further operations
//////////////////////////
                    //checking for the key and checking that it is not null
                    if (currentMovie.has("id") && !currentMovie.isNull("id")) {
                        id = currentMovie.getLong("id");
                    }

//////////////////////////////
                    if (currentMovie.has("original_title") && !currentMovie.isNull("original_title")) {

                        title = currentMovie.getString("original_title"); //getting all the ids form the JSON

                    }

////////////////////////////////

                    if (currentMovie.has("overview") && !currentMovie.isNull("overview")) {
                        overview = currentMovie.getString("overview");
                    }

////////////////////////////////

                    //checking if  the JSON has this Param Value or no
                    if (currentMovie.has("vote_average") && !currentMovie.isNull("vote_average")) {
                        voteavg = currentMovie.getInt("vote_average");

                    }
////////////////////////////////
                    //check for the poster
                    if (currentMovie.has("poster_path") && !currentMovie.isNull("poster_path")) {
                        poster = "http://image.tmdb.org/t/p/w300" + currentMovie.getString("poster_path");
                    }
//////////////////////////////////
                    //getting Date


                    //check for the poster
                    if (currentMovie.has("release_date") && !currentMovie.isNull("release_date")) {
                        releasedate = currentMovie.getString("release_date");
                    }

///setting data to MOVIE LIST
                    Movie movie = new Movie();
                    movie.setID(id);
                    movie.setTitle(title);

                    Date date = null;
                    try {
                        //using date format to pass the date ...might throw an exception
                        date = dateFormat.parse(releasedate);
                    } catch (ParseException e) {
                        e.printStackTrace();

                    }
                    movie.setReleaseDate(date);

                    /////////
                    movie.setVote(voteavg);
                    movie.setOverview(overview);
                    movie.setURLPoster(poster);

                    if (id != -1 && !title.equals(Constant.NA)) {
//only add the movies when valid otherwise not
                        listMovies.add(movie); //this will call the toString() method in Movie.java

                    }
                    //data.append(ID + "\n" + name + "\n" + voteavg + "\n" + poster + "\n" + overview);
                }
                // L.toast(getActivity(), listMovies.toString());

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return listMovies;
    }
}