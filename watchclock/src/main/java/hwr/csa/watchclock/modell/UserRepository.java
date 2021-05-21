package hwr.csa.watchclock.modell;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface UserRepository extends CrudRepository<User, Integer> {
    User findByPersonalNr(long personalNr);
    User findByUsername(String username);
    List<User> findAll();

    @Transactional
    void deleteByPersonalNr(long personalNr);

    User saveAndFlush(User user);
}

