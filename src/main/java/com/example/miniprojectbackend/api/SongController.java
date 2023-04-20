package com.example.miniprojectbackend.api;

import com.example.miniprojectbackend.dto.ChangeRequest;
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

  @GetMapping
  String string(){
    return "hej";
  }

   @PostMapping("write")
   SongResponse getSong(@RequestBody SongRequest songRequest) throws JsonProcessingException {
    SongResponse songResponse = songService.writeSong(songRequest).block();
     System.out.println(songResponse);
    return songResponse;
  }

  @PostMapping("change")
  SongResponse getChangedSong(@RequestBody ChangeRequest changeRequest) throws JsonProcessingException {
    SongResponse songResponse = songService.changeSong(changeRequest).block();
    System.out.println(songResponse);
    return songResponse;
  }
}
