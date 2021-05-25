package ru.Busygin.Mikhail;

import java.util.Scanner;

public class Console {

    public String inExpression;

    public Console (Scanner sc) {
        this.inExpression = sc.nextLine().replaceAll(" ", "");
    }
}