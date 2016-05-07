import java.time.LocalDate;

class DateProviderStub extends DateProvider {
  private int modifier = 0;

  public void incrementDate(int number) {
    modifier += number;
  }

  public void decrementDate(int number) {
    modifier -= number;
  }

  @Override
  public LocalDate getDate() {
    return LocalDate.now().plusDays(modifier);
  }
}
