import java.util.*;
import static org.junit.Assert.*;
import org.junit.jupiter.api.Test;
import java.util.HashMap;

/*
 * Tests the methods in Declaration
 */
public class Test_Declaration {
    
    /* CREATE FUNCTION TESTS */

    /* Tests create with regular input */
    @Test
    void test_create_1(){
        Declaration dec = Declaration.create("12/01/2023", "John", "john@gmail.com", 5, 123, "Doe", 456, false);
        
        assertNotNull(dec);
        assertEquals("12/01/2023", dec.date);
        assertEquals("John", dec.name);
        assertEquals("john@gmail.com", dec.email);
        assertEquals(5, dec.durationOfSupport);
        assertEquals(123, dec.applicantNumber);
        assertEquals("Doe", dec.immigrantName);
        assertEquals(456, dec.alienNumber);
        assertFalse(dec.isExpired);
    }

    /* Tests create with regular input 2x */
    @Test
    void test_create_2(){
        Declaration dec = Declaration.create("06/12/2020", "Hiba", "hiba@gmail.com", 3, 456, "Awan", 789, false);
        
        assertNotNull(dec);
        assertEquals("06/12/2020", dec.date);
        assertEquals("Hiba", dec.name);
        assertEquals("hiba@gmail.com", dec.email);
        assertEquals(3, dec.durationOfSupport);
        assertEquals(456, dec.applicantNumber);
        assertEquals("Awan", dec.immigrantName);
        assertEquals(789, dec.alienNumber);
        assertFalse(dec.isExpired);
    }

    /* Tests create with null values */
    @Test
    void test_create_3(){
        Declaration dec = Declaration.create(null, null, null, -1, -1, null, -1, true);

        assertNotNull(dec);
        assertNull(dec.date);
        assertNull(dec.name);
        assertNull(dec.email);
        assertEquals(-1, dec.durationOfSupport);
        assertEquals(-1, dec.applicantNumber);
        assertNull(dec.immigrantName);
        assertEquals(-1, dec.alienNumber);
        assertTrue(dec.isExpired);
    }

    /* Tests create by comparing two declarations */
    @Test
    void test_create_4(){
        Declaration dec1 = Declaration.create("12/01/2021", "John", "john@gmail.com", 5, 12345, "Doe", 9876, false);
        Declaration dec2 = Declaration.create("12/02/2020", "Jane", "jane@gmail.com", 4, 54321, "Smith", 6543, true);

        assertNotNull(dec1);
        assertNotNull(dec2);
        assertNotEquals(dec1.declarationID, dec2.declarationID);
    }

    /* GETFROMDB FUNCTION TESTS */

    /* Tests with existing id */
    @Test
    void test_get_1(){
        Declaration dec = new Declaration("12/01/2023", "John", "john@gmail.com", 5, 123, "Doe", 456, false, 321);
        MainScreen.database.add(dec);

        Declaration retrievedDec = Declaration.getFromDB(321);

        assertNotNull(retrievedDec);
        assertEquals(321, retrievedDec.declarationID);
        assertEquals("John", retrievedDec.name);
    }

    /* Tests with non-existing id */
    @Test
    void test_get_2(){
        MainScreen.database.clear();

        Declaration dec = Declaration.getFromDB(999);

        assertNull(dec);
    }

    /* Tests with empty database */
    @Test
    void test_get_3(){
        MainScreen.database.clear();

        Declaration retrievedDeclaration = Declaration.getFromDB(321);

        assertNull(retrievedDeclaration);
    }

    /* Tests with multiple declarations */
    @Test
    void test_get_4(){
        Declaration dec1 = new Declaration("06/12/2020", "Hiba", "hiba@gmail.com", 2, 259, "Awan", 371, false, 604);
        Declaration dec2 = new Declaration("02/27/2019", "Mehr", "mehr@gmail.com", 6, 537, "Bano", 416, true, 713);
        MainScreen.database.add(dec1);
        MainScreen.database.add(dec2);

        Declaration retrieveDec1 = Declaration.getFromDB(604);
        Declaration retrieveDec2 = Declaration.getFromDB(713);

        assertNotNull(retrieveDec1);
        assertNotNull(retrieveDec2);
        assertEquals(604, retrieveDec1.declarationID);
        assertEquals(713, retrieveDec2.declarationID);
    }

    /* ADD FUNCTION TESTS */

    @Test
    void test_add_1(){
        MainScreen.database.clear();
    }
}
