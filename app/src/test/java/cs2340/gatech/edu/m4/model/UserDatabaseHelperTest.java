package cs2340.gatech.edu.m4.model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

/**
 * Created by tianyunan on 11/12/17.
 */
@Config(manifest=Config.NONE)
@RunWith(RobolectricTestRunner.class)
public class UserDatabaseHelperTest {
    UserDatabaseHelper testhelper;
    @Before
    public void setUp() throws Exception {
        testhelper = new UserDatabaseHelper(RuntimeEnvironment.application);
    }

    @Test
    public void checkIfDataExists() throws Exception {
        User testUser = new User();
        testUser.setUsername("user1");
        testUser.setEmail("user1@gmail.com");
        testUser.setPassword("user1");
        testUser.setUserType("User");
        testhelper.insertUser(testUser);
        assertEquals(true, testhelper.CheckIfDataExists("user1"));
        assertEquals(false,testhelper.CheckIfDataExists("user2"));
    }

}