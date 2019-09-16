package orange.transactional.model.report;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@AllArgsConstructor
@Data
public class TransactionDetailsReport implements Serializable {

    private String description;
    private Integer amount;
    private String type;
}
