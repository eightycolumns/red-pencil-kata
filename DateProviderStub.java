import java.time.LocalDate;

class DateProviderStub extends DateProvider {
  private LocalDate date;

  public void setDate(LocalDate date) {
    this.date = date;
  }

  @Override
  public LocalDate getDate() {
    return (date == null) ? LocalDate.now() : date;
  }
}
