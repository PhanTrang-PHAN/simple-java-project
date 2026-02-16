import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.time.*;
import java.time.format.*;
import java.lang.reflect.Type;

public class Library {
    static List<Book> books = new ArrayList<>();
    static Scanner sc = new Scanner(System.in);
    static Gson gson = new GsonBuilder().setPrettyPrinting().create();
    static final String DATA_FILE = "data/books.json";
    static final String REPORT_FILE = "reports/library-report.html";
    
    static class Book {
        String id, title, author, publisher, isbn, imagePath;
        int year, pages, quantity, available;
        double price;
        LocalDate importDate;
        List<String> categories;
        Map<String, String> reviews;
        boolean isBorrowed;
        
        Book(String id, String title, String author, int year) {
            this.id = id;
            this.title = title;
            this.author = author;
            this.year = year;
            this.publisher = "Chưa cập nhật";
            this.isbn = "N/A";
            this.imagePath = "/images/book.png";
            this.pages = 0;
            this.quantity = 1;
            this.available = 1;
            this.price = 0.0;
            this.importDate = LocalDate.now();
            this.categories = new ArrayList<>();
            this.reviews = new HashMap<>();
            this.isBorrowed = false;
        }
    }
    
    public static void main(String[] args) {
        createDirectories();
        loadData();
        
        while (true) {
            System.out.println("\n" + "=".repeat(60));
            System.out.println("            QUAN LY THU VIEN SACH");
            System.out.println("=".repeat(60));
            System.out.println("1. Them sach moi");
            System.out.println("2. Xem danh sach sach");
            System.out.println("3. Tim kiem sach");
            System.out.println("4. Muon sach");
            System.out.println("5. Tra sach");
            System.out.println("6. Cap nhat thong tin sach");
            System.out.println("7. Xoa sach");
            System.out.println("8. Thong ke thu vien");
            System.out.println("9. Xuat bao cao HTML");
            System.out.println("0. Luu & Thoat");
            System.out.println("-".repeat(60));
            System.out.print("Chon chuc nang: ");
            
            try {
                int chon = Integer.parseInt(sc.nextLine());
                
                switch (chon) {
                    case 1: themSach(); break;
                    case 2: xemSach(); break;
                    case 3: timSach(); break;
                    case 4: muonSach(); break;
                    case 5: traSach(); break;
                    case 6: capNhatSach(); break;
                    case 7: xoaSach(); break;
                    case 8: thongKe(); break;
                    case 9: xuatBaoCaoHTML(); break;
                    case 0: 
                        saveData();
                        System.out.println("\n✅ Đã lưu dữ liệu. Hẹn gặp lại!");
                        return;
                    default: System.out.println("❌ Vui lòng chọn từ 0-9!");
                }
            } catch (Exception e) {
                System.out.println("❌ Lỗi: " + e.getMessage());
            }
        }
    }
    
    static void createDirectories() {
        new File("data").mkdirs();
        new File("images").mkdirs();
        new File("reports").mkdirs();
        new File("css").mkdirs();
        
        // Tạo ảnh mặc định nếu chưa có
        createDefaultImage();
        createCSSFile();
    }
    
    static void createDefaultImage() {
        String defaultImg = "/images/book.png";
        if (!new File(defaultImg).exists()) {
            try {
                // Tạo file ảnh mặc định (dạng text để demo)
                Files.write(Paths.get(defaultImg), 
                    "DEFAULT BOOK COVER IMAGE".getBytes());
            } catch (Exception e) {}
        }
    }
    // test Hello
    static void createCSSFile() {
        String css = 
            "* {\n" +
            "    margin: 0;\n" +
            "    padding: 0;\n" +
            "    box-sizing: border-box;\n" +
            "    font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;\n" +
            "}\n" +
            "\n" +
            "body {\n" +
            "    background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);\n" +
            "    padding: 20px;\n" +
            "    min-height: 100vh;\n" +
            "}\n" +
            "\n" +
            ".container {\n" +
            "    max-width: 1200px;\n" +
            "    margin: 0 auto;\n" +
            "    background: white;\n" +
            "    border-radius: 15px;\n" +
            "    padding: 30px;\n" +
            "    box-shadow: 0 10px 40px rgba(0,0,0,0.1);\n" +
            "}\n" +
            "\n" +
            "h1 {\n" +
            "    color: #2c3e50;\n" +
            "    border-bottom: 3px solid #3498db;\n" +
            "    padding-bottom: 10px;\n" +
            "    margin-bottom: 30px;\n" +
            "    font-size: 32px;\n" +
            "}\n" +
            "\n" +
            ".stats {\n" +
            "    display: grid;\n" +
            "    grid-template-columns: repeat(auto-fit, minmax(250px, 1fr));\n" +
            "    gap: 20px;\n" +
            "    margin-bottom: 30px;\n" +
            "}\n" +
            "\n" +
            ".stat-card {\n" +
            "    background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);\n" +
            "    padding: 20px;\n" +
            "    border-radius: 10px;\n" +
            "    text-align: center;\n" +
            "}";
                    
        try {
            Files.write(Paths.get("css/style.css"), css.getBytes());
        } catch (Exception e) {}
    }
    
