import java.time.LocalDate;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class ProductTest {
  private DateProviderStub dateProvider;
  private Product product;

  @Before
  public void init() {
    dateProvider = new DateProviderStub();
    product = new Product(dateProvider);
  }

  @Test
  public void promotionCannotStartOn29thDayOfStablePricing() {
    // Arrange
    product.setPriceInCents(100);
    stepForwardTo29thDayOfStablePricing();

    // Act
    product.decreasePriceByPercent(10);

    // Assert
    assertFalse(product.isPromotion());
  }

  @Test
  public void promotionCanStartOn30thDayOfStablePricing() {
    // Arrange
    product.setPriceInCents(100);
    stepForwardTo30thDayOfStablePricing();

    // Act
    product.decreasePriceByPercent(10);

    // Assert
    assertTrue(product.isPromotion());
  }

  @Test
  public void priceReductionOf4PercentDoesNotStartPromotion() {
    // Arrange
    product.setPriceInCents(100);
    stepForwardTo30thDayOfStablePricing();

    // Act
    product.decreasePriceByPercent(4);

    // Assert
    assertFalse(product.isPromotion());
  }

  @Test
  public void priceReductionOf5PercentDoesStartPromotion() {
    // Arrange
    product.setPriceInCents(100);
    stepForwardTo30thDayOfStablePricing();

    // Act
    product.decreasePriceByPercent(5);

    // Assert
    assertTrue(product.isPromotion());
  }

  @Test
  public void priceReductionOf30PercentDoesStartPromotion() {
    // Arrange
    product.setPriceInCents(100);
    stepForwardTo30thDayOfStablePricing();

    // Act
    product.decreasePriceByPercent(30);

    // Assert
    assertTrue(product.isPromotion());
  }

  @Test
  public void priceReductionOf31PercentDoesNotStartPromotion() {
    // Arrange
    product.setPriceInCents(100);
    stepForwardTo30thDayOfStablePricing();

    // Act
    product.decreasePriceByPercent(31);

    // Assert
    assertFalse(product.isPromotion());
  }

  @Test
  public void promotionExpiresInThirtyDays() {
    // Arrange
    product.setPriceInCents(100);
    stepForwardTo30thDayOfStablePricing();

    // Act
    product.decreasePriceByPercent(10);

    // Assert
    LocalDate expirationDate = product.getPromotionExpirationDate();
    assertEquals(dateProvider.getDate().plusDays(29), expirationDate);
  }

  @Test
  public void promotionCannotStartOn29thDayAfterPreviousPromotion() {
    // Arrange
    product.setPriceInCents(100);
    stepForwardTo30thDayOfStablePricing();
    product.decreasePriceByPercent(10);
    stepForwardToLastDayOfPromotion();
    stepForwardTo29thDayAfterPromotion();

    // Act
    product.decreasePriceByPercent(10);

    // Assert
    assertFalse(product.isPromotion());
  }

  @Test
  public void promotionCanStartOn30thDayAfterPreviousPromotion() {
    // Arrange
    product.setPriceInCents(100);
    stepForwardTo30thDayOfStablePricing();
    product.decreasePriceByPercent(10);
    stepForwardToLastDayOfPromotion();
    stepForwardTo30thDayAfterPromotion();

    // Act
    product.decreasePriceByPercent(10);

    // Assert
    assertTrue(product.isPromotion());
  }

  @Test
  public void furtherPriceReductionDoesNotExtendPromotion() {
    // Arrange
    product.setPriceInCents(100);
    stepForwardTo30thDayOfStablePricing();
    product.decreasePriceByPercent(10);
    stepForwardToLastDayOfPromotion();
    LocalDate promotionExpirationDate = product.getPromotionExpirationDate();

    // Act
    product.decreasePriceByPercent(10);

    // Assert
    assertEquals(product.getPromotionExpirationDate(), promotionExpirationDate);
  }

  @Test
  public void promotionEndsEarlyIfPriceIncreases() {
    // Arrange
    product.setPriceInCents(100);
    stepForwardTo30thDayOfStablePricing();
    product.decreasePriceByPercent(10);

    // Act
    product.increasePriceByPercent(1);

    // Assert
    assertFalse(product.isPromotion());
  }

  @Test
  public void promotionEndsIfPriceDropsBelow30PercentStartingPoint() {
    // Arrange
    product.setPriceInCents(100);
    stepForwardTo30thDayOfStablePricing();
    product.decreasePriceByPercent(70);

    // Act
    product.decreasePriceByPercent(1);

    // Assert
    assertFalse(product.isPromotion());
  }

  private void stepForwardTo29thDayOfStablePricing() {
    dateProvider.incrementDate(29);
  }

  private void stepForwardTo30thDayOfStablePricing() {
    dateProvider.incrementDate(30);
  }

  private void stepForwardToLastDayOfPromotion() {
    dateProvider.incrementDate(29);
  }

  private void stepForwardTo29thDayAfterPromotion() {
    dateProvider.incrementDate(29);
  }

  private void stepForwardTo30thDayAfterPromotion() {
    dateProvider.incrementDate(30);
  }
}
