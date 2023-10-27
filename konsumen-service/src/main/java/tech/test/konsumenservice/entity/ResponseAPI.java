package tech.test.konsumenservice.entity;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResponseAPI{
    private String code;
    private List<String> messages;
    private List<Konsumen> data;
}