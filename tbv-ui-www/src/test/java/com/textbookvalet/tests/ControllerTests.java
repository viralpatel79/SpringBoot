package com.textbookvalet.tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.textbookvalet.tests.controllers.LoginLogoutControllerTest;
import com.textbookvalet.tests.controllers.UserReferralRestControllerTest;
import com.textbookvalet.tests.controllers.UserRestControllerTest;

@RunWith(Suite.class)
@SuiteClasses({ LoginLogoutControllerTest.class, UserReferralRestControllerTest.class,
		/*ApplicationRestControllerTest.class,*/ UserRestControllerTest.class })
public class ControllerTests {

}
