package orange.transactional.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "tr_details")
public class TransactionDetails implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "transactionDetailsIdgen")
    @SequenceGenerator(name = "transactionDetailsIdgen", sequenceName = "seqTransactionDetails")
    private Long trDetailsId;

    @NotEmpty(message = "Description may not be empty")
    @Column(name="description")
    private String description;

    @NotNull(message = "Amount may not be null")
    @Column(name="amount")
    private Integer amount;

    @Column(name="type")
    private String type;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TransactionDetails that = (TransactionDetails) o;
        return Objects.equals(trDetailsId, that.trDetailsId) &&
                Objects.equals(description, that.description) &&
                Objects.equals(amount, that.amount) &&
                Objects.equals(type, that.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(trDetailsId, description, amount, type);
    }
}
