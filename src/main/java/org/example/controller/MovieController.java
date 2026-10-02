package org.example.controller;

import org.example.model.Movie;
import org.example.service.MovieService;
import org.example.service.MovieServiceImpl;

import java.util.List;

public class MovieController {

    private final MovieService movieService =
            new MovieServiceImpl();

    public boolean addMovie(Movie movie) {
        return movieService.addMovie(movie);
    }

    public boolean saveMovie(Movie movie) {
        return movieService.saveMovie(movie);
    }

    public Movie findMovieById(int movieId) {
        return movieService.findMovieById(movieId);
    }

    public boolean modifyMovie(Movie movie) {
        return movieService.modifyMovie(movie);
    }

    public boolean eraseMovie(int movieId) {
        return movieService.eraseMovie(movieId);
    }

    public List<Movie> getAllMovies() {
        return movieService.getAllMovies();
    }
}