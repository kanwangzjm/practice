package com.mmall.practice.example.datasource.routing;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({
        ElementType.METHOD
})
public @interface RoutingDataSource {

    String value() default DataSources.MASTER_DB;
}
