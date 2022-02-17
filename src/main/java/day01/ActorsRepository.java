package day01;

import javax.sql.DataSource;
import java.sql.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class ActorsRepository {
    private DataSource dataSource;

    public ActorsRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public long saveActor(String name) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement("insert into actors(actor_name) values(?)",
                     Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, name);
            statement.executeUpdate();
            try (ResultSet rs = statement.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getLong(1);
                }
                throw new IllegalStateException("no id ");
            }

        } catch (SQLException throwables) {
            throw new IllegalStateException("cannot update" + name, throwables);
        }
    }

    public Optional findActorByName(String name) {
        Optional<Actor> result;
        try (Connection connection = dataSource.getConnection();
             PreparedStatement stmt = connection.prepareStatement("select * from actors where actor_name = ?")) {
            stmt.setString(1, name);
            result = processSelectStatement(stmt);

        } catch (SQLException throwables) {
            throw new IllegalStateException("cannot connect select by name", throwables);
        }
        return result;
    }

    private Optional<Actor> processSelectStatement(PreparedStatement prep) throws SQLException {
        try (ResultSet rs = prep.executeQuery()) {
            if (rs.next()) {
                return Optional.of(new Actor(rs.getInt("id"), rs.getString("actor_name")));
            }
            return Optional.empty();
        }
    }

    public List<String> findActorsWithPrefix(String prefix) {
        List<String> result = new LinkedList<>();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement stmt = connection.prepareStatement("select actor_name from actors where actor_name LIKE ?")) {
            stmt.setString(1, prefix + "%");

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    result.add(rs.getString("actor_name"));
                }
            }

        } catch (SQLException throwables) {
            throw new IllegalStateException("Cannot query" + prefix, throwables);
        }
        return result;
    }
}
