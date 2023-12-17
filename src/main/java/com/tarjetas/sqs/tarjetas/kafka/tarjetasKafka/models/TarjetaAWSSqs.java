package com.tarjetas.sqs.tarjetas.kafka.tarjetasKafka.models;

import com.amazonaws.services.sqs.model.MessageAttributeValue;

import java.util.Map;

public record TarjetaAWSSqs(Map<String, MessageAttributeValue> atributosMensaje) implements TarjetaInterna{

    @Override
    public String getKey() {
        final String prefijoAWSSqs = "SQS-";
        return prefijoAWSSqs + this.atributosMensaje.get("numeroTarjeta").getStringValue();
    }

    @Override
    public Long getNumeroTarjeta() {
        return Long.valueOf(this.atributosMensaje.get("numeroTarjeta").getStringValue());
    }

    @Override
    public String getNombre() {
        return this.atributosMensaje.get("nombre").getStringValue();
    }

    @Override
    public String getDescripcion() {
        return this.atributosMensaje.get("descripcion").getStringValue();
    }

    @Override
    public String getDatosAdicionales() {
        return "{" + "'snActivo':" + this.atributosMensaje.get("activo").getStringValue() +
                '}';
    }

    @Override
    public String tarjetaToString() {
        return "{" +
                "'key':'" + this.getKey() + '\'' +
                ", 'numeroTarjeta':" + this.getNumeroTarjeta()  +
                ", 'nombre':'" + this.getNombre() + '\'' +
                ", 'descripcion':'" + this.getDescripcion() + '\'' +
                ", 'datosAdicionales':'" + this.getDatosAdicionales() + '\'' +
                '}';
    }
}
