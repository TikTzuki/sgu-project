package com.javabyexamples.spring.core.usingautowired;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OptionalDependencyStoreService {

    private final EmployeeService employeeService;
    private final ManagerService managerService;

    @Autowired(required = false)
    private OrderService orderService;

    @Autowired
    public OptionalDependencyStoreService(EmployeeService employeeService,
                                          ManagerService managerService) {
        this.employeeService = employeeService;
        this.managerService = managerService;
    }

    public void start() {
        managerService.manage();
        employeeService.work();
        if (orderService != null) {
            orderService.order();
        }
    }
}
