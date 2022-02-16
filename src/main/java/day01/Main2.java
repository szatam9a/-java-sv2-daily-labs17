package day01;

import org.flywaydb.core.Flyway;
import org.mariadb.jdbc.MariaDbDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;

public class Main2 {
    public static void main(String[] args) {
        MariaDbDataSource dataSource = new MariaDbDataSource();
        try {
            dataSource.setUrl("jdbc:mariadb://localhost:3306/movies-actors-test?useUnicode=true");
            dataSource.setUserName("root");
            dataSource.setPassword("root");
        } catch (SQLException e) {
            throw new IllegalStateException("no sql connection");
        }
        Flyway flyway = Flyway.configure().dataSource(dataSource).load();
        flyway.migrate();
        ActorsRepository actorsRepository = new ActorsRepository(dataSource);
        //actorsRepository.saveActor("J");
        System.out.println(actorsRepository.findActorsWithPrefix("J"));
        MoviesRepository moviesRepository = new MoviesRepository(dataSource);
        //moviesRepository.saveMovie("titanico", LocalDate.of(1997, 12, 10));
        System.out.println(moviesRepository.findAllMovies());
    }
}
