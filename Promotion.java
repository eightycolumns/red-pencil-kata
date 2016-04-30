import java.time.LocalDate;

class Promotion {
  private static final int DURATION_IN_DAYS = 30;

  private SystemCalendar systemCalendar;

  private LocalDate expirationDate;

  public Promotion(SystemCalendar systemCalendar) {
    this.systemCalendar = systemCalendar;

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
}
