import java.time.LocalDate;

class RedPencilPromotion {
  private static final int DURATION_IN_DAYS = 30;

  private int originalPriceInCents;
  private LocalDate expirationDate;

  public RedPencilPromotion(LocalDate startingDate, int originalPriceInCents) {
    this.originalPriceInCents = originalPriceInCents;
    expirationDate = startingDate.plusDays(DURATION_IN_DAYS - 1);
  }

  public LocalDate getExpirationDate() {
    return expirationDate;
  }

  public int getOriginalPriceInCents() {
    return originalPriceInCents;
  }
}
