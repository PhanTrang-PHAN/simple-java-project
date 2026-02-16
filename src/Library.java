import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.util.*;

public class Library {
    private List<Book> books;
    private static Gson gson = new GsonBuilder().setPrettyPrinting().create();
    
    public Library() {
        this.books = new ArrayList<>();
    }
    
    // QUAN TRỌNG: Book phải là static class
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
    }
    
    public void addBook(Book book) {
        books.add(book);
    }
    
    public void removeBook(String isbn) {
        books.removeIf(book -> book.getIsbn().equals(isbn));
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
        Book[] bookArray = gson.fromJson(json, Book[].class);
        if (bookArray != null) {
            library.books.addAll(Arrays.asList(bookArray));
        }
        return library;
    }
}