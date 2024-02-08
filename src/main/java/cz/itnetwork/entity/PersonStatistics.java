package cz.itnetwork.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PersonStatistics {

    private long personId;
    private String personName;
     private double revenue;
}
