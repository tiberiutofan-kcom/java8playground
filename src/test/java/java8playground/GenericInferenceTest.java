package java8playground;

import org.junit.Assert;
import org.junit.Test;

import java.util.Collection;
import java.util.Collections;

public class GenericInferenceTest {
	public static String getFirst(Collection<String> collection) {
		return collection.isEmpty() ? null : collection.iterator().next();
	}

	@Test
	public void test() {
//      Assert.assertNull(getFirst(Collections.<String>emptyList())); //Java7
		Assert.assertNull(getFirst(Collections.emptyList()));
	}
}
