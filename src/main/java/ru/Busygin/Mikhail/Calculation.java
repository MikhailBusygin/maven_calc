package ru.Busygin.Mikhail;

import java.util.ArrayList;
import java.util.List;

public class Calculation {

    private final List<String> listExpression;
    private int indexCalculate = 0;

    public Calculation (List<String> ex) {
        this.listExpression = ex;
    }

    public String shuffleListExpression() throws NotValidExpressionException {
        List<String> listCalculate = new ArrayList<>();
        int firstExpression;
        do {
            firstExpression = listExpression.size();
            while (indexCalculate < listExpression.size()) {
                try {
                    listCalculate.add(countOperation(listExpression.get(indexCalculate + 2)));
                    indexCalculate++;
                } catch (IndexOutOfBoundsException indexOut) {
                    listCalculate.add(listExpression.get(indexCalculate));
                    indexCalculate++;
                }
            }
            indexCalculate = 0;
            listExpression.clear();
            listExpression.addAll(listCalculate);
            listCalculate.clear();
        } while (firstExpression > 3);
        if (listExpression.get(0).contains(".")) {
            return listExpression.get(0).substring(0, listExpression.get(0).lastIndexOf(".") + 2);
        } else {
            return listExpression.get(0).concat(".0");
        }
    }

    public String countOperation(String operation) throws NotValidExpressionException {
        switch (operation) {
            case "+":
                try {
                    float sum =
                            Float.parseFloat(listExpression.get(indexCalculate)) +
                                    Float.parseFloat(listExpression.get(indexCalculate + 1));
                    indexCalculate = indexCalculate + 2;
                    return String.valueOf(sum);
                } catch (NumberFormatException nfe) {
                    return listExpression.get(indexCalculate);
                }
            case "-":
                try {
                    float sub =
                            Float.parseFloat(listExpression.get(indexCalculate)) -
                                    Float.parseFloat(listExpression.get(indexCalculate + 1));
                    indexCalculate = indexCalculate + 2;
                    return String.valueOf(sub);
                } catch (NumberFormatException nfe) {
                    return listExpression.get(indexCalculate);
                }
            case "*":
                try {
                    float mul =
                            Float.parseFloat(listExpression.get(indexCalculate)) *
                                    Float.parseFloat(listExpression.get(indexCalculate + 1));
                    indexCalculate = indexCalculate + 2;
                    return String.valueOf(mul);
                } catch (NumberFormatException nfe) {
                    return listExpression.get(indexCalculate);
                }
            case "/":
                try {
                    float div =
                            Float.parseFloat(listExpression.get(indexCalculate)) /
                                    Float.parseFloat(listExpression.get(indexCalculate + 1));
                    indexCalculate = indexCalculate + 2;
                    if (Float.isInfinite(div)) {
                        throw new NotValidExpressionException("Делить на ноль нельзя!");
                    }
                    return String.valueOf(div);
                } catch (NumberFormatException nfExpr) {
                    return listExpression.get(indexCalculate);
                }
            default: return listExpression.get(indexCalculate);
        }
    }
}