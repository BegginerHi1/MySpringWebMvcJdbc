package web.dao;

import org.springframework.stereotype.Repository;
import web.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class UserDaoImpl implements UserDao {

    private static final String URL = "jdbc:mysql://localhost:3306/base";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "Rootroot1";

    private static Connection connection;

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        try {
            connection = DriverManager.getConnection(URL,USERNAME,PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public List<User> list() {
        List<User> list = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            String sql = "select * from users";
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getInt("id"));
                user.setName(resultSet.getString("name"));
                list.add(user);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    @Override
    public User user(int id) {
        User user;
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "select * from users where id=?");
            statement.setInt(1,id);
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            user = new User();
            user.setId(resultSet.getInt("id"));
            user.setName(resultSet.getString("name"));
            user.setAge(resultSet.getInt("age"));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return user;
    }

    @Override
    public void save(User user) {
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "insert into users (name,age) values (?,?)");
            statement.setString(1,user.getName());
            statement.setInt(2,user.getAge());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(User user, int id) {
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "update users set name=?,age=? where id=?");
            statement.setString(1,user.getName());
            statement.setInt(2,user.getAge());
            statement.setInt(3,id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(int id) {
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "delete from users where id=?");
            statement.setInt(1,id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
