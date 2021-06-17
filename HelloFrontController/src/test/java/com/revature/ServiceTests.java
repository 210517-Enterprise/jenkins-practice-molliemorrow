package com.revature;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.revature.model.Employee;
import com.revature.repositories.EmployeeDAOImpl;
import com.revature.service.EmployeeService;

public class ServiceTests {
	
	private EmployeeDAOImpl edaoImpl;
	
	//before every test method is run, do this
	@Before
	public void setup() {
		edaoImpl = mock(EmployeeDAOImpl.class); //reflection!
		
		//set edao of employee service class = mocked dao
		EmployeeService.edao = edaoImpl;
	}
	
	
	//happy path scenario test
	@Test
	public void test_Employee_findByUsername() {
		Employee sampleEmp = new Employee(1, "a", "b", "c", "d");
		Employee sampleEmp2 = new Employee(2, "e", "f", "g", "h");
		
		List<Employee> list = new ArrayList<>();
		list.add(sampleEmp);
		list.add(sampleEmp2);
		
		//program dao to return data as its fake DB data
		when(edaoImpl.findAll()).thenReturn(list);
		
		String dummyUsername = sampleEmp.getUsername();
		
		//fBU() method in service class returns fetched data
		Employee returned = EmployeeService.findByUsername(dummyUsername);
		
		assertEquals(sampleEmp, returned);
	}
	
	/*
	 * here we are testing to make sure our findBU() service method
	 * returns null when the employee is not in the DB
	 */
	@Test
	public void employeeNotPresentInDb() {
		List<Employee> emptyList = new ArrayList<>();
		when(edaoImpl.findAll()).thenReturn(emptyList);
		
		Employee empFoundByUsername = EmployeeService.findByUsername("test");
		
		assertNull(empFoundByUsername);
	}

}
