package com.quest.etna.DTO;

public record ResponseDTO<T> (T details, int code, String message, String path) {

}