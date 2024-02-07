package com.codedifferently.studycrm.organizations.web;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

@SpringBootTest
@ContextConfiguration(classes = {TestConfiguration.class})
class OrganizationsWebConfigurationTest {

  @Test
  void testWebConfiguration_loads() throws Exception {
    System.out.println(getClass());
  }
}