package TradeGuide.AI;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import io.jsonwebtoken.JwtException;
import org.springframework.transaction.annotation.Transactional;
    @RestController
@CrossOrigin(origins = "http://localhost:8081")
public class UserController {
    private final OpenRouter router;
    private final RetrieveValue retrieveValue;
    private final JWTservice JWTservice;
    public UserController(OpenRouter router,RetrieveValue retrieveValue,JWTservice JWTservice){
        this.router=router;
        this.retrieveValue=retrieveValue;
        this.JWTservice=JWTservice;

    }
    private final BCryptPasswordEncoder passwordEncoder =new BCryptPasswordEncoder();
    @Autowired
    private AppUserRepo repo;
    @Autowired
    private StockDirectoryRepo stockrepo;
    @Autowired
    private HistoryRepo historyrepo;
    @PostMapping("/login")
    public Loginreturn login(@RequestBody loginJSON json){
        String email=json.getEmail();
        String password=json.getPassword();
        AppUser user=repo.findByEmail(email);
        if (user==null||password==null){
            System.out.println("failed");
            Loginreturn response=new Loginreturn();
            response.setLoginSucess(false);
            return response;
        }
        if (passwordEncoder.matches(password, user.getPassword())){
            System.out.println("login sucessful");
            Loginreturn response=new Loginreturn();
            response.setLoginSucess(true);
            response.setEmail(user.getEmail());
            response.setUsername(user.getUsername());
            response.setRiskLevel(user.getriskLevel());
            response.setTimeFrame(user.gettimeFrame());
            String token = JWTservice.generateToken(user.getEmail());
            System.out.println(token);
            response.setToken(token);
            return response;
        }
        else{
            Loginreturn response=new Loginreturn();
            response.setLoginSucess(false);
            return response;
        }
    }
    @PostMapping("/signup")
    public int signup(@RequestBody signupJSON json){
        String email=json.getEmail();
        String password=json.getPassword();
        String username=json.getUsername();
        AppUser user=repo.findByEmail(email);
        if (user!=null||password==null||password.isBlank()||username==null||username.isBlank()){
            return 0;
        }
        
        AppUser newUser=new AppUser();
        newUser.setEmail(email);
        newUser.setPassword(passwordEncoder.encode(password));
        newUser.setUsername(username);
        newUser.setDate(LocalDate.now());
        newUser.setUsage(0);
        newUser.setRiskLevel(1);
        newUser.settimeFrame(12);
        repo.save(newUser);
        return 1;
    }
    @PostMapping("/autofill")
    public List<StockDirectory> autofill(@RequestBody String text){
        System.out.println(text);
        System.out.println("Total stocks: " + stockrepo.count());
        List<StockDirectory> suggestions=stockrepo.findTop5ByNameStartingWithIgnoreCaseOrSymbolStartingWithIgnoreCase(text,text);
        System.out.println("autofill sent"+suggestions);
        return suggestions;
    }
    @PostMapping("evalStock")
    public Reply evalStock(@RequestHeader(value = "Authorization", required = false) String auth,@RequestBody prompt prompt) throws Exception{
        Reply reply=new Reply();
        if(auth==null||!auth.startsWith("Bearer ")){
            reply.setResponseCode(1);
            return reply;
        }
        String token = auth.substring(7);
        String email;
        try {
        email = JWTservice.validateToken(token);
            } 
        catch (JwtException | IllegalArgumentException e) {
            reply.setResponseCode(1);
        return reply;
        }
        AppUser user=repo.findByEmail(email);
        if (user==null){
            reply.setResponseCode(1);
            return reply;
        }
        if(!user.getDate().equals(LocalDate.now())){
            user.setUsage(0);
            user.setDate(LocalDate.now());
        }
        if(user.getUsage()>=25){
            reply.setResponseCode(1);
            return reply;
        }
        Double value=retrieveValue.getValue(prompt.getName());
        prompt.setValue(value);
        int newcount=user.getUsage()+1;
        user.setUsage(newcount);
        repo.save(user);
        reply = router.send("stockCheck", prompt);
    if (reply != null && reply.getResponseCode() == 0) {
    EvaluationHistory history = new EvaluationHistory();
    history.setUser(user);
    history.setSymbol(prompt.getName()); 
    history.setcurrentValue(reply.getCurrentValue());
    history.setprojectedValue(reply.getProjectedValue());
    history.setInformation(reply.getInformation());
    history.setDate(LocalDate.now(ZoneId.of("America/New_York")));
    historyrepo.save(history);
    }
    return reply;
    }

@PostMapping("/changesettings")
public int changesettings(@RequestHeader(value="Authorization",required=false) String auth,@RequestBody prompt prompt){
    if(auth==null||!auth.startsWith("Bearer ")){
        System.out.println("Settings failed: Missing token");
        return 1;
    }
    String email;
    try{
        email=JWTservice.validateToken(auth.substring(7));
    }
    catch(JwtException|IllegalArgumentException e){
        System.out.println("Settings failed: Invalid or expired token");
        return 1;
    }
    AppUser user=repo.findByEmail(email);
    if(user==null){
        System.out.println("Settings failed: User not found");
        return 1;
    }

    System.out.println("Risk received: "+prompt.getRiskLevel());
    System.out.println("Timeframe received: "+prompt.getTimeFrame());

    if(prompt.getRiskLevel()>5||prompt.getRiskLevel()<1){
        System.out.println("Settings failed: Invalid risk");
        return 1;
    }
    if(prompt.getTimeFrame()>60||prompt.getTimeFrame()<1){
        System.out.println("Settings failed: Invalid timeframe");
        return 1;
    }

    user.setRiskLevel(prompt.getRiskLevel());
    user.settimeFrame(prompt.getTimeFrame());
    if(prompt.getName()!=null&&prompt.getName().length()<=30&&!prompt.getName().isEmpty()){
        user.setUsername(prompt.getName());
    }
    repo.save(user);
    System.out.println("Settings saved successfully");
    return 0;
}

@GetMapping("/loadHistory")
public ResponseEntity<List<HistoryResponse>> loadHistory(@RequestHeader(value = "Authorization",required = false) String auth){
    if (auth == null || !auth.startsWith("Bearer ")) {
        return ResponseEntity.status(401).build();
    }
    String email;
        try {
        email = JWTservice.validateToken(auth.substring(7));
    } catch (JwtException | IllegalArgumentException e) {
        return ResponseEntity.status(401).build();
    }

    AppUser user = repo.findByEmail(email);

    if (user == null) {
        return ResponseEntity.status(401).build();
    }

    LocalDate cutoff = LocalDate.now(
        ZoneId.of("America/New_York")
    ).minusDays(6);

    List<HistoryResponse> history = historyrepo.findByUserAndDateGreaterThanEqualOrderByDateDescIdDesc(user, cutoff).stream()
        .map(h -> new HistoryResponse(
            h.getId(),
            h.getSymbol(),
            h.getcurrentValue(),
            h.getprojectedValue(),
            h.getInformation(),
            h.getDate()
        ))
        .toList();

    return ResponseEntity.ok(history);
}
@DeleteMapping("/deleteAccount")
@Transactional
public ResponseEntity<Void> deleteAccount(
    @RequestHeader(
        value = "Authorization",
        required = false
    ) String auth
) {
    if (auth == null || !auth.startsWith("Bearer ")) {
        return ResponseEntity.status(401).build();
    }

    String email;

    try {
        email = JWTservice.validateToken(auth.substring(7));
    }
    catch (JwtException | IllegalArgumentException e) {
        return ResponseEntity.status(401).build();
    }

    AppUser user = repo.findByEmail(email);

    if (user == null) {
        return ResponseEntity.status(401).build();
    }

    historyrepo.deleteByUser(user);
    repo.delete(user);

    return ResponseEntity.noContent().build();
}
}
