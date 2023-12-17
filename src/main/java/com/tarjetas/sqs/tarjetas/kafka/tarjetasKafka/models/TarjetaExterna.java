package com.tarjetas.sqs.tarjetas.kafka.tarjetasKafka.models;

public record TarjetaExterna (Integer numeroTarjeta, String nombre, String descripcion, Boolean snActivo) implements TarjetaInterna{
    @Override
    public String getKey() {
        return this.numeroTarjeta.toString();
    }

    @Override
    public String getDatosAdicionales() {
        return "{" + "'snActivo':" + snActivo +
                '}';
    }

    @Override
    public Long getNumeroTarjeta() {
        return Long.valueOf(this.numeroTarjeta);
    }

    @Override
    public String getNombre() {
        return this.nombre;
    }

    @Override
    public String getDescripcion() {
        return this.descripcion;
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

