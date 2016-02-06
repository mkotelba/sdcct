package gov.hhs.onc.sdcct.utils;

import com.github.sebhoss.warnings.CompilerWarnings;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.annotation.Nullable;

public final class SdcctStreamUtils {
    private SdcctStreamUtils() {
    }

    @SuppressWarnings({ CompilerWarnings.RAWTYPES })
    public static <T, U, V extends Entry<T, U>> Collector<V, ?, Map<T, U>> toMap() {
        return toMap(Entry::getKey, Entry::getValue, HashMap::new);
    }

    @SuppressWarnings({ CompilerWarnings.RAWTYPES })
    public static <T, U, V extends Entry<T, U>, W extends Map<T, U>> Collector<V, ?, W> toMap(Supplier<W> mapSupplier) {
        return toMap(Entry::getKey, Entry::getValue, mapSupplier);
    }

    public static <T, U, V, W extends Map<U, V>> Collector<T, ?, W> toMap(Function<? super T, ? extends U> keyMapper,
        Function<? super T, ? extends V> valueMapper, Supplier<W> mapSupplier) {
        return Collectors.toMap(keyMapper, valueMapper, (value1, value2) -> value2, mapSupplier);
    }

    @Nullable
    public static <T, U> U findInstance(Stream<T> stream, Class<U> clazz) {
        return stream.filter(instances(clazz)).findFirst().map(clazz::cast).orElse(null);
    }

    public static <T, U> Stream<U> asInstances(Stream<T> stream, Class<U> clazz) {
        return stream.filter(instances(clazz)).map(clazz::cast);
    }

    public static <T, U> Predicate<? super T> instances(Class<U> clazz) {
        return clazz::isInstance;
    }

    public static <T> Stream<T> ofTree(@Nullable T obj, Function<T, Stream<T>> streamBuilder) {
        return ((obj != null) ? traverse(streamBuilder).apply(obj) : Stream.empty());
    }

    public static <T> Function<T, Stream<T>> traverse(Function<T, Stream<T>> streamBuilder) {
        return obj -> Stream.concat(Stream.of(obj), streamBuilder.apply(obj).flatMap(traverse(streamBuilder)));
    }
}