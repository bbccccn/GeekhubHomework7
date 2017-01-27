import java.util.*;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

public class CollectionUtils {

    private CollectionUtils() {
    }

    public static <E> List<E> filter(List<E> elements, Predicate<E> filter) {
        List<E> result = new ArrayList<>(elements);
        for (E item : result) {
            if (!filter.test(item)) result.remove(item);
        }
        return result;
    }

    public static <E> boolean anyMatch(List<E> elements, Predicate<E> predicate) {
        for (E item : elements) if (predicate.test(item)) return true;
        return false;
    }

    public static <E> boolean allMatch(List<E> elements, Predicate<E> predicate) {
        for (E item : elements) if (!predicate.test(item)) return false;
        return true;
    }

    public static <E> boolean noneMatch(List<E> elements, Predicate<E> predicate) {
        for (E item : elements) if (predicate.test(item)) return false;
        return true;
    }

    public static <T, R> List<R> map(List<T> elements, Function<T, R> mappingFunction) {
        List<R> result = new ArrayList<>();
        for (T item : elements) result.add(mappingFunction.apply(item));
        return result;
    }

    public static <E> Optional<E> max(List<E> elements, Comparator<E> comparator) {
        if (elements.isEmpty()) return Optional.empty();
        E maxElement = elements.get(0);
        for (int i = 1; i < elements.size(); i++) {
            if (comparator.compare(maxElement, elements.get(i)) == 1) maxElement = elements.get(i);
        }
        return Optional.ofNullable(maxElement);
    }

    public static <E> Optional<E> min(List<E> elements, Comparator<E> comparator) {
        if (elements.isEmpty()) return Optional.empty();
        E minElement = elements.get(0);
        for (int i = 1; i < elements.size(); i++) {
            if (comparator.compare(minElement, elements.get(i)) == -1) minElement = elements.get(i);
        }
        return Optional.ofNullable(minElement);
    }

    public static <E> List<E> distinct(List<E> elements) {
        List<E> result = new ArrayList<>();
        for (E item : elements)
            if (!result.contains(item))
                result.add(item);
        return result;
    }

    public static <E> void forEach(List<E> elements, Consumer<E> consumer) {
        for (E item : elements) consumer.accept(item);
    }

    public static <E> Optional<E> reduce(List<E> elements, BinaryOperator<E> accumulator) {
        if (elements.isEmpty()) return Optional.empty();
        E result = elements.get(0);
        for (int i = 1; i < elements.size(); i++) {
            result = accumulator.apply(result, elements.get(i));
        }
        return Optional.of(result);
    }

    public static <E> E reduce(E seed, List<E> elements, BinaryOperator<E> accumulator) {
        E result = seed;
        for (E item : elements) {
            result = accumulator.apply(result, item);
        }
        return result;
    }

    public static <E> Map<Boolean, List<E>> partitionBy(List<E> elements, Predicate<E> predicate) {
        List<E> trueGroup = new ArrayList<>();
        List<E> falseGroup = new ArrayList<>();
        for (E item : elements) {
            if (predicate.test(item)) {
                trueGroup.add(item);
            } else {
                falseGroup.add(item);
            }
        }
        Map<Boolean, List<E>> result = new HashMap<>();
        result.put(Boolean.TRUE, trueGroup);
        result.put(Boolean.FALSE, falseGroup);
        return result;
    }

    public static <T, K> Map<K, List<T>> groupBy(List<T> elements, Function<T, K> classifier) {
        Map<K, List<T>> result = new HashMap<>();
        for (T item : elements) {
            K key = classifier.apply(item);
            if (result.containsKey(key)) {
                result.get(key).add(item);
            } else {
                List<T> localList = new ArrayList<>();
                localList.add(item);
                result.put(key, localList);
            }
        }
        return result;
    }

    public static <T, K, U> Map<K, U> toMap(List<T> elements,
                                            Function<T, K> keyFunction,
                                            Function<T, U> valueFunction,
                                            BinaryOperator<U> mergeFunction) {
        Map<K, U> resultMap = new HashMap<>();
        for (T item : elements) {
            U value = valueFunction.apply(item);
            K key = keyFunction.apply(item);
            if (resultMap.containsKey(key)) {
                resultMap.put(key, mergeFunction.apply(resultMap.get(key), value));
            } else {
                resultMap.put(key, value);
            }

        }
        return null;
    }
}
