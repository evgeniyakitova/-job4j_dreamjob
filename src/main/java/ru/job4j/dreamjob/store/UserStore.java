package ru.job4j.dreamjob.store;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.stereotype.Repository;
import ru.job4j.dreamjob.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public class UserStore implements Store<User> {
    private final BasicDataSource pool;

    public UserStore(BasicDataSource pool) {
        this.pool = pool;
    }

    @Override
    public Collection<User> findAll() {
        List<User> users = new ArrayList<>();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =  cn.prepareStatement("SELECT * FROM users")) {
            try (ResultSet resultSet = ps.executeQuery()) {
                while (resultSet.next()) {
                    users.add(buildUser(resultSet));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return users;
    }

    @Override
    public Optional<User> add(User user) {
        Optional<User> resultUser = Optional.empty();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement("INSERT INTO users (email, password, name) values (?, ?, ?)",
                PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, user.getEmail());
            ps.setString(2, user.getPassword());
            ps.setString(3, user.getName());
            ps.execute();
            try (ResultSet resultSet = ps.getGeneratedKeys()) {
                if (resultSet.next()) {
                    user.setId(resultSet.getInt("id"));
                    resultUser = Optional.of(user);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultUser;
    }

    @Override
    public User findById(int id) {
        User user = null;
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =  cn.prepareStatement("SELECT * FROM users WHERE id = ?")
        ) {
            ps.setInt(1, id);
            try (ResultSet resultSet = ps.executeQuery()) {
                if (resultSet.next()) {
                    user = buildUser(resultSet);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }

    @Override
    public void update(User user) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =  cn.prepareStatement(
                     "UPDATE users SET email = ?, password = ?, name = ? WHERE id = ?")
        ) {
            ps.setString(1, user.getEmail());
            ps.setString(2, user.getPassword());
            ps.setString(3, user.getName());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Optional<User> findUserByEmailAndPwd(String email, String password) {
        Optional<User> resultUser = Optional.empty();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps =  cn.prepareStatement("SELECT * FROM users WHERE email = ? AND password = ?")
        ) {
            ps.setString(1, email);
            ps.setString(2, password);
            try (ResultSet resultSet = ps.executeQuery()) {
                if (resultSet.next()) {
                    resultUser = Optional.of(buildUser(resultSet));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultUser;
    }

    private User buildUser(ResultSet resultSet) throws SQLException {
        return new User(
                resultSet.getInt("id"),
                resultSet.getString("name"),
                resultSet.getString("email"),
                resultSet.getString("password")
        );
    }
}
