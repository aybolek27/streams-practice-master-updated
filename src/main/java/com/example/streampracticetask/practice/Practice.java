package com.example.streampracticetask.practice;

import com.example.streampracticetask.model.*;
import com.example.streampracticetask.service.*;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.Comparator.comparing;

@Component
public class Practice {

    public static CountryService countryService;
    public static DepartmentService departmentService;
    public static EmployeeService employeeService;
    public static JobHistoryService jobHistoryService;
    public static JobService jobService;
    public static LocationService locationService;
    public static RegionService regionService;

    public Practice(CountryService countryService, DepartmentService departmentService,
                    EmployeeService employeeService, JobHistoryService jobHistoryService,
                    JobService jobService, LocationService locationService,
                    RegionService regionService) {

        Practice.countryService = countryService;
        Practice.departmentService = departmentService;
        Practice.employeeService = employeeService;
        Practice.jobHistoryService = jobHistoryService;
        Practice.jobService = jobService;
        Practice.locationService = locationService;
        Practice.regionService = regionService;

    }

    // You can use the services above for all the CRUD (create, read, update, delete) operations.
    // Above services have all the required methods.
    // Also, you can check all the methods in the ServiceImpl classes inside the service.impl package, they all have explanations.

    // Display all the employees
    public static List<Employee> getAllEmployees() {
        return employeeService.readAll();
//        return new ArrayList<>();
    }

    // Display all the countries
    public static List<Country> getAllCountries() {
        return countryService.readAll();
//        return new ArrayList<>();
    }

    // Display all the departments
    public static List<Department> getAllDepartments() {
        return departmentService.readAll();
//        return new ArrayList<>();
    }

    // Display all the jobs
    public static List<Job> getAllJobs() {
        return jobService.readAll();
//        return new ArrayList<>();
    }

    // Display all the locations
    public static List<Location> getAllLocations() {
        return locationService.readAll();
//        return new ArrayList<>();
    }

    // Display all the regions
    public static List<Region> getAllRegions() {
        return regionService.readAll();
//        return new ArrayList<>();
    }

    // Display all the job histories
    public static List<JobHistory> getAllJobHistories() {
        return jobHistoryService.readAll();
//        return new ArrayList<>();
    }

    // Display all the employees' first names
    public static List<String> getAllEmployeesFirstName() {
        return employeeService.readAll().stream()
                .map(Employee::getFirstName)
                .collect(Collectors.toList());
//        return new ArrayList<>();
    }

    // Display all the countries' names
    public static List<String> getAllCountryNames() {
        return countryService.readAll().stream()
                .map(Country::getCountryName)
                .collect(Collectors.toList());
//        return new ArrayList<>();
    }

    // Display all the departments' managers' first names
    public static List<String> getAllDepartmentManagerFirstNames() {
        return departmentService.readAll().stream()
                .map(department -> department.getManager().getFirstName())
                .collect(Collectors.toList());
//        return new ArrayList<>();
    }

    // Display all the departments where manager name of the department is 'Steven'
    public static List<Department> getAllDepartmentsWhichManagerFirstNameIsSteven() {
        return departmentService.readAll().stream()
                .filter(department -> department.getManager().getFirstName().equals("Steven"))
                .collect(Collectors.toList());
//        return new ArrayList<>();
    }

    // Display all the departments where postal code of the location of the department is '98199'
    public static List<Department> getAllDepartmentsWhereLocationPostalCodeIs98199() {
        return departmentService.readAll().stream()
                .filter(department -> department.getLocation().getPostalCode().equals("98199"))
                .collect(Collectors.toList());
//        return new ArrayList<>();
    }

    // Display the region of the IT department
    public static Region getRegionOfITDepartment() throws Exception {
        Optional<Region> regionOfItDepartment=departmentService.readAll().stream()
                .filter(department -> department.getDepartmentName().equals("IT"))
                .map(department -> department.getLocation().getCountry().getRegion())
                .findFirst();
    
        return regionOfItDepartment.get();
//        return new Region();
    }

    // Display all the departments where the region of department is 'Europe'
    public static List<Department> getAllDepartmentsWhereRegionOfCountryIsEurope() {
        return departmentService.readAll().stream()
                .filter(department -> department.getLocation().getCountry().getRegion().getRegionName().equals("Europe"))
                .collect(Collectors.toList());
//        return new ArrayList<>();
    }

