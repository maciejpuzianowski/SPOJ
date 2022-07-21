import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PALINTest {
    @Test
    void palinTest(){
        assertEquals("818", PALIN.PalinSearch.search("808"));
        assertEquals("2222", PALIN.PalinSearch.search("2133"));
        assertEquals("10000001", PALIN.PalinSearch.search("9999999"));
        assertEquals("1000001", PALIN.PalinSearch.search("999999"));
        assertEquals("3423443243", PALIN.PalinSearch.search("3423355356"));
        assertEquals("101101", PALIN.PalinSearch.search("100001"));
        assertEquals("46888864", PALIN.PalinSearch.search("46887767"));
        assertEquals("11", PALIN.PalinSearch.search("9"));
        assertEquals("101", PALIN.PalinSearch.search("99"));
        assertEquals("1", PALIN.PalinSearch.search("0"));
        assertEquals("2002", PALIN.PalinSearch.search("1991"));
    }
}