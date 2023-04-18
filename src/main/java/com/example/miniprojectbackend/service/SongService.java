package com.example.miniprojectbackend.service;

import com.example.miniprojectbackend.dto.SongRequest;
import com.example.miniprojectbackend.dto.SongResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.*;

@Service
public class SongService {

  public Mono<String> writeSong(SongRequest songRequest) {
      WebClient client = WebClient.create();
    List<Map<String, String>> messagesList = new ArrayList<>();
    Map<String, String> messageMap = new HashMap<>();
    messageMap.put("role", "user");
    messageMap.put("content", "Hello!");
    messagesList.add(messageMap);
      Map<String, Object> bodyMap = new HashMap<>();
      bodyMap.put("messages", messagesList);
      bodyMap.put("model", "gpt-3.5-turbo");
      Mono<String> text = client.post()
          .uri("https://api.openai.com/v1/chat/completions")
          .header("Authorization", "Bearer " + System.getenv("API_KEY"))
          .header("Content-Type", "application/json")
          .bodyValue(bodyMap)
          .retrieve()
          .bodyToMono(String.class);
    System.out.println(text.block());
      return text;
    }
  }

