package other;

import org.junit.jupiter.api.Test;

public class StaticTest {
    public static Integer i;

    public Integer j;

    @Test
    public void test1(){
        StaticTest.i = 1;
        System.out.println(StaticTest.i);
    }


    public static void main(String[] args) {
        StaticTest staticTest = new StaticTest();
        staticTest.j = 2;

        
    }
}
