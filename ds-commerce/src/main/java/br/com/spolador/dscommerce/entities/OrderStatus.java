package br.com.spolador.dscommerce.entities;

public enum OrderStatus {
    WAITING_PAYMENT,
    PAID,
    SHIPPED,     // ENVIADO
    DELIVERED,
    CANCELED
}
