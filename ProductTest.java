import java.time.LocalDate;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class ProductTest {
  private StubSystemCalendar systemCalendar;
  private Product product;

  @Before
  public void init() {
    systemCalendar = new StubSystemCalendar();
    product = new Product(systemCalendar);
  }

  @Test
  public void promotionCannotStartOn29thDayOfStablePricing() {
    // Arrange
    product.setPriceInCents(100);
    systemCalendar.incrementDate(29);

    // Act
    product.setPriceInCents(90);

    // Assert
    assertFalse(product.isPromotion());
  }

  @Test
  public void promotionCanStartOn30thDayOfStablePricing() {
    // Arrange
    product.setPriceInCents(100);
    systemCalendar.incrementDate(30);

    // Act
    product.setPriceInCents(90);

    // Assert
    assertTrue(product.isPromotion());
  }

  @Test
  public void priceReductionOf4PercentDoesNotStartPromotion() {
    // Arrange
    product.setPriceInCents(100);
    systemCalendar.incrementDate(30);

    // Act
    product.setPriceInCents(96);

    // Assert
    assertFalse(product.isPromotion());
  }

  @Test
  public void priceReductionOf5PercentDoesStartPromotion() {
    // Arrange
    product.setPriceInCents(100);
    systemCalendar.incrementDate(30);

    // Act
    product.setPriceInCents(95);

    // Assert
    assertTrue(product.isPromotion());
  }

  @Test
  public void priceReductionOf30PercentDoesStartPromotion() {
    // Arrange
    product.setPriceInCents(100);
    systemCalendar.incrementDate(30);

    // Act
    product.setPriceInCents(70);

    // Assert
    assertTrue(product.isPromotion());
  }

  @Test
  public void priceReductionOf31PercentDoesNotStartPromotion() {
    // Arrange
    product.setPriceInCents(100);
    systemCalendar.incrementDate(30);

    // Act
    product.setPriceInCents(69);

    // Assert
    assertFalse(product.isPromotion());
  }

  @Test
  public void promotionExpiresInThirtyDays() {
    // Arrange
    product.setPriceInCents(100);
    systemCalendar.incrementDate(30);

    // Act
    product.setPriceInCents(90);

    // Assert
    LocalDate expirationDate = product.getPromotionExpirationDate();
    assertEquals(systemCalendar.getDate().plusDays(29), expirationDate);
  }

  @Test
  public void promotionCannotStartOn29thDayAfterPreviousPromotion() {
    // Arrange
    product.setPriceInCents(100);
    systemCalendar.incrementDate(30);
    product.setPriceInCents(90);
    systemCalendar.incrementDate(29);
    systemCalendar.incrementDate(29);

    // Act
    product.setPriceInCents(81);

    // Assert
    assertFalse(product.isPromotion());
  }

  @Test
  public void promotionCanStartOn30thDayAfterPreviousPromotion() {
    // Arrange
    product.setPriceInCents(100);
    systemCalendar.incrementDate(30);
    product.setPriceInCents(90);
    systemCalendar.incrementDate(29);
    systemCalendar.incrementDate(30);

    // Act
    product.setPriceInCents(81);

    // Assert
    assertTrue(product.isPromotion());
  }

  @Test
  public void furtherPriceReductionDoesNotExtendPromotion() {
    // Arrange
    product.setPriceInCents(100);
    systemCalendar.incrementDate(30);
    product.setPriceInCents(90);
    systemCalendar.incrementDate(29);
    LocalDate expirationDate = product.getPromotionExpirationDate();

    // Act
    product.setPriceInCents(81);

    // Assert
    assertEquals(product.getPromotionExpirationDate(), expirationDate);
  }

  @Test
  public void promotionEndsEarlyIfPriceIncreases() {
    // Arrange
    product.setPriceInCents(100);
    systemCalendar.incrementDate(30);
    product.setPriceInCents(75);

    // Act
    product.setPriceInCents(76);

    // Assert
    assertFalse(product.isPromotion());
  }

  @Test
  public void promotionEndsIfPriceDropsBelow30PercentStartingPoint() {
    // Arrange
    product.setPriceInCents(100);
    systemCalendar.incrementDate(30);
    product.setPriceInCents(70);

    // Act
    product.setPriceInCents(69);

    // Assert
    assertFalse(product.isPromotion());
  }
}
