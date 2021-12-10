package service;

import exception.ParseFileException;
import exception.ReadFileException;
import manager.FileManager;
import manager.FileManagerCsvImpl;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class EmployeeOperationsServiceCsvImpl implements EmployeeOperationsService {
    private final String inputEmployeeFileName;
    private final String inputDepartmentFileName;
    private final String inputSeparator;
    private final String outputSeparator;
    private final FileManager fileManager = new FileManagerCsvImpl();

    private static final int EMPLOYEE_ID = 0;
    private static final int EMPLOYEE_NAME = 1;
    private static final int EMPLOYEE_SURNAME = 2;
    private static final int EMPLOYEE_DEPT_ID = 3;
    private static final int DEPARTMENT_ID = 0;
    private static final int DEPARTMENT_NAME = 1;
    private static final int EMPLOYEE_COLUMNS = 4;
    private static final int DEPARTMENT_COLUMNS = 2;

    public EmployeeOperationsServiceCsvImpl(String inputEmployeeFileName, String inputDepartmentFileName) {
        this.inputEmployeeFileName = inputEmployeeFileName;
        this.inputDepartmentFileName = inputDepartmentFileName;
        this.inputSeparator = ";";
        this.outputSeparator = "    ";
    }

    public EmployeeOperationsServiceCsvImpl(String inputEmployeeFileName, String inputDepartmentFileName,
                                            String inputSeparator, String outputSeparator) {
        this.inputEmployeeFileName = inputEmployeeFileName;
        this.inputDepartmentFileName = inputDepartmentFileName;
        this.inputSeparator = inputSeparator;
        this.outputSeparator = outputSeparator;

        if (inputSeparator == null || outputSeparator == null) {
            throw new RuntimeException("Separator cannot be NULL");
        }
    }

    @Override
    public String getEmployeeData() {
        List<String> employeeRows = getRowsFromFile(inputEmployeeFileName);
        List<String> departmentRows = getRowsFromFile(inputDepartmentFileName);
        return combineDateFromEmployeeAndDepartmentFiles(
                getDataArray(employeeRows, EMPLOYEE_COLUMNS),
                getDataArray(departmentRows, DEPARTMENT_COLUMNS));
    }

    private List<String> getRowsFromFile(String inputFileName) {
        try {
            return fileManager.getListOfAllRowsFromFile(inputFileName, true);
        } catch (IOException e) {
            throw new ReadFileException("Cannot find file " + inputFileName);
        }
    }

    private String[][] getDataArray(List<String> dataRows, int columns) {
        String[][] dataArray = new String[dataRows.size()][columns];
        try {
            for (int i = 0; i < dataRows.size(); i++) {
                dataArray[i] = dataRows.get(i).split(inputSeparator);
            }
        } catch (Exception e) {
            throw new ParseFileException("Cannot split the row");
        }
        return dataArray;
    }

    private String combineDateFromEmployeeAndDepartmentFiles(String[][] employees, String[][] departments) {
        StringBuilder stringBuilder = new StringBuilder();
        addEmployeeHeader(stringBuilder);
        Arrays.stream(employees).forEach(e -> fillEmployeesFullData(stringBuilder, e, departments));
        return stringBuilder.toString().trim();
    }

    private void addEmployeeHeader(StringBuilder stringBuilder) {
        stringBuilder
                .append("N").append(outputSeparator)
                .append("NAME").append(outputSeparator)
                .append("SURNAME").append(outputSeparator)
                .append("DEPARTMENT").append(System.lineSeparator());
    }

    private void fillEmployeesFullData(StringBuilder stringBuilder, String[] employee, String[][] departments) {
        addEmployeeData(stringBuilder, employee);
        addDepartmentData(stringBuilder, departments, employee);
    }

    private void addEmployeeData(StringBuilder stringBuilder, String[] employee) {
        try {
            stringBuilder
                    .append(employee[EMPLOYEE_ID]).append(outputSeparator)
                    .append(employee[EMPLOYEE_NAME]).append(outputSeparator)
                    .append(employee[EMPLOYEE_SURNAME]).append(outputSeparator);
        } catch (Exception e) {
            throw new ParseFileException("Cannot parse row");
        }
    }

    private void addDepartmentData(StringBuilder stringBuilder, String[][] departments, String[] employee) {
        for (String[] department : departments) {
            if (employee[EMPLOYEE_DEPT_ID].equals(department[DEPARTMENT_ID])) {
                stringBuilder.append(department[DEPARTMENT_NAME]);
                break;
            }
        }
        stringBuilder.append(System.lineSeparator());
    }
}
