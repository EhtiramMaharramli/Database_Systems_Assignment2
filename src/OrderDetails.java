import java.math.BigDecimal;

public class OrderDetails {
    private int orderDetailID;
    private int orderID;
    private int bookID;
    private int quantity;
    private BigDecimal total;

    public OrderDetails(int orderDetailID, int orderID, int bookID, int quantity, BigDecimal total) {
        this.orderDetailID = orderDetailID;
        this.orderID = orderID;
        this.bookID = bookID;
        this.quantity = quantity;
        this.total = total;
    }

    public int getOrderDetailID() {
        return orderDetailID;
    }

    public void setOrderDetailID(int orderDetailID) {
        this.orderDetailID = orderDetailID;
    }

    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    public int getBookID() {
        return bookID;
    }

    public void setBookID(int bookID) {
        this.bookID = bookID;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "OrderDetails{" +
                "orderDetailID=" + orderDetailID +
                ", orderID=" + orderID +
                ", bookID=" + bookID +
                ", quantity=" + quantity +
                ", total=" + total +
                '}';
    }
}
