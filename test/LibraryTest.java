
package com.example;

import org.junit.*;
import static org.junit.Assert.*;
import java.util.*;

public class LibraryTest {
     // uu tru
    private Library library;
    private Library.Book book1;
    private Library.Book book2;
    
    @Before
    public void setUp() {
        library = new Library();
        book1 = new Library.Book("Clean Code", "Robert Martin", "978-0132350884", 2008);
        book2 = new Library.Book("Effective Java", "Joshua Bloch", "978-0134685991", 2018);
    }
    
    @Test
    public void testAddBook() {
        library.addBook(book1);
        assertEquals(1, library.getTotalBooks());
    }
    
    @Test
    public void testRemoveBook() {
        library.addBook(book1);
        library.addBook(book2);
        assertEquals(2, library.getTotalBooks());
        
        library.removeBook("978-0132350884");
        assertEquals(1, library.getTotalBooks());
    }
    
    @Test
    public void testSearchByAuthor() {
        library.addBook(book1);
        library.addBook(book2);
        
        List<Library.Book> results = library.searchByAuthor("Robert Martin");
        assertEquals(1, results.size());
        assertEquals("Clean Code", results.get(0).getTitle());
    }
    
    @Test
    public void testToJson() {
        library.addBook(book1);
        String json = library.toJson();
        assertNotNull(json);
        assertTrue(json.contains("Clean Code"));
    }
}