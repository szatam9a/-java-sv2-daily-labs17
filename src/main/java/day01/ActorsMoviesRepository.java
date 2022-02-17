package day01;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class ActorsMoviesRepository {
    private DataSource dataSource;

    public ActorsMoviesRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void insertActorAndMovie(long actor_id, long movie_id) {
        try (Connection conn = dataSource.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement("insert into actors_movies(actor_id,movie_id) values(?,?)");
        ) {
            preparedStatement.setLong(1, actor_id);
            preparedStatement.setLong(2, movie_id);
            preparedStatement.executeUpdate();

        } catch (SQLException sqle) {
            throw new IllegalStateException("cannot insert actors and movies");
        }
    }

    public List<String> getBackAll() {
        List<String> result = new LinkedList<>();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement prep = connection.prepareStatement("select * From actors_movies join movies ON  movies.id = actors_movies.movie_id\n" +
                     "JOIN actors  ON  movies.id=actors.id;")
        ) {
            prep.executeQuery();
            ResultSet rs = prep.getResultSet();
            while (rs.next()) {
                result.add(rs.getString("actor_name")+rs.getString("title")+rs.getString("release_date"));

            }
            rs.close();

        } catch (SQLException sql) {
            System.out.println("nope");
        }
        return result;
    }
}
