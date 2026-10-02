package org.example.service;

import org.example.dao.MovieDAO;
import org.example.dao.MovieDAOImpl;
import org.example.model.Movie;

import java.util.List;

public class MovieServiceImpl implements MovieService {

    private final MovieDAO movieDAO;

    public MovieServiceImpl() {
        this.movieDAO = new MovieDAOImpl();
    }

    public MovieServiceImpl(MovieDAO movieDAO) {
        this.movieDAO = movieDAO;
    }

    @Override
    public boolean addMovie(Movie movie) {

        if (movie == null ||
                movie.getTitle() == null ||
                movie.getTitle().isBlank() ||
                movie.getLanguage() == null ||
                movie.getLanguage().isBlank() ||
                movie.getGenre() == null ||
                movie.getGenre().isBlank() ||
                movie.getDuration() <= 0 ||
                movie.getReleaseDate() == null) {

            return false;
        }

        return movieDAO.addMovie(movie);
    }

    @Override
    public boolean saveMovie(Movie movie) {

        if (movie == null ||
                movie.getTitle() == null ||
                movie.getTitle().isBlank() ||
                movie.getLanguage() == null ||
                movie.getLanguage().isBlank() ||
                movie.getGenre() == null ||
                movie.getGenre().isBlank() ||
                movie.getDuration() <= 0 ||
                movie.getReleaseDate() == null) {

            return false;
        }

        return movieDAO.saveMovie(movie);
    }

    @Override
    public Movie findMovieById(int movieId) {

        if (movieId <= 0) {
            return null;
        }

        return movieDAO.findMovieById(movieId);
    }

    @Override
    public boolean modifyMovie(Movie movie) {

        if (movie == null ||
                movie.getMovieId() <= 0 ||
                movie.getTitle() == null ||
                movie.getTitle().isBlank() ||
                movie.getLanguage() == null ||
                movie.getLanguage().isBlank() ||
                movie.getGenre() == null ||
                movie.getGenre().isBlank() ||
                movie.getDuration() <= 0 ||
                movie.getReleaseDate() == null) {

            return false;
        }

        return movieDAO.modifyMovie(movie);
    }

    @Override
    public boolean eraseMovie(int movieId) {

        if (movieId <= 0) {
            return false;
        }

        return movieDAO.eraseMovie(movieId);
    }

    @Override
    public List<Movie> getAllMovies() {
        return movieDAO.getAllMovies();
    }
}