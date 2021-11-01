package tacos.web;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.support.SessionStatus;
import tacos.Order;
import tacos.User;
import tacos.data.OrderRepository;
import tacos.data.UserRepository;

import javax.validation.Valid;

@Slf4j
@Controller
@RequestMapping("/orders")
@SessionAttributes("order")
public class OrderController {

    private OrderRepository orderRepository;
    private UserRepository userRepository;

    @Autowired
    public OrderController (OrderRepository orderRepository, UserRepository userRepository) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
    }

    @GetMapping("/current")
    public String orderForm() {
        return "orderForm";
    }

    @PostMapping
    public String processOrder(@Valid Order order, Errors errors, SessionStatus sessionStatus,
                               Authentication authentication) {
        if (errors.hasErrors())
            return "orderForm";
        User user = (User) authentication.getPrincipal();
        order.setUser(user);
        orderRepository.save(order);
        sessionStatus.setComplete();
        log.info("Order submitted: " + order);
        return "redirect:/home";
    }

    @ResponseBody
    @PutMapping("/{orderId}")
    public Order putOrder(@RequestBody Order order) {
        return orderRepository.save(order);
    }

    @ResponseBody
    @PatchMapping(path="/{orderId}", consumes = "application/json")
    public Order patchOrder(@PathVariable("orderId") long orderId, @RequestBody Order patch) {

        Order order = orderRepository.findById(orderId).get();

        if (patch.getName() != null)
            order.setName(patch.getName());

        if (patch.getStreet() != null)
            order.setStreet(patch.getStreet());

        if (patch.getCity() != null)
            order.setCity(patch.getCity());

        if (patch.getState() != null)
            order.setState(patch.getState());

        if (patch.getZip() != null)
            order.setZip(patch.getZip());

        if (patch.getCcNumber() != null)
            order.setCcNumber(patch.getCcNumber());

        if (patch.getCcExpiration() != null)
            order.setCcExpiration(patch.getCcExpiration());

        if (patch.getCcCVV() != null)
            order.setCcCVV(patch.getCcCVV());

        return orderRepository.save(order);
    }

    @DeleteMapping("/{orderId}")
    @ResponseStatus(code= HttpStatus.NO_CONTENT)
    public ResponseEntity<Object> deleteOrder(@PathVariable long orderId) {
        try {
            orderRepository.deleteById(orderId);
            return new ResponseEntity<Object>(HttpStatus.OK);
        }
        catch (EmptyResultDataAccessException e) {
            log.info(e.toString());
            return new ResponseEntity<Object>(HttpStatus.NO_CONTENT);
        }
    }
}
