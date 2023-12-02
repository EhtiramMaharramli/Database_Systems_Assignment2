import java.math.BigDecimal;
import java.sql.Date;

public class Order {
    private int orderID;
    private int customerID;
    private Date orderDate;
    private BigDecimal total;
    private String status;

    public Order(int orderID, int customerID, Date orderDate, BigDecimal total, String status) {
        this.orderID = orderID;
        this.customerID = customerID;
        this.orderDate = orderDate;
        this.total = total;
        this.status = status;
    }

    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    public int getCustomerID() {
        return customerID;
    }

    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderID=" + orderID +
                ", customerID=" + customerID +
                ", orderDate=" + orderDate +
                ", total=" + total +
                ", status='" + status + '\'' +
                '}';
    }
}
