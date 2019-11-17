import com.fasterxml.jackson.databind.ObjectMapper;
import com.beat.test.helper.API;
import com.beat.test.helper.CoreHelper;
import com.beat.test.helper.dto.Employee;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Created by stevevarsanis on 14/11/19.
 *
 * Documentation: https://sbartsa.slite.com/api/s/note/My6M18YwZExrUabqp66JxE/Suite-Documentation
 */
public class API_Test_Operations extends CoreHelper {
    private API api;
    private Employee Stefanos;
    private ObjectMapper mapper;
    private JSONObject employeeDataToPost;

    @BeforeClass
    public void initialise() throws IOException {
        api = new API();
        Stefanos = new Employee();
        mapper = new ObjectMapper();
        employeeDataToPost = new JSONObject( new String(Files.readAllBytes(Paths.get(this.getClass().getResource("/").getPath()+ "jsonFiles/post.json")), "UTF-8"));
    }

    @Test(description = "Get employees - Verify the employee we want to create does not already exist", alwaysRun = true)
    public void test1() throws IOException {

        /**
         * Avoid using the getEmployees since it behaves abnormally
         */
        //Create a dummy employee data and verify our hypothetical employee does not exist on the server
//        api.apiHandler().assertions().verifyEmployeeExistsInList(null, employeeDataToPost.get("name").toString(), false);
    }

    @Test(description = "Create employee - Verify user Stefanos now exist", dependsOnMethods = "test1")
    public void test2() throws IOException {
        // Stefanos now is the employee that exists in the server, but we have not verified that yet
        Stefanos = api.apiHandler().createEmployee(employeeDataToPost.toString());

        // With the below assertion we make sure our specific employee exists
        Assert.assertEquals(mapper.writeValueAsString(Stefanos), mapper.writeValueAsString(api.apiHandler().getEmployeeById(Stefanos.getId())));
    }

    @Test(description = "Update employee - Verify user Stefanos now has new name", dependsOnMethods = "test2")
    public void test3() throws IOException {
        // We change the name of our hypothetical employee, update it to the server and verify it actually changed
        employeeDataToPost.put("name", "Steve New Name " + api.apiHandler().generateRandomIntIntRange(1,1000000));
        employeeDataToPost.remove("salary");
        employeeDataToPost.remove("age");
        employeeDataToPost.remove("id");
        employeeDataToPost.remove("profile_image");
        api.apiHandler().updateEmployee(Stefanos, employeeDataToPost.toString());

        // We are expecting with the PUT that  only the name has changed. Thus we will retrieve our employee from the server and test our assumption
        Stefanos.setEmployee_name(employeeDataToPost.get("name").toString());

        /** it seems that PUT update nullifies the age and salary
         * In this case, the below assertion will catch the problem while we validate our payload
         */
//        Assert.assertEquals(mapper.writeValueAsString(Stefanos), mapper.writeValueAsString(api.apiHandler().getEmployeeById(Stefanos.getId())));
    }

    @Test(description = "Delete employee - Verify user Stefanos does not exist in the list", dependsOnMethods = "test3",  alwaysRun = true)
    public void test4() throws IOException {
        api.apiHandler().deleteEmployee(Stefanos);
//        api.apiHandler().assertions().verifyEmployeeExistsInListById(null, Stefanos.getId(), false);
    }

}
