import java.time.LocalDate;
import java.util.TreeMap;

class Product {
  private SystemCalendar systemCalendar;
  private RedPencilPromotion redPencilPromotion;
  private TreeMap<LocalDate, Integer> pricingHistory;

  public Product(SystemCalendar systemCalendar) {
    this.systemCalendar = systemCalendar;
    pricingHistory = new TreeMap<LocalDate, Integer>();
  }

  public void setPriceInCents(int newPriceInCents) {
    int currentPriceInCents;

    if (!pricingHistory.isEmpty()) {
      currentPriceInCents = pricingHistory.get(pricingHistory.lastKey());

      if (newPriceInCents == currentPriceInCents) {
        return;
      }

      determinePromotionStatus(currentPriceInCents, newPriceInCents);
    }

    LocalDate today = systemCalendar.getDate();
    newPriceInCents = new Integer(newPriceInCents);

    pricingHistory.put(today, newPriceInCents);
  }

  private void determinePromotionStatus(
    int currentPriceInCents,
    int newPriceInCents
  ) {
    if (redPencilPromotion != null && !isExpired(redPencilPromotion)) {
      if (
        newPriceInCents > currentPriceInCents ||
        newPriceInCents < redPencilPromotion.getOriginalPriceInCents() * 0.70
      ) {
        redPencilPromotion = null;
      }
    } else if (
      newPriceInCents <= currentPriceInCents * 0.95 &&
      newPriceInCents >= currentPriceInCents * 0.70 &&
      priceIsStable()
    ) {
      LocalDate today = systemCalendar.getDate();
      redPencilPromotion = new RedPencilPromotion(today, currentPriceInCents);
    }
  }

  private boolean isExpired(RedPencilPromotion redPencilPromotion) {
    LocalDate today = systemCalendar.getDate();
    LocalDate expirationDate = redPencilPromotion.getExpirationDate();

    return today.isAfter(expirationDate);
  }

  private boolean priceIsStable() {
    if (pricingHistory.isEmpty()) {
      return false;
    }

    LocalDate dateOfLastPriceChange = pricingHistory.lastKey();
    LocalDate today = systemCalendar.getDate();

    if (dateOfLastPriceChange.plusDays(30).isAfter(today)) {
      return false;
    }

    if (redPencilPromotion != null) {
      LocalDate expirationDate = redPencilPromotion.getExpirationDate();

      if (expirationDate.plusDays(30).isAfter(today)) {
        return false;
      }
    }

    return true;
  }

  public boolean isRedPencilPromotion() {
    return redPencilPromotion != null && !isExpired(redPencilPromotion);
  }
}
