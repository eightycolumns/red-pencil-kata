import java.util.Calendar;

class RedPencilPromotion {
  private static final int DURATION_IN_DAYS = 30;

  private SystemCalendar systemCalendar;

  private Calendar expirationDate;

  public RedPencilPromotion(SystemCalendar systemCalendar) {
    this.systemCalendar = systemCalendar;

    expirationDate = systemCalendar.getDate();
    expirationDate.add(Calendar.DATE, DURATION_IN_DAYS);
  }

  public boolean isExpired() {
    Calendar today = systemCalendar.getDate();
    return today.after(expirationDate);
  }
}
