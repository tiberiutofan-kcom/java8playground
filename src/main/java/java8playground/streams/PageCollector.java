package java8playground.streams;

import java8playground.streams.Page.Builder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

public class PageCollector<T> implements Collector<T, List<T>, Page<T>> {

    private Integer currentPageOffset;
    private Integer pageSize;
    private Integer totalCount;

    private PageCollector(Integer currentPageOffset, Integer pageSize, Integer totalCount) {
        this.currentPageOffset = currentPageOffset;
        this.pageSize = pageSize;
        this.totalCount = totalCount;
    }

    public static <T> Collector<T, ?, Page<T>> toPage(Integer currentPageOffset, Integer pageSize, Integer totalCount) {
        return Collector.of(ArrayList::new, List::add, (left, right) -> {
            left.addAll(right);
            return left;
        }, (List<T> l) -> Builder.<T>page()
                .withCurrentPage(l)
                .withOffset(currentPageOffset)
                .withLimit(pageSize)
                .withTotalCount(totalCount)
                .build());
    }

    @Override
    public Supplier<List<T>> supplier() {
        return ArrayList::new;
    }

    @Override
    public BiConsumer<List<T>, T> accumulator() {
        return List::add;
    }

    @Override
    public BinaryOperator<List<T>> combiner() {
        return (left, right) -> {
            left.addAll(right);
            return left;
        };
    }

    @Override
    public Function<List<T>, Page<T>> finisher() {
        return l -> Builder.<T>page()
                .withCurrentPage(l)
                .withOffset(currentPageOffset)
                .withLimit(pageSize)
                .withTotalCount(totalCount)
                .build();
    }

    @Override
    public Set<Characteristics> characteristics() {
        return Collections.emptySet();
    }
}
