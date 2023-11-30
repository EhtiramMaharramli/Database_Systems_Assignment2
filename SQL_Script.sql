CREATE TABLE Authors (
    authorID int PRIMARY KEY,
    authorName VARCHAR(100) NOT NULL,
    birthDate DATE
);
CREATE TABLE Books (
    bookID INT PRIMARY KEY,
    bookTitle VARCHAR(255) NOT NULL,
    genre VARCHAR(50),
    authorID INT REFERENCES Authors(authorID),
    inStock INT,
    price DECIMAL(10, 2),
    publicationYear INT
);

CREATE TABLE Customers (
    customerID  INT PRIMARY KEY,
    firstName VARCHAR(60),
    lastName VARCHAR(60),
    email VARCHAR(100),                                                 
    customerAddress VARCHAR(250),
    phoneNumber VARCHAR(20)                                              
);

CREATE TABLE Orders (
    orderID INT PRIMARY KEY,
    customerID INT REFERENCES Customers(CustomerID),
    orderDate DATE,
    total DECIMAL(10, 2),
    status VARCHAR(30)
);

CREATE TABLE OrderDetails (
    orderDetailID INT PRIMARY KEY,
    orderID INT REFERENCES Orders(orderID),
    bookID INT REFERENCES Books(bookID),
    quantity INT,
    total DECIMAL(10, 2)
);





