package ru.Busygin.Mikhail;

import java.util.Scanner;

public class App {
    public static void main(String [] args) throws NotValidExpressionException {
        //Выполнение запроса на ввод выражения и его проверка на валидность в цикле
        ValidExpression validExpression = new ValidExpression(new Console(new Scanner(System.in)));

        //Передача количества пар скобок и выражения в класс парсинга, парсинг выражения
        CommandString commandString = new CommandString(validExpression.someExpressionArray, validExpression.parentheses);

        //Передача выражения после парсинга для вычисления, вычисление результата и передача в консоль
        System.out.println(new Calculation(commandString.dataOut).shuffleListExpression());
    }
}
