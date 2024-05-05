package edu.school21.app;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import edu.school21.printer.Printer;

public class Program {
    public static void main(String[] args) {
        ApplicationContext applicationContext =
                new ClassPathXmlApplicationContext("context.xml");
        Printer datePrinter = applicationContext.getBean("printerWithDateStdUpper", Printer.class);
        datePrinter.output("Hello!");
    }
}
