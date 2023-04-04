package com.dg_mw.calculator.services;

import com.dg_mw.calculator.dtos.CalculatorOperationResponse;

import java.util.List;

public interface ICalculatorOperationService {
    CalculatorOperationResponse add(List<Float> numbers);
    CalculatorOperationResponse subtract(List<Float> numbers);
    CalculatorOperationResponse multiply(List<Float> numbers);
    CalculatorOperationResponse divide(List<Float> numbers);
}






package com.dg_mw.calculator.services;

import com.dg_mw.calculator.dtos.CalculatorOperationResponse;
import lombok.AllArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@AllArgsConstructor
public class CalculatorOperationService implements ICalculatorOperationService {
    private final Logger logger = LogManager.getLogger(CalculatorOperationService.class);
    @Override
    public CalculatorOperationResponse add(List<Float> numbers) {
        logger.info("Called method add");
        Float result = numbers.stream().reduce(0f, Float::sum);
        return new CalculatorOperationResponse(result);
    }

    @Override
    public CalculatorOperationResponse subtract(List<Float> numbers) {
        logger.info("Called method subtract");
        Float identity = numbers.remove(0);
        Float result = numbers.stream().reduce(identity, (partialSum, number) -> partialSum - number);
        return new CalculatorOperationResponse(result);
    }

    @Override
    public CalculatorOperationResponse multiply(List<Float> numbers) {
        logger.info("Called method multiply");
        Float result = numbers.stream().reduce(1f, (partialSum, number) -> partialSum * number);
        return new CalculatorOperationResponse(result);
    }

    @Override
    public CalculatorOperationResponse divide(List<Float> numbers) {
        logger.info("Called method divide");
        if (numbers.stream().anyMatch(number -> number == 0f)) {
            logger.error("Divided by 0");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Can't divide by 0!");
        }
        Float identity = numbers.remove(0);
        Float result = numbers.stream().reduce(identity, (partialSum, number) -> partialSum / number);
        return new CalculatorOperationResponse(result);
    }
}




package com.dg_mw.calculator.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CalculatorOperationResponse {
    private Float result;
}




package com.dg_mw.calculator.controllers;

import com.dg_mw.calculator.dtos.CalculatorOperationResponse;
import com.dg_mw.calculator.services.CalculatorOperationService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Validated
@RestController
@AllArgsConstructor
@RequestMapping("calculator-operations")
public class CalculatorOperationController {
    private CalculatorOperationService calculatorOperationsService;

    @GetMapping("/add")
    public CalculatorOperationResponse add(
            @Valid
            @NotNull
            @Size(min = 2, message = "Provide at least two numbers")
            @RequestParam(name = "numbers")
            List<Float> numbers
    ) {
        return this.calculatorOperationsService.add(numbers);
    }

    @GetMapping("/subtract")
    public CalculatorOperationResponse subtract(
            @Valid
            @NotNull
            @Size(min = 2, message = "Provide at least two numbers")
            @RequestParam(name = "numbers")
            List<Float> numbers
    ) {
        return this.calculatorOperationsService.subtract(numbers);
    }

    @GetMapping("/multiply")
    public CalculatorOperationResponse multiply(
            @Valid
            @NotNull
            @Size(min = 2, message = "Provide at least two numbers")
            @RequestParam(name = "numbers")
            List<Float> numbers
    ) {
        return this.calculatorOperationsService.multiply(numbers);
    }

    @GetMapping("/divide")
    public CalculatorOperationResponse divide(
            @Valid
            @NotNull
            @Size(min = 2, message = "Provide at least two numbers")
            @RequestParam(name = "numbers")
            List<Float> numbers
    ) {
        return this.calculatorOperationsService.divide(numbers);
    }
}



// log4j2.xml

<?xml version="1.0" encoding="UTF-8"?>
<Configuration status="WARN">
    <Appenders>
        <Console name="Console" target="SYSTEM_OUT">
            <PatternLayout pattern="%d{yyyy-MM-dd HH:mm:ss.SSS} [%t] %-5level
%logger{36} - %msg%n"/>
        </Console>
    </Appenders>
    <Loggers>
        <Logger name="com.example" level="info" additivity="false">
            <AppenderRef ref="Console"/>
        </Logger>
        <Root level="error">
            <AppenderRef ref="Console"/>
        </Root>
    </Loggers>
</Configuration>

