package ma.ebank.customer;
import org.springframework.web.bind.annotation.*; import org.springframework.web.server.ResponseStatusException; import org.springframework.http.HttpStatus; import jakarta.validation.Valid; import java.util.List;
@RestController @RequestMapping("/api/customers") public class CustomerController {
private final CustomerRepository repo; public CustomerController(CustomerRepository repo){this.repo=repo;}
@GetMapping public List<Customer> all(){return repo.findAll();}
@GetMapping("/{id}") public Customer one(@PathVariable Long id){return repo.findById(id).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Client introuvable"));}
@PostMapping @ResponseStatus(HttpStatus.CREATED) public Customer create(@Valid @RequestBody Customer c){c.id=null;return repo.save(c);}
@PutMapping("/{id}") public Customer update(@PathVariable Long id,@Valid @RequestBody Customer c){one(id);c.id=id;return repo.save(c);}
}