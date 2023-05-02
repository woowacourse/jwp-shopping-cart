package cart.service;

import cart.dao.AccountDao;
import cart.domain.account.Account;
import cart.dto.application.AccountDto;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class AccountService {

    private final AccountDao accountDao;

    public AccountService(final AccountDao accountDao) {
        this.accountDao = accountDao;
    }

    public List<Account> fetchAll() {
        return accountDao.fetchAll();
    }

    public boolean isMember(final AccountDto accountDto) {
        final Account account = new Account(accountDto.getUsername(), accountDto.getPassword());
        return accountDao.isMember(account);
    }
}
