import java.time.LocalDate;

class RedPencilPromotion {
  private static final int DURATION_IN_DAYS = 30;

  private SystemCalendar systemCalendar;

  private LocalDate expirationDate;

  public RedPencilPromotion(SystemCalendar systemCalendar) {
    this.systemCalendar = systemCalendar;

    LocalDate today = systemCalendar.getDate();
    expirationDate = today.plusDays(DURATION_IN_DAYS);
  }

  public boolean isExpired() {
    LocalDate today = systemCalendar.getDate();
    return today.isAfter(expirationDate);
  }
}
