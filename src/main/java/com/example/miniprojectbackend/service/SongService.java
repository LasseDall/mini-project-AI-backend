package com.example.miniprojectbackend.service;

import com.example.miniprojectbackend.dto.ChangeRequest;
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

  private Map<String, String> songTitles = new HashMap<>();
  private boolean titleKnown = false;


  public Mono<SongResponse> writeSong(SongRequest songRequest) {
      WebClient client = WebClient.create();
      songTitles.put("jeg ved en lærkerede", "Jeg ved en lærkerede \n Jeg siger ikke mer' \n Den findes på en hede \n Et sted som ingen ser.");
      songTitles.put("velkommen i den grønne lund", "Velkommen i den grønne lund! \n Hvor fuglene de sjunge. \n Det høres skal: Den danske mund \n Til sang har og en tunge \n Vi har det godt i grunden her, \n Såvel som vore fædre; \n vil gud, den dag tør være nær, \n vi får det end lidt bedre.");
    List<Map<String, String>> messagesList = new ArrayList<>();
    Map<String, String> messageMap = new HashMap<>();
    messageMap.put("role", "user");
    if (songTitles.containsKey(songRequest.getMelody().toLowerCase())) {
        String verse = songTitles.get(songRequest.getMelody().toLowerCase());
      messageMap.put("content", "Skriv en sang til " + songRequest.getName() + " i af anledning af " + songRequest.getName() + "'s " + songRequest.getCause() + ". Sangen skal være på melodien " + songRequest.getMelody() + " og have " + songRequest.getNumberOfVerses() + " vers. Første vers af " + songRequest.getMelody() + " er som følger: " + verse + "Du skal således skrive en sang med samme antal linjer og stavelser og samme type rim pr. vers. Stemningen i sangen skal være " + songRequest.getMood() + ". " + songRequest.getName() + " er " + songRequest.getAdjectives() + ". Sangen skal rime i par på dansk! Snagen skal således ikke skrives på engelsk og derefter oversættes, men skrives direkte på dansk og rime! Ligeledes skal stavelserne passe på melodien på dansk!");
    } else {
      messageMap.put("content", "Skriv en sang til " + songRequest.getName() + " i af anledning af " + songRequest.getName() + "'s " + songRequest.getCause() + ". Sangen skal være på melodien " + songRequest.getMelody() + " og have " + songRequest.getNumberOfVerses() + " vers. Stemningen i sangen skal være " + songRequest.getMood() + ". " + songRequest.getName() + " er " + songRequest.getAdjectives() + ". Sangen skal rime i par på dansk! Snagen skal således ikke skrives på engelsk og derefter oversættes, men skrives direkte på dansk og rime! Ligeledes skal stavelserne passe på melodien på dansk!");
    }
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

    public Mono<SongResponse> changeSong(ChangeRequest changeRequest) {
      WebClient client = WebClient.create();
      List<Map<String, String>> messagesList = new ArrayList<>();
      Map<String, String> messageMap = new HashMap<>();
      messageMap.put("role", "user");
      messageMap.put("content", "Du har skrevet denne sang: " + changeRequest.getSong() + ". På melodien " + changeRequest.getMelody() + ". Ændre sangen så den opfylder det følgende: " + changeRequest.getComment());
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
            String melody = changeRequest.getMelody();
            String song = json.get("choices").get(0).get("message").get("content").asText();
            return new SongResponse(melody, song);
          });
      return songResponse;
    }
  }

