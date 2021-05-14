package hwr.csa.watchclock.modell;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserRepository extends CrudRepository<User, Integer> {

    User findUserByEmail(String email);
    List<User> findByEmail(String email);
    List<User> findAll();

}

