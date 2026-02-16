package com.anil.airreportscheduler.validation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StationCodeValidatorTest {

    private StationCodeValidator validator;

    @BeforeEach
    void setUp() {
        validator = new StationCodeValidator();
    }

    @Test
    void testValidStationCode_ThreeLetters() {
        assertTrue(validator.isValid("JFK", null));
    }

    @Test
    void testValidStationCode_FourLetters() {
        assertTrue(validator.isValid("KJFK", null));
    }

    @Test
    void testInvalidStationCode_Null() {
        assertFalse(validator.isValid(null, null));
    }

    @Test
    void testInvalidStationCode_Blank() {
        assertFalse(validator.isValid("", null));
    }

    @Test
    void testInvalidStationCode_Numbers() {
        assertFalse(validator.isValid("123", null));
    }

    @Test
    void testInvalidStationCode_LowerCase() {
        assertFalse(validator.isValid("kjfk", null));
    }

    @Test
    void testInvalidStationCode_TooShort() {
        assertFalse(validator.isValid("KJ", null));
    }

    @Test
    void testInvalidStationCode_TooLong() {
        assertFalse(validator.isValid("KJFKX", null));
    }

    @Test
    void testInvalidStationCode_MixedCase() {
        assertFalse(validator.isValid("KjFk", null));
    }
}
