import java.time.LocalDate;

class Price {
  private static SystemCalendar systemCalendar;

  public static void setSystemCalendar(SystemCalendar newSystemCalendar) {
    systemCalendar = newSystemCalendar;
  }

  private int priceInCents;
  private LocalDate datePriceSet;

  public Price(int priceInCents) {
    if (systemCalendar == null) {
      systemCalendar = new SystemCalendar();
    }

    this.priceInCents = priceInCents;
    datePriceSet = systemCalendar.getDate();
  }

  public int inCents() {
    return priceInCents;
  }

  public LocalDate dateSet() {
    return datePriceSet;
  }
}
