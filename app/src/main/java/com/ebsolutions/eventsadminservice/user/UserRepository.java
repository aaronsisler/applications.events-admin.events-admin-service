package com.ebsolutions.eventsadminservice.user;

import com.ebsolutions.eventsadminservice.model.User;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class UserRepository {
  private final UserDao userDao;

  public List<User> create(List<User> users) {
    return userDao.create(users);
  }

  public User read(String userId) {
    return userDao.read(userId);
  }

  public List<User> readAll() {
    return userDao.readAll();
  }
}
