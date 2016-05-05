import java.time.LocalDate;

final class Promotion {
  private static final int DURATION_IN_DAYS = 30;

  private final SystemCalendar systemCalendar;

  private final LocalDate expirationDate;

  public Promotion(SystemCalendar systemCalendar) {
    this.systemCalendar = systemCalendar;

    LocalDate today = systemCalendar.getDate();
    expirationDate = today.plusDays(DURATION_IN_DAYS - 1);
  }

  public boolean hasExpired() {
    LocalDate today = systemCalendar.getDate();
    return today.isAfter(expirationDate);
  }

  public boolean expiredThirtyDaysAgo() {
    LocalDate today = systemCalendar.getDate();
    return expirationDate.plusDays(29).isBefore(today);
  }
}
