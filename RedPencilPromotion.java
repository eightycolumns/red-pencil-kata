import java.time.LocalDate;

class RedPencilPromotion {
  private static final int DURATION_IN_DAYS = 30;

  private SystemCalendar systemCalendar;

  private int originalPriceInCents;
  private LocalDate expirationDate;

  public RedPencilPromotion(SystemCalendar systemCalendar, int originalPriceInCents) {
    this.systemCalendar = systemCalendar;
    this.originalPriceInCents = originalPriceInCents;

    LocalDate today = systemCalendar.getDate();
    expirationDate = today.plusDays(DURATION_IN_DAYS - 1);
  }

  public boolean isExpired() {
    LocalDate today = systemCalendar.getDate();
    return today.isAfter(expirationDate);
  }

  public boolean expiredThirtyDaysAgo() {
    LocalDate today = systemCalendar.getDate();
    return expirationDate.plusDays(29).isBefore(today);
  }

  public LocalDate getExpirationDate() {
    return expirationDate;
  }

  public int getOriginalPriceInCents() {
    return originalPriceInCents;
  }
}
