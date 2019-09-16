package orange.transactional.model.report;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@AllArgsConstructor
@Data
public class TransactionReport implements Serializable {

    private String type;
    private Long transactionsNumber;
    private Long transactionsSum;
}
