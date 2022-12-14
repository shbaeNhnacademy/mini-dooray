package com.nhnacademy.edu.minidooraygateway.controller;

import com.nhnacademy.edu.minidooraygateway.dto.AccountDto;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@RequestMapping("/accounts")
@RequiredArgsConstructor
public class AccountController {

    private static final ParameterizedTypeReference<List<AccountDto>> LIST_PARAMETERIZED_TYPE =
            new ParameterizedTypeReference<List<AccountDto>>() {};

    private final RestTemplate restTemplate;

    @GetMapping
    public List<AccountDto> getAccounts() {

    }
}