    // Display if there is any employee with salary less than 1000. If there is none, the method should return true
    public static boolean checkIfThereIsNoSalaryLessThan1000() throws Exception {
        Long minSalary=getMinSalary();
        if (minSalary>1000)
            return true;
    
        return false;
       
    }

    // Check if the salaries of all the employees in IT department are greater than 2000 (departmentName: IT)
    public static boolean checkIfThereIsAnySalaryGreaterThan2000InITDepartment() {
        if(employeeService.readAll().stream()
                .anyMatch(employee -> employee.getSalary()>2000))
            return true;
    
        return false;
    }

    // Display all the employees whose salary is less than 5000
    public static List<Employee> getAllEmployeesWithLessSalaryThan5000() {
        return employeeService.readAll().stream()
                .filter(employee -> employee.getSalary()<5000)
                .collect(Collectors.toList());
//        return new ArrayList<>();
    }

    // Display all the employees whose salary is between 6000 and 7000
    public static List<Employee> getAllEmployeesSalaryBetween() {
        return employeeService.readAll().stream()
                .filter(employee -> employee.getSalary()>6000&&employee.getSalary()<7000)
                .collect(Collectors.toList());
//        return new ArrayList<>();
    }

    // Display the salary of the employee Grant Douglas (lastName: Grant, firstName: Douglas)
    public static Long getGrantDouglasSalary() throws Exception {
        Optional<Long> dogSalary=employeeService.readAll().stream()
                .filter(employee -> employee.getFirstName().equals("Douglas")&&employee.getLastName().equals("Grant"))
                .map(Employee::getSalary)
                .findFirst();
        return dogSalary.get();
//        return 1L;
    }

    // Display the maximum salary an employee gets
    public static Long getMaxSalary() throws Exception {
        Optional<Long> highestSalary=employeeService.readAll().stream()
                .map(Employee::getSalary)
                .reduce(Long::max);
        return highestSalary.get();
//        return 1L;
    }

    // Display the employee(s) who gets the maximum salary
    public static List<Employee> getMaxSalaryEmployee() {
        return employeeService.readAll().stream()
                .reduce((t1,t2)->t1.getSalary()>t2.getSalary()?t1:t2)
                .stream().collect(Collectors.toList());
//        return new ArrayList<>();
    }

    // Display the max salary employee's job
    public static Job getMaxSalaryEmployeeJob() throws Exception {
        return employeeService.readAll().stream()
                .reduce((t1,t2)->t1.getSalary()>t2.getSalary()?t1:t2)
                .map(employee -> employee.getJob()).get();
//        return new Job();
    }

    // Display the max salary in Americas Region
    public static Long getMaxSalaryInAmericasRegion() throws Exception {
        Optional<Long> highestSalaryInAmerica= employeeService.readAll().stream()
                .filter(employee -> employee.getDepartment().getLocation().getCountry().getRegion().getRegionName().equals("Americas"))
                .map(Employee::getSalary)
                .reduce(Long::max);
        return highestSalaryInAmerica.get();
//        return 1L;
    }

    // Display the second maximum salary an employee gets
    public static Long getSecondMaxSalary() throws Exception {
        Optional<Long> secondMax=employeeService.readAll().stream()
                .sorted(Comparator.comparingDouble(Employee::getSalary).reversed())
                .skip(1)
                .findFirst()
                .map(Employee::getSalary);
        return secondMax.get();
//        return 1L;
    }

    // Display the employee(s) who gets the second maximum salary
    public static List<Employee> getSecondMaxSalaryEmployee() {
        return employeeService.readAll().stream()
                .filter(employee -> {
                            try {
                                return employee.getSalary().equals(getSecondMaxSalary());
                            } catch (Exception e) {
                                e.printStackTrace();
                            
                            }
                            return Boolean.parseBoolean(null);
                        }
                )
                .collect(Collectors.toList());
//        return new ArrayList<>();
    }

    // Display the minimum salary an employee gets
    public static Long getMinSalary() throws Exception {
        Optional<Long> lowestSalary=employeeService.readAll().stream()
                .map(Employee::getSalary)
                .reduce(Long::min);
        return lowestSalary.get();
//        return 1L;
    }

