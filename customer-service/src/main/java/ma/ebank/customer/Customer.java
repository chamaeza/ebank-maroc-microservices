package ma.ebank.customer;
import jakarta.persistence.*; import jakarta.validation.constraints.*; import java.time.LocalDate;
@Entity public class Customer {
@Id @GeneratedValue(strategy=GenerationType.IDENTITY) public Long id;
@NotBlank public String firstName; @NotBlank public String lastName;
@NotBlank @Column(unique=true,nullable=false) public String cin;
@NotBlank @Email public String email;
@NotBlank @Pattern(regexp="\\+212[0-9]{9}") public String phone;
@NotNull @Past public LocalDate birthDate;
@NotBlank public String city; @NotBlank public String region; @NotBlank public String address; @NotBlank public String profession;
}