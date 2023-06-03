package org.hse.example;

import java.util.Optional;

/**
 * Утилитные методы для работы с билетами
 */
public final class TicketUtils {
    private TicketUtils() {
        throw new RuntimeException("Класс содердит только утилитные методы. Создание экземпляров запрещено!");
    }

    /**
     * Взвращает сумму цифр переданного числа
     *
     * @param number число, для которого ищем сумму цифр
     * @return сумма цифр
     */
    public static int getDigitsSum(final int number) {
        int sum = 0;
        for (int tmp = number; tmp > 0; tmp = tmp / 10) {
            sum = sum + tmp % 10;
        }

        return sum;
    }

    public static Optional<Integer> getDigitsSum(final Ticket ticket) {
        return Optional
                .ofNullable(ticket)
                .map(Ticket::getNumber)
                .map(TicketUtils::getDigitsSum);
//                .orElse(() -> -1);
//                .orElseThrow(IllegalArgumentException::new);
        // 1. Если null, то выкинуть исключение.
        // 2. Какое-то значение по умолчанию, в т.ч. null.
        // 3. Вернуть не число, а Optional
    }
}