    // Display the employee(s) who gets the minimum salary
    public static List<Employee> getMinSalaryEmployee() {
        return employeeService.readAll().stream()
                .filter(employee -> {
                    try {
                        return employee.getSalary().equals(getMinSalary());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return Boolean.parseBoolean(null);
                }).collect(Collectors.toList());
//        return new ArrayList<>();
    }

    // Display the second minimum salary an employee gets
    public static Long getSecondMinSalary() throws Exception {
        Optional<Long> secondMin=employeeService.readAll().stream()
                .sorted(Comparator.comparingDouble(Employee::getSalary))
                .skip(1)
                .findFirst()
                .map(Employee::getSalary);
        return secondMin.get();
//        return 1L;
    }

    // Display the employee(s) who gets the second minimum salary
    public static List<Employee> getSecondMinSalaryEmployee() {
        return employeeService.readAll().stream()
                .filter(employee -> {
                            try {
                                return employee.getSalary().equals(getSecondMinSalary());
                            } catch (Exception e) {
                                e.printStackTrace();
                            
                            }
                            return Boolean.parseBoolean(null);
                        }
                )
                .collect(Collectors.toList());
//        return new ArrayList<>();
    }

    // Display the average salary of the employees
    public static Double getAverageSalary() {
        Double average=  employeeService.readAll().stream()
                .collect(Collectors.averagingLong(Employee::getSalary));
        return average;
//        return 1d;
    }

    // Display all the employees who are making more than average salary
    public static List<Employee> getAllEmployeesAboveAverage() {
        return employeeService.readAll().stream()
                .filter(employee -> employee.getSalary()>getAverageSalary())
                .collect(Collectors.toList());
//        return new ArrayList<>();
    }

    // Display all the employees who are making less than average salary
    public static List<Employee> getAllEmployeesBelowAverage() {
        return employeeService.readAll().stream()
                .filter(employee -> employee.getSalary()<getAverageSalary())
                .collect(Collectors.toList());
//        return new ArrayList<>();
    }

    // Display all the employees separated based on their department id number
    public static Map<Long, List<Employee>> getAllEmployeesForEachDepartment() {
        Map<Long,List<Employee>> grouped=employeeService.readAll().stream()
                .collect(Collectors.groupingBy(employee -> employee.getDepartment().getId()));
        return grouped;
//        return new HashMap<>();
    }

    // Display the total number of the departments
    public static Long getTotalDepartmentsNumber() {
        Integer total=departmentService.readAll().stream()
                .map(d -> 1).reduce(0, (a,b)-> a+b);
    
        return total.longValue();
//        return 1L;
    }

    // Display the employee whose first name is 'Alyssa' and manager's first name is 'Eleni' and department name is 'Sales'
    public static Employee getEmployeeWhoseFirstNameIsAlyssaAndManagersFirstNameIsEleniAndDepartmentNameIsSales() throws Exception {
        Optional<Employee> unique=employeeService.readAll().stream()
                .filter(employee -> employee.getFirstName().equals("Alyssa")&&employee.getManager().getFirstName().equals("Eleni")&&employee.getDepartment().getDepartmentName().equals("Sales"))
                .findFirst();
        return unique.get();
//        return new Employee();
    }

    // Display all the job histories in ascending order by start date
    public static List<JobHistory> getAllJobHistoriesInAscendingOrder() {
        return jobHistoryService.readAll().stream()
                .sorted(comparing(JobHistory::getStartDate))
                .collect(Collectors.toList());
//        return new ArrayList<>();
    }

    // Display all the job histories in descending order by start date
    public static List<JobHistory> getAllJobHistoriesInDescendingOrder() {
        return jobHistoryService.readAll().stream()
                .sorted(comparing(JobHistory::getStartDate).reversed())
                .collect(Collectors.toList());
//        return new ArrayList<>();
    }

    // Display all the job histories where the start date is after 01.01.2005
    public static List<JobHistory> getAllJobHistoriesStartDateAfterFirstDayOfJanuary2005() {
        LocalDate startDate= LocalDate.parse("2005-01-01");
        return jobHistoryService.readAll().stream()
                .filter(jobHistory -> jobHistory.getStartDate().isAfter(startDate))
                .collect(Collectors.toList());
//        return new ArrayList<>();
    }

    // Display all the job histories where the end date is 31.12.2007 and the job title of job is 'Programmer'
    public static List<JobHistory> getAllJobHistoriesEndDateIsLastDayOfDecember2007AndJobTitleIsProgrammer() {
        LocalDate endDate= LocalDate.parse("2007-12-31");
        return jobHistoryService.readAll().stream()
                .filter(jobHistory -> jobHistory.getEndDate().isEqual(endDate)&&jobHistory.getJob().getJobTitle().equals("Programmer"))
                .collect(Collectors.toList());
//        return new ArrayList<>();
    }

    // Display the employee whose job history start date is 01.01.2007 and job history end date is 31.12.2007 and department's name is 'Shipping'
    public static Employee getEmployeeOfJobHistoryWhoseStartDateIsFirstDayOfJanuary2007AndEndDateIsLastDayOfDecember2007AndDepartmentNameIsShipping() throws Exception {
        LocalDate endDate= LocalDate.parse("2007-12-31");
        LocalDate startDate= LocalDate.parse("2007-01-01");
        Optional<Employee> expectedEmployee= jobHistoryService.readAll().stream()
                .filter(e->e.getStartDate().isEqual(startDate)&&e.getEndDate().isEqual(endDate))
                .filter(e-> e.getDepartment().getDepartmentName().equals("Shipping"))
                .map(JobHistory::getEmployee)
                .findAny();
        return expectedEmployee.get();
        
//        return new Employee();
    }

    // Display all the employees whose first name starts with 'A'
    public static List<Employee> getAllEmployeesFirstNameStartsWithA() {
        return employeeService.readAll().stream()
                .filter(employee -> employee.getFirstName().startsWith("A"))
                .collect(Collectors.toList());
//        return new ArrayList<>();
    }

    // Display all the employees whose job id contains 'IT'
    public static List<Employee> getAllEmployeesJobIdContainsIT() {
        return employeeService.readAll().stream()
                .filter(employee -> employee.getJob().getId().contains("IT"))
                .collect(Collectors.toList());
//        return new ArrayList<>();
    }

    // Display the number of employees whose job title is programmer and department name is 'IT'
    public static Long getNumberOfEmployeesWhoseJobTitleIsProgrammerAndDepartmentNameIsIT() {
        Long sum = employeeService.readAll().stream()
                .filter(employee -> employee.getDepartment().getDepartmentName().equals("IT"))
                .filter(employee -> employee.getJob().getJobTitle().equals("Programmer"))
                .count();
        return sum;
//        return 1L;
    }

    // Display all the employees whose department id is 50, 80, or 100
    public static List<Employee> getAllEmployeesDepartmentIdIs50or80or100() {
        return employeeService.readAll().stream()
                .filter(employee -> employee.getDepartment().getId()==50||employee.getDepartment().getId()==80||employee.getDepartment().getId()==100)
                .collect(Collectors.toList());
//        return new ArrayList<>();
    }

    // Display the initials of all the employees
    // Note: You can assume that there is no middle name
    public static List<String> getAllEmployeesInitials() {
       return employeeService.readAll().stream()
                .map(e ->e.getFirstName().substring(0, 1)+e.getLastName().substring(0, 1) )
                .collect(Collectors.toList());
//        return new ArrayList<>();
    }

    // Display the full names of all the employees
    public static List<String> getAllEmployeesFullNames() {
        return employeeService.readAll().stream()
                .map(employee -> employee.getFirstName()+" "+employee.getLastName())
                .collect(Collectors.toList());
//        return new ArrayList<>();
    }

    // Display the length of the longest full name(s)
    public static Integer getLongestNameLength(){
        Optional<Integer> longestName=getAllEmployeesFullNames().stream()
                .map(s->s.length())
                .reduce(Integer::max);
        return longestName.get();
//        return 1;
    }

    // Display the employee(s) with the longest full name(s)
    public static List<Employee> getLongestNamedEmployee() {
       return employeeService.readAll().stream()
               .filter(e-> (e.getFirstName()+" "+e.getLastName()).length()==getLongestNameLength())
                .collect(Collectors.toList());
//        return new ArrayList<>();
    }

    // Display all the employees whose department id is 90, 60, 100, 120, or 130
    public static List<Employee> getAllEmployeesDepartmentIdIs90or60or100or120or130() {
        return employeeService.readAll().stream()
                .filter(employee -> employee.getDepartment().getId()==90||employee.getDepartment().getId()==60||employee.getDepartment().getId()==100||employee.getDepartment().getId()==120||employee.getDepartment().getId()==130)
                .collect(Collectors.toList());
//        return new ArrayList<>();
    }

    // Display all the employees whose department id is NOT 90, 60, 100, 120, or 130
    public static List<Employee> getAllEmployeesDepartmentIdIsNot90or60or100or120or130() {
        return employeeService.readAll().stream()
                .filter(employee -> employee.getDepartment().getId()!=90&&employee.getDepartment().getId()!=60&&employee.getDepartment().getId()!=100&&employee.getDepartment().getId()!=120&&employee.getDepartment().getId()!=130)
                .collect(Collectors.toList());
//        return new ArrayList<>();
    }

}
