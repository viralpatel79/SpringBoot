package com.textbookvalet.tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.textbookvalet.tests.services.AuthenticationManagerExtendedTest;
import com.textbookvalet.tests.services.SchoolServiceTest;
import com.textbookvalet.tests.services.TokenHandlerTest;
import com.textbookvalet.tests.services.UserReferralServiceTest;
import com.textbookvalet.tests.services.UserServiceTest;

@RunWith(Suite.class)
@SuiteClasses({ UserServiceTest.class, UserReferralServiceTest.class, SchoolServiceTest.class,
		AuthenticationManagerExtendedTest.class, TokenHandlerTest.class })

public class ServicesTests {

}
