//package org.viora.viorastreamingcore;
//
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Component;
//import org.viora.viorastreamingcore.account.model.AccountModel;
//import org.viora.viorastreamingcore.account.repository.AccountRepository;
//
//
//@Component
//@RequiredArgsConstructor
//public class CreateUserRunner implements CommandLineRunner {
//
//  private final AccountRepository accountRepository;
//  private final PasswordEncoder passwordEncoder;
//
//  @Override
//  public void run(String... args) throws Exception {
//    AccountModel accountModel = new AccountModel(null, "arthur@example.com",
//        passwordEncoder.encode("123456"), true);
//    accountModel = accountRepository.save(accountModel);
//  }
//}
