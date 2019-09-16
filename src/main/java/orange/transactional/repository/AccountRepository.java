package orange.transactional.repository;


import orange.transactional.model.Account;
import orange.transactional.model.report.TransactionDetailsReport;
import orange.transactional.model.report.TransactionReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    Account findAccountByCnpAndIban(String cnp, String iban);
    List<Account> findByCnp(String cnp);

    @Query("select new orange.transactional.model.report.TransactionDetailsReport( td.description AS description, td.amount AS amount, td.type AS type )" +
            "  from TransactionDetails td, Transaction tr " +
            "  where td.trDetailsId = tr.transactionDetails.trDetailsId and " +
            "  tr.payer.iban = :ibanParameter and " +
            "  tr.payer.cnp = :cnpParameter")
    List<TransactionDetailsReport> findTrDetailsForPayer(@Param("cnpParameter") String cnp, @Param("ibanParameter") String iban);

    @Query("select new orange.transactional.model.report.TransactionReport(td.type AS type, count(td) AS transactionsNumber, sum(td.amount) AS transactionsSum) " +
            "  from TransactionDetails td, Transaction tr" +
            "  where td.trDetailsId = tr.transactionDetails.trDetailsId and" +
            "  tr.payer.iban = :ibanParameter and " +
            "  tr.payer.cnp = :cnpParameter " +
            "  group by(td.type) ")
    List<TransactionReport> findTransactionReportForPayer(@Param("cnpParameter") String cnp, @Param("ibanParameter") String iban);

    @Query("select new orange.transactional.model.report.TransactionDetailsReport( td.description AS description, td.amount AS amount, td.type AS type )" +
            "  from TransactionDetails td, Transaction tr " +
            "  where td.trDetailsId = tr.transactionDetails.trDetailsId and " +
            "  tr.payee.iban = :ibanParameter and " +
            "  tr.payee.cnp = :cnpParameter")
    List<TransactionDetailsReport> findTrDetailsForPayee(@Param("cnpParameter") String cnp, @Param("ibanParameter") String iban);

    @Query("select new orange.transactional.model.report.TransactionReport(td.type AS type, count(td) AS transactionsNumber, sum(td.amount) AS transactionsSum) " +
            "  from TransactionDetails td, Transaction tr" +
            "  where td.trDetailsId = tr.transactionDetails.trDetailsId and" +
            "  tr.payee.iban = :ibanParameter and " +
            "  tr.payee.cnp = :cnpParameter " +
            "  group by(td.type) ")
    List<TransactionReport> findTransactionReportForPayee(@Param("cnpParameter") String cnp, @Param("ibanParameter") String iban);

}
