package unibuc.cc.postservice.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.Hibernate;

import java.util.List;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class BlogPost {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "blog_id")
    private Long blogId;

    private String description;

    @OneToMany(mappedBy= "blogPost")
    @ToString.Exclude
    private List<Comment> comments;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        BlogPost blogPost = (BlogPost) o;
        return blogId != null && Objects.equals(blogId, blogPost.blogId);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
