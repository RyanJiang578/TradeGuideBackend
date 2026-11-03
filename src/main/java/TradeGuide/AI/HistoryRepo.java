package TradeGuide.AI;

import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

public interface HistoryRepo extends JpaRepository<EvaluationHistory, Long> {
    List<EvaluationHistory> findByUserAndDateGreaterThanEqualOrderByDateDescIdDesc(AppUser user,LocalDate cutoff);
    void deleteByUser(AppUser user);
    @Transactional
    void deleteByDateBefore(LocalDate cutoff);
    
}
