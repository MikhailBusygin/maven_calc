package ru.Busygin.Mikhail;

import static ru.Busygin.Mikhail.App.main;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class AppTest {
    private final InputStream systemIn = System.in;
    private final PrintStream systemOut = System.out;

    private ByteArrayInputStream testIn;
    private ByteArrayOutputStream testOut;

    @Before
    public void setUpOutput() {
        testOut = new ByteArrayOutputStream();
        System.setOut(new PrintStream(testOut));
    }

    @Test
    public void mainTest() throws NotValidExpressionException {
        String data = "2 * (4 - 3) - (3 + 2) / 5 + 5 * ((10 - 5) * 5) / 25 - 5 + 2 * 2 / 4";
        System.setIn(new ByteArrayInputStream(data.getBytes()));
        main(new String[]{});
        assertThat("2.0\r\n", equalTo(testOut.toString()));
    }

    @After
    public void restoreSystemInputOutput() {
        System.setIn(System.in);
        System.setOut(System.out);
    }
}
