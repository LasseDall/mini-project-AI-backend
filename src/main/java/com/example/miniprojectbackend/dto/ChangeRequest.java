package com.example.miniprojectbackend.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ChangeRequest {
  private String melody;
  private String song;
  private String comment;
}
