import org.junit.*;
import static org.junit.Assert.*;

public class LibraryTest {
    
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
        // Không có cách kiểm tra trực tiếp, nhưng không lỗi là được
    }
    
    @Test
    public void testToJson() {
        library.addBook(book1);
        String json = library.toJson();
        assertNotNull(json);
        assertTrue(json.contains("Clean Code"));
    }
}