package hwr.csa.watchclock.modell;

import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

//Repository für Anfragen auf Entität User
public interface UserRepository extends CrudRepository<User, Integer> {
    User findByPersonalNr(long personalNr);
    User findByUsername(String username);
    List<User> findAll();

    @Transactional
    void deleteByPersonalNr(long personalNr);

    User saveAndFlush(User user);
}

