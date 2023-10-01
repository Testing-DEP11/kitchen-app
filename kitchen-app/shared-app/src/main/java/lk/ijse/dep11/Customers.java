package lk.ijse.dep11;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Customers implements Serializable {
    private String id;
    private String name;
    private String contact;
    private String status;
}
