package com.example.miniprojectbackend.api;

import com.example.miniprojectbackend.dto.SongRequest;
import com.example.miniprojectbackend.dto.SongResponse;
import com.example.miniprojectbackend.service.SongService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/api/song/")
public class SongController {

  private SongService songService;

  public SongController(SongService songService){
    this.songService = songService;
  }

   @PostMapping
   SongResponse getSong(@RequestBody SongRequest songRequest) throws JsonProcessingException {
    SongResponse songResponse = songService.writeSong(songRequest).block();
     System.out.println(songResponse.getText());
    return songResponse;
  }
}
