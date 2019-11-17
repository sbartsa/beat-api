package com.beat.test.helper.dto

class EmployeePostBody {

    String id
    String name
    String salary
    String age
    String profile_image

    /**
     This is mapper from the server response to the actual get implementation of employee.
     This way we can also verify each field is correctly mapped to the corresponding field
     */

    static Employee castToEmployee(EmployeePostBody employeePostBody, Employee employee = null) {
        if(employee == null) {employee = new Employee(); }
        if (employeePostBody.getId() != null) {
            employee.setId(employeePostBody.getId());
        }
        if (employeePostBody.getAge() != null) {
            employee.setEmployee_age(employeePostBody.getAge());
        }
        if (employeePostBody.getName() != null) {
            employee.setEmployee_name(employeePostBody.getName());
        }
        if (employeePostBody.getSalary() != null) {
            employee.setEmployee_salary(employeePostBody.getSalary());
        }
        if (employeePostBody.getProfile_image() == null) {
            employee.setProfile_image("");
        }
        return employee;
    }

}
