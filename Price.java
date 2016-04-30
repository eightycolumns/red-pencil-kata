import java.time.LocalDate;

class Price {
  private LocalDate date;
  private int priceInCents;

  public Price(LocalDate date, int priceInCents) {
    this.date = date;
    this.priceInCents = priceInCents;
  }

  public LocalDate getDate() {
    return date;
  }

  public int inCents() {
    return priceInCents;
  }
}
