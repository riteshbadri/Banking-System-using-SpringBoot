package net.javaguides.banking_app.dto;
//
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.Getter;
//import lombok.Setter;
//
//@Setter
//@Getter
//@AllArgsConstructor
//@Data
//public class AccountDto {
//    private Long id;
//    private String AccountHolderName;
//    private double balance;
//
//}

public record AccountDto(Long id, String AccountHolderName, double balance) {
}
