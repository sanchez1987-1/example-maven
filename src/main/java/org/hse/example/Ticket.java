package org.hse.example;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;


/**
 * Сущность Билет
 */
public interface Ticket extends Lucky {

    static Ticket getInstance(final int length, final int number) {
        Ticket ticket = new TicketImpl(length, number);

        /*TicketUtils
                .getDigitsSum(ticket)
                .filter(num -> num % 13 == 0)
                .map(num -> "Создаём билет с суммой цифр кратной 13 " + num)
                .ifPresent(System.out::println);*/
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
    class TicketImpl implements Ticket {

        /**
         * Количество цифр в номере билета
         */
        private int length;

        /**
         * Номер билета
         */
        private int number;
        private TicketImpl() {
        }

        private TicketImpl(int length, int number) {
            this.length = length;
            this.number = number;
        }

        @Override
        public int getLength() {
            return length;
        }

        public void setLength(Integer length) {
            Optional
                    .ofNullable(length)
                    .ifPresentOrElse(len -> this.length = len, () -> this.length = Counter.DEFAULT_LENGTH );
        }

        @Override
        public int getNumber() {
            return number;
        }

        public void setNumber(int number) {
            this.number = number;
        }

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
    }

}
