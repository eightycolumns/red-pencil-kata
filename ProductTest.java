import org.junit.Test;
import static org.junit.Assert.*;

public class ProductTest {
  @Test
  public void fourPercentPriceReductionDoesNotTriggerRedPencilPromotion() {
    Product product = new Product(100);
    product.setPriceInCents(96);
    assertFalse(product.isRedPencilPromotion());
  }
}
