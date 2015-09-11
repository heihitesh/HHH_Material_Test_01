package com.itshiteshverma.hhh_material_test_01.extras;

import java.util.Date;

/**
 * Created by Nilesh Verma on 9/11/2015.
 */
public class Movie {


    private long id;
    private String title;
    private Date releaseDate;
    private int vote;
    private String overView;
    private String urlSelf;
    private String urlCast;
    private String urlReviews;
    private String urlSimilar;
    private String urlPoster;

    public Movie(){

    }

    public Movie(long id,
                 String title,
                 Date releaseDate,
                 int vote,
                 String overView,
                 String urlPoster,
                 String urlSelf,
                 String urlCast,
                 String urlReviews,
                 String urlSimilar
                 ){
        this.id = id;
        this.releaseDate = releaseDate;
        this.vote = vote;
        this.overView = overView;
        this.urlPoster = urlPoster;
        this.urlSelf = urlSelf;
        this.urlCast = urlCast;
        this.urlReviews = urlReviews;
        this.urlSimilar = urlSimilar;
        this.title = title;


    }

    public String toString(){
        return "ID: " +id+ "\n"+
                "Title " +title+"\n"+
                "Date"  +releaseDate+"\n"+
                "Overview" +overView+"\n"+
                "Vote" +vote+"\n"+
                "Poster " +urlPoster +"\n";
    }

    public void setID(long id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public void setVote(int vote) {
        this.vote = vote;
    }

    public void setOverview(String overview) {
        this.overView = overview;
    }

    public void setURLPoster(String URLPoster) {
        this.urlPoster = URLPoster;
    }

    public String getTitle() {
        return title;
    }

    public Date getDate() {
        return releaseDate;
    }

    public int getVote() {
        return vote;
    }

    public String geturlPoster() {
        return urlPoster;
    }
}
