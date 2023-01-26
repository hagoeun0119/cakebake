package springjpasideproject.cakebake.domain;

import jakarta.persistence.Embeddable;
import lombok.Getter;

@Embeddable
@Getter
public class Address {

    private String basicAddress;
    private String restAddress;
    private String zipcode;

    protected Address() {
    }

    public Address(String basicAddress, String restAddress, String zipcode) {
        this.basicAddress = basicAddress;
        this.restAddress = restAddress;
        this.zipcode = zipcode;
    }

}
