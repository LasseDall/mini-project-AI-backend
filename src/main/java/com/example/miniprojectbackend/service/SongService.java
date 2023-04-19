package com.example.miniprojectbackend.service;

import com.example.miniprojectbackend.dto.SongRequest;
import com.example.miniprojectbackend.dto.SongResponse;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.util.JSONPObject;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.*;

@Service
public class SongService {

  public Mono<SongResponse> writeSong(SongRequest songRequest) {
      WebClient client = WebClient.create();
    List<Map<String, String>> messagesList = new ArrayList<>();
    Map<String, String> messageMap = new HashMap<>();
    messageMap.put("role", "user");
    messageMap.put("content", "Skriv en sang til mathilde på melodien jeg ved en lærkerede på 2 vers!");
    messagesList.add(messageMap);
      Map<String, Object> bodyMap = new HashMap<>();
      bodyMap.put("messages", messagesList);
      bodyMap.put("model", "gpt-3.5-turbo");
      Mono<SongResponse> songResponse = client.post()
          .uri("https://api.openai.com/v1/chat/completions")
          .header("Authorization", "Bearer " + System.getenv("API_KEY"))
          .header("Content-Type", "application/json")
          .bodyValue(bodyMap)
          .retrieve()
          .bodyToMono(JsonNode.class)
          .map(json -> {
            String melody = songRequest.getMelody();
            String song = json.get("choices").get(0).get("message").get("content").asText();
            return new SongResponse(melody, song);
          });
      return songResponse;
    }
  }

