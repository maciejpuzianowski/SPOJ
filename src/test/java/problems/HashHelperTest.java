package problems;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HashHelperTest {

    @Test
    void add() {
        HashHelper testHelper = new HashHelper();

        testHelper.add("ABCDEFGHIJK");
        assertEquals(1,testHelper.getTable().size());
        testHelper.add("ABCDEFGHIJK");
        assertEquals(1,testHelper.getTable().size());
        testHelper.add("TETSGS");
        assertEquals(2,testHelper.getTable().size());
        testHelper.add("ABCDEFGHIJK");
        assertEquals(2,testHelper.getTable().size());
        testHelper.add("ABCDEFHIJK");
        assertEquals(3,testHelper.getTable().size());
    }

    @Test
    void delete() {
        HashHelper testHelper = new HashHelper();

        testHelper.add("ABCDEFGHIJK");
        assertEquals(1,testHelper.getTable().size());
        testHelper.add("ABCDEFGHIJK");
        assertEquals(1,testHelper.getTable().size());
        testHelper.add("TETSGS");
        assertEquals(2,testHelper.getTable().size());
        testHelper.add("ABCDEFGHIJK");
        assertEquals(2,testHelper.getTable().size());
        testHelper.add("ABCDEFHIJK");
        assertEquals(3,testHelper.getTable().size());

        //removing
        testHelper.delete("ABCDEFGHIJK");
        assertEquals(2,testHelper.getTable().size());
        testHelper.delete("TETE");
        assertEquals(2,testHelper.getTable().size());
        testHelper.delete("233324");
        assertEquals(2,testHelper.getTable().size());
        testHelper.delete("TETSGS");
        assertEquals(1,testHelper.getTable().size());
        testHelper.delete("ABCDEFHIJK");
        assertEquals(0,testHelper.getTable().size());
    }

}