package tiktzuki.e_store.BUS;

import java.util.Arrays;

import tiktzuki.e_store.DAL.EmployeeRepository;
import tiktzuki.e_store.DTO.Employee;

public class UserController {
    EmployeeRepository empRepos = new EmployeeRepository();

    public Employee login(String username, char[] password) {
        Employee emp = empRepos.findByName(username);
        if (emp == null || !Arrays.equals(password, emp.getPassword().toCharArray())) {
            return null;
        }
        return emp;
    }
}
