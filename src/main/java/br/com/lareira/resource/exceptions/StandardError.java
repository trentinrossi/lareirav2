package br.com.lareira.resource.exceptions;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// Classe criada na aula https://www.udemy.com/course/spring-boot-ionic/learn/lecture/8090544#overview
// Serve como uma classe auxiliar para padronizar as mensagens
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class StandardError implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer status;
    private String msg;
    private Long timeStamp;    

}