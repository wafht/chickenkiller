package com.wafht.exercise.guava;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLClassLoader;

import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableSet;
import com.google.common.reflect.ClassPath;

/**
 * @author haitao.fu
 * @since: 7/31/14
 */
public class ClassPathTest {
    public static void main(String[] args) throws IOException, IllegalAccessException, InstantiationException, URISyntaxException {
        ClassPath cp = ClassPath.from(ClassPathTest.class.getClassLoader());
//        System.out.println(Joiner.on("\n").join(cp.getTopLevelClassesRecursive("com.google.common")));
        ImmutableSet<ClassPath.ClassInfo> topLevelClasses = cp.getAllClasses();
        System.out.println(Joiner.on("\n").join(topLevelClasses));


        ClassLoader classLoader = ClassPathTest.class.getClassLoader();
        if (classLoader instanceof URLClassLoader) {
            URLClassLoader urlClassLoader = (URLClassLoader) classLoader;
            for (URL entry : urlClassLoader.getURLs()) {
                System.out.println(entry.toURI());
            }
        }

    }
}
