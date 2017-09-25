package java8playground;

import org.junit.Assert;
import org.junit.Test;

import java.util.stream.Stream;

public class TailRecursionTest {

    public static long sum(final long n) {
        return n == 1 ? n : n + sum(n - 1);
    }

    public static Tail<Long> tailSum(final long accumulator, final long n) {
        return n == 1 ? Tail.done(accumulator) : Tail.compute(() -> tailSum(accumulator + n, n - 1));
    }

    @Test(expected = StackOverflowError.class)
    public void stackOverflow() throws Exception {
        sum(200_000);
    }

    @Test
    public void factorialSmallNumber() {
        Assert.assertEquals(50_005_000, sum(10_000));
    }

    @Test
    public void factorialTail() {
        Assert.assertEquals(new Long(20_000_099_999L), tailSum(0, 200_000).invoke());
    }

    interface Tail<T> {

        static <T> Tail<T> compute(final Tail<T> nextCall) {
            return nextCall;
        }

        static <T> Tail<T> done(final T value) {
            return new Tail<T>() {

                @Override
                public boolean isComplete() {
                    return true;
                }

                @Override
                public T result() {
                    return value;
                }

                @Override
                public Tail<T> call() {
                    throw new UnsupportedOperationException();
                }
            };
        }

        Tail<T> call();

        default boolean isComplete() {
            return false;
        }

        default T result() {
            throw new UnsupportedOperationException();
        }

        default T invoke() {
            return Stream.iterate(this, Tail::call)
                    .filter(Tail::isComplete)
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("No result"))
                    .result();
        }
    }
}
