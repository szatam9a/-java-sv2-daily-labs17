package day01;

import org.flywaydb.core.Flyway;
import org.mariadb.jdbc.MariaDbDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
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
        actorsRepository.saveActor("J");
        System.out.println(actorsRepository.findActorsWithPrefix("J"));
        MoviesRepository moviesRepository = new MoviesRepository(dataSource);
        moviesRepository.saveMovie("titanico", LocalDate.of(1997, 12, 10));


        ActorsMoviesRepository actorsMoviesRepository = new ActorsMoviesRepository(dataSource);
        ActorsMoviesService actorsMoviesService = new ActorsMoviesService(actorsRepository, moviesRepository, actorsMoviesRepository);
        //actorsMoviesService.insertMovieWithActors("Titanic", LocalDate.of(1990, 12, 10), List.of("Diccprio", "Rose", "Newyears"));
        System.out.println(moviesRepository.findAllMovies());
        for (String s : actorsMoviesRepository.getBackAll()) {
            System.out.println(s);
        }
    }
}
