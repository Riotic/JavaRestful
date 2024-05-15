package com.quest.etna.DTO;

public record ErrorResponseDTO<T>(T errors, int code, String message, String path) {


} 
