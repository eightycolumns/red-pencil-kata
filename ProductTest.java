import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class ProductTest {
  private StubSystemCalendar stubSystemCalendar;
  private Product product;

  @Before
  public void init() {
    stubSystemCalendar = new StubSystemCalendar();
    product = new Product(stubSystemCalendar);
  }

  @Test
  public void priceReductionOf4PercentDoesNotStartRedPencilPromotion() {
    // Arrange
    product.setPriceInCents(100);
    stubSystemCalendar.incrementDate(30);

    // Act
    product.setPriceInCents(96);

    // Assert
    assertFalse(product.isRedPencilPromotion());
  }

  @Test
  public void priceReductionOf5PercentDoesStartRedPencilPromotion() {
    // Arrange
    product.setPriceInCents(100);
    stubSystemCalendar.incrementDate(30);

    // Act
    product.setPriceInCents(95);

    // Assert
    assertTrue(product.isRedPencilPromotion());
  }

  @Test
  public void priceReductionOf30PercentDoesStartRedPencilPromotion() {
    // Arrange
    product.setPriceInCents(100);
    stubSystemCalendar.incrementDate(30);

    // Act
    product.setPriceInCents(70);

    // Assert
    assertTrue(product.isRedPencilPromotion());
  }

  @Test
  public void priceReductionOf31PercentDoesNotStartRedPencilPromotion() {
    // Arrange
    product.setPriceInCents(100);
    stubSystemCalendar.incrementDate(30);

    // Act
    product.setPriceInCents(69);

    // Assert
    assertFalse(product.isRedPencilPromotion());
  }

  @Test
  public void redPencilPromotionCannotStartOn29thDayOfStablePricing() {
    // Arrange
    product.setPriceInCents(100);

    // Act
    stubSystemCalendar.incrementDate(29);
    product.setPriceInCents(75);

    // Assert
    assertFalse(product.isRedPencilPromotion());
  }

  @Test
  public void redPencilPromotionCanStartOn30thDayOfStablePricing() {
    // Arrange
    product.setPriceInCents(100);

    // Act
    stubSystemCalendar.incrementDate(30);
    product.setPriceInCents(75);

    // Assert
    assertTrue(product.isRedPencilPromotion());
  }

  @Test
  public void redPencilPromotionCannotStartOn29thDayAfterPreviousPromotion() {
    // Arrange
    product.setPriceInCents(100);
    stubSystemCalendar.incrementDate(30);
    product.setPriceInCents(90);
    stubSystemCalendar.incrementDate(29);

    // Act
    stubSystemCalendar.incrementDate(29);
    product.setPriceInCents(81);

    // Assert
    assertFalse(product.isRedPencilPromotion());
  }

  @Test
  public void redPencilPromotionCanStartOn30thDayAfterPreviousPromotion() {
    // Arrange
    product.setPriceInCents(100);
    stubSystemCalendar.incrementDate(30);
    product.setPriceInCents(90);
    stubSystemCalendar.incrementDate(29);

    // Act
    stubSystemCalendar.incrementDate(30);
    product.setPriceInCents(81);

    // Assert
    assertTrue(product.isRedPencilPromotion());
  }

  @Test
  public void redPencilPromotionLastsFor30Days() {
    // Arrange
    product.setPriceInCents(100);
    stubSystemCalendar.incrementDate(30);
    product.setPriceInCents(75);

    // Act
    stubSystemCalendar.incrementDate(29);

    // Assert
    assertTrue(product.isRedPencilPromotion());
  }

  @Test
  public void redPencilPromotionExpiresOn31stDay() {
    // Arrange
    product.setPriceInCents(100);
    stubSystemCalendar.incrementDate(30);
    product.setPriceInCents(75);

    // Act
    stubSystemCalendar.incrementDate(30);

    // Assert
    assertFalse(product.isRedPencilPromotion());
  }

  @Test
  public void furtherPriceReductionDoesNotExtendRedPencilPromotion() {
    // Arrange
    product.setPriceInCents(100);
    stubSystemCalendar.incrementDate(30);
    product.setPriceInCents(90);

    // Act
    stubSystemCalendar.incrementDate(15);
    product.setPriceInCents(81);
    stubSystemCalendar.incrementDate(15);

    // Assert
    assertFalse(product.isRedPencilPromotion());
  }

  @Test
  public void redPencilPromotionEndsEarlyIfPriceIncreases() {
    // Arrange
    product.setPriceInCents(100);
    stubSystemCalendar.incrementDate(30);
    product.setPriceInCents(75);

    // Act
    product.setPriceInCents(76);

    // Assert
    assertFalse(product.isRedPencilPromotion());
  }

  @Test
  public void redPencilPromotionEndsIfPriceDropsBelow30PercentStartingPoint() {
    // Arrange
    product.setPriceInCents(100);
    stubSystemCalendar.incrementDate(30);
    product.setPriceInCents(90);

    // Act
    product.setPriceInCents(80);
    product.setPriceInCents(70);
    product.setPriceInCents(60);

    // Assert
    assertFalse(product.isRedPencilPromotion());
  }
}
