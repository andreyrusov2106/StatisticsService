package ru.practicum.event.model;

import ru.practicum.exceptions.IllegalEnumStateException;

public enum Sort {
    EVENT_DATE, VIEWS;

    public static Sort stringToState(String stringSort) {
        Sort sort;
        try {
            if (stringSort == null) {
                sort = Sort.EVENT_DATE;
            } else {
                sort = Sort.valueOf(stringSort);
            }
        } catch (Exception e) {
            throw new IllegalEnumStateException("Unknown sort: " + stringSort);
        }
        return sort;
    }
}
