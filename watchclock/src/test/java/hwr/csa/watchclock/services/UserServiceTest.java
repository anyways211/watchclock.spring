package hwr.csa.watchclock.services;

import hwr.csa.watchclock.modell.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;


import java.sql.Date;

import static org.junit.jupiter.api.Assertions.*;
class UserServiceTest {
    @Autowired
    UserService userService = new UserService();

    @Test
    void leereFelderAlleLeer() {
        User user = new User(1,"","","","",
                0,"", null,true);

        Boolean expected = true;
        Boolean actual = userService.leereFelder(user);

        assertEquals(expected, actual);

    }
    @Test
    void leereFelderNurDatumLeer() {
        User user = new User(1,"Annika","Paß","Test","abc",
                10,"test", null,true);

        Boolean expected = true;
        Boolean actual = userService.leereFelder(user);

        assertEquals(expected, actual);

    }
    @Test
    void leereFelderNurNachnameLeer() {
        User user = new User(1,"Annika","","Test","abc",
                10,"test", Date.valueOf("1998-02-21"),true);

        Boolean expected = true;
        Boolean actual = userService.leereFelder(user);

        assertEquals(expected, actual);

    }
    @Test
    void leereFelderAlleVoll() {
        User user = new User(1,"Annika","paß","Test","abc",
                10,"test", Date.valueOf("1998-02-21"),true);

        Boolean expected = false;
        Boolean actual = userService.leereFelder(user);

        assertEquals(expected, actual);

    }
}