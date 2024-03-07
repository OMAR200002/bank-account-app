package net.youssfi.accountservice.web;

import lombok.AllArgsConstructor;
import net.youssfi.accountservice.CustomerRestClient;
import net.youssfi.accountservice.entities.BankAccount;
import net.youssfi.accountservice.model.Customer;
import net.youssfi.accountservice.repository.BankAccountRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
public class AccountRestController {
    private BankAccountRepository accountRepository;
    private CustomerRestClient customerRestClient;
    @GetMapping("/accounts")
    public List<BankAccount> accountList(){
        List<BankAccount> accountList = accountRepository.findAll();
        accountList.forEach(acc-> acc.setCustomer(customerRestClient.findCustomerById(acc.getCustomerId())));
        return accountList;
    }
    @GetMapping("/accounts/{id}")
    public BankAccount bankAccountById(@PathVariable String id){
        BankAccount bankAccount= accountRepository.findById(id).orElseThrow(() -> new RuntimeException("Account not found"));
        Customer customer=customerRestClient.findCustomerById(bankAccount.getCustomerId());
        bankAccount.setCustomer(customer);
        return bankAccount;
    }

}
