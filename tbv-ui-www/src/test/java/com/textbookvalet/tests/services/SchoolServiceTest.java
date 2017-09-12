package com.textbookvalet.tests.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;

import com.textbookvalet.commons.School;
import com.textbookvalet.services.SchoolService;
import com.textbookvalet.ui.www.springboot.Application;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { Application.class })
@Transactional
public class SchoolServiceTest {

	@Autowired
	private SchoolService schoolService;  

	@Test
	@Rollback
	public void testGetAllSchools() {
		
		List<Integer> schoolIds = new ArrayList<Integer>();

		// Data Setup
		School school = getSchool("School 1");
		schoolIds.add(schoolService.save(school).getId());

		school = getSchool("School 2");
		schoolIds.add(schoolService.save(school).getId());
		
		school = getSchool("School 3");
		schoolIds.add(schoolService.save(school).getId());
		
		List<School> schoolsList = schoolService.findAll(1, 5); 
		
		assertNotNull("School List can not be null", schoolsList); 		
		assertEquals("Total Number of Schools", 5, schoolsList.size());
		
		schoolsList = schoolService.findBySchoolIds(schoolIds);
		
		assertNotNull("School List can not be null", schoolsList); 		
		assertEquals("Total Number of Schools", 3, schoolsList.size());	
		 
	} 

	public School getSchool(String name) {
		School school = new School();
		school.setFullName("Test School 1");
		school.setAboutUs("School About US");
		school.setAppointmentsEnabled(true);
		school.setBuyBack("BuyBack String");
		school.setName(name);
		school.setCommission(new BigDecimal("0.5"));
		school.setSalesTax(new Float("7.2"));
		school.setImage("/some/fake/image/path.jpg");
		school.setLocalDelivery(true);
		school.setEmbedCode("some code");
		return school;
	} 
}