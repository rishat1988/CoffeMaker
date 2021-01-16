package com.example.server;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.config.server.environment.EnvironmentController;

@SpringBootTest(properties = { "spring.profiles.active=native" })
class ServerApplicationTests {

    @Autowired
    private EnvironmentController controller;

    @Test
    void contextLoads()  {
        assertThat(controller).isNotNull();
    }

}
