package org.example.service;

import org.example.model.Movie;

import java.util.List;

public interface MovieService {

    boolean addMovie(Movie movie);

    boolean saveMovie(Movie movie);

    Movie findMovieById(int movieId);

    boolean modifyMovie(Movie movie);

    boolean eraseMovie(int movieId);

    List<Movie> getAllMovies();
}