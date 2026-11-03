
package TradeGuide.AI;

import java.time.LocalDate;
import java.time.ZoneId;

import org.springframework.stereotype.Service;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.Transactional;

@Service
public class HistoryCleanup {

    private final HistoryRepo historyrepo;

    public HistoryCleanup(HistoryRepo historyrepo) {
        this.historyrepo = historyrepo;
    }

    @Scheduled(
        cron = "0 5 0 * * *",
        zone = "America/New_York"
    )
    @Transactional
    public void cleanupHistory() {

        LocalDate cutoff = LocalDate.now(
            ZoneId.of("America/New_York")
        ).minusDays(6);

        historyrepo.deleteByDateBefore(cutoff);

        System.out.println(
            "Evaluation history cleanup completed. " +
            "Deleted records before: " + cutoff
        );
    }
}
