package com.example.miniprojectbackend.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
@NoArgsConstructor
public class SongRequest {
  private String melody;
  private int numberOfVerses;
  private String cause;
  private String name;
  private String adjectives;
  private String mood;

}
