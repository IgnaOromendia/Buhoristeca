package com.itpatagonia.Buhoristeca.util;

import com.itpatagonia.Buhoristeca.exceptions.EndDateIsAfterStartDateException;

import java.time.LocalDate;

public class DateValidator {

    public static void assertEndDateIsAfterStartDate(LocalDate startDate, LocalDate endDate) {
        if (endDate.isBefore(startDate))
            throw new EndDateIsAfterStartDateException();
    }

}
