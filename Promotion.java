import java.time.LocalDate;

final class Promotion {
  private static final int DURATION_IN_DAYS = 30;

  private final DateProvider dateProvider;

  private final LocalDate expirationDate;

  public Promotion(DateProvider dateProvider) {
    this.dateProvider = dateProvider;

    LocalDate today = dateProvider.getDate();
    expirationDate = today.plusDays(DURATION_IN_DAYS - 1);
  }

  public LocalDate getExpirationDate() {
    return expirationDate;
  }

  public boolean isExpired() {
    LocalDate today = dateProvider.getDate();
    return today.isAfter(expirationDate);
  }

  public boolean expiredThirtyDaysAgo() {
    LocalDate today = dateProvider.getDate();
    return expirationDate.plusDays(29).isBefore(today);
  }
}
