package com.qt.qualithon.api.omdb;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.io.UnsupportedEncodingException;
import java.util.Iterator;

import io.opentelemetry.exporter.logging.SystemOutLogExporter;
import kong.unirest.Unirest;
import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.json.JSONObject;

import com.qt.qualithon.model.Movie;

/**
 * OMDb API Rest Client to get movie metadata. 
 * The data is represented as com.qt.qualithon.model.Movie model
 **/
public class OMDbAPI {

    /**
     * get movie metadata from OMDb 
     *
     * @param   movieTitle    exact movie name
     * @return    Movie object representing metadata
     **/
    public Movie getMovie(String movieTitle) throws UnsupportedEncodingException {
        //call OMDb movie api
        HttpResponse<JsonNode> response = Unirest
            .get("https://www.omdbapi.com/?apikey=b569cb4e&t="
                + URLEncoder.encode(movieTitle, StandardCharsets.UTF_8.toString()))
            .asJson();
        JSONObject movieMetadata = response.getBody().getObject();
        System.out.println(movieMetadata.toString());

        // init Movie model
        Movie movie = new Movie();
        movie.setTitle(movieMetadata.getString("Title"));
        movie.setReleaseYear(movieMetadata.getString("Year"));
        movie.setDirector(movieMetadata.getString("Director"));
        movie.setGenres(movieMetadata.getString("Genre"));
        movie.setWriters(movieMetadata.getString("Writer"));
        movie.setImdbRating(movieMetadata.getString("imdbRating"));
        String rottenTomatoRating = getRatingsFromJson(movieMetadata, "Rotten Tomatoes");
        movie.setRottenTomatoRating(rottenTomatoRating);
        return  movie;
    }

    private String getRatingsFromJson(JSONObject json, String Source) {
        String rottenTomatoRating = null;
        Iterator<String> keys = json.keys();

        while(keys.hasNext()) {
            String key = keys.next();
            if (json.get(key) instanceof JSONObject) {
                System.out.println(json.get(key));
            }
        }
        return rottenTomatoRating;
    }

}
