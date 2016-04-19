class Product {
  private int priceInCents;
  private boolean isRedPencilPromotion;

  public Product(int initialPriceInCents) {
    priceInCents = initialPriceInCents;
    isRedPencilPromotion = false;
  }

  public void setPriceInCents(int newPriceInCents) {
    if (isInRedPencilRange(newPriceInCents)) {
      isRedPencilPromotion = true;
    }

    priceInCents = newPriceInCents;
  }

  private boolean isInRedPencilRange(int newPriceInCents) {
    return (
      newPriceInCents <= priceInCents * 0.95 &&
      newPriceInCents >= priceInCents * 0.70
    );
  }

  public boolean isRedPencilPromotion() {
    return isRedPencilPromotion;
  }
}
