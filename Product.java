import java.time.LocalDate;

class Product {
  private SystemCalendar systemCalendar;

  private Price currentPrice;
  private Price previousPrice;
  private Price prePromotionPrice;

  private Promotion promotion;

  public Product(SystemCalendar systemCalendar) {
    this.systemCalendar = systemCalendar;
  }

  public void setPriceInCents(int priceInCents) {
    LocalDate today = systemCalendar.getDate();
    Price newPrice = new Price(today, priceInCents);

    if (currentPrice == null) {
      currentPrice = newPrice;
    } else if (newPrice.inCents() < currentPrice.inCents()) {
      reducePrice(newPrice);
    } else if (newPrice.inCents() > currentPrice.inCents()) {
      increasePrice(newPrice);
    }
  }

  private void reducePrice(Price newPrice) {
    changePrice(newPrice);

    if (priceReducedTooMuch()) {
      promotion = null;
    } else if (priceIsStable() && priceReducedEnough()) {
      promotion = new Promotion(systemCalendar);
      prePromotionPrice = previousPrice;
    }
  }

  private void changePrice(Price newPrice) {
    previousPrice = currentPrice;
    currentPrice = newPrice;
  }

  private boolean priceReducedTooMuch() {
    if (currentPrice.inCents() < previousPrice.inCents() * 0.70) {
      return true;
    }

    if (
      isPromotion() &&
      currentPrice.inCents() < prePromotionPrice.inCents() * 0.70
    ) {
      return true;
    }

    return false;
  }

  public boolean isPromotion() {
    return promotion != null && !promotion.isExpired();
  }

  private boolean priceIsStable() {
    if (previousPrice == null) {
      return false;
    }

    LocalDate today = systemCalendar.getDate();

    if (previousPrice.getDate().plusDays(30).isAfter(currentPrice.getDate())) {
      return false;
    }

    if (promotion != null && !promotion.expiredThirtyDaysAgo()) {
      return false;
    }

    return true;
  }

  private boolean priceReducedEnough() {
    return currentPrice.inCents() <= previousPrice.inCents() * 0.95;
  }

  private void increasePrice(Price newPrice) {
    changePrice(newPrice);

    if (isPromotion()) {
      promotion = null;
    }
  }
}
