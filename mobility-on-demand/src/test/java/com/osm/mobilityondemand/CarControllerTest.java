package com.osm.mobilityondemand;

import static org.assertj.core.api.Assertions.assertThat;

import com.osm.mobilityondemand.entity.Car;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class CarControllerTest {

  @LocalServerPort
  private int port;

  @Autowired
  private TestRestTemplate restTemplate;

  @Test
  public void getOneCar() throws Exception {
    Assertions.assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/cars/1", Car.class).
        equals(new Car("BMW","2.0","CUSTOM","SPECIAL","44.25")));
  }

}
