package ma.m2t.paywidget.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@DiscriminatorValue("AMANTY")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Amanty extends PaymentMethod {

    private String amantyName;
}
