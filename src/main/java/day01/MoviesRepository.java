package day01;

import javax.sql.DataSource;
import javax.xml.crypto.Data;
import java.sql.*;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

public class MoviesRepository {

    private DataSource dataSource;

    public MoviesRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public long saveMovie(String title, LocalDate release_Date) {
        try (Connection con = dataSource.getConnection(); PreparedStatement prep = con.prepareStatement(
                "insert into movies (title,release_date) values(?,?)", Statement.RETURN_GENERATED_KEYS
        )) {
            prep.setString(1, title);
            prep.setDate(2, Date.valueOf(release_Date));
            prep.executeUpdate();

            try (ResultSet rs = prep.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getLong(1);
                }
                throw new IllegalStateException("can not insert movie");
            }

        } catch (SQLException sql) {
            throw new IllegalStateException("nosql");
        }

    }

    public List<Movie> findAllMovies() {
        List<Movie> result = new LinkedList<>();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement stmt = connection.prepareStatement("select * from movies")) {

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    result.add(new Movie(rs.getLong("id"), rs.getString("title"), LocalDate.parse(rs.getString("release_date"))));
                }
            }

        } catch (SQLException throwables) {
            throw new IllegalStateException("Cannot query", throwables);
        }
        return result;
    }
}