    static void themSach() {
        System.out.println("\n📖  THÊM SÁCH MỚI");
        System.out.println("-".repeat(40));
        
        System.out.print("Mã sách: "); 
        String id = sc.nextLine();
        
        System.out.print("Tên sách: "); 
        String title = sc.nextLine();
        
        System.out.print("Tác giả: "); 
        String author = sc.nextLine();
        
        System.out.print("Nhà xuất bản: "); 
        String publisher = sc.nextLine();
        
        System.out.print("Năm xuất bản: "); 
        int year = Integer.parseInt(sc.nextLine());
        
        System.out.print("Số trang: "); 
        int pages = Integer.parseInt(sc.nextLine());
        
        System.out.print("Giá (VNĐ): "); 
        double price = Double.parseDouble(sc.nextLine());
        
        System.out.print("Số lượng: "); 
        int quantity = Integer.parseInt(sc.nextLine());
        
        System.out.print("ISBN: "); 
        String isbn = sc.nextLine();
        
        System.out.print("Đường dẫn ảnh bìa (Enter để dùng ảnh mặc định): "); 
        String imagePath = sc.nextLine();
        if (imagePath.isEmpty()) {
            imagePath = "/images/book.png";
        }
        
        System.out.print("Thể loại (cách nhau bằng dấu phẩy): "); 
        String[] cats = sc.nextLine().split(",");
        
        Book book = new Book(id, title, author, year);
        book.publisher = publisher;
        book.pages = pages;
        book.price = price;
        book.quantity = quantity;
        book.available = quantity;
        book.isbn = isbn;
        book.imagePath = imagePath;
        book.categories = Arrays.asList(cats);
        book.importDate = LocalDate.now();
        
        books.add(book);
        saveData();
        
        System.out.println("\n✅ Đã thêm sách thành công!");
        System.out.println("   📌 Mã sách: " + id);
        System.out.println("   📌 Tên sách: " + title);
        System.out.println("   📌 Số lượng: " + quantity);
    }
    
    static void xemSach() {
        if (books.isEmpty()) {
            System.out.println("\n📭 Thư viện chưa có sách nào!");
            return;
        }
        
        System.out.println("\n📋  DANH SÁCH SÁCH TRONG THƯ VIỆN");
        System.out.println("=".repeat(100));
        System.out.printf("%-6s %-25s %-20s %-15s %-10s %-10s %s\n", 
            "Mã", "Tên sách", "Tác giả", "NXB", "Năm", "SL", "Trạng thái");
        System.out.println("-".repeat(100));
        
        for (Book b : books) {
            String status = b.available > 0 ? "✅ Có sẵn" : "❌ Hết";
            System.out.printf("%-6s %-25s %-20s %-15s %-10d %-10d %s\n",
                b.id, 
                truncate(b.title, 25), 
                truncate(b.author, 20),
                truncate(b.publisher, 15),
                b.year,
                b.available,
                status);
        }
        System.out.println("=".repeat(100));
        System.out.println("Tổng số: " + books.size() + " đầu sách");
    }
    
    static void timSach() {
        System.out.println("\n🔍  TÌM KIẾM SÁCH");
        System.out.println("-".repeat(40));
        System.out.print("Nhập từ khóa (tên sách/tác giả): ");
        String keyword = sc.nextLine().toLowerCase();
        
        List<Book> results = new ArrayList<>();
        for (Book b : books) {
            if (b.title.toLowerCase().contains(keyword) || 
                b.author.toLowerCase().contains(keyword) ||
                b.publisher.toLowerCase().contains(keyword)) {
                results.add(b);
            }
        }
        
        if (results.isEmpty()) {
            System.out.println("❌ Không tìm thấy sách nào!");
            return;
        }
        
        System.out.println("\n🔎 Kết quả tìm kiếm (" + results.size() + " sách):");
        System.out.println("-".repeat(80));
        
        for (int i = 0; i < results.size(); i++) {
            Book b = results.get(i);
            System.out.printf("%d. %s - %s (%d)\n", 
                i + 1, b.title, b.author, b.year);
            System.out.printf("   📍 Mã: %s | NXB: %s | Còn: %d/%d\n",
                b.id, b.publisher, b.available, b.quantity);
            System.out.printf("   💰 Giá: %.0f VNĐ | 📄 %d trang\n", 
                b.price, b.pages);
            System.out.println("   🏷️  Thể loại: " + String.join(", ", b.categories));
            System.out.println();
        }
    }
    
