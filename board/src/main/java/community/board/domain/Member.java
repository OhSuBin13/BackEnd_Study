package community.board.domain;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;
import java.util.ArrayList;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;
    private String username;
    private String password;
    private String email;

    @Embedded
    private Address address;
    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY,
            orphanRemoval = true, cascade = CascadeType.PERSIST)
    private List<Post> posts = new ArrayList<>();

    @Builder
    public Member(String username, String password, String email, Address address) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.address = address;
    }

    public void addPost(Post post) {
        posts.add(post);
        post.setMember(this);
    }

    public boolean isSameUsername(String username) {
        return this.username.equals(username);
    } 
}
