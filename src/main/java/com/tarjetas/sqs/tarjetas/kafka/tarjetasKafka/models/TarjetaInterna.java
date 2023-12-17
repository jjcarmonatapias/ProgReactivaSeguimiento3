package com.tarjetas.sqs.tarjetas.kafka.tarjetasKafka.models;

public interface TarjetaInterna {

    String getKey();
    Long getNumeroTarjeta();
    String getNombre();
    String getDescripcion();
    String getDatosAdicionales();
    String tarjetaToString();
}
