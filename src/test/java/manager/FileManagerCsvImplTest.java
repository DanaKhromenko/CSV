package manager;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

class FileManagerCsvImplTest {
    private FileManager fileManager = new FileManagerCsvImpl();
    private static final String DEPARTMENTS_FILE_PATH = "Departments.csv";
    private static final String EMPLOYEE_FILE_PATH = "Employee.csv";
    private static final String INVALID_FILE_PATH = "123.csv";

    @Test
    void getListOfAllRowsFromFile_nullPath() {
        try {
            fileManager.getListOfAllRowsFromFile(null, false);
        } catch (IOException e) {
            return;
        }
        Assertions.fail("IOException should be caught for the NULL path");
    }

    @Test
    void getListOfAllRowsFromFile_unrealPath() {
        try {
            fileManager.getListOfAllRowsFromFile(INVALID_FILE_PATH, false);
        } catch (IOException e) {
            return;
        }
        Assertions.fail("IOException should be caught for the path [" + INVALID_FILE_PATH + "]");
    }

    @Test
    void getListOfAllRowsFromFile_withTheHeader() {
        List<String> expected = new ArrayList<>();
        expected.add("id;name");
        expected.add("1;Marketing");
        expected.add("2;Management");
        expected.add("3;Finance");
        expected.add("4;Production");
        List<String> actual;
        try {
            actual = fileManager.getListOfAllRowsFromFile(DEPARTMENTS_FILE_PATH, false);
        } catch (IOException e) {
            Assertions.fail("File [" + DEPARTMENTS_FILE_PATH + "] should be found!");
            return;
        }
        Assertions.assertEquals(expected, actual);

        expected = new ArrayList<>();
        expected.add("id;name;surname;department_id");
        expected.add("1;John;Smith;2");
        expected.add("2;Pete;Hallock;1");
        expected.add("3;Freddie;Ruckman;4");
        expected.add("4;Lee;Alen;4");
        expected.add("5;Paul;Miller;3");
        expected.add("6;David;King;4");
        expected.add("7;Nicol;Green;2");

        try {
            actual = fileManager.getListOfAllRowsFromFile(EMPLOYEE_FILE_PATH, false);
        } catch (IOException e) {
            Assertions.fail("File [" + EMPLOYEE_FILE_PATH + "] should be found!");
            return;
        }
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void getListOfAllRowsFromFile_skipTheHeader() {
        List<String> expected = new ArrayList<>();
        expected.add("1;Marketing");
        expected.add("2;Management");
        expected.add("3;Finance");
        expected.add("4;Production");
        List<String> actual;
        try {
            actual = fileManager.getListOfAllRowsFromFile(DEPARTMENTS_FILE_PATH, true);
        } catch (IOException e) {
            Assertions.fail("File [" + DEPARTMENTS_FILE_PATH + "] should be found!");
            return;
        }
        Assertions.assertEquals(expected, actual);

        expected = new ArrayList<>();
        expected.add("1;John;Smith;2");
        expected.add("2;Pete;Hallock;1");
        expected.add("3;Freddie;Ruckman;4");
        expected.add("4;Lee;Alen;4");
        expected.add("5;Paul;Miller;3");
        expected.add("6;David;King;4");
        expected.add("7;Nicol;Green;2");

        try {
            actual = fileManager.getListOfAllRowsFromFile(EMPLOYEE_FILE_PATH, true);
        } catch (IOException e) {
            Assertions.fail("File [" + EMPLOYEE_FILE_PATH + "] should be found!");
            return;
        }
        Assertions.assertEquals(expected, actual);
    }
}
