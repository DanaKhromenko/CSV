import service.EmployeeOperationsService;
import service.EmployeeOperationsServiceCsvImpl;

public class Main {
    public static void main(String[] args) {
        EmployeeOperationsService operationsService = new EmployeeOperationsServiceCsvImpl(
                "Employee.csv", "Departments.csv");
        String employeeData = operationsService.getEmployeeData();
        System.out.println(employeeData);
    }
}
