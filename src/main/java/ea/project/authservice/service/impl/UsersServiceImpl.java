package ea.project.authservice.service.impl;

import ea.project.authservice.domain.Role;
import ea.project.authservice.domain.User;
import ea.project.authservice.repository.RoleRepository;
import ea.project.authservice.repository.UserRepository;
import ea.project.authservice.service.UserService;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UsersServiceImpl implements UserService, UserDetailsService {

    private final UserRepository usersRepository;
    private final RoleRepository repository;
    private final PasswordEncoder passwordEncoder;

    public UsersServiceImpl(UserRepository usersRepository, RoleRepository repository, PasswordEncoder passwordEncoder) {
        this.usersRepository = usersRepository;
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        System.out.println("======");
        User user = usersRepository
                .getUserByUsername(username)
                .orElseThrow(
                        () -> new UsernameNotFoundException("Username not Found")
                );
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        user.getRoles().forEach(role -> authorities.add(new SimpleGrantedAuthority(role.getName())));
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), authorities);
    }

    @Override
    public User getByUserName(String username) {
        return usersRepository
                .getUserByUsername(username)
                .orElseThrow(()->new UsernameNotFoundException("Username Not Found"));
    }

    @Override
    public User saveUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return usersRepository.save(user);
    }

    @Override
    public Role saveRole(Role role) {
        return null;
    }

    @Override
    public void addRoleToUser(String username, String roleName) {

    }

    @Override
    public List<User> getUsers() {
        return null;
    }


}
