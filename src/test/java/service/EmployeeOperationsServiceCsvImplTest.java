package service;

import exception.ParseFileException;
import exception.ReadFileException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class EmployeeOperationsServiceCsvImplTest {
    private EmployeeOperationsService operationsService;

    private static final String EMPLOYEE_FILE_PATH = "Employee.csv";
    private static final String DEPARTMENTS_FILE_PATH = "Departments.csv";
    private static final String INVALID_FILE_PATH = "123.csv";
    private static final String INPUT_FILE_SEPARATOR = ";";
    private static final String OUTPUT_FILE_SEPARATOR = "    ";
    private static final String INVALID_INPUT_FILE_SEPARATOR = "/";
    private static final String LINE_SEPARATOR = System.lineSeparator();

    @Test
    void getEmployeeData_nullInputFileName() {
        operationsService = new EmployeeOperationsServiceCsvImpl(null, DEPARTMENTS_FILE_PATH);
        try {
            operationsService.getEmployeeData();
        } catch (ReadFileException e) {
            return;
        }
        Assertions.fail("ReadFileException should be thrown for NULL inputEmployeeFileName");
    }

    @Test
    void getEmployeeData_nullOutputFileName() {
        operationsService = new EmployeeOperationsServiceCsvImpl(EMPLOYEE_FILE_PATH, null);
        try {
            operationsService.getEmployeeData();
        } catch (ReadFileException e) {
            return;
        }
        Assertions.fail("ReadFileException should be thrown for NULL inputDepartmentFileName");
    }

    @Test
    void getEmployeeData_nullInputAndOutputFileName() {
        operationsService = new EmployeeOperationsServiceCsvImpl(null, null);
        try {
            operationsService.getEmployeeData();
        } catch (ReadFileException e) {
            return;
        }
        Assertions.fail("ReadFileException should be thrown for NULL inputEmployeeFileName " +
                "and inputDepartmentFileName");
    }

    @Test
    void getEmployeeData_invalidInputFileName() {
        operationsService = new EmployeeOperationsServiceCsvImpl(INVALID_FILE_PATH, DEPARTMENTS_FILE_PATH);
        try {
            operationsService.getEmployeeData();
        } catch (ReadFileException e) {
            return;
        }
        Assertions.fail("ReadFileException should be thrown for invalid inputEmployeeFileName");
    }

    @Test
    void getEmployeeData_invalidOutputFileName() {
        operationsService = new EmployeeOperationsServiceCsvImpl(EMPLOYEE_FILE_PATH, INVALID_FILE_PATH);
        try {
            operationsService.getEmployeeData();
        } catch (ReadFileException e) {
            return;
        }
        Assertions.fail("ReadFileException should be thrown for invalid inputDepartmentFileName");
    }

    @Test
    void getEmployeeData_invalidInputAndOutputFileName() {
        operationsService = new EmployeeOperationsServiceCsvImpl(INVALID_FILE_PATH, INVALID_FILE_PATH);
        try {
            operationsService.getEmployeeData();
        } catch (ReadFileException e) {
            return;
        }
        Assertions.fail("ReadFileException should be thrown for invalid inputEmployeeFileName " +
                "and inputDepartmentFileName");
    }

    @Test
    void getEmployeeData_defaultInputOutputSeparators() {
        operationsService = new EmployeeOperationsServiceCsvImpl(EMPLOYEE_FILE_PATH, DEPARTMENTS_FILE_PATH);
        String actual = operationsService.getEmployeeData();
        String expected = getValidExpectedResult(OUTPUT_FILE_SEPARATOR);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void getEmployeeData_defaultInputCustomOutputSeparators() {
        String customOutputSeparator = "____";
        operationsService = new EmployeeOperationsServiceCsvImpl(EMPLOYEE_FILE_PATH, DEPARTMENTS_FILE_PATH,
                INPUT_FILE_SEPARATOR, customOutputSeparator);
        String actual = operationsService.getEmployeeData();
        String expected = getValidExpectedResult(customOutputSeparator);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void getEmployeeData_defaultInputNullOutputSeparators() {
        try {
            operationsService = new EmployeeOperationsServiceCsvImpl(EMPLOYEE_FILE_PATH, DEPARTMENTS_FILE_PATH,
                    INPUT_FILE_SEPARATOR, null);
        } catch (RuntimeException e) {
            return;
        }
        Assertions.fail("RuntimeException should be thrown for NULL input separator");
    }

    @Test
    void getEmployeeData_nullInputDefaultOutputSeparators() {
        try {
            operationsService = new EmployeeOperationsServiceCsvImpl(EMPLOYEE_FILE_PATH, DEPARTMENTS_FILE_PATH,
                    null, OUTPUT_FILE_SEPARATOR);
        } catch (RuntimeException e) {
            return;
        }
        Assertions.fail("RuntimeException should be thrown for NULL output separator");
    }

    @Test
    void getEmployeeData_nullInputOutputSeparators() {
        try {
            operationsService = new EmployeeOperationsServiceCsvImpl(EMPLOYEE_FILE_PATH, DEPARTMENTS_FILE_PATH,
                    null, null);
        } catch (RuntimeException e) {
            return;
        }
        Assertions.fail("RuntimeException should be thrown for NULL input and output separators");
    }

    @Test
    void getEmployeeData_invalidInputFileSeparator() {
        operationsService = new EmployeeOperationsServiceCsvImpl(EMPLOYEE_FILE_PATH, DEPARTMENTS_FILE_PATH,
                INVALID_INPUT_FILE_SEPARATOR, OUTPUT_FILE_SEPARATOR);
        try {
            operationsService.getEmployeeData();
        } catch (ParseFileException e) {
            return;
        }
        Assertions.fail("ParseFileException should be thrown for invalid inputSeparator");
    }

    private String getValidExpectedResult(String separator) {
        return "N" + separator + "NAME" + separator + "SURNAME" + separator + "DEPARTMENT" + LINE_SEPARATOR +
                "1" + separator + "John" + separator + "Smith" + separator + "Management" + LINE_SEPARATOR +
                "2" + separator + "Pete" + separator + "Hallock" + separator + "Marketing" + LINE_SEPARATOR +
                "3" + separator + "Freddie" + separator + "Ruckman" + separator + "Production" + LINE_SEPARATOR +
                "4" + separator + "Lee" + separator + "Alen" + separator + "Production" + LINE_SEPARATOR +
                "5" + separator + "Paul" + separator + "Miller" + separator + "Finance" + LINE_SEPARATOR +
                "6" + separator + "David" + separator + "King" + separator + "Production" + LINE_SEPARATOR +
                "7" + separator + "Nicol" + separator + "Green" + separator + "Management";
    }
}
