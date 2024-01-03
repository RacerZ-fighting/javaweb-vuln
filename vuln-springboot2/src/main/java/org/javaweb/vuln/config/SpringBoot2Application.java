package org.javaweb.vuln.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import sun.hotspot.WhiteBox;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

@SpringBootApplication
@ComponentScan("org.javaweb.vuln.*")
public class SpringBoot2Application {

	public static void main(String[] args) {
//		if (args.length >= 1) {
//			WhiteBox whiteBox = WhiteBox.getWhiteBox();
//			try {
//				BufferedReader in = new BufferedReader(new FileReader("CLASS"));
//				String className;
//				while ((className = in.readLine()) != null) {
//					try {
//						Class<?> clazz = Class.forName(className);
//						if (clazz != null) {
//							for (Method each : clazz.getDeclaredMethods()) {
//								whiteBox.makeMethodNotCompilable(each);
//								whiteBox.testSetDontInlineMethod(each, true);
//								whiteBox.testSetForceInlineMethod(each, false);
//							}
//							for (Constructor constructor: clazz.getDeclaredConstructors()) {
//								whiteBox.makeMethodNotCompilable(constructor);
//								whiteBox.testSetDontInlineMethod(constructor, true);
//								whiteBox.testSetForceInlineMethod(constructor, false);
//							}
//						}
//					} catch (ClassNotFoundException e) {
//						System.out.println("[debug]" + className);
//						continue;
//					}
//				}
//			} catch (IOException e){
//				e.printStackTrace();
//			}
//			System.out.println("[debug] whiteBox operation finished");
//		}
		SpringApplication.run(SpringBoot2Application.class, args);
	}

}
