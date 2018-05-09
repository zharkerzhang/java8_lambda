package com.zharker;

import org.junit.Test;
import org.junit.runner.Runner;

import javax.swing.text.DateFormatter;
import java.text.SimpleDateFormat;

public class Java8LambdaTest {

    @Test
    public void testt(){
        ThreadLocal<DateFormatter> formatter = ThreadLocal.withInitial(() -> new DateFormatter(new SimpleDateFormat("dd-MMM-yyyy")));

        Runnable helloWorld = () -> System.out.println("hello world");

    }


}
