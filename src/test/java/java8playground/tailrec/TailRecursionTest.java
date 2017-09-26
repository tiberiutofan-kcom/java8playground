package java8playground.tailrec;

import org.junit.Assert;
import org.junit.Test;

import java.util.stream.Stream;

public class TailRecursionTest {

    @Test(expected = StackOverflowError.class)
    public void stackOverflow() throws Exception {
        sum(200_000);
    }

    @Test
    public void sumSmallNumber() {
        Assert.assertEquals(500_500, sum(1_000));
    }

    @Test
    public void factorialTail() {
        Assert.assertEquals(new Long(20_000_099_999L), tailSum(0, 200_000).invoke());
    }

    private long sum(final long n) {
        return n == 1 ? n : n + sum(n - 1);
    }

    private Tail<Long> tailSum(final long accumulator, final long n) {
        return n == 1 ? Tail.done(accumulator) : Tail.compute(() -> tailSum(accumulator + n, n - 1));
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
