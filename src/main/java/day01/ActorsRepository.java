package day01;

import javax.sql.DataSource;
import javax.swing.plaf.nimbus.State;
import javax.xml.transform.Result;
import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class ActorsRepository {
    private DataSource dataSource;

    public ActorsRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void saveActor(String name) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement("insert into actors(actor_name) values(?)")) {
            statement.setString(1, name);
            statement.executeUpdate();

        } catch (SQLException throwables) {
            throw new IllegalStateException("cannot update" + name, throwables);
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
