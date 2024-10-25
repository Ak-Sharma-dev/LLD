package design.patterns;

import java.util.ArrayList;
import java.util.List;

public class Employee {

    private String name;
    private int age;
    private String gender;

    public Employee(String name, int age, String gender) {
        this.name = name;
        this.age = age;
        this.gender = gender;
    }

    public Employee(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}

class EmployeeList {
    public List<Employee> getEmployees() {
        List<Employee> list = new ArrayList<>();
        list.add(new Employee("Sakshi", 24));
        list.add(new Employee("Adarsh", 24, "Male"));
        list.add(new Employee("Anuradha", 24, "Male"));
        return list;
    }

    public List<Employee> filterByGender(String gender) {
        List<Employee> employeeList = getEmployees();
        List<Employee> emps = new ArrayList<>();
        for(Employee emp : employeeList) {
            if(emp.getGender().equals(null) && gender.equals(null)) {
                emps.add(emp);
            }
            if(!emp.getGender().equals(null) && emp.getGender().equals(gender)) {
                emps.add(emp);
            }
        }
        return emps;
    }
}

class Main {

    public static void main(String[] args) {
        EmployeeList employeeList = new EmployeeList();
        List<Employee> list = employeeList.filterByGender("Male");
        list.forEach(val -> System.out.println(val.getName() + " " + val.getAge() + " " + val.getGender()));
    }

}
