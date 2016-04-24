import java.time.LocalDate;

class RedPencilPromotion {
  private static final int DURATION_IN_DAYS = 30;

  private SystemCalendar systemCalendar;

  private LocalDate startingDate;
  private int startingPriceInCents;
  private LocalDate expirationDate;

  public RedPencilPromotion(LocalDate startingDate, int startingPriceInCents) {
    this.startingDate = startingDate;
    this.startingPriceInCents = startingPriceInCents;
    expirationDate = startingDate.plusDays(DURATION_IN_DAYS - 1);
  }

  public LocalDate getExpirationDate() {
    return expirationDate;
  }

  public int getStartingPriceInCents() {
    return startingPriceInCents;
  }
}
