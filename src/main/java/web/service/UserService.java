package web.service;

import web.model.User;

import java.util.List;

public interface UserService {

    List<User> list();

    User user(int id);

    void save(User user);

    void update(User user, int id);

    void delete(int id);
}
