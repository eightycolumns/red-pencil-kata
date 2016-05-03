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

    if (priceReducedEnoughToEndPromotion()) {
      promotion = null;
    } else if (priceIsStable() && priceReducedEnoughToStartPromotion()) {
      promotion = new Promotion();
      prePromotionPrice = previousPrice;
    }
  }

  private void changePrice(Price newPrice) {
    previousPrice = currentPrice;
    currentPrice = newPrice;
  }

  private boolean priceReducedEnoughToEndPromotion() {
    if (isPromotion()) {
      return newPriceIsLessThan70PercentOf(prePromotionPrice);
    } else {
      return newPriceIsLessThan70PercentOf(previousPrice);
    }
  }

  public boolean isPromotion() {
    return promotion != null && !promotion.hasExpired();
  }

  private boolean newPriceIsLessThan70PercentOf(Price price) {
    int priceInCents = price.inCents();
    return currentPrice.inCents() < (int)Math.round(priceInCents * 0.7);
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

  private boolean priceReducedEnoughToStartPromotion() {
    return currentPrice.inCents() <= previousPrice.inCents() * 0.95;
  }

  private void increasePrice(Price newPrice) {
    changePrice(newPrice);

    if (isPromotion()) {
      promotion = null;
    }
  }
}
