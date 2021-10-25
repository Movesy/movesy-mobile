package hu.bme.aut.movesy

import com.squareup.moshi.Moshi
import dagger.hilt.EntryPoint
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.testing.CustomTestApplication
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import hu.bme.aut.movesy.database.UserDao
import hu.bme.aut.movesy.model.User
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import javax.inject.Inject


@HiltAndroidTest
class UserDatabaseTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var userDao: UserDao

    @Before
    fun setup() {
        hiltRule.inject()
    }

    @Test
    fun diInjection(){
        assert(userDao is UserDao)
    }

    @Test
    fun livedataUser() {

        runBlocking {
            userDao.insertUser(User("testuserid", "asd", "", "", "", "", ""));
            assert( userDao.getUserForExistence("testuserid")?.username == "asd")
        }

       // assert(user?.username == "asd")
    }

    @Test
    fun updateOrInsert(){
        runBlocking {
            userDao.deleteUser("testuserid")
            val user = User("testuserid", "asd", "dsf", "asdf", "adsf", "asdf", "asdf")
            userDao.updateOrInsert(user)
            assert(userDao.getUserForExistence("testuserid") == user)

            userDao.updateOrInsert(user.copy(username = "not matching"))
            assert(userDao.getUserForExistence("testuserid") != user)
        }
    }


    @After
    fun cleanUp() {
        runBlocking {
            userDao.deleteUser("testuserid")

        }
    }

}