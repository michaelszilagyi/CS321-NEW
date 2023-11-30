//package test;
import static org.junit.Assert.*;
import org.junit.jupiter.api.Test;

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
    
    @Test
    void test_get_1(){
      Integer ret = 0;
      ret = WorkflowTable.getTask(WorkflowTable.Step.REVIEW);  
      assertEquals(ret, null);
    }

    @Test
    void test_get_2(){
        Integer ret = 0;
        ret = WorkflowTable.getTask(WorkflowTable.Step.ENTRY);  
        assertEquals(ret, null);
    }
    
    @Test
    void test_get_3(){
        Integer ret = 0;
        ret = WorkflowTable.getTask(WorkflowTable.Step.APPROVAL);  
        assertEquals(ret, null);
    }
    
  

    



    
}
