package com.mmall.practice.example.lambda;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.IntUnaryOperator;
import java.util.function.Supplier;

@Slf4j
public class LambdaExample3 {

    public static void main(String[] args) {

        Dog dog = new Dog("二哈", 10);

        log.info("accept data:");

        IntUnaryOperator unaryOperator = dog::eat;
        log.info("【{}】还剩{}", dog.getName(), unaryOperator.applyAsInt(2));

        Consumer<Dog> consumer2 = Dog::bark;
        consumer2.accept(dog);

        Supplier<Dog> dogSupplier = Dog::new;
        Dog dog2 = dogSupplier.get();

        BiFunction<Dog, Integer, Integer> biFunction = Dog::eat;
        log.info("【{}】还剩{}", dog2.getName(), biFunction.apply(dog2, 2));

        consumer2.accept(dog2);
    }
}

@Getter
@Setter
@ToString
@AllArgsConstructor
@Slf4j
class Dog {
    private String name;
    private int food;

    public Dog() {
        this.name = "二哈一号";
        this.food = 8;
    }

    public int eat(int num) {
        this.food -= num;
        return this.food;
    }

    public void bark() {
        log.info("【{}】bark", name);
    }
}
