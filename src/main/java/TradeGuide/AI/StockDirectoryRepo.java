package TradeGuide.AI;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
public interface StockDirectoryRepo extends JpaRepository<StockDirectory,String>{
    List<StockDirectory> findTop5ByNameStartingWithIgnoreCaseOrSymbolStartingWithIgnoreCase(String name,String symbol);
}