    static void muonSach() {
        System.out.println("\n📤  MƯỢN SÁCH");
        System.out.println("-".repeat(40));
        System.out.print("Nhập mã sách cần mượn: ");
        String id = sc.nextLine();
        
        for (Book b : books) {
            if (b.id.equals(id)) {
                if (b.available <= 0) {
                    System.out.println("❌ Sách đã hết! Vui lòng đợi bản khác.");
                    return;
                }
                
                System.out.print("Nhập tên người mượn: ");
                String borrower = sc.nextLine();
                System.out.print("Số điện thoại: ");
                String phone = sc.nextLine();
                
                b.available--;
                b.isBorrowed = true;
                saveData();
                
                System.out.println("\n✅ Mượn sách thành công!");
                System.out.println("   📚 Sách: " + b.title);
                System.out.println("   👤 Người mượn: " + borrower);
                System.out.println("   📅 Ngày mượn: " + LocalDate.now());
                System.out.println("   ⏰ Hạn trả: " + LocalDate.now().plusDays(14));
                return;
            }
        }
        System.out.println("❌ Không tìm thấy sách với mã: " + id);
    }
    
    static void traSach() {
        System.out.println("\n📥  TRẢ SÁCH");
        System.out.println("-".repeat(40));
        System.out.print("Nhập mã sách cần trả: ");
        String id = sc.nextLine();
        
        for (Book b : books) {
            if (b.id.equals(id)) {
                if (b.available == b.quantity) {
                    System.out.println("❌ Sách này chưa được mượn!");
                    return;
                }
                
                b.available++;
                b.isBorrowed = false;
                saveData();
                
                System.out.println("\n✅ Trả sách thành công!");
                System.out.println("   📚 Sách: " + b.title);
                System.out.println("   📅 Ngày trả: " + LocalDate.now());
                System.out.println("   ⭐ Cảm ơn bạn đã đọc sách!");
                return;
            }
        }
        System.out.println("❌ Không tìm thấy sách với mã: " + id);
    }
    
    static void capNhatSach() {
        System.out.println("\n✏️  CẬP NHẬT THÔNG TIN SÁCH");
        System.out.println("-".repeat(40));
        System.out.print("Nhập mã sách cần cập nhật: ");
        String id = sc.nextLine();
        
        for (Book b : books) {
            if (b.id.equals(id)) {
                System.out.println("\nThông tin hiện tại:");
                System.out.println("1. Tên: " + b.title);
                System.out.println("2. Tác giả: " + b.author);
                System.out.println("3. NXB: " + b.publisher);
                System.out.println("4. Năm: " + b.year);
                System.out.println("5. Giá: " + b.price);
                System.out.println("6. Số lượng: " + b.quantity);
                System.out.println("7. Còn lại: " + b.available);
                System.out.println("8. Ảnh bìa: " + b.imagePath);
                
                System.out.print("\nChọn thông tin cần sửa (1-8): ");
                int choice = Integer.parseInt(sc.nextLine());
                
                switch (choice) {
                    case 1:
                        System.out.print("Tên mới: ");
                        b.title = sc.nextLine();
                        break;
                    case 2:
                        System.out.print("Tác giả mới: ");
                        b.author = sc.nextLine();
                        break;
                    case 3:
                        System.out.print("NXB mới: ");
                        b.publisher = sc.nextLine();
                        break;
                    case 4:
                        System.out.print("Năm mới: ");
                        b.year = Integer.parseInt(sc.nextLine());
                        break;
                    case 5:
                        System.out.print("Giá mới: ");
                        b.price = Double.parseDouble(sc.nextLine());
                        break;
                    case 6:
                        System.out.print("Số lượng mới: ");
                        int newQty = Integer.parseInt(sc.nextLine());
                        int diff = newQty - b.quantity;
                        b.quantity = newQty;
                        b.available += diff;
                        break;
                    case 7:
                        System.out.print("Số lượng còn lại: ");
                        b.available = Integer.parseInt(sc.nextLine());
                        break;
                    case 8:
                        System.out.print("Đường dẫn ảnh mới: ");
                        b.imagePath = sc.nextLine();
                        break;
                }
                
                saveData();
                System.out.println("✅ Cập nhật thành công!");
                return;
            }
        }
        System.out.println("❌ Không tìm thấy sách!");
    }
    
