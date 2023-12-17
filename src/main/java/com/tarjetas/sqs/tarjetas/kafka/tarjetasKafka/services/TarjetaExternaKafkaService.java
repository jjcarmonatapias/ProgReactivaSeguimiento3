package com.tarjetas.sqs.tarjetas.kafka.tarjetasKafka.services;

import com.amazonaws.services.sqs.model.Message;
import com.amazonaws.services.sqs.model.MessageAttributeValue;
import com.tarjetas.sqs.tarjetas.kafka.tarjetasKafka.models.TarjetaAWSSqs;
import com.tarjetas.sqs.tarjetas.kafka.tarjetasKafka.models.TarjetaInterna;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Slf4j
@AllArgsConstructor
@Service
public class TarjetaExternaKafkaService {

    private final KafkaTemplate<String, String> kafkaTemplate;

    public void send(String topico, TarjetaInterna tarjetaInterna){
        var future = kafkaTemplate.send(topico, tarjetaInterna.getKey(), tarjetaInterna.tarjetaToString());

        future.whenComplete((resultadoEnvio, excepcion)->{
            if(excepcion != null){
                log.error(excepcion.getMessage());
                future.completeExceptionally(excepcion);
            } else {
                future.complete(resultadoEnvio);
                log.info("Tarjeta Externa enviada al topico -> " + topico + " en Kafka " + tarjetaInterna.tarjetaToString());
            }
        });
    }

    private List<TarjetaInterna> transformTarjetaFromAWSSqsToTarjetaInterno(List<Message> messages) {
        List<TarjetaInterna> productos = new LinkedList<>();
        for(Message message: messages){
            Map<String, MessageAttributeValue> atributosMensaje = message.getMessageAttributes();
            TarjetaAWSSqs tarjetaAWSSqs = new TarjetaAWSSqs(atributosMensaje);
            productos.add(tarjetaAWSSqs);
        }
        return productos;
    }

    public String sendAWSSqsListMessagesToKafka(List<Message> messages, String topico){
        List<TarjetaInterna> tarjetasInternas = transformTarjetaFromAWSSqsToTarjetaInterno(messages);
        for(TarjetaInterna tarjetaInterna: tarjetasInternas){
            send(topico, tarjetaInterna);
        }
        return "Se han enviado " + tarjetasInternas.size() + " tarjetas desde AWSSqs hacia Kafka";
    }
}
