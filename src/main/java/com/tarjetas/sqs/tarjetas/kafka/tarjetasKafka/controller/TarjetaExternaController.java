package com.tarjetas.sqs.tarjetas.kafka.tarjetasKafka.controller;

import com.amazonaws.services.sqs.model.Message;
import com.tarjetas.sqs.tarjetas.kafka.tarjetasKafka.models.TarjetaExterna;
import com.tarjetas.sqs.tarjetas.kafka.tarjetasKafka.models.Topicos;
import com.tarjetas.sqs.tarjetas.kafka.tarjetasKafka.services.TarjetaExternaKafkaService;
import com.tarjetas.sqs.tarjetas.kafka.tarjetasKafka.services.TarjetaSQSService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/ext/tarjetas")
@RestController
@AllArgsConstructor
public class TarjetaExternaController {

    private TarjetaExternaKafkaService tarjetaExternaKafkaService;
    private TarjetaSQSService tarjetaSQSService;

    @PostMapping("/")
    public TarjetaExterna enviarTarjetaExternaKafka(@RequestBody TarjetaExterna tarjetaExterna){
        tarjetaExternaKafkaService.send(String.valueOf(Topicos.TARJETAS_EXTERNAS_2023_11), tarjetaExterna);
        return tarjetaExterna;
    }

    @PostMapping("/sqs")
    public String enviarTarjetaDesdeSQSHaciaKafka(){
        List<Message> awsSqsMessages = tarjetaSQSService.receiveMessagesFromQueue("tarjetas-credito", 10, 10);
        return tarjetaExternaKafkaService.sendAWSSqsListMessagesToKafka(awsSqsMessages, String.valueOf(Topicos.TARJETAS_SQS_2023_12));
    }

}
