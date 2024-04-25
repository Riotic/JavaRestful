package com.quest.etna.DTO;

public record ResponseDTO<T> (T data, int code, String message, String path) {
    
}
