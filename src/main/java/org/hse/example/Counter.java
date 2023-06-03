package org.hse.example;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.function.Supplier;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * Вычисляет количество счастливых билетов заданной длины
 */
@FunctionalInterface
public interface Counter {

    static int DEFAULT_LENGTH = 6;

    /**
     * Вычисляет количество счастливых билетов с номерами заданной длины
     *
     * @return количество билетов
     */
    int count();

}
/**
 * Реализация {@link Counter}
 */
@Data
@AllArgsConstructor
class CounterImpl implements Counter {
    private final int length;

    protected Lucky getInstance(final int length, final int number) {
        return Ticket.getInstance(length, number);
    }

    public int getLength() {
        return length;
    }

    /**
     * Вычисляет количество счастливых билетов с номерами заданной длины
     *
     * @return количество счастливых билетов
     */
    @Override
    public int count() {
        int result = 0;
        for (int i = 0; i < Math.pow(10, getLength()); i++) {
            Lucky ticket = getInstance(getLength(), i);

            if (ticket.isLucky()) {
                result++;
            }
        }
        return result;
    }
}

class CounterStreamImpl extends CounterImpl implements Supplier<Stream<Ticket>> {

    private IntFunction<Ticket> toTicket =
            ((Function<Integer, Lucky>) num -> this.getInstance(getLength(), num)).andThen(Ticket.class::cast)::apply;

    public CounterStreamImpl(int length) {
        super(length);
    }

    @Override
    public int count() {
        return (int) get().count();

    }

    @Override
    public Stream<Ticket> get() {
        return IntStream
                .range(0, (int) Math.pow(10, getLength()))
                .parallel()
                .mapToObj(toTicket)
                .filter(Lucky::isLucky);
    }
}