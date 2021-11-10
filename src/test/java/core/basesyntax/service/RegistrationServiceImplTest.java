package core.basesyntax.service;

import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static RegistrationServiceImpl registrationService;
    private static StorageDaoImpl storageDao;
    private static User user;

    @BeforeAll
    static void beforeAll() {
        registrationService
                = new RegistrationServiceImpl();
        storageDao = new StorageDaoImpl();
        user = new User();
    }

    @BeforeEach
    void setUp() {
        Storage.people.clear();
        user.setAge(18);
        user.setLogin("user'sLogin");
        user.setPassword("user'sPassword");
    }

    @Test
    void register_validUser_Ok() {
        registrationService.register(user);
        Assertions.assertEquals(user, storageDao.get(user.getLogin())
                , "User must be added to data");
    }

    @Test
    void register_whenAgeLessThan18_NotOk() {
        user.setAge(17);
        Assertions.assertThrows(RuntimeException.class, () -> {
            registrationService.register(user);
        }, "Expected RuntimeException");
    }

    @Test
    void register_whenAgeMoreThan18_Ok() {
        user.setAge(25);
        registrationService.register(user);
        Assertions.assertEquals(user, storageDao.get(user.getLogin())
                , "User must be added to data");
    }

    @Test
    void register_whenAgeIsMaxInt() {
        user.setAge(Integer.MAX_VALUE);
        registrationService.register(user);
        Assertions.assertEquals(user, storageDao.get(user.getLogin())
                , "User must be added to data");
    }

    @Test
    void register_withSuchLogin_NotOk() {
        Assertions.assertThrows(RuntimeException.class, () -> {
            registrationService.register(user);
            registrationService.register(user);
        }, "Expected RuntimeException");
    }

    @Test
    void register_withInvalidPassword_NotOk() {
        user.setPassword(Integer.toString(123));
        Assertions.assertThrows(RuntimeException.class, () -> {
            registrationService.register(user);
        }, "Expected RuntimeException");
    }

    @Test
    void register_withNullPassword_NotOk() {
        user.setPassword(null);
        Assertions.assertThrows(RuntimeException.class, () -> {
            registrationService.register(user);
        }, "Expected RuntimeException");
    }

    @Test
    void register_withNullLogin_NotOk() {
        user.setLogin(null);
        Assertions.assertThrows(RuntimeException.class, () -> {
            registrationService.register(user);
        }, "Expected RuntimeException");
    }

    @Test
    void register_withNullAge_NotOk() {
        user.setAge(null);
        Assertions.assertThrows(RuntimeException.class, () -> {
            registrationService.register(user);
        }, "Expected RuntimeException");
    }

    @Test
    void register_withNegativeAge_NotOk() {
        user.setAge(-15);
        Assertions.assertThrows(RuntimeException.class, () -> {
            registrationService.register(user);
        }, "Expected RuntimeException");
    }

    @Test
    void register_withEmptyLogin_NotOk() {
        user.setLogin("");
        Assertions.assertThrows(RuntimeException.class, () -> {
            registrationService.register(user);
        }, "Expected RuntimeException");
    }
}
