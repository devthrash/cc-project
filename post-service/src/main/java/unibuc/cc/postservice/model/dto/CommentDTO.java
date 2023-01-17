package unibuc.cc.postservice.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import unibuc.cc.postservice.model.BlogPost;
import unibuc.cc.postservice.model.Comment;
import unibuc.cc.postservice.model.UserAccount;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommentDTO {

    private Long id;
    private String title;

    private String text;

    private Long blogPostId;

    private String tag;

    private Long userAccountId;

    public CommentDTO(Comment savedEntity) {
        this.blogPostId = savedEntity.getId();
        this.tag = savedEntity.getTag();
        this.title = savedEntity.getTitle();
        this.text = savedEntity.getText();
        this.tag = savedEntity.getTag();
        this.id = savedEntity.getId();
        this.userAccountId = savedEntity.getUserAccount().getId();
        this.blogPostId = savedEntity.getBlogPost().getBlogId();
    }
}
