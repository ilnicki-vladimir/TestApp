package model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

@Configuration
@ComponentScan
@EnableAutoConfiguration
public class App {

    @Bean
    CommandLineRunner init(ClientRepository clientRepository, CarRepository carRepository, ClientOrderRepository orderRepository) {
        return (evt) -> Arrays.asList("jhoeller", "dsyer", "pwebb", "ogierke", "rwinch", "mfisher", "mpollack").forEach(a -> {
            Client client = new Client();
            client.setFirstName(a);
            client.setLastName(a + "-last");
            clientRepository.save(client);
            Car car = new Car();
            car.setMake("create-" + a);
            car.setModel("zaz-" + a);
            car.setClient(client);
            carRepository.save(car);
            car = new Car();
            car.setMake("delete-" + a);
            car.setModel("vaz-" + a);
            car.setClient(client);
            carRepository.save(car);
            ClientOrder clientOrder = new ClientOrder();
            clientOrder.setStatus("Complite-" + car.getMake());
            clientOrder.setCar(car);
            orderRepository.save(clientOrder);
        });
    }

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }


    @RestController
    @RequestMapping("{id}/client")
    public class AppController {
        private final ClientRepository clientRepository;
        private final CarRepository carRepository;
        private final ClientOrderRepository orderRepository;

        @RequestMapping
        Collection<Client> readClients() {
            return clientRepository.findAll();
        };

        @RequestMapping(method = RequestMethod.POST)
        public void addClient(@RequestBody Client client) {
            clientRepository.save(client);
        };

        @RequestMapping(method = RequestMethod.DELETE)
        public void deleteClient(@PathVariable String id) {
            Long idclient = new Long(id);
            clientRepository.delete(idclient);
        };



        @RequestMapping(value = "/{carid}/cars", method = RequestMethod.GET)
        Collection<Car> readCars(@PathVariable String carid) {
            if ("".equals(carid))
                return Collections.emptyList();
            Long idcar = new Long(carid);
            return carRepository.findByClientId(idcar);
        };

        @RequestMapping(value = "/{carid}/cars", method = RequestMethod.POST)
        public void addCar(@PathVariable String carid, @RequestBody Car car) {
            Long idcar = new Long(carid);
            Client client = clientRepository.findById(idcar).orElseThrow(() ->
                    new ClientNotFoundException(carid));
            car.setClient(client);
            carRepository.save(car);
        };

        @RequestMapping(value = "/{carid}/cars", method = RequestMethod.DELETE)
        public void deleteCar(@PathVariable String carid) {
            Long idcar = new Long(carid);
            carRepository.delete(idcar);
        };


        @RequestMapping(value = "/{orderid}/orders", method = RequestMethod.GET)
        Collection<ClientOrder> readOrders(@PathVariable String orderid) {
            if ("".equals(orderid))
                return Collections.emptyList();
            Long idorder = new Long(orderid);
            return orderRepository.findByCarId(idorder);
        };

        @RequestMapping(value = "/{orderid}/orders", method = RequestMethod.POST)
        public void addOrder(@PathVariable String orderid, @RequestBody ClientOrder clientOrder) {
            Long idorder = new Long(orderid);
            Car car = carRepository.findById(idorder).orElseThrow(() ->
                    new ClientNotFoundException(orderid));
            clientOrder.setCar(car);
            orderRepository.save(clientOrder);
        };

        @RequestMapping(value = "/{orderid}/orders", method = RequestMethod.DELETE)
        public void deleteOrder(@PathVariable String orderid) {
            Long idorder = new Long(orderid);
            orderRepository.delete(idorder);
        };

        @Autowired
        AppController(CarRepository carRepository, ClientRepository clientRepository, ClientOrderRepository orderRepository) {
            this.clientRepository = clientRepository;
            this.carRepository = carRepository;
            this.orderRepository = orderRepository;
        };
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    class ClientNotFoundException extends RuntimeException {
        public ClientNotFoundException(String id) {
            super("could not find id '" + id + "'");
        }
    }
}
