import java.util.Calendar;

class RedPencilPromotion {
  private static final int DURATION_IN_DAYS = 30;

  private Calendar expirationDate;

  public RedPencilPromotion(Calendar startingDate) {
    expirationDate = (Calendar)startingDate.clone();
    expirationDate.add(Calendar.DATE, DURATION_IN_DAYS);
  }

  public Calendar getExpirationDate() {
    return expirationDate;
  }
}
