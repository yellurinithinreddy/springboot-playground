package com.nithin.LMS.LibraryManagementSystem.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.envers.Audited;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Audited
@Builder
@ToString
public class Author extends AuditingEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String authorName;

    private int authorAge;

    @OneToMany(cascade = CascadeType.ALL,orphanRemoval = true,mappedBy = "author")
    @Builder.Default
    private List<Book> books = new ArrayList<>();
}
