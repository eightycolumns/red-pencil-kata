import java.util.Calendar;

class StubSystemCalendar extends SystemCalendar {
  private int modifier = 0;

  public void incrementDate(int number) {
    modifier += number;
  }

  public void decrementDate(int number) {
    modifier -= number;
  }

  @Override
  public Calendar getDate() {
    Calendar date = Calendar.getInstance();
    date.add(Calendar.DATE, modifier);

    return date;
  }
}
