package com.example.whgml.sejongapps.Helper;

import java.util.Stack;
import java.util.Vector;

public class Calculator {
    private double answer = 0.0;

    public Calculator(CharSequence formula) throws Exception{
        Stack<Character> operatorStack = new Stack<Character>();
        Vector<String> postFixQueue = new Vector<String>();

        int index = 0;
        boolean operatorTrigger = true;
        StringBuilder numString = new StringBuilder();

        while (index < formula.length()) {
            char ch = formula.charAt(index++);
            if (Character.isDigit(ch) || ch == '.' || (operatorTrigger && (ch == '+' || ch == '-'))) {
                operatorTrigger = false;
                numString.append(ch);
            } else {
                postFixQueue.add(numString.toString());
                numString = new StringBuilder();
                operatorTrigger = true;
                switch (ch) {
                    case '+':
                    case '-':
                        while (!operatorStack.isEmpty() && operatorStack.peek() != '(') {
                            postFixQueue.add(operatorStack.pop().toString());
                        }
                        operatorStack.push(ch);
                        break;
                    case '*':
                    case '/':
                        while(!operatorStack.isEmpty())
                        {
                            Character operator = operatorStack.peek();
                            if(operator == '*' || operator == '/')
                            {
                                postFixQueue.add(operatorStack.pop().toString());
                            }
                            else
                            {
                                break;
                            }
                        }
                        operatorStack.push(ch);
                        break;
                    case '(':
                        operatorStack.push(ch);
                        break;
                    case ')':
                        Character operator = operatorStack.pop();
                        while(operator != '(')
                        {
                            postFixQueue.add(operator.toString());
                            operator = operatorStack.pop();
                        }
                        break;
                }
            }
        }
        if(numString.length() > 0)
        {
            postFixQueue.add(numString.toString());
        }
        while(!operatorStack.isEmpty())
        {
            postFixQueue.add(operatorStack.pop().toString());
        }

        // Complete to convert to postfix
        // Now, start to calculate
        index = 0;
        Stack<Double> calcStack = new Stack<Double>();
        while(index < postFixQueue.size())
        {
            Double src1, src2;
            String fix = postFixQueue.get(index++);
            if(fix.equals("+"))
            {
                src2 = calcStack.pop();
                src1 = calcStack.pop();
                calcStack.push(src1 + src2);
            }
            else if(fix.equals("-"))
            {
                src2 = calcStack.pop();
                src1 = calcStack.pop();
                calcStack.push(src1 - src2);
            }
            else if(fix.equals("*"))
            {
                src2 = calcStack.pop();
                src1 = calcStack.pop();
                calcStack.push(src1 * src2);
            }
            else if(fix.equals("/"))
            {
                src2 = calcStack.pop();
                src1 = calcStack.pop();
                calcStack.push(src1 / src2);
            }
            else if(fix.length() > 0)
            {
                calcStack.push(Double.parseDouble(fix));
            }
        }

        answer = calcStack.pop();
    }

    public double getAnswer()
    {
        return answer;
    }
}
