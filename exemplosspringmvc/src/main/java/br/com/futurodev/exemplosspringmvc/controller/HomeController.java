package br.com.futurodev.exemplosspringmvc.controller;

import br.com.futurodev.exemplosspringmvc.model.Employee;
import br.com.futurodev.exemplosspringmvc.repository.EmployeeRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class HomeController {

    private final EmployeeRepository employeeRepository;

    public HomeController(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @GetMapping("/home")
    public ModelAndView getHomePage() {
        ModelAndView homeModelAndView = new ModelAndView("home");

        List<Employee> employees = this.employeeRepository.findAll();
        homeModelAndView.addObject("employees", employees);
        return homeModelAndView;
    }
}
