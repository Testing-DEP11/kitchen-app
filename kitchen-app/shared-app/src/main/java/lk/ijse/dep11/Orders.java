package lk.ijse.dep11;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Orders implements Serializable {
    private String id;
    private int burgerQty;
    private int subQty;
    private int hotDogQty;
    private int cokeQty;
}
