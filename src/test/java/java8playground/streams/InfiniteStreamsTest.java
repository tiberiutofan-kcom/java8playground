package java8playground.streams;

import org.junit.Test;

import java.math.BigInteger;
import java.util.List;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class InfiniteStreamsTest {

    @Test
    public void primeNumbers() {
        List<Integer> firstPrimes = Stream.iterate(0, n -> n + 1)
                .map(BigInteger::valueOf)
                .filter(i -> i.isProbablePrime(Integer.MAX_VALUE))
                .limit(10)
                .map(BigInteger::intValue)
                .collect(toList());

        assertArrayEquals(new Integer[]{2, 3, 5, 7, 11, 13, 17, 19, 23, 29},
                firstPrimes.toArray(new Integer[firstPrimes.size()]));
    }

    @Test
    public void generateFirstPrimeNumbers() {
        List<Integer> accumulator =
                Stream.iterate(0, n -> n + 1).parallel()
                        .map(BigInteger::valueOf)
                        .filter(i -> i.isProbablePrime(Integer.MAX_VALUE))
                        .limit(100_000)
                        .map(BigInteger::intValue)
                        .collect(toList());

        assertEquals(100_000, accumulator.size());
    }

    @Test
    public void _10RandomNumbers() {
        Stream.generate(Math::random)
                .map(n -> n * 100)
                .map(Double::intValue)
                .limit(10)
                .forEach(System.out::println);
    }

}
