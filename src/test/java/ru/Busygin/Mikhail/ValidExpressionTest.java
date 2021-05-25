package ru.Busygin.Mikhail;

import org.junit.Test;
import org.junit.Rule;
import org.junit.rules.ExpectedException;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.MatcherAssert.assertThat;

public class ValidExpressionTest {

    @Rule
    public final ExpectedException thrown = ExpectedException.none();

    @Test
    public void checkedWrongSymbolsTest() throws NotValidException {
        thrown.expect(NotValidException.class);
        thrown.expectMessage("Содержит посторонние символы или не содержит ничего, введите заного");
        new ValidExpression(new Console(new Scanner("aAzZ?")));
    }

    @Test
    public void validSymbolsTest() throws NotValidException {
        List<String> ex = Arrays.asList("1", "2", "+", "(", "3", "4", "-", "5", "6", ")", "*", "7", "8", "/", "9", "0");
        ValidExpression validEx = new ValidExpression(new Console(new Scanner("12 + (34 - 56) * 78 / 90")));
        assertThat(ex, equalTo(validEx.someExpressionArray));
    }

    @Test
    public void countParenthesesTest() throws NotValidException {
        ValidExpression expressionA = new ValidExpression(new Console(new Scanner("((1 + 2) * 2)")));
        ValidExpression expressionB = new ValidExpression(new Console(new Scanner("(12 / (4 - 2))")));
        ValidExpression expressionC = new ValidExpression(new Console(new Scanner("(2 + (4 - 2) * 2)")));
        assertThat(2, allOf(
                equalTo(expressionA.parentheses),
                equalTo(expressionB.parentheses),
                equalTo(expressionC.parentheses)));
    }

    @Test
    public void openCountParenthesesTest() throws NotValidException {
        thrown.expect(NotValidException.class);
        thrown.expectMessage("Не все скобки открыты");
        new ValidExpression(new Console(new Scanner("1)")));
    }

    @Test
    public void closeCountParenthesesTest() throws NotValidException {
        thrown.expect(NotValidException.class);
        thrown.expectMessage("Не все скобки закрыты");
        new ValidExpression(new Console(new Scanner("(1")));
    }

    @Test
    public void correctStartEndTest() throws NotValidException {
        thrown.expect(NotValidException.class);
        thrown.expectMessage("Некорректное местоположение знаков операций или разделительного знака");
        new ValidExpression(new Console(new Scanner("-2")));
    }

    @Test
    public void doubleCorrectExpressionTest() throws NotValidException {
        thrown.expect(NotValidException.class);
        thrown.expectMessage("Между знаками операций нет чисел");
        new ValidExpression(new Console(new Scanner("2++2")));
    }

    @Test
    public void negativeNumberTest() throws NotValidException {
        List<String> ex = Arrays.asList("(", "0", "-", "2", ")");
        ValidExpression validEx = new ValidExpression(new Console(new Scanner("( - 2 )")));
        assertThat(ex, equalTo(validEx.someExpressionArray));
    }

    @Test
    public void parenthesesCorrectExpressionTest() throws NotValidException {
        thrown.expect(NotValidException.class);
        thrown.expectMessage("Недопустимое расположение скобок");
        new ValidExpression(new Console(new Scanner("2()2")));
    }

    @Test
    public void correctNumbersTest() throws NotValidException {
        thrown.expect(NotValidException.class);
        thrown.expectMessage("Некорректные числа в выражении, или их местоположение");
        new ValidExpression(new Console(new Scanner("2.2.2")));
    }
}
