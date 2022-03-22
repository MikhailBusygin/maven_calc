package ru.Busygin.Mikhail;

import org.junit.Test;

import java.util.Scanner;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ConsoleTest {

    @Test
    public void getExpressionTest() {
        assertThat("aAzZ1234567890+-*/.",
                equalTo(new Console(new Scanner("aAzZ1234567890+-*/.")).expression));
    }
}