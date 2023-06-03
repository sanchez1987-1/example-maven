package org.hse.example;

import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Stream;

/**
 * Вычисляет количество счастливых билетов
 */
public class Main {

    private static Function<Integer, LuckyStatisticsCalculator> toStatisticCalculator =
            ((Function<Integer, Supplier<Stream<Ticket>>>) Main::getTicketSupplier)
                    .andThen(Main::getStatisticsCalculator);

    private static Counter getInstance(int length) {
        return new CounterStreamImpl(length);
    }
    private static Supplier<Stream<Ticket>> getTicketSupplier(int length) {
        return new CounterStreamImpl(length);
    }

    private static LuckyStatisticsCalculator getStatisticsCalculator(Supplier<Stream<Ticket>> ticketsSupplier) {
        return new LuckyStatisticsCalculatorImpl(ticketsSupplier);
    }

    public static void main(String[] args) {
        var start = System.currentTimeMillis();
        var count = getInstance(8).count();
        var end = System.currentTimeMillis();

        System.out.printf("Всего %d счастливых билетов.\nВремя работы метода %d мс.\n", count, end - start);

        start = System.currentTimeMillis();
        var statistics = toStatisticCalculator.apply(8).getStatistic();
        end = System.currentTimeMillis();

        System.out.printf("Счастливые билеты в разрезе суммы цифр\n%s\nВремя работы метода %d мс.\n", statistics, end - start);
    }
}