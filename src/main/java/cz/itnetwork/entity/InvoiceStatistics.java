package cz.itnetwork.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InvoiceStatistics {

private double currentYearSum;
private double AllTimeSum;
private long invoicesCount;
}
