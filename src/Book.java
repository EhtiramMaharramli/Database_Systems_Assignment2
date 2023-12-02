import java.math.BigDecimal;

public class Book {
    private int bookID;
    private String bookTitle;
    private String genre;
    private int authorID;
    private int inStock;
    private BigDecimal price;
    private int publicationYear;

    public Book(int bookID, String bookTitle, String genre, int authorID, int inStock, BigDecimal price, int publicationYear) {
        this.bookID = bookID;
        this.bookTitle = bookTitle;
        this.genre = genre;
        this.authorID = authorID;
        this.inStock = inStock;
        this.price = price;
        this.publicationYear = publicationYear;
    }

    public int getBookID() {
        return bookID;
    }

    public void setBookID(int bookID) {
        this.bookID = bookID;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public int getAuthorID() {
        return authorID;
    }

    public void setAuthorID(int authorID) {
        this.authorID = authorID;
    }

    public int getInStock() {
        return inStock;
    }

    public void setInStock(int inStock) {
        this.inStock = inStock;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public int getPublicationYear() {
        return publicationYear;
    }

    public void setPublicationYear(int publicationYear) {
        this.publicationYear = publicationYear;
    }

    @Override
    public String toString() {
        return "Book{" +
                "bookID=" + bookID +
                ", bookTitle='" + bookTitle + '\'' +
                ", genre='" + genre + '\'' +
                ", authorID=" + authorID +
                ", inStock=" + inStock +
                ", price=" + price +
                ", publicationYear=" + publicationYear +
                '}';
    }
}
