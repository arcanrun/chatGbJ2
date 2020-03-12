package lesson07;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

public class Invoker {
    Class c;
    Object o;

    public Invoker(Object o) {
        this.c = o.getClass();
        this.o = o;

    }

    public void start() {

        if (!c.isAnnotationPresent(ForTest.class)) {
            throw new RuntimeException("This class is not for tests");
        }

        Method[] methods = c.getDeclaredMethods();

        checkAfterAndBeforeSuite(methods);
        startSuite("startTest");
        invokeTesting(methods);
        startSuite("endTest");
    }

    private void invokeTesting(Method[] methods) {
        ArrayList<Method> methodsForTesting = new ArrayList<>();
        for (Method i : methods) {
            if (i.getAnnotation(Test.class) != null) {
                methodsForTesting.add(i);
            }
        }
        methodsForTesting.sort(new Comparator<Method>() {
            @Override
            public int compare(Method meth1, Method meth2) {
                int prior1 = meth1.getAnnotation(Test.class).priority();
                int prior2 = meth2.getAnnotation(Test.class).priority();
                if (prior1 > 10) {
                    prior1 = 10;
                } else if (prior1 < 1) {
                    prior1 = 1;
                }

                if (prior2 > 10) {
                    prior2 = 10;
                } else if (prior2 < 1) {
                    prior2 = 1;
                }

                return prior2 - prior1;
            }
        });


        for (Method i : methodsForTesting
        ) {
            try {
                i.invoke(o);
            } catch (IllegalAccessException | InvocationTargetException e) {
               e.printStackTrace();
            }

        }
    }

    private void startSuite(String test) {
        try {
            Method end = c.getDeclaredMethod(test);
            end.invoke(o);

        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    public void checkAfterAndBeforeSuite(Method[] methods) {
        int counter = 0;

        for (Method i : methods) {
            if (i.getAnnotation(AfterSuite.class) != null) {
                counter++;
            }
            ;
            if (i.getAnnotation(BeforeSuite.class) != null) {
                counter++;
            }
            ;
            if (counter > 2) {
                throw new RuntimeException("Wrong count of before or after suites");
            }
        }

        System.out.println("Prepare parameters are OK...");
    }

}
