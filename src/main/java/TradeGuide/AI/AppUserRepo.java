package TradeGuide.AI;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AppUserRepo extends JpaRepository<AppUser, Long>{
    AppUser findByEmail(String email);
}
