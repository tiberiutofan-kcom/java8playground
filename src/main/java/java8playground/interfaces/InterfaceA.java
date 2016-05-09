package java8playground.interfaces;

public interface InterfaceA {

    default String test() {
        return "Hello from InterfaceA";
    }

}
