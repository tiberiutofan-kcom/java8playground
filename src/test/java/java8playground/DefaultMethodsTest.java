package java8playground;

import org.junit.Test;

public class DefaultMethodsTest {

	@Test
	public void testDisambiguation() {
		System.out.println(new Implementation().test());
	}

	interface InterfaceA {

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
		public String test() {
			return InterfaceA.super.test();
		}
	}

}