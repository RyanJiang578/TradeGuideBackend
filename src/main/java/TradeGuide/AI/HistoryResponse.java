package TradeGuide.AI;
import java.time.LocalDate;

public record HistoryResponse(
    Long id,
    String symbol,
    double currentValue,
    double projectedValue,
    String information,
    LocalDate evaluationDate
)   {}
