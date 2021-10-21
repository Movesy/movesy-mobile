package hu.bme.aut.movesy

import dagger.hilt.EntryPoint
import dagger.hilt.android.AndroidEntryPoint
import hu.bme.aut.movesy.database.UserDao
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import javax.inject.Inject

@EntryPoint
@RunWith(JUnit4::class)
class UserDatabaseTest {

    @Inject
    private lateinit var userDao: UserDao

    @Before
    fun setup(){
    }

    @Test
    fun `test userDao exists`(){
        assert(userDao != null)
    }


    @After
    fun cleanUp(){

    }

}