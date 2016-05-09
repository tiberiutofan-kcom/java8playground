package java8playground.interfaces;

public interface InterfaceB {

    default String test() {
        return "Hello from InterfaceB";
    }
}