    static void xoaSach() {
        System.out.println("\n🗑️  XÓA SÁCH");
        System.out.println("-".repeat(40));
        System.out.print("Nhập mã sách cần xóa: ");
        String id = sc.nextLine();
        
        for (Book b : books) {
            if (b.id.equals(id)) {
                System.out.println("\nBạn có chắc chắn muốn xóa sách:");
                System.out.println("   📚 " + b.title + " - " + b.author);
                System.out.print("Nhập 'YES' để xác nhận: ");
                String confirm = sc.nextLine();
                
                if (confirm.equals("YES")) {
                    books.remove(b);
                    saveData();
                    System.out.println("✅ Đã xóa sách khỏi thư viện!");
                } else {
                    System.out.println("❌ Hủy thao tác xóa!");
                }
                return;
            }
        }
        System.out.println("❌ Không tìm thấy sách!");
    }
    
    static void thongKe() {
        System.out.println("\n📊  THỐNG KÊ THƯ VIỆN");
        System.out.println("=".repeat(50));
        
        int totalBooks = books.size();
        int totalCopies = books.stream().mapToInt(b -> b.quantity).sum();
        int availableCopies = books.stream().mapToInt(b -> b.available).sum();
        int borrowedCopies = totalCopies - availableCopies;
        
        System.out.println("📚 Tổng số đầu sách: " + totalBooks);
        System.out.println("📦 Tổng số bản sách: " + totalCopies);
        System.out.println("✅ Sẵn có: " + availableCopies);
        System.out.println("📤 Đang mượn: " + borrowedCopies);
        System.out.println("-".repeat(50));
        
        // Thống kê theo tác giả
        System.out.println("\n✍️  SÁCH THEO TÁC GIẢ:");
        Map<String, Integer> authorStats = new HashMap<>();
        for (Book b : books) {
            authorStats.put(b.author, authorStats.getOrDefault(b.author, 0) + 1);
        }
        authorStats.forEach((author, count) -> 
            System.out.printf("   %s: %d sách\n", author, count));
        
        // Thống kê theo năm
        System.out.println("\n📅  SÁCH THEO NĂM:");
        Map<Integer, Integer> yearStats = new HashMap<>();
        for (Book b : books) {
            yearStats.put(b.year, yearStats.getOrDefault(b.year, 0) + 1);
        }
        yearStats.entrySet().stream()
            .sorted(Map.Entry.<Integer, Integer>comparingByKey().reversed())
            .limit(5)
            .forEach(e -> System.out.printf("   %d: %d sách\n", e.getKey(), e.getValue()));
        
        System.out.println("=".repeat(50));
    }
    
