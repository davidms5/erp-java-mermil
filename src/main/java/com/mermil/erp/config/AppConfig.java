import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.mermil.erp.controllers.stock.UploadStockController;
import com.mermil.erp.services.businessLogic.ProductService;

@Configuration
public class AppConfig {

    @Bean
    public UploadStockController uploadStockController(ProductService productService) {
        return new UploadStockController(productService);
    }

    @Bean
    public ProductService productService() {
        return new ProductService();
    }
}
