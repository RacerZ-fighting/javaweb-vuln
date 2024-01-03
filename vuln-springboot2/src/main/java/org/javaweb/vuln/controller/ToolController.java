package org.javaweb.vuln.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sun.hotspot.WhiteBox;

import java.lang.reflect.Executable;
import java.lang.reflect.Method;

/**
 * ToolController used for JIT  
 * @author RacerZ
 * @version 2024/01/03 19:59
**/
@RestController
@RequestMapping("/JIT/")
public class ToolController {

    public static void forceCompilationLevel(Executable m) {
        WhiteBox wb = WhiteBox.getWhiteBox();
        wb.deoptimizeMethod(m);
        while (wb.getMethodCompilationLevel(m) != 0) {
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("[web-debug] successfully deOptimized method: " +
                m.getName() + " " + wb.getMethodCompilationLevel(m));
    }

    @GetMapping("/dop.do")
    public String getDeOptimization(String str) {
//         todo: format: class:method
//        if (str.indexOf(":") == -1) {
//            throw new RuntimeException("input format error!");
//        }
//         default： str -> class
        if (str == null || str.length() == 0) {
            return "empty input!";
        }
        String className = str;

        try {
            Class<?> aClass = Class.forName(className);
            for (Method method : aClass.getDeclaredMethods()) {
                forceCompilationLevel(method);
            }
        } catch (ClassNotFoundException e) {
            System.out.println("[web-debug] ClassNotFoundException: " + className);
        }

        return "ok";
    }

    @GetMapping("/dopAll.do")
    public String getDeOptimizationAll() {
        WhiteBox wb = WhiteBox.getWhiteBox();
        wb.deoptimizeAll();
        try {
            Thread.sleep(10 * 1000L);   // wait for 10s
        } catch (InterruptedException e) {
            return "error";
        }

        return "ok";
    }
}

