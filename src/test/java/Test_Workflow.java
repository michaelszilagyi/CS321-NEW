//package test;
import static org.junit.Assert.*;
import org.junit.jupiter.api.Test;
import java.util.HashMap;

//import Task;
//import WorkflowTable;
//import WorkflowTable.Step;

import java.util.*;

/* Test the methods in WorkflowTable.
 * Tests must be run from top to bottom, as subsequent tests use
 * persistent state from previous ones.
 */
public class Test_Workflow {
    int id = 1;
    
    //Checks that NULL is returned upon empty workflow for review
    @Test
    void test_get_1(){
      Integer ret = 0;
      ret = WorkflowTable.getTask(WorkflowTable.Step.REVIEW);  
      assertEquals(null, ret);
    }

    //Checks that NULL is returned upon empty workflow for entry
    @Test
    void test_get_2(){
        Integer ret = 0;
        ret = WorkflowTable.getTask(WorkflowTable.Step.ENTRY);  
        assertEquals(null, ret);
    }
    
    //Checks that NULL is returned upon empty workflow for approval
    @Test
    void test_get_3(){
        Integer ret = 0;
        ret = WorkflowTable.getTask(WorkflowTable.Step.APPROVAL);  
        assertEquals(null, ret);
    }

    //Checks that the expected first entry is returned
    @Test
    void test_get_4(){
        WorkflowTable.addTask(111, WorkflowTable.Step.REVIEW);
        WorkflowTable.addTask(222, WorkflowTable.Step.REVIEW);
        WorkflowTable.addTask(333, WorkflowTable.Step.REVIEW);
        Integer ret = 0;
        ret = WorkflowTable.getTask(WorkflowTable.Step.REVIEW);  
        assertEquals((Integer)111, ret);
        reset();
    }

    //Checks that the expected first entry is returned
    @Test
    void test_get_5(){
        WorkflowTable.addTask(111, WorkflowTable.Step.APPROVAL);
        WorkflowTable.addTask(222, WorkflowTable.Step.APPROVAL);
        WorkflowTable.addTask(333, WorkflowTable.Step.APPROVAL);
        Integer ret = 0;
        ret = WorkflowTable.getTask(WorkflowTable.Step.APPROVAL);  
        assertEquals((Integer)111, ret);
        reset();
    }

    //Checks that the declaration id returned is an actual Integer
    @Test
    void test_get_6(){
        WorkflowTable.addTask(111, WorkflowTable.Step.REVIEW);
        WorkflowTable.addTask(222, WorkflowTable.Step.REVIEW);
        WorkflowTable.addTask(333, WorkflowTable.Step.REVIEW);
        Integer ret = 0;
        ret = WorkflowTable.getTask(WorkflowTable.Step.REVIEW);
        assertTrue(ret instanceof Integer);  
        reset();
    }

    //Checks that the correct first entry of Approval is returned given a series of adding and removing of all stages
    @Test
    void test_get_7(){
        WorkflowTable.addTask(111, WorkflowTable.Step.REVIEW);
        WorkflowTable.addTask(222, WorkflowTable.Step.APPROVAL);
        WorkflowTable.addTask(333, WorkflowTable.Step.ENTRY);
        WorkflowTable.getTask(WorkflowTable.Step.REVIEW);
        Integer ret = 0;
        ret = WorkflowTable.getTask(WorkflowTable.Step.APPROVAL);  
        assertEquals((Integer)222, ret);
        reset();
    }

    //Checks for a null paramter for the get function
    @Test
    void test_get_8(){
        WorkflowTable.addTask(111, WorkflowTable.Step.REVIEW);
        WorkflowTable.addTask(222, WorkflowTable.Step.APPROVAL);
        WorkflowTable.addTask(333, WorkflowTable.Step.ENTRY);
        Integer ret = 0;
        ret = WorkflowTable.getTask(null);  
        assertEquals(null, ret);
        reset();
    }

    //Tests a basic add to the workflow for review
    @Test
    void test_add_1(){
        WorkflowTable.addTask(222, WorkflowTable.Step.REVIEW);
        Integer ret = 0;
        ret = WorkflowTable.getTask(WorkflowTable.Step.REVIEW);  
        assertEquals((Integer)222, ret);
        reset();
    }