    static void xuatBaoCaoHTML() {
        
        System.out.println("\n🌐  XUẤT BÁO CÁO HTML");
        
        StringBuilder html = new StringBuilder();
        html.append("<!DOCTYPE html>\n");
        html.append("<html lang=\"vi\">\n");
        html.append("<head>\n");
        html.append("    <meta charset='UTF-8'>\n");  // THÊM DÒNG NÀY
        html.append("    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n");
        html.append("    <title>Báo cáo Thư viện</title>\n");
        html.append("    <link rel=\"stylesheet\" href=\"../css/style.css\">\n");
        html.append("</head>\n");
        html.append("<body>\n");
        html.append("    <div class=\"container\">\n");
        html.append("        <h1>📚 BÁO CÁO THƯ VIỆN</h1>\n");
        html.append("        <p style=\"color: #7f8c8d;\">Ngày xuất: " 
                + LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")) 
                + "</p>\n");
        html.append("        <div class=\"stats\">\n");
        
        int totalBooks = books.size();
        int totalCopies = books.stream().mapToInt(b -> b.quantity).sum();
        int availableCopies = books.stream().mapToInt(b -> b.available).sum();
        int borrowedCopies = totalCopies - availableCopies;
        
        html.append(String.format(
            "            <div class=\"stat-card\">\n" +
            "                <h3>TỔNG ĐẦU SÁCH</h3>\n" +
            "                <div class=\"number\">%d</div>\n" +
            "            </div>\n", totalBooks));
        
        html.append(String.format(
            "            <div class=\"stat-card\">\n" +
            "                <h3>TỔNG BẢN SÁCH</h3>\n" +
            "                <div class=\"number\">%d</div>\n" +
            "            </div>\n", totalCopies));
        
        html.append(String.format(
            "            <div class=\"stat-card\">\n" +
            "                <h3>SẴN CÓ</h3>\n" +
            "                <div class=\"number\">%d</div>\n" +
            "            </div>\n", availableCopies));
        
        html.append(String.format(
            "            <div class=\"stat-card\">\n" +
            "                <h3>ĐANG MƯỢN</h3>\n" +
            "                <div class=\"number\">%d</div>\n" +
            "            </div>\n", borrowedCopies));
        
        html.append("        </div>\n");
        html.append("        <h2>📋 DANH SÁCH CHI TIẾT</h2>\n");
        html.append("        <table>\n");
        html.append("            <thead>\n");
        html.append("                <tr>\n");
        html.append("                    <th>Ảnh</th>\n");
        html.append("                    <th>Mã</th>\n");
        html.append("                    <th>Tên sách</th>\n");
        html.append("                    <th>Tác giả</th>\n");
        html.append("                    <th>NXB</th>\n");
        html.append("                    <th>Năm</th>\n");
        html.append("                    <th>Số lượng</th>\n");
        html.append("                    <th>Còn</th>\n");
        html.append("                    <th>Trạng thái</th>\n");
        html.append("                </tr>\n");
        html.append("            </thead>\n");
        html.append("            <tbody>\n");
        
        for (Book b : books) {
            String status = b.available > 0 ? 
                "<span class='badge available'>Có sẵn</span>" : 
                "<span class='badge borrowed'>Đã hết</span>";
            
            html.append(String.format(
                "                <tr>\n" +
                "                    <td><img src='%s' alt='Book cover' class='book-cover' onerror='this.src=\"/images/book.png\"'></td>\n" +
                "                    <td>%s</td>\n" +
                "                    <td>%s</td>\n" +
                "                    <td>%s</td>\n" +
                "                    <td>%s</td>\n" +
                "                    <td>%d</td>\n" +
                "                    <td>%d</td>\n" +
                "                    <td>%d</td>\n" +
                "                    <td>%s</td>\n" +
                "                </tr>\n", 
                b.imagePath, b.id, b.title, b.author, b.publisher, 
                b.year, b.quantity, b.available, status));
        }
        
        html.append("            </tbody>\n");
        html.append("        </table>\n");
        html.append("        <div style='margin-top: 30px; text-align: center; color: #7f8c8d;'>\n");
        html.append("            <p>© 2026 - Hệ thống quản lý thư viện</p>\n");
        html.append("        </div>\n");
        html.append("    </div>\n");
        html.append("</body>\n");
        html.append("</html>\n");
        
        try {
            Files.write(Paths.get(REPORT_FILE), html.toString().getBytes("UTF-8"));
            System.out.println("✅ Đã xuất báo cáo HTML thành công!");
            System.out.println("   📁 Đường dẫn: " + REPORT_FILE);
            System.out.println("   🌐 Mở file bằng trình duyệt để xem!");
        } catch (Exception e) {
            System.out.println("❌ Lỗi xuất báo cáo: " + e.getMessage());
        }
    }

    static void loadData() {
        try {
            File file = new File(DATA_FILE);
            if (file.exists()) {
                // Đọc file JSON dạng String
                String content = new String(Files.readAllBytes(file.toPath()));
                
                // Tạo Gson với cấu hình đặc biệt cho LocalDate
                Gson gson = new GsonBuilder()
                    .setPrettyPrinting()
                    .registerTypeAdapter(LocalDate.class, (JsonDeserializer<LocalDate>) (json, type, context) -> 
                        LocalDate.parse(json.getAsString()))
                    .create();
                    
                Type listType = new TypeToken<ArrayList<Book>>(){}.getType();
                books = gson.fromJson(content, listType);
                if (books == null) books = new ArrayList<>();
                System.out.println("✅ Đã tải " + books.size() + " cuốn sách");
            }
        } catch (Exception e) {
            books = new ArrayList<>();
        }
    }

    static void saveData() {
        try {
            new File("data").mkdirs();
            
            // Tạo Gson với cấu hình đặc biệt cho LocalDate
            Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .registerTypeAdapter(LocalDate.class, (JsonSerializer<LocalDate>) (date, type, context) -> 
                    new JsonPrimitive(date.toString()))
                .create();
                
            try (Writer writer = new FileWriter(DATA_FILE)) {
                gson.toJson(books, writer);
            }
        } catch (Exception e) {
            System.out.println("❌ Lỗi lưu file: " + e.getMessage());
        }
    }
// helo
    static String truncate(String str, int length) {
        if (str.length() <= length) return str;
        return str.substring(0, length - 3) + "...";
    }
}