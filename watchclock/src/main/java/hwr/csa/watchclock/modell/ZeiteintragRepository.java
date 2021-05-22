package hwr.csa.watchclock.modell;

import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.util.List;


//Repository für Anfragen auf Entität Zeiteintrag
public interface ZeiteintragRepository extends CrudRepository<Zeiteintrag, Integer> {
    Zeiteintrag findByEintragNr(int eintragNr);
    List<Zeiteintrag> findByBisAndUser(Timestamp bis, User user);
    List<Zeiteintrag> findAllByUserOrderByBisDesc(User user);

    @Transactional
    Zeiteintrag saveAndFlush(Zeiteintrag zeiteintrag);
}
