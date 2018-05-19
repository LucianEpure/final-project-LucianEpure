package repository;

import entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import entity.User;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Integer>{

    User findByUsername(String username);

    User findByRoles(List<Role> roles);
}
