package day01;

import java.sql.Connection;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class RatingService {
    private MoviesRepository moviesRepository;
    private RatingRepository ratingRepository;

    public RatingService(MoviesRepository moviesRepository, RatingRepository ratingRepository) {
        this.moviesRepository = moviesRepository;
        this.ratingRepository = ratingRepository;
    }

    public void addRatings(String title, Integer... ratings) {
        Optional<Movie> actual = moviesRepository.findMovieByName(title);
        if (actual.isPresent()) {
            ratingRepository.insertRating(actual.get().getId(), Arrays.asList(ratings));
        } else {
            throw new IllegalStateException("no movie to rating or cant find" + title);
        }
    }

    public void upDateRatings(){

    }
}
