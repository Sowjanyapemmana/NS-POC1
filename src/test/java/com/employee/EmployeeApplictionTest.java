package com.employee;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.neosoft.Appliction;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Appliction.class)
class EmployeeApplictionTest {

		@Test
		void contextLoads() {
		}

		@Test
		public void employeeApplication() {
		    Appliction.main(new String[] {});
		}
}


