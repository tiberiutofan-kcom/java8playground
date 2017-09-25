package java8playground.interfaces;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class InterfacesTest {

    @Test
    public void testDisambiguation() {
        assertEquals("Hello from InterfaceA", new Implementation().test());
    }

    @Test
    public void staticMethod() {
        assertEquals("I'm a static method", InterfaceA.foo());
    }

    @Test
    public void functionalInterfaceTest() {
        assertEquals("message", new Implementation().saySomething());
    }

    @Test
    public void lambda() {
        InterfaceA impl = () -> "I'm a lambda";
        assertEquals("I'm a lambda", impl.saySomething());
    }

    @FunctionalInterface
    interface InterfaceA {

        static String foo() {
            return "I'm a static method";
        }

        String saySomething();

        default String test() {
            return "Hello from InterfaceA";
        }

    }

    interface InterfaceB {

        default String test() {
            return "Hello from InterfaceB";
        }

    }

    static class Implementation implements InterfaceA, InterfaceB {

        @Override
        public String saySomething() {
            return "message";
        }

        @Override
        public String test() {
            return InterfaceA.super.test();
        }

    }

}