package ma.ebank.ebank;
import org.springframework.web.bind.annotation.*; import org.springframework.http.HttpStatus; import org.springframework.web.server.ResponseStatusException; import jakarta.validation.Valid; import jakarta.validation.constraints.*; import java.math.BigDecimal; import java.util.*;
@RestController @RequestMapping("/api/accounts") public class BankController {
final BankService bank; public BankController(BankService b){bank=b;}
record NewAccount(@NotNull @Positive Long customerId,@NotNull BankAccount.AccountType type,@NotBlank String agency){}
record Movement(@NotNull @Positive @Digits(integer=15,fraction=2) BigDecimal amount,String description){}
record Transfer(@NotBlank String from,@NotBlank String to,@NotNull @Positive @Digits(integer=15,fraction=2) BigDecimal amount){}
record Details(BankAccount account,CustomerClient.CustomerView customer,String customerStatus){}
@GetMapping public List<BankAccount> all(){return bank.accounts.findAll();}
@GetMapping("/{id}") public BankAccount one(@PathVariable String id){return bank.get(id);}
@GetMapping("/{id}/details") public Details details(@PathVariable String id){BankAccount a=bank.get(id);try{var c=bank.customers.find(a.customerId);return new Details(a,c,c==null?"NOT_FOUND":"AVAILABLE");}catch(ResponseStatusException e){if(e.getStatusCode()!=HttpStatus.SERVICE_UNAVAILABLE)throw e;return new Details(a,null,"UNAVAILABLE");}}
@PostMapping @ResponseStatus(HttpStatus.CREATED) public BankAccount create(@Valid @RequestBody NewAccount n){return bank.create(n.customerId,n.type,n.agency);}
@PostMapping("/{id}/credit") public BankAccount credit(@PathVariable String id,@Valid @RequestBody Movement m){return bank.move(id,m.amount,true,m.description);}
@PostMapping("/{id}/debit") public BankAccount debit(@PathVariable String id,@Valid @RequestBody Movement m){return bank.move(id,m.amount,false,m.description);}
@PostMapping("/transfer") public Map<String,String> transfer(@Valid @RequestBody Transfer t){return Map.of("reference",bank.transfer(t.from,t.to,t.amount));}
@GetMapping("/{id}/operations") public List<BankOperation> history(@PathVariable String id){bank.get(id);return bank.operations.findByAccountIdOrderByCreatedAtDesc(id);}
}