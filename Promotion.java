import java.time.LocalDate;

class Promotion {
  private static final int DURATION_IN_DAYS = 30;
  private static SystemCalendar systemCalendar;

  public static void setSystemCalendar(SystemCalendar newSystemCalendar) {
    systemCalendar = newSystemCalendar;
  }

  private LocalDate expirationDate;

  public Promotion() {
    if (systemCalendar == null) {
      systemCalendar = new SystemCalendar();
    }

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
