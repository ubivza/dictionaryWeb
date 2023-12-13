package ru.aleksandr.dictionaryweb;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.Arrays;

@SpringBootApplication
@EnableTransactionManagement
public class DictionaryWebApplication implements CommandLineRunner {

    private ApplicationContext applicationContext;

    public DictionaryWebApplication(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }


    public static void main(String[] args) {
        SpringApplication.run(DictionaryWebApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        /*String[] arr = applicationContext.getBeanDefinitionNames();

        Arrays.sort(arr);

        for (String s : arr) {
            System.out.println(s + "||| Class |||" + applicationContext.getBean(s).getClass());
        }*/
    }
}
