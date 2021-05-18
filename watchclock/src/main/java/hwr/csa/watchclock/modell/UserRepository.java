package hwr.csa.watchclock.modell;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserRepository extends CrudRepository<User, Integer> {

    User findByUsername(String username);
    List<User> findByEmail(String email);
    List<User> findAll();

}

