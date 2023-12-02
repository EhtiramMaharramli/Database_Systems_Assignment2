import java.sql.Date;

public class Author {
    private int authorID;
    private String authorName;
    private Date birthDate;


    public Author(int authorID, String authorName, Date birthDate) {
        this.authorID = authorID;
        this.authorName = authorName;
        this.birthDate = birthDate;
    }

    public int getAuthorID() {
        return authorID;
    }

    public void setAuthorID(int authorID) {

        this.authorID = authorID;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {

        this.authorName = authorName;


    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    @Override
    public String toString() {
        return "Author{" +
                "authorID=" + authorID +
                ", authorName='" + authorName + '\'' +
                ", birthDate=" + birthDate +
                '}';
    }
}

