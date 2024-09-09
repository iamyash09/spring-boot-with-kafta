package com.springkafka.demo.main.dto;

/**
 * A class representing login response
 *
 * @param username
 * @param token
 */
public record LoginRespDTO(String username, String token) {
}