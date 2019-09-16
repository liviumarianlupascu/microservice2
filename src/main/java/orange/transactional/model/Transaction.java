package orange.transactional.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name ="transaction")
public class Transaction implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "transactionIdgen")
    @SequenceGenerator(name = "transactionIdgen", sequenceName = "seqTransaction")
    @Column(name ="transactionId")
    private Long transactionId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumns({
            @JoinColumn(name="cnpPayee", referencedColumnName="cnp"),
            @JoinColumn(name="ibanPayee", referencedColumnName="iban")
    })
    private Account payee;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumns({
            @JoinColumn(name="cnpPayer", referencedColumnName="cnp"),
            @JoinColumn(name="ibanPayer", referencedColumnName="iban")
    })
    private Account payer;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "transactionDetailsId")
    private TransactionDetails transactionDetails;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transaction that = (Transaction) o;
        return Objects.equals(transactionId, that.transactionId) &&
                Objects.equals(payee, that.payee) &&
                Objects.equals(payer, that.payer) &&
                Objects.equals(transactionDetails, that.transactionDetails);
    }

    @Override
    public int hashCode() {
        return Objects.hash(transactionId, payee, payer, transactionDetails);
    }
}


