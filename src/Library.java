import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class Library {
    private List<Book> books;
    private static Gson gson = new GsonBuilder().setPrettyPrinting().create();
    
    public Library() {
        this.books = new ArrayList<>();
    }
    
    public void addBook(Book book) {
        books.add(book);
        System.out.println("[ADDED] " + book.getTitle());
    }
    
    public void removeBook(String isbn) {
        books.removeIf(book -> book.getIsbn().equals(isbn));
        System.out.println("[REMOVED] Book with ISBN: " + isbn);
    }
    
    public List<Book> searchByAuthor(String author) {
        List<Book> result = new ArrayList<>();
        for (Book book : books) {
            if (book.getAuthor().equalsIgnoreCase(author)) {
                result.add(book);
            }
        }
        return result;
    }
    
    public int getTotalBooks() {
        return books.size();
    }
    
    public String toJson() {
        return gson.toJson(books);
    }
    
    public static Library fromJson(String json) {
        Library library = new Library();
        Type listType = new TypeToken<ArrayList<Book>>(){}.getType();
        List<Book> bookList = gson.fromJson(json, listType);
        if (bookList != null) {
            library.books.addAll(bookList);
        }
        return library;
    }
    
    public void displayAllBooks() {
        if (books.isEmpty()) {
            System.out.println("[EMPTY] Library is empty");
            return;
        }
        System.out.println("\n=== ALL BOOKS ===");
        for (Book book : books) {
            System.out.println(book);
        }
    }
    
    // Inner class Book
    public static class Book {
        private String title;
        private String author;
        private String isbn;
        private int year;
        
        public Book(String title, String author, String isbn, int year) {
            this.title = title;
            this.author = author;
            this.isbn = isbn;
            this.year = year;
        }
        
        public String getTitle() { return title; }
        public String getAuthor() { return author; }
        public String getIsbn() { return isbn; }
        public int getYear() { return year; }
        
        @Override
        public String toString() {
            return String.format("%s | %s | %s | %d", title, author, isbn, year);
        }
    }
    
    public static void main(String[] args) {
        System.out.println("=== Library Management System ===");
        Library library = new Library();
        
        // Thêm sách mẫu
        library.addBook(new Book("Clean Code", "Robert Martin", "978-0132350884", 2008));
        library.addBook(new Book("Effective Java", "Joshua Bloch", "978-0134685991", 2018));
        library.addBook(new Book("Design Patterns", "GoF", "978-0201633610", 1994));
        
        library.displayAllBooks();
        
        // Test search
        System.out.println("\n=== Searching books by 'Joshua Bloch' ===");
        List<Book> results = library.searchByAuthor("Joshua Bloch");
        for (Book book : results) {
            System.out.println("  " + book.getTitle());
        }
        
        // Test JSON
        System.out.println("\n=== JSON Output ===");
        System.out.println(library.toJson());
    }
}