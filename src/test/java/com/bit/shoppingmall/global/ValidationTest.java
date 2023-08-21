package com.bit.shoppingmall.global;

import com.bit.shoppingmall.exception.FormatException;
import com.bit.shoppingmall.exception.RangeException;
import com.bit.shoppingmall.exception.SizeException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ValidationTest {
    Validation validation = new Validation();
    @BeforeEach
    public void before(){
        validation = new Validation();
    }

    @Test
    public void validateIntTest(){
        assertTrue(validation.validateInt("age","11",false,1,2));
        assertTrue(validation.validateInt("age","2",true,1,2));
        assertTrue(validation.validateInt("age","2",false,1,200000));
        assertThrows(FormatException.class, () -> validation.validateInt("age", "csh", true, 1, 2));
        assertThrows(RangeException.class, () -> validation.validateInt("age","11",true,1,2));
        assertThrows(FormatException.class, () -> validation.validateInt("age", "21000000000", true, 1, 2));
    }

    @Test
    public void validateLongTest(){
        assertTrue(validation.validateLong("age","11",false,1,2));
        assertTrue(validation.validateLong("age","2",true,1,2));
        assertTrue(validation.validateLong("age","2",false,1,200000));
        assertThrows(FormatException.class, () -> validation.validateLong("age", "csh", true, 1, 2));
        assertThrows(RangeException.class, () -> validation.validateLong("age","11",true,1,2));
        assertThrows(FormatException.class, () -> validation.validateLong("age", "2100000000000000000000000000000", true, 1, 2));
    }

    @Test
    public void validateString(){
        assertTrue(validation.validateString("name","최성훈",6));
        assertThrows(SizeException.class, () -> assertTrue(validation.validateString("name","최성훈",5)));
    }
}