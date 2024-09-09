package com.springkafka.demo.test;

import com.springkafka.demo.main.SpringKafkaApplication;
import com.springkafka.demo.main.dto.Book;
import org.junit.jupiter.api.*;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;

import java.util.Collections;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.web.util.UriComponentsBuilder.fromUriString;

/**
 *  Integration Test Implementation
 *  of the {@link com.springkafka.demo.main.controller.RestApiController}
 *
 *  accessToken - Change with Real time generated token - Before test
 *
 *  Api endpoints test case
 *
 *  1. Create Book Detail
 *  2. Update Book Detail by Book Id
 *  3. Get Book Detail by Book Id
 *  4. Delete Book Detail by Book Id
 *
 */

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = SpringKafkaApplication.class)
class CreateGetUpdateDeleteBookAPITest {
    private final TestRestTemplate restTemplate;
    HttpHeaders headers;
    @LocalServerPort
    private int port;

    @Autowired
    public CreateGetUpdateDeleteBookAPITest(TestRestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        //Change with Real time generated token - Before test
        String accessToken ="eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImlhdCI6MTcyNTY1MzUxMiwiZXhwIjoxNzI1NjcxNTEyfQ.poONH11Os_1zEwomJxnXMCpPVpPZovH-rZkGTM0DxI45M2HPbxV7F8T5I4fBEtcHRsvqGm1l5H2vTgtI6spluQ";
        headers.set("Authorization", "Bearer " + accessToken);
    }

    @Test
    @Order(1)
    void createBookDetail(){
        var resMsg = "Book details saved";
        Book request = new Book("Java Fundamental",  "John Doe");
        final var requestEntity = new HttpEntity<>(request, headers);
        final var targetUrl = fromUriString("http://localhost:" + port + "/rest/api/books")
                .build()
                .encode()
                .toUri();
        System.out.println(targetUrl);
        final var response = restTemplate.exchange(
                targetUrl,
                HttpMethod.POST,
                requestEntity,
                new ParameterizedTypeReference<Map<String, String>>() {
                });

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(resMsg, response.getBody().get("message"));
    }

    @Test
    @Order(2)
    void updateBookDetail(){
        var resMsg = "Book details updated";
        Book request = new Book("Java Fundamental Update",  "John Smith");
        final var requestEntity = new HttpEntity<>(request, headers);
        final var targetUrl = fromUriString("http://localhost:" + port + "/rest/api/books/1")
                .build()
                .encode()
                .toUri();
        System.out.println(targetUrl);
        final var response = restTemplate.exchange(
                targetUrl,
                HttpMethod.PUT,
                requestEntity,
                new ParameterizedTypeReference<Map<String, String>>() {
                });

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(resMsg, response.getBody().get("message"));
    }

    @Test
    @Order(3)
    void getBookDetail(){
        Book expectedBook = new Book("Java Fundamental Update",  "John Smith");
        final var requestEntity = new HttpEntity<>(headers);
        final var targetUrl = fromUriString("http://localhost:" + port + "/rest/api/books/1")
                .build()
                .encode()
                .toUri();
        System.out.println(targetUrl);
        final var response = restTemplate.exchange(
                targetUrl,
                HttpMethod.GET,
                requestEntity,
                new ParameterizedTypeReference<Map<String, String>>() {
                });

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(expectedBook.getTitle(), response.getBody().get("title"));
        assertEquals(expectedBook.getAuthor(), response.getBody().get("author"));
    }

    @Test
    @Order(4)
    void deleteBooKDetail(){
        var resMsg = "Book details deleted";
        final var requestEntity = new HttpEntity<>(headers);
        final var targetUrl = fromUriString("http://localhost:" + port + "/rest/api/books/1")
                .build()
                .encode()
                .toUri();
        System.out.println(targetUrl);
        final var response = restTemplate.exchange(
                targetUrl,
                HttpMethod.DELETE,
                requestEntity,
                new ParameterizedTypeReference<Map<String, String>>() {
                });

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(resMsg, response.getBody().get("message"));
    }

}