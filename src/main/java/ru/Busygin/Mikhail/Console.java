package ru.Busygin.Mikhail;

import java.util.Scanner;

public class Console {

    public String expression;

    public Console (Scanner sc) {
        this.expression = sc.nextLine().replaceAll(" ", "");
    }
}