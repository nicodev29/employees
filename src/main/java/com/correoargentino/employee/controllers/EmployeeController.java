package com.correoargentino.employee.controllers;

import com.correoargentino.employee.models.Employee;
import com.correoargentino.employee.services.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Optional;

@Controller
public class EmployeeController {

    private static final Logger logger = LoggerFactory.getLogger(EmployeeController.class);

    @Autowired
    private EmployeeService employeeService;

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @PostMapping("/search")
    public String search(@RequestParam("dni") String dni, Model model) {

        logger.info("DNI recibido: {}", dni);

        Optional<Employee> employee = employeeService.getEmployeeByDni(dni);

        if (employee.isPresent()) {
            model.addAttribute("employee", employee.get());
            return "result";
        } else {
            return "not-found";
        }
    }

    @GetMapping("/createEmployee")
    public String createEmployee() {
        return "createEmployee";
    }

    @PostMapping("/saveEmployee")
    public String saveEmployee(@RequestParam("dni") String dni,
                               @RequestParam("name") String name,
                               @RequestParam("lastname") String lastname) {
        Employee newEmployee = new Employee();
        newEmployee.setDni(dni);
        newEmployee.setName(name);
        newEmployee.setLastName(lastname);

        employeeService.saveEmployee(newEmployee);
        return "index";
    }

    @GetMapping("/employees")
    @ResponseBody
    public List<Employee> getAllEmployees() {
        return employeeService.getAllEmployees();
    }
}
