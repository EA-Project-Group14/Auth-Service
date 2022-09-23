package ea.project.authservice.service;

import ea.project.authservice.domain.Role;
import ea.project.authservice.domain.User;

import java.util.List;

public interface UserService {
    User getByUserName(String username);

    User saveUser(User user);

    Role saveRole(Role role);

    void addRoleToUser(String username, String roleName);

    //TODO: Pagination
    List<User> getUsers();

}
