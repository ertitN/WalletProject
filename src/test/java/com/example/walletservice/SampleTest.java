package com.example.walletservice;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class SampleTest {

    Calculator calculator = new Calculator();
    @Test
    public void testAdd(){
        int firstNumber = 10;
        int secondNumber = 20;
        int expected = 30;

        int actual = calculator.add(firstNumber,secondNumber);

        Assertions.assertEquals(expected,actual);

    }


    class Calculator{
        public int add(int a,int b){
            return a+b;

        }
    }
}
