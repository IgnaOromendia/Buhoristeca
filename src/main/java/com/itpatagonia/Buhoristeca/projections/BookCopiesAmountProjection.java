package com.itpatagonia.Buhoristeca.projections;

public interface BookCopiesAmountProjection {
    String getTitle();
    Integer getAmountOfCopies();
    Integer getAmountOfAvailableCopies();
}
