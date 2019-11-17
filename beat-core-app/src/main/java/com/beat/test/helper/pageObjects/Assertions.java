package com.beat.test.helper.pageObjects;


import com.beat.test.helper.Logger;
import com.beat.test.helper.dto.Employee;
import org.testng.Assert;

import java.io.IOException;
import java.util.List;

public class Assertions extends ApiHandler {
    private static Logger logger;

    /**
     If no specific employeeList is included, then it defaults to a new list from the server
     */
    public void verifyEmployeeExistsInList(List<Employee> employeesList, String userName , boolean isIncluded) throws IOException {
        if (employeesList == null) {
            employeesList = getEmployees();
        }
        boolean exists = false;
        for (int i=0; i<employeesList.size();i++) {
            if (employeesList.get(i).getEmployee_name().equals(userName)) {
                exists = true;
            }
        }
        Assert.assertEquals(exists, isIncluded);
        Logger.pass("User exists: " + exists);
    }

    /**
     Same as above but this time we use the id in order to search through the employee list
     */
    public void verifyEmployeeExistsInListById(List<Employee> employeesList, String id , boolean isIncluded) throws IOException {
        if (employeesList == null) {
            employeesList = getEmployees();
        }
        boolean exists = false;
        for (int i=0; i<employeesList.size();i++) {
            if (employeesList.get(i).getId().equals(id)) {
                exists = true;
            }
        }
        Assert.assertEquals(exists, isIncluded);
        Logger.pass("User exists: " + exists);
    }
}
