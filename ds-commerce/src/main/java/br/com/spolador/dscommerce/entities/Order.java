package br.com.spolador.dscommerce.entities;

import javax.persistence.*;
import java.time.Instant;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name = "tb_order")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TIMESTAMP WITHOUT TIME ZONE") //salvo no banco com o padrão UTC
    private Instant moment; // instante em que o pedido foi feito
    private OrderStatus status;

    @ManyToOne
    @JoinColumn(name = "client_id") // chave estrangeira que será inclusa em tb_order
    private User client; // vários Orders podem ter 1 User -> muitos para um

    //Order pode existir sem o Payment. Posso ter 1 Order com 0 Payment
    @OneToOne(mappedBy = "order", cascade = CascadeType.ALL) // cascade permite que a persistência, atualização ou exclusão sejam aplicadas não apenas à entidade principal, mas também a suas entidades associadas
    private Payment payment;

    @OneToMany(mappedBy = "id.order")
    private Set<OrderItem> items = new HashSet<>();

    public Order() {
    }

    public Order(Long id, Instant moment, OrderStatus status, User client, Payment payment) {
        this.id = id;
        this.moment = moment;
        this.status = status;
        this.client = client;
        this.payment = payment;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getMoment() {
        return moment;
    }

    public void setMoment(Instant moment) {
        this.moment = moment;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public User getClient() {
        return client;
    }

    public void setClient(User client) {
        this.client = client;
    }

    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }

    public Set<OrderItem> getItems() {  // a partir do order eu consigo acessar os items
        return items;
    }

    // etornar uma lista de produtos associados a esses itens
    // a lista é convertida para um stream, e o map converte cada elemento 'x' do stream. Nesse caso, está sendo chamado o método getProduct() de x. Isso está mapeando cada item para o seu respectivo produto.
    // por fim os elementos são novamente transformados em uma lista
    public List<Product> getProducts(){
        return items.stream().map(x -> x.getProduct()).collect(Collectors.toList());
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (!(object instanceof Order)) return false;
        Order order = (Order) object;
        return Objects.equals(getId(), order.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
