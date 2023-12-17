package com.restaurant.utils;

import com.restaurant.util.Utils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class UtilsTest {

    @Test
    @DisplayName("Remove spaces")
    void removeSpaces_Success_MultipleSpaces() {
        // Test removing spaces
        String input = "   hello   world  ";
        String expectedOutput = "hello world";
        String actualOutput = Utils.removeSpaces(input);
        Assertions.assertEquals(expectedOutput, actualOutput);
    }

    @Test
    @DisplayName("Remove spaces and lower case")
    void removeSpacesAndLowerCase_Success_MixedCaseInputAndMultipleSpaces() {
        // Test removing spaces and converting to lower case
        String input = "   HeLLo WORLd   ";
        String expectedOutput = "hello world";
        String actualOutput = Utils.removeSpacesAndLowerCase(input);
        Assertions.assertEquals(expectedOutput, actualOutput);
    }

}
