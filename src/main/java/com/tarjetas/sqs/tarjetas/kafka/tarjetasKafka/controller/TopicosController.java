package com.tarjetas.sqs.tarjetas.kafka.tarjetasKafka.controller;

import com.tarjetas.sqs.tarjetas.kafka.tarjetasKafka.models.Topicos;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/ext/topicos")
@RestController
public class TopicosController {
    @GetMapping("")
    public Topicos[] enviarTarjetaExternaKafka(){
        return Topicos.values();
    }
}
