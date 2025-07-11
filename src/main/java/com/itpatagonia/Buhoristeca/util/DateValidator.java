package com.itpatagonia.Buhoristeca.util;

import java.time.LocalDate;

public class DateValidator {

    public void assertEndDateIsAfterStartDate(LocalDate startDate, LocalDate endDate) {
        if (endDate.isBefore(startDate)) throw new RuntimeException("Período de tiempo inválido");
    }

}
