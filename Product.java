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

  public void setPriceInCents(int priceInCents) {
    if (priceQualifiesForRedPencilPromotion(priceInCents)) {
      redPencilPromotion = new RedPencilPromotion(systemCalendar);
    }

    LocalDate today = systemCalendar.getDate();
    priceInCents = new Integer(priceInCents);

    pricingHistory.put(today, priceInCents);
  }

  private boolean priceQualifiesForRedPencilPromotion(int priceInCents) {
    if (pricingHistory.isEmpty() || !priceIsStable()) {
      return false;
    }

    int previousPriceInCents = pricingHistory.get(pricingHistory.lastKey());
    
    return (
      priceInCents <= previousPriceInCents * 0.95 &&
      priceInCents >= previousPriceInCents * 0.70
    );
  }

  private boolean priceIsStable() {
    LocalDate dateOfLastPriceChange = pricingHistory.lastKey();
    LocalDate today = systemCalendar.getDate();

    return dateOfLastPriceChange.plusDays(29).isBefore(today);
  }

  public boolean isRedPencilPromotion() {
    return redPencilPromotion != null && !redPencilPromotion.isExpired();
  }
}
