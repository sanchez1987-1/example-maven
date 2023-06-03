package org.hse.example;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;


/**
 * Сущность Билет
 */
public interface Ticket extends Lucky {

    static Ticket getInstance(final int length, final int number) {
        Ticket ticket = new TicketImpl(length, number);
        return ticket;
    }

    /**
     * @return true, если в номере содердится цифра 7
     */
    default boolean containsSeven() {
        Set<Integer> digits = getDigits();
        return digits.contains(7);
    }

    private Set<Integer> getDigits() {
        Set<Integer> digits = new HashSet<>();
        for (int number = getNumber(); number > 0; number = number / 10) {
            int digit = number % 10;
            digits.add(digit);
        }
        return digits;
    }

    int getLength();

    int getNumber();

    /**
     * Реализация Сущности Билет
     */
    @Data
    @AllArgsConstructor
    @FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
    class TicketImpl implements Ticket {

        /**
         * Количество цифр в номере билета
         */
        @Getter(AccessLevel.NONE)
        int length;

        /**
         * Номер билета
         */
        @Getter(AccessLevel.NONE)
        private int number;

        /**
         * Вычисляет, является ли билет счастливым
         *
         * @return true, если является
         */
        @Override
        public boolean isLucky() {
            int middle = getLength() / 2;
            int half = Double.valueOf(Math.pow(10, middle)).intValue();

            int first = TicketUtils.getDigitsSum(getNumber() / half);
            int last = TicketUtils.getDigitsSum(getNumber() % half);

            return first == last;
        }

        @Override
        public int getLength() {
            return 0;
        }

        @Override
        public int getNumber() {
            return 0;
        }
    }

}
