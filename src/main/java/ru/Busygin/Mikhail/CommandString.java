package ru.Busygin.Mikhail;

import java.util.ArrayList;
import java.util.List;

public class CommandString {

    private String buffer = "";
    private int parentheses;
    private final int[] bufferSizeStack;
    private final List<String> dataIn;
    public List<String> dataOut = new ArrayList<>();

    public CommandString (List<String> ex, int count) {
        this.dataIn = ex;
        bufferSizeStack = new int[count + 1];
        getNumbers();
    }

    public void getNumbers() {
        List<String> stack = new ArrayList<>();
        for (String s : dataIn) {
            switch (s) {
                case "(" -> parentheses++;
                case ")" -> {
                    if (!buffer.equals("")) {
                        dataOut.add(buffer);
                        buffer = "";
                    }
                    if (bufferSizeStack[parentheses] != 0) {
                        for (int j = 1; j <= bufferSizeStack[parentheses]; j++) {
                            dataOut.add(stack.remove(stack.size() - j));
                            bufferSizeStack[parentheses]--;
                        }
                    }
                    parentheses--;
                }
                case "+", "-" -> {
                    if (!buffer.equals("")) {
                        dataOut.add(buffer);
                        buffer = "";
                    }
                    if (bufferSizeStack[parentheses] != 0) {
                        while (bufferSizeStack[parentheses] != 0) {
                            if (stack.get(stack.size() - 1).equals("-") || stack.get(stack.size() - 1).equals("+")) {
                                dataOut.add(stack.remove(stack.size() - 1));
                                stack.add(s);
                                break;
                            } else {
                                dataOut.add(stack.remove(stack.size() - 1));
                                bufferSizeStack[parentheses]--;
                                if (bufferSizeStack[parentheses] == 0) {
                                    stack.add(s);
                                    bufferSizeStack[parentheses]++;
                                    break;
                                }
                            }
                        }
                    } else {
                        stack.add(s);
                        bufferSizeStack[parentheses]++;
                    }
                }
                case "/", "*" -> {
                    if (!buffer.equals("")) {
                        dataOut.add(buffer);
                        buffer = "";
                    }
                    if (bufferSizeStack[parentheses] != 0) {
                        if (stack.get(stack.size() - 1).equals("*") || stack.get(stack.size() - 1).equals("/")) {
                            dataOut.add(stack.remove(stack.size() - 1));
                            stack.add(s);
                        } else {
                            stack.add(s);
                            bufferSizeStack[parentheses]++;
                        }
                    } else {
                        stack.add(s);
                        bufferSizeStack[parentheses]++;
                    }
                }
                default -> buffer = buffer.concat(s);
            }
        }
        if (!buffer.equals("")) {
            dataOut.add(buffer);
            buffer = "";
        }
        for (int p = 1; p <= stack.size(); p++) {
            dataOut.add(stack.get(stack.size() - p));
        }
    }
}
