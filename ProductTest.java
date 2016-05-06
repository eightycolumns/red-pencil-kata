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
  public void promotionLastsFor30Days() {
    // Arrange
    product.setPriceInCents(100);
    systemCalendar.incrementDate(30);
    product.setPriceInCents(90);

    // Act
    systemCalendar.incrementDate(29);

    // Assert
    assertTrue(product.isPromotion());
  }

  @Test
  public void promotionExpiresOn31stDay() {
    // Arrange
    product.setPriceInCents(100);
    systemCalendar.incrementDate(30);
    product.setPriceInCents(90);

    // Act
    systemCalendar.incrementDate(30);

    // Assert
    assertFalse(product.isPromotion());
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

    // Act
    systemCalendar.incrementDate(15);
    product.setPriceInCents(81);
    systemCalendar.incrementDate(15);

    // Assert
    assertFalse(product.isPromotion());
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
