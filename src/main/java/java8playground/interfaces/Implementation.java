package java8playground.interfaces;

public class Implementation implements InterfaceA, InterfaceB {

    public static void main(String[] args) {
        System.out.println(new Implementation().test());
    }

    @Override
    public String test() {
        return InterfaceA.super.test();
    }
}
