package ru.job4j.accident.service;

import org.springframework.stereotype.Service;
import ru.job4j.accident.model.User;
import ru.job4j.accident.repository.UserRepository;

@Service
public class UserService {
    private final UserRepository userStore;

    public UserService(UserRepository userStore) {
        this.userStore = userStore;
    }

    public User saveUser(User user) {
        return userStore.save(user);
    }
}
