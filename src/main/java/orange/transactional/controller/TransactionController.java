package orange.transactional.controller;

import orange.transactional.model.Account;
import orange.transactional.model.Transaction;
import orange.transactional.service.PersistService;
import orange.transactional.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orange")
public class TransactionController {

    @Autowired
    private PersistService persistService;

    @Autowired
    private ReportService reportService;

    @RequestMapping(value = "/enroll/transaction", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public HttpEntity<Transaction> enrollAccount(@RequestBody Transaction transaction){
        persistService.persistTransaction(transaction);
        return new ResponseEntity(persistService.findById(transaction.getTransactionId()), HttpStatus.OK);
    }

    @RequestMapping(value = "/enroll/account", method = RequestMethod.POST)
    public HttpEntity<Account> enrollAccount(@RequestBody Account account){
        return new ResponseEntity(persistService.loadAccount(account), HttpStatus.OK);
    }

    @RequestMapping(value = "/report", method = RequestMethod.GET)
    public HttpEntity<String> getReport(@RequestParam(required=true) String cnp){
        return new ResponseEntity(reportService.findReportForPayer(cnp) + reportService.findReportForPayee(cnp), HttpStatus.OK);
    }

}
