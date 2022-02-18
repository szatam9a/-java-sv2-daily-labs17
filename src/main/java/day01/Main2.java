package day01;

import org.flywaydb.core.Flyway;
import org.mariadb.jdbc.MariaDbDataSource;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class Main2 {
    public static void main(String[] args) {
        MariaDbDataSource dataSource = new MariaDbDataSource();
        try {
            dataSource.setUrl("jdbc:mariadb://localhost:3306/movies-actors?useUnicode=true");
            dataSource.setUserName("root");
            dataSource.setPassword("root");
        } catch (SQLException e) {
            throw new IllegalStateException("no sql connection");
        }
        Flyway flyway = Flyway.configure().dataSource(dataSource).load();
        flyway.migrate();

        ActorsRepository actorsRepository = new ActorsRepository(dataSource);
        MoviesRepository moviesRepository = new MoviesRepository(dataSource);
        //moviesRepository.saveMovie("titanico", LocalDate.of(1997, 12, 10));
        //moviesRepository.saveMovie("Titanic", LocalDate.of(1997, 12, 10));


        ActorsMoviesRepository actorsMoviesRepository = new ActorsMoviesRepository(dataSource);
        ActorsMoviesService actorsMoviesService = new ActorsMoviesService(actorsRepository, moviesRepository, actorsMoviesRepository);
        //actorsMoviesService.insertMovieWithActors("Titanic", LocalDate.of(1990, 12, 10), List.of("Diccprio", "Rose", "Newyears"));
        //actorsMoviesService.insertMovieWithActors("Akapulco", LocalDate.of(1990, 12, 10), List.of("Diccprio", "Rose", "Newyears"));

        RatingRepository ratingRepository = new RatingRepository(dataSource);
        RatingService movieRatingService = new RatingService(moviesRepository, ratingRepository);
        movieRatingService.addRatings("Titanic", 5,5,5,5,5,5,5,5,5,5);
        movieRatingService.addRatings("Akapulco", 1,3,5,5,5,5,5,5,5);
        //movieRatingService.addRatings("Titanic", 1,3,5,3,56);
        //moviesRepository.saveMovie("Akapulkp", LocalDate.now());
        //movieRatingService.addRatings("Akapulco", 1,3,5,3,56);
    }
}
