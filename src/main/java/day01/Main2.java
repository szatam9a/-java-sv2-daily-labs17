package day01;

import org.mariadb.jdbc.MariaDbDataSource;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

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
        ActorsRepository actorsRepository = new ActorsRepository(dataSource);
        //actorsRepository.saveActor("J");
        System.out.println(actorsRepository.findActorsWithPrefix("J"));
    }
}
