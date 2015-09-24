package model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Car {

    @JsonIgnore
    @ManyToOne
    private Client client;

    @Id
    @GeneratedValue
    private Long id;

    @OneToMany(mappedBy = "car")
    private Set<ClientOrder> orders = new HashSet<>();

    private String make;
    private String model;
    private Integer year;
    private String vin;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public String getVin() {
        return vin;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Car() {
    }

}
