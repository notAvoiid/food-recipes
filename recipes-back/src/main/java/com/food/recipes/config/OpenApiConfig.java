package com.food.recipes.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    OpenAPI customOpenApi(){
        return new OpenAPI()
                .info(new Info()
                        .title("Recipes!")
                        .version("v1")
                        .description("This API is for you post your recipes and learn with others recipes!")
                        .termsOfService("https://github.com/notAvoiid/food-recipes")
                        .license(
                                new License()
                                        .name("Apache 2.0")
                                        .url("https://github.com/notAvoiid/food-recipes")));
    }

}
