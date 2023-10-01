package com.example.demo.test.serviceImp;

import com.example.demo.test.entity.Employee;
import com.example.demo.test.repository.EmployeeRepository;
import com.example.demo.test.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;


@Service
@RequiredArgsConstructor
public class EmployeeServiceImp implements EmployeeService {

    @Autowired
    private final PasswordEncoder passwordEncoder;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public Employee saveEmployee(Employee employee) {
        String pass = passwordEncoder.encode(employee.getPassword());
        employee.setPassword(pass);
        return employeeRepository.save(employee);
    }

    @Override
    public Employee findEmployee(String email) {
        return employeeRepository.findEmployeeByEmail(email);

    }

    @Override
    public Map<String,String> checkAuth(Employee employee) {
        Map<String,String> result = new HashMap<String,String>();
        String res = "";
        Employee emp = employeeRepository.findEmployeeByEmail(employee.getEmail());
        if (emp != null) {
            if (!passwordEncoder.matches(employee.getPassword(), emp.getPassword())) {
                res = "Password is not match";
            } else if (passwordEncoder.matches(employee.getPassword(), emp.getPassword())) {
                res = "Password is match";
            } else {
                res = "something is not ok";
            }
        } else {
            res = "email is not found";
        }
        result.put("message", res);
        return result;

    }
}
