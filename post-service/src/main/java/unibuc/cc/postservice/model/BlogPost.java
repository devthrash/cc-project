package unibuc.cc.postservice.model;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
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

    @Column
    private String tag;

    @Column
    private String title;

    @Column
    private Integer likes;

    @Column
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
