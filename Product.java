import java.util.Calendar;

class Product {
  private SystemCalendar systemCalendar;
  private int priceInCents;
  private RedPencilPromotion redPencilPromotion;

  public Product(SystemCalendar systemCalendar) {
    this.systemCalendar = systemCalendar;
  }

  public void setPriceInCents(int priceInCents) {
    if (qualifiesForRedPencilPromotion(priceInCents)) {
      Calendar today = systemCalendar.getDate();
      redPencilPromotion = new RedPencilPromotion(today);
    }

    this.priceInCents = priceInCents;
  }

  private boolean qualifiesForRedPencilPromotion(int priceInCents) {
    return (
      priceInCents <= this.priceInCents * 0.95 &&
      priceInCents >= this.priceInCents * 0.70
    );
  }

  public boolean isRedPencilPromotion() {
    if (redPencilPromotion == null) {
      return false;
    }

    Calendar today = systemCalendar.getDate();
    Calendar expirationDate = redPencilPromotion.getExpirationDate();

    if (today.after(expirationDate)) {
      return false;
    }

    return true;
  }
}
