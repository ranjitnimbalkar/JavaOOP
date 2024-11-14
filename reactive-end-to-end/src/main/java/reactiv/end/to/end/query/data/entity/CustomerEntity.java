package reactiv.end.to.end.query.data.entity;

import lombok.Getter;
import lombok.Setter;
import nonapi.io.github.classgraph.json.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.util.Objects;

@Getter
@Setter
@Table("customers")
public class CustomerEntity {

    @Column("customerNumber")
    @Id Integer customerNumber;
    @Column("customerName")
    String customerName;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CustomerEntity that = (CustomerEntity) o;
        return customerNumber == that.customerNumber;
    }

    @Override
    public int hashCode() {
        return Objects.hash(customerNumber);
    }

    @Override
    public String toString() {
        return "CustomerEntity{" +
                "customerNumber=" + customerNumber +
                ", customerName='" + customerName + '\'' +
                '}';
    }
}
