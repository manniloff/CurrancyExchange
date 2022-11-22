package com.orange.user.repository;

import com.orange.user.dto.UserDto;
import com.orange.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    @Query("select new com.orange.user.dto.UserDto( u.login, u.password, u.active, u.roles) from User u")
    List<UserDto> findAllUsers();

    @Query("select new com.orange.user.dto.UserDto( u.login, u.password, u.active, u.roles) from User u where u.id = :id")
    Optional<UserDto> findByIdUsers(@Param("id") int id);

    Optional<User> findByLogin(String login);
}
