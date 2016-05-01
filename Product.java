import java.time.LocalDate;

class Product {
  private static SystemCalendar systemCalendar;

  public static void setSystemCalendar(SystemCalendar newSystemCalendar) {
    systemCalendar = newSystemCalendar;
  }

  private Price currentPrice;
  private Price previousPrice;
  private Price prePromotionPrice;

  private Promotion promotion;

  public Product() {
    if (systemCalendar == null) {
      systemCalendar = new SystemCalendar();
    }
  }

  public void setPriceInCents(int priceInCents) {
    Price newPrice = new Price(priceInCents);

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
      promotion = new Promotion();
      prePromotionPrice = previousPrice;
    }
  }

  private void changePrice(Price newPrice) {
    previousPrice = currentPrice;
    currentPrice = newPrice;
  }

  private boolean priceReducedTooMuch() {
    if (isPromotion()) {
      return priceReducedTooMuchDuringPromotion();
    } else {
      return priceReducedTooMuchNotDuringPromotion();
    }
  }

  public boolean isPromotion() {
    return promotion != null && !promotion.hasExpired();
  }

  private boolean priceReducedTooMuchDuringPromotion() {
    return currentPrice.inCents() < prePromotionPrice.inCents() * 0.70;
  }

  private boolean priceReducedTooMuchNotDuringPromotion() {
    return currentPrice.inCents() < previousPrice.inCents() * 0.70;
  }

  private boolean priceIsStable() {
    if (promotion == null) {
      return priceIsStableBeforePromotion();
    } else {
      return priceIsStableAfterPromotion();
    }
  }

  private boolean priceIsStableBeforePromotion() {
    return (
      previousPrice != null &&
      previousPrice.dateSet().plusDays(29).isBefore(currentPrice.dateSet())
    );
  }

  private boolean priceIsStableAfterPromotion() {
    return (
      promotion.expiredThirtyDaysAgo() &&
      previousPrice.dateSet().plusDays(29).isBefore(currentPrice.dateSet())
    );
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
