package tiktzuki.e_store.GUI.frames.employee;

import java.awt.FlowLayout;
import java.util.List;

import javax.swing.JPanel;

import tiktzuki.e_store.DAL.EmployeeRepository;
import tiktzuki.e_store.DTO.Employee;
import tiktzuki.e_store.GUI.Resources;
import tiktzuki.e_store.GUI.frames.receiving.ReceivingManagerPanel;

public class EmployeeMainPanel extends JPanel {
    public static JPanel mainContent;
    public static Employee selectedEmployee;
    public static List<Employee> lisEmployees;
    public static EmployeeRepository employeeRepository = new EmployeeRepository();

    public EmployeeMainPanel() {
        super(new FlowLayout(0, 0, 0));
        lisEmployees = employeeRepository.findAll();
        selectedEmployee = new Employee();
        mainContent = new JPanel(new FlowLayout(0, 0, 0));
        mainContent.add(new EmployeeManagerPanel());
        this.add(mainContent);
    }
}
