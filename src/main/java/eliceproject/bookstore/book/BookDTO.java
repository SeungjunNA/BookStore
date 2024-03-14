package eliceproject.bookstore.book;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookDTO {
    private Long id;
    private String title;
    private String writer;
    private int price;
    private int stock;
    private int publishDate;
    private String thumbnailUrl;
    private String publisher;
    private String contents;
    private LocalDateTime created;
    private LocalDateTime updated;

    public static BookDTO toDTO(Book book) {
        return BookDTO.builder()
                .id(book.getId())
                .title(book.getTitle())
                .writer(book.getWriter())
                .price(book.getPrice())
                .stock(book.getStock())
                .publishDate(book.getPublishDate())
                .thumbnailUrl(book.getThumbnailUrl())
                .publisher(book.getPublisher())
                .contents(book.getContents())
                .created(book.getCreated())
                .updated(book.getUpdated())
                .build();
    }
}
