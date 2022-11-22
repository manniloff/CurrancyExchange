package com.orange.user.service.impl;

import com.orange.auth.filter.JwtRequestFilter;
import com.orange.user.dto.UserDto;
import com.orange.user.model.User;
import com.orange.user.repository.UserRepository;
import com.orange.user.service.UserService;
import com.orange.user.util.Roles;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private static final Logger LOGGER = LoggerFactory.getLogger("repo");
    private final UserRepository userRepository;

    @Override
    public List<UserDto> findAll() {
        LOGGER.info("Getting list of Users from db");
        return userRepository.findAllUsers();
    }

    @Override
    public Optional<UserDto> findById(int id) {
        LOGGER.info("Getting User by Id - {} from db", id);
        return userRepository.findByIdUsers(id);
    }

    @Override
    public int create(User newUser) {
        if (userRepository.findByLogin(newUser.getLogin()).isPresent()) {
            LOGGER.info("Can't create user with login {}, this login exist", newUser.getLogin());
            return 0;
        }

        LOGGER.info("Creating User with login {}", newUser.getLogin());

        User user = new User();

        user.setLogin(newUser.getLogin());
        user.setPassword(newUser.getPassword());
        user.setActive(true);
        user.setRoles(newUser.getRoles() == null ? Roles.USER : newUser.getRoles());

        return userRepository.save(user).getId();
    }

    @Override
    public Optional<User> update(User newUser, int id) {
        if (userRepository.findById(id).isPresent()) {
            return userRepository.findByLogin(newUser.getLogin())
                    .map(user -> {
                        LOGGER.info("Updating User with Id - {} and Login - {}", user.getId(), user.getLogin());

                        user.setPassword(newUser.getPassword());
                        user.setActive(newUser.isActive());
                        user.setRoles(Roles.USER);
                        if (JwtRequestFilter.role.equals("ADMIN")) {
                            user.setRoles(newUser.getRoles() == null ? Roles.USER : newUser.getRoles());
                        }
                        userRepository.save(user);
                        return newUser;
                    });
        }
        return Optional.empty();
    }

    @Override
    public void deleteById(int id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            LOGGER.info("Deleting User by Id - {} from db", id);
            userRepository.deleteById(id);
        }
    }
}
