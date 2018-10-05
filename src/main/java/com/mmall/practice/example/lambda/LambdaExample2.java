package com.mmall.practice.example.lambda;

import lombok.extern.slf4j.Slf4j;

import java.text.DecimalFormat;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

@Slf4j
public class LambdaExample2 {

    public static void main(String[] args) {
        Function<Integer, String> moneyFormat = (i) -> new DecimalFormat("#,###").format(i);
        log.info("{}", moneyFormat.apply(1234567890));

        Predicate<Integer> predicate = (i) -> i > 0;
        log.info("{}", predicate.test(-1));
        log.info("{}", predicate.test(1));

        Consumer<String> consumer = (i) -> log.info(i);
        consumer.accept("test consumer");

        Supplier<Integer> supplier = () -> (1 + 100) * 100 / 2;
        log.info("{}", supplier.get());

        UnaryOperator<Integer> unaryOperator = (i -> (1 + i) * i / 2);
        log.info("{}", unaryOperator.apply(100));

        BinaryOperator<Integer> binaryOperator = (i, j) -> i * j;
        log.info("{}", binaryOperator.apply(2, 3));

        BiFunction<Integer, Integer, String> biFunction = (i, j) -> String.valueOf(i * j);
        log.info("{}", biFunction.apply(2, 3));
    }
}
