package com.restaurant.controller;

import com.restaurant.RestaurantManagementApplication;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Profile;

@Profile(value = {"test"})
@SpringBootTest(classes = RestaurantManagementApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class WebClientControllerTest {

  @LocalServerPort
  int randomServerPort;

  @Autowired
  private TestRestTemplate restTemplate;

  private String baseUrl;

  @BeforeEach
  void before() {
    baseUrl = "http://localhost:" + randomServerPort + "/api/v1/web-client/track-package?trackingNumber=SPXVN048106749441";
  }

  //    @Test
  //    void testSingleton() throws InterruptedException, URISyntaxException {
  //        URI url = new URI(baseUrl);
  //        System.out.println("url:: " + url);
  //        String baseUrl2 = "http://localhost:" + randomServerPort + "/api/v1/web-client/track-package?trackingNumber=SPXVN049000000000";
  //        URI url2 = new URI(baseUrl2);
  //        //      UserDto user1 = new UserDto("user1", "123");
  //        //      UserDto user2 = new UserDto("user2", "456");
  //        //
  //        //      HttpHeaders headers = new HttpHeaders();
  //        //      headers.set("X-COM-PERSIST", "true");
  //        //
  //        //      HttpEntity<UserDto> request1 = new HttpEntity<>(user1, headers);
  //        //      HttpEntity<UserDto> request2 = new HttpEntity<>(user2, headers);
  //
  //        Thread thread1 = new Thread(new GetRequestTask<>(url));
  //        Thread thread2 = new Thread(new GetRequestTask<>(url2));
  //
  //        thread1.start();
  //        thread2.start();
  //
  //        thread1.join();
  //        thread2.join();
  //
  //        //      System.out.println("number of users created: " + userService.numberOfCreatedUsers);
  //        //
  //        //      assertEquals(2, userService.numberOfCreatedUsers,
  //        //            "number of users created: " + userService.numberOfCreatedUsers);
  //    }

  //    @Test
  //    public void testTrackPackageWithInvalidCredentials() throws Exception {
  //        //        try {
  //        restTemplate.getForEntity(baseUrl, String.class, "invalid", "invalid");
  //        //        } catch (RestClientResponseException ex) {
  //        //            // Expected 403 Forbidden response
  //        //            assertEquals(HttpStatusCode.valueOf(403), ex.getStatusCode());
  //        //        }
  //    }
}