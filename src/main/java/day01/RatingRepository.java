package day01;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class RatingRepository {
    private DataSource dataSource;
    private final int minRating = 1;
    private final int maxRating = 5;

    public RatingRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void insertRating(Long movieId, List<Integer> ratings) {
        try (Connection connection = dataSource.getConnection();

        ) {
            connection.setAutoCommit(false);
            try (PreparedStatement preparedStatement = connection.prepareStatement("insert into ratings(movie_id,rating) values(?,?)")) {
                for (Integer i : ratings) {
                    if (i < minRating || i > maxRating) {
                        throw new IllegalArgumentException("invalid rating");
                    }
                    preparedStatement.setLong(1, movieId);
                    preparedStatement.setLong(2, i);
                    preparedStatement.executeUpdate();
                }

                connection.commit();

            } catch (IllegalArgumentException illegalArgumentException) {
                connection.rollback();
            }

        } catch (SQLException throwables) {
            throw new IllegalStateException("cant insert into ratings" + throwables);
        }
        updateAverage();
    }

    private void updateAverage() {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT AVG(rating) as ratings_ave , ratings.movie_id FROM ratings GROUP BY ratings.movie_id");
             PreparedStatement updateAverage = connection.prepareStatement("UPDATE movies SET movies.ratings_ave = ? where movies.id = ?")
        ) {
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                updateAverage.setFloat(1, rs.getFloat("ratings_ave"));
                updateAverage.setInt(2, rs.getInt("movie_id"));
                updateAverage.executeUpdate();
            }
            rs.close();
        } catch (SQLException e) {
            throw new IllegalStateException("cant query movies rating with id");
        }
        //SELECT AVG(rating), ratings.movie_id FROM ratings GROUP BY ratings.movie_id;
    }
}
