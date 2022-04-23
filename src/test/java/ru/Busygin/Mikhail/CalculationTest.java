package ru.Busygin.Mikhail;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

@RunWith(Parameterized.class)
public class CalculationTest {
    private final List<String> value;
    private final String expected;

    public CalculationTest(List<String> value, String expected) {
        this.value = value;
        this.expected = expected;
    }

    @Parameterized.Parameters(name = "{index} : command {0} = {1}")
    public static Iterable<Object[]> dataForTest() {
        return Arrays.asList(new Object[][]{
                {Arrays.asList("12", "34", "-"), "-22.0"},
                {Arrays.asList("56", "78", "+"), "134.0"},
                {Arrays.asList("90", "12", "*"), "1080.0"},
                {Arrays.asList("34", "56", "/"), "0.6"},
                {Arrays.asList("789", "0", "/"), "NaN"},
                {Arrays.asList("12", "4", "/", "5", "*", "5", "-"), "10.0"},
                {Arrays.asList("12", "4", "/", "5", "*", "5", "+", "10", "/", "2", "*"), "4.0"},
                {Arrays.asList("1"), "1.0"}
        });
    }

    @Rule
    public final ExpectedException thrown = ExpectedException.none();

    @Test
    public void shuffleListExpressionTest() throws IllegalArgumentException {
        if (value.equals(Arrays.asList("789", "0", "/"))) {
            thrown.expect(IllegalArgumentException.class);
            thrown.expectMessage("Делить на ноль нельзя!");
        }
        Calculation expressionC = new Calculation(new ArrayList<>(value));
        assertThat(expected, equalTo(expressionC.shuffleListExpression()));
    }
}
