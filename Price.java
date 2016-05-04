import java.time.LocalDate;

class Price {
  private int priceInCents;
  private LocalDate datePriceWasSet;

  public Price(int priceInCents, LocalDate datePriceWasSet) {
    this.priceInCents = priceInCents;
    this.datePriceWasSet = datePriceWasSet;
  }

  public int getPriceInCents() {
    return priceInCents;
  }

  public LocalDate getDatePriceWasSet() {
    return datePriceWasSet;
  }
}
