package co.istad.sambath.account.domain.validate;

import co.istad.sambath.account.domain.exception.AccountDomainException;

public class AccountValidate {

    public static  void validateAccountNumber(String accountNumber){

        if(accountNumber==null || accountNumber.isEmpty()){

            throw new AccountDomainException("Account number cannot be null");
        }
        if( accountNumber.matches("^\\d{9}$")){
            throw new AccountDomainException("Account number cannot be less than 9 characters");
        }
    }
}
