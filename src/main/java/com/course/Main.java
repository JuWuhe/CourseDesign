package com.course;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import javax.sql.DataSource;

@SpringBootApplication
@EnableAspectJAutoProxy(exposeProxy = true)
public class Main {


    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

}
