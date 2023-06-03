package org.hse.example;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Supplier;
import java.util.stream.Stream;

/**
 * Вычисляет статистику по счастливым билетам
 */
public interface LuckyStatisticsCalculator {
    /**
     * Вычисляет статистику по счастливым билетам
     *
     * @return полученная статистика в виде {@link Map}
     */
    Map<Integer, Integer> getStatistic();
}

/**
 * Реализация {@link LuckyStatisticsCalculator}
 */
class LuckyStatisticsCalculatorImpl implements LuckyStatisticsCalculator {
    private Stream<Ticket> tickets;

    public LuckyStatisticsCalculatorImpl(Supplier<Stream<Ticket>> supplier) {
        this.tickets = supplier.get();
    }

    /**
     * Вычисляет статистику по счастливым билетам
     *
     * @return полученная статистика в виде {@link Map}
     */
    @Override
    public Map<Integer, Integer> getStatistic() {
        Map<Integer, Integer> result = new ConcurrentHashMap<>(new HashMap<>());
        tickets
                .map(Ticket::getNumber)
                .map(TicketUtils::getDigitsSum)
                .map(sum -> sum / 2)
                .forEach(num -> {
                    result.computeIfAbsent(num, key -> 0);
                    result.computeIfPresent(num, (key, val) -> ++val);
                });

        return result;
    }
}
