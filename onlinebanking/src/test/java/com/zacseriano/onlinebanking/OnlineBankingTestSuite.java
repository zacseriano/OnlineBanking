package com.zacseriano.onlinebanking;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.zacseriano.onlinebanking.repositories.AccountRepositoryTest;
import com.zacseriano.onlinebanking.repositories.UserRepositoryTest;
import com.zacseriano.onlinebanking.resources.AccountResourceTest;
import com.zacseriano.onlinebanking.resources.AuthResourceTest;
import com.zacseriano.onlinebanking.resources.UserResourceTest;

@RunWith(Suite.class)
@SuiteClasses({ AccountRepositoryTest.class, UserRepositoryTest.class, AccountResourceTest.class, 
	AuthResourceTest.class, UserResourceTest.class})
public class OnlineBankingTestSuite {

}
