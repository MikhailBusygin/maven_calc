package ru.Busygin.Mikhail;

import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

public class ValidExpression {

    private final String[] operations = {"\\.", "-", "\\+", "/", "\\*"}; //перечень операций с защитой в виде "\\"
    private final List<String> someParenthesesCases = Arrays.asList(").", ".(", "()", ")("); //отдельные случаи
    private List<String> cases; //варианты последовательностей символов, которые должны отсутствовать
    private List<String> singleOperations; //набор операций без защиты "\\"
    private List<String> addParentheses; //набор недопустимых вариантов операций со скобками
    private String regexExpression; //допустимые символы ввода operations + скобки и числа
    public int parentheses; //для подсчета количества пар скобок
    private String someExpression; //входная строка
    public List<String> someExpressionArray; //выходная строка в виде коллекции

    public ValidExpression(Console console) throws NotValidExpressionException {
        this.someExpression = console.expression;
        setOptions();
        checkedWrongSymbols();
        countParentheses();
        correctStartEnd();
        correctExpression();
        correctNumbers();
    }

    public void setOptions() {
        String simpleOperation;
        regexExpression = "\\d|\\)|\\(";
        cases = new ArrayList<>();
        singleOperations = new ArrayList<>();
        addParentheses = new ArrayList<>();
        for (String operation : operations) {
            regexExpression = regexExpression.concat("|" + operation);
            simpleOperation = operation.replace("\\", "");
            singleOperations.add(simpleOperation);
            addParentheses.add("(" + simpleOperation);
            addParentheses.add(simpleOperation + ")");
            addParentheses.addAll(someParenthesesCases);
            for (String secondOperation : operations) {
                cases.add(simpleOperation + secondOperation.replace("\\", ""));
            }
        }
    }

    public void checkedWrongSymbols() throws NotValidExpressionException {
        someExpressionArray = Arrays.asList(someExpression.split(""));
        if (!someExpressionArray.stream().allMatch(n -> n.matches(regexExpression))) {
            throw new NotValidExpressionException("Содержит посторонние символы или не содержит ничего, введите заново");
        }
    }

    public void countParentheses() throws NotValidExpressionException {
        int openParentheses = 0;
        int closeParentheses = 0;
        for (String symbol : someExpressionArray) {
            if (symbol.equals("(")) {
                openParentheses++;
            }
            if (symbol.equals(")")) {
                closeParentheses++;
            }
            if (openParentheses < closeParentheses) {
                throw new NotValidExpressionException("Не все скобки открыты");
            }
        }
        if (openParentheses == closeParentheses) {
            parentheses = openParentheses;
        } else {
            throw new NotValidExpressionException("Не все скобки закрыты");
        }
    }

    public void correctStartEnd() throws NotValidExpressionException {
        for (String singleOperation : singleOperations) {
            if (someExpression.startsWith(singleOperation) || someExpression.endsWith(singleOperation)) {
                throw new NotValidExpressionException("Некорректное местоположение знаков операций или разделительного знака");
            }
        }
    }

    public void correctExpression() throws NotValidExpressionException {
        int sizeExpression = someExpressionArray.size() - 1;
        for (int i = 0; i < sizeExpression; i ++) {
            String negativeCaseArrayExpression = someExpressionArray.get(i) + someExpressionArray.get(i + 1);
            if (negativeCaseArrayExpression.equals("(-")) {
                someExpression = someExpression.substring(0, i + 1) + "0" + someExpression.substring(i + 1);
                someExpressionArray = Arrays.asList((someExpression).split(""));
                sizeExpression++;
            }
        }
        for (int i = 0; i < sizeExpression; i ++) {
            String caseArrayExpression = someExpressionArray.get(i) + someExpressionArray.get(i + 1);
            for (String symbolsEx : cases) {
                if ((caseArrayExpression).equals(symbolsEx)) {
                    throw new NotValidExpressionException("Между знаками операций нет чисел");
                }
            }
            for (String parenthesesCases : addParentheses) {
                if ((caseArrayExpression).equals(parenthesesCases)) {
                    throw new NotValidExpressionException("Недопустимое расположение скобок");
                }
            }
        }
    }

    public void correctNumbers() throws NotValidExpressionException {
        String[] wrongNumbers = someExpression.split("\\d+");
        for (int i = 0; i < wrongNumbers.length - 1; i++) {
            if (wrongNumbers[i].equals(".") && wrongNumbers[i + 1].equals(".")) {
                throw new NotValidExpressionException("Некорректные числа в выражении, или их местоположение");
            }
        }
    }
}