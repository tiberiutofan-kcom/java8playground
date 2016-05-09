package java8playground.interfaces;

import org.junit.Assert;
import org.junit.Test;

public class ImplementationTest {

    private Implementation implementation = new Implementation();

    @Test
    public void testDisambiguation() {
        Assert.assertEquals("Hello from InterfaceA", implementation.test());
    }

}