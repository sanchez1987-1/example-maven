package org.hse.example;

/**
 * Возвращает, является ли некая сущность "счастливой"
 */
public interface Lucky {
    boolean isLucky();

    default boolean unfortunate() {
        return !this.isLucky();
    }
}
