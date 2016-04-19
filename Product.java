import java.util.Calendar;

class Product {
  private int priceInCents;
  private boolean isRedPencilPromotion;
  private Calendar dateOfLastPriceChange;
  private Calendar redPencilPromotionExpirationDate;

  public Product(int initialPriceInCents) {
    priceInCents = initialPriceInCents;
    isRedPencilPromotion = false;
  }

  public void setPriceInCents(int newPriceInCents) {
    if (isInRedPencilPromotionRange(newPriceInCents)) {
      isRedPencilPromotion = true;

      redPencilPromotionExpirationDate = Calendar.getInstance();
      redPencilPromotionExpirationDate.add(Calendar.DATE, 30);
    }

    priceInCents = newPriceInCents;
    dateOfLastPriceChange = Calendar.getInstance();
  }

  private boolean isInRedPencilPromotionRange(int newPriceInCents) {
    return (
      newPriceInCents <= priceInCents * 0.95 &&
      newPriceInCents >= priceInCents * 0.70
    );
  }

  public boolean isRedPencilPromotion() {
    return isRedPencilPromotion;
  }

  public Calendar getRedPencilPromotionExpirationDate() {
    return redPencilPromotionExpirationDate;
  }
}
