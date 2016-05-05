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

  public boolean isEqualToPrice(Price price) {
    return priceInCents == price.getPriceInCents();
  }

  public boolean isEqualToPercentOfPrice(double percent, Price price) {
    return priceInCents == Math.round(percent / 100 * price.getPriceInCents());
  }

  public boolean isLessThanPrice(Price price) {
    return priceInCents < price.getPriceInCents();
  }

  public boolean isLessThanPercentOfPrice(double percent, Price price) {
    return priceInCents < Math.round(percent / 100 * price.getPriceInCents());
  }

  public boolean isGreaterThanPrice(Price price) {
    return priceInCents > price.getPriceInCents();
  }

  public boolean isGreaterThanPercentOfPrice(double percent, Price price) {
    return priceInCents > Math.round(percent / 100 * price.getPriceInCents());
  }
}