    //Tests a basic add to approval that is attempted to be accessed by the Review
    @Test
    void test_add_2(){
        WorkflowTable.addTask(222, WorkflowTable.Step.APPROVAL);
        Integer ret = 0;
        ret = WorkflowTable.getTask(WorkflowTable.Step.REVIEW);  
        assertEquals(null, ret);
        reset();
    }

    //Tests a basic add to the workflow for approval
    @Test
    void test_add_3(){
        WorkflowTable.addTask(999, WorkflowTable.Step.APPROVAL);
        Integer ret = 0;
        ret = WorkflowTable.getTask(WorkflowTable.Step.APPROVAL);  
        assertEquals((Integer)999, ret);
        reset();
    }

    //Tests a basic add to the workflow for entry
    @Test
    void test_add_4(){
        WorkflowTable.addTask(535, WorkflowTable.Step.ENTRY);
        Integer ret = 0;
        ret = WorkflowTable.getTask(WorkflowTable.Step.ENTRY);  
        assertEquals((Integer)535, ret);
        reset();
    }

    //Tests a add and removal for entry
    @Test
    void test_remove_1(){
        WorkflowTable.addTask(555, WorkflowTable.Step.ENTRY);
        Integer ret = 0;
        ret = WorkflowTable.getTask(WorkflowTable.Step.ENTRY);
        WorkflowTable.removeTask(ret);  
        ret = WorkflowTable.getTask(WorkflowTable.Step.ENTRY);
        assertEquals(null, ret);
        reset();
    }

    //Tests a add and removal for approval
    @Test
    void test_remove_2(){
        WorkflowTable.addTask(555, WorkflowTable.Step.APPROVAL);
        Integer ret = 0;
        ret = WorkflowTable.getTask(WorkflowTable.Step.APPROVAL);
        WorkflowTable.removeTask(ret);  
        ret = WorkflowTable.getTask(WorkflowTable.Step.APPROVAL);
        assertEquals(null, ret);
        reset();
    }

    //Tests a add and removal for review
    @Test
    void test_remove_3(){
        WorkflowTable.addTask(555, WorkflowTable.Step.REVIEW);
        Integer ret = 0;
        ret = WorkflowTable.getTask(WorkflowTable.Step.REVIEW);
        WorkflowTable.removeTask(ret);  
        ret = WorkflowTable.getTask(WorkflowTable.Step.REVIEW);
        assertEquals(null, ret);
        reset();
    }

    //Tests the case in which null is entered for the remove function parameter, therefore removing nothing from the workflow
    @Test
    void test_remove_4(){
        WorkflowTable.addTask(555, WorkflowTable.Step.REVIEW);
        WorkflowTable.addTask(888, WorkflowTable.Step.REVIEW);
        Integer ret = 0;
        ret = WorkflowTable.getTask(WorkflowTable.Step.REVIEW);
        WorkflowTable.removeTask(null);  
        ret = WorkflowTable.getTask(WorkflowTable.Step.REVIEW);
        assertEquals((Integer)555, ret);
        reset();
    }

    //Resets the Workflow table LinkedHashMap - used only for testing
    private void reset(){
        Integer E = WorkflowTable.getTask(WorkflowTable.Step.ENTRY);
        WorkflowTable.removeTask(E);
        Integer A = WorkflowTable.getTask(WorkflowTable.Step.APPROVAL);
        WorkflowTable.removeTask(A);
        Integer R = WorkflowTable.getTask(WorkflowTable.Step.REVIEW);
        WorkflowTable.removeTask(R);

        while(E != null || A != null || R != null){
            E = WorkflowTable.getTask(WorkflowTable.Step.ENTRY);
            WorkflowTable.removeTask(E);
            A = WorkflowTable.getTask(WorkflowTable.Step.APPROVAL);
            WorkflowTable.removeTask(A);
            R = WorkflowTable.getTask(WorkflowTable.Step.REVIEW);
            WorkflowTable.removeTask(R);
        }
    }


    
  

    



    
}
