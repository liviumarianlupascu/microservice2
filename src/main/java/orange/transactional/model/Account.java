package orange.transactional.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.Objects;

@IdClass(AccountId.class)
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "account")
public class Account implements Serializable {

    @Id
    @NotEmpty(message = "CNP may not be empty")
    @Column(name="cnp", length=13)
    private String cnp;

    @Id
    @NotEmpty(message = "IBAN may not be empty")
    @Column(name="iban", length=50)
    private String iban;

    @NotEmpty(message = "Name may not be empty")
    @Column(name="name")
    private String name;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return Objects.equals(cnp, account.cnp) &&
               Objects.equals(iban, account.iban) &&
               Objects.equals(name, account.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cnp, iban, name);
    }
}
