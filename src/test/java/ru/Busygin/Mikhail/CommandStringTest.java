package ru.Busygin.Mikhail;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.List;
import java.util.Arrays;

@RunWith(Parameterized.class)
public class CommandStringTest {
    private final List<String> value;
    private final List<String> expected;
    private final int countParentheses;

    public CommandStringTest(List<String> value, List<String> expected, int countParentheses) {
        this.value = value;
        this.expected = expected;
        this.countParentheses = countParentheses;
    }

    @Parameterized.Parameters(name = "{index} : command {0} = {1}")
    public static Iterable<Object[]> dataForTest() {
        return Arrays.asList(new Object[][]{
                {Arrays.asList("1", "-", "3", "/", "0"),
                        Arrays.asList("1", "3", "0", "/", "-"), 0},
                {Arrays.asList("(", "0", "-", "5", ")", "*", "7"),
                        Arrays.asList("0", "5", "-", "7", "*"), 1},
                {Arrays.asList("1", "*", "(", "2", "-", "(", "3", "+", "4", ")", ")"),
                        Arrays.asList("1", "2", "3", "4", "+", "-", "*"), 2},
                {Arrays.asList("(", "1", "+", "2", ")", "/", "(", "3", "+", "4", ")"),
                        Arrays.asList("1", "2", "+", "3", "4", "+", "/"), 2},
                {Arrays.asList("1", "*", "(", "(", "6", "-", "(", "7", "+", "8", ")", ")", "/", "9", ")"),
                        Arrays.asList("1", "6", "7", "8", "+", "-", "9", "/", "*"), 3},
                {Arrays.asList("2", "*", "2", "/", "4"),
                        Arrays.asList("2", "2", "*", "4", "/"), 0},
                {Arrays.asList("2", "-", "2", "+", "4"),
                        Arrays.asList("2", "2", "-", "4", "+"), 0},
                {Arrays.asList("2", "*", "2", "+", "4"),
                        Arrays.asList("2", "2", "*", "4", "+"), 0},
        });
    }

    @Test
    public void getNumbersTest() {
        CommandString actual = new CommandString(value, countParentheses);
        assertThat(expected, equalTo(actual.dataOut));
    }
}
