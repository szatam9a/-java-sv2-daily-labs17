package day01;

import javax.sql.DataSource;
import javax.swing.plaf.nimbus.State;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

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
}
