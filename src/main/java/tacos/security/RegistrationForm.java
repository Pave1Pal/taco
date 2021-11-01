package tacos.security;

import lombok.Data;
import org.springframework.security.crypto.password.PasswordEncoder;
import tacos.User;


@Data
public class RegistrationForm {

    private String email;
    private String password;
    private String fullname;
    private String street;
    private String city;
    private String state;
    private String zip;
    private String phoneNumber;

    public User toUser(PasswordEncoder passwordEncoder) {
        return new User(email, passwordEncoder.encode(password), fullname,
                street, city, state, zip, phoneNumber, Role.USER, Status.ACTIVE);
    }

    public RegistrationForm() {
    }

    public RegistrationForm(String email, String password, String fullname, String street,
                            String city, String state, String zip, String phoneNumber) {
        this.email = email;
        this.password = password;
        this.fullname = fullname;
        this.street = street;
        this.city = city;
        this.state = state;
        this.zip = zip;
        this.phoneNumber = phoneNumber;
    }

    //    public User toUser(PasswordEncoder passwordEncoder) {
//        return new User(username, passwordEncoder.encode(password), fullname, street, city, state, zip, phone);
//    }
}
