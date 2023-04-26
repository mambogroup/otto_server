package com.mambo.otto.ottoserver.util;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class ParseTest {

    @Test
    public void chateTest_test() {
        String val = "1";

        int result = Integer.parseInt(val);

        System.out.println("확인");
        System.out.println(result);

        assertEquals(1, result);

    }

}
