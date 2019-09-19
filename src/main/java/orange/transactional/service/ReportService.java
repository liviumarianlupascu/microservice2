package orange.transactional.service;

import orange.transactional.model.Account;
import orange.transactional.model.TransactionTypeEnum;
import orange.transactional.model.report.TransactionDetailsReport;
import orange.transactional.model.report.TransactionReport;
import orange.transactional.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.stream.Collectors;

@Transactional
@Service
public class ReportService {

    @Autowired
    private AccountRepository accountRepository;

    private List<TransactionDetailsReport> findTrDetailsForPayer(String cnp, String iban) {
        return accountRepository.findTrDetailsForPayer(cnp, iban);
    }

    private List<TransactionReport> findTransactionReportForPayer(String cnp, String iban) {
        return accountRepository.findTransactionReportForPayer(cnp, iban);
    }

    private List<TransactionDetailsReport> findTrDetailsForPayee(String cnp, String iban) {
        return accountRepository.findTrDetailsForPayee(cnp, iban);
    }

    private List<TransactionReport> findTransactionReportForPayee(String cnp, String iban) {
        return accountRepository.findTransactionReportForPayee(cnp, iban);
    }

    public String findReportForPayer(String cnp) {
        StringBuffer buffer = new StringBuffer();
        List<Account> accountList = accountRepository.findByCnp(cnp);
        for (Account account : accountList) {
            appendHeader(buffer, account, "Role -> Payer\n");
            List<TransactionDetailsReport> transactionDetailsReport = findTrDetailsForPayer(cnp, account.getIban());
            List<TransactionReport> transactionsReport = findTransactionReportForPayer(cnp, account.getIban());
            appendReportContent(buffer, transactionDetailsReport, transactionsReport);
            appendNoTransactions(buffer, transactionsReport);
            buffer.append("\n");
        }
        return buffer.toString();
    }

    public String findReportForPayee(String cnp) {
        StringBuffer buffer = new StringBuffer();
        List<Account> accountList = accountRepository.findByCnp(cnp);
        for (Account account : accountList) {
            buffer.append("\n");
            buffer.append("\n");
            appendHeader(buffer, account, "Role -> Payee\n");
            List<TransactionDetailsReport> transactionDetailsReport = findTrDetailsForPayee(cnp, account.getIban());
            List<TransactionReport> transactionsReport = findTransactionReportForPayee(cnp, account.getIban());
            appendReportContent(buffer, transactionDetailsReport, transactionsReport);
            appendNoTransactions(buffer, transactionsReport);
            buffer.append("\n");
        }
        return buffer.toString();
    }

    private void appendReportContent(StringBuffer buffer, List<TransactionDetailsReport> transactionDetailsReport, List<TransactionReport> transactionsReport) {
        for (TransactionTypeEnum transactionType : TransactionTypeEnum.values()) {
            transactionsReport
                    .stream()
                    .filter(transactionReport -> transactionReport.getType().equals(transactionType.type()))
                    .forEach(transactionReport ->
                            buffer.append(transactionType + " | " + transactionReport.getTransactionsNumber() + " tranzactii | " + transactionReport.getTransactionsSum() + " de RON\n"));
            transactionDetailsReport
                    .stream()
                    .filter(transactionDetail -> transactionDetail.getType().equals(transactionType.type()))
                    .forEach(transactionDetail ->
                            buffer.append("     Descriere: " + transactionDetail.getDescription() + "   " + "Valoare: " + transactionDetail.getAmount() + "\n"));
        }
    }

    private void appendNoTransactions(StringBuffer buffer, List<TransactionReport> transactionsReport) {
        for (TransactionTypeEnum transactionType : TransactionTypeEnum.values()) {
            if (!transactionsReport
                    .stream()
                    .map(transactionReport -> transactionReport.getType())
                    .collect(Collectors.toList())
                    .contains(transactionType.type())) {
                buffer.append(transactionType + " | fara tranzactii \n");
            }
        }
    }

    private void appendHeader(StringBuffer buffer, Account account, String s) {
        buffer.append("Nume: " + account.getName() + "\n");
        buffer.append("CNP: " + account.getCnp() + "\n");
        buffer.append(s);
        buffer.append("\n");
        buffer.append("Tranzactii: \n");
        buffer.append("IBAN: " + account.getIban() + "\n");
    }

}
