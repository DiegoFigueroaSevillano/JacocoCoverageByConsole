package org.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class MyClassTest {

    @Test
    void testAdd() {
        MyClass myClass = new MyClass();
        assertEquals(5, myClass.add(2, 3));
    }

    @Test
    void testSubtract() {
        MyClass myClass = new MyClass();
        assertEquals(1, myClass.subtract(3, 2));
    }
}
