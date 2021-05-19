package hwr.csa.watchclock.modell;

import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface UserRepository extends CrudRepository<User, Integer> {
    User findByPersonalNr(long personalNr);
    User findByUsername(String username);
    List<User> findByEmail(String email);
    List<User> findAll();

    @Transactional
    void deleteByPersonalNr(long personalNr);

}

