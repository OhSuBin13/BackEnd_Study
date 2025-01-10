package community.board.service;

import community.board.domain.Address;
import community.board.domain.Like;
import community.board.domain.Member;
import community.board.domain.Post;
import community.board.exception.MemberAlreadyExistsException;
import community.board.exception.NoSuchMemberException;
import community.board.exception.PasswordMismatchException;
import community.board.repository.PostRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import community.board.repository.MemberRepository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
public class MemberServiceTest {

  @Autowired
  private MemberRepository memberRepository;
  @Autowired
  private MemberService memberService;
  @Autowired
  private PostRepository postRepository;

  @BeforeEach
  public void setUp() {
    Member member1 = Member.builder()
            .username("Kim")
            .email("Kim@google.com")
            .address(new Address("서울", "광화로", "33-1"))
            .password("1234")
            .build();

    Member member2 = Member.builder()
            .username("Park")
            .email("Park@google.com")
            .address(new Address("인천", "미추홀", "24-2"))
            .password("4321")
            .build();

    Member member3 = Member.builder()
            .username("Kang")
            .email("Kang@google.com")
            .address(new Address("제주", "한라로", "48-3"))
            .password("123")
            .build();

    memberRepository.save(member1);
    memberRepository.save(member2);
    memberRepository.save(member3);
  }

  @Test
  void testFindMembers() {
    List<Member> members = memberService.findMembers();

    assertThat(members.size()).isEqualTo(3);
    assertThat(members.get(0).getUsername()).isEqualTo("Kim");
    assertThat(members.get(1).getUsername()).isEqualTo("Park");
    assertThat(members.get(2).getUsername()).isEqualTo("Kang");
  }

  @Test
  void testEmptyFindMembers() {
    memberRepository.deleteAll();
    List<Member> emptyMembers = memberService.findMembers();
    assertThat(emptyMembers.size()).isEqualTo(0);
  }

  @Test
  void testFindOne() {
    Member findMember1 = memberService.findOne("Kim");
    Member findMember2 = memberService.findOne("Park");
    Member findMember3 = memberService.findOne("Kang");

    assertThat(findMember1.getUsername()).isEqualTo("Kim");
    assertThat(findMember2.getUsername()).isEqualTo("Park");
    assertThat(findMember3.getUsername()).isEqualTo("Kang");
  }

  @Test
  void testFindOneNotFound() {
    assertThatThrownBy(() -> memberService.findOne("Ann")).isInstanceOf(NoSuchMemberException.class)
            .hasMessage("회원이 존재하지 않습니다.");
  }

  @Test
  void testJoin() {
    Member newMember = Member.builder()
            .username("Sam")
            .email("Sam@google.com")
            .address(new Address("경북", "고산", "72-3"))
            .password("123")
            .build();

    memberService.join(newMember);
    Member findMember = memberService.findOne(newMember.getUsername());
    assertThat(findMember.getUsername()).isEqualTo("Sam");
    assertThatThrownBy(() -> memberService.join(newMember))
            .isInstanceOf(MemberAlreadyExistsException.class)
            .hasMessage("이미 존재하는 회원입니다.");
  }

  @Test
  void testLogin() {
    Member loginMember = memberService.login("Kim", "1234");

    assertThat(loginMember.getUsername()).isEqualTo("Kim");
    assertThatThrownBy(() -> memberService.login("Ann", "1234"))
            .isInstanceOf(NoSuchMemberException.class)
            .hasMessage("회원이 존재하지 않습니다.");
    assertThatThrownBy(() -> memberService.login("Kim", "4321"))
            .isInstanceOf(PasswordMismatchException.class)
            .hasMessage("비밀번호가 일치하지 않습니다.");
  }

  @Test
  void testWithdrawalMember() {
    Member kim = memberService.findOne("Kim");
    Post post1 = new Post("제목1", "내용1");
    Post post2 = new Post("제목2", "내용2");

    Like like1 = new Like(1);
    Like like2 = new Like(1);
    post1.addLikes(like1);
    kim.addPost(post1);
    kim.addPost(post2);

    List<Post> postsBeforeDeleteMember = postRepository.findAll();
    memberService.withdrawalMember("Kim");
    List<Post> postsAfterDeleteMember = postRepository.findAll();

    assertThatThrownBy(() -> memberService.findOne("Kim")).isInstanceOf(NoSuchMemberException.class)
            .hasMessage("회원이 존재하지 않습니다.");
    assertThat(postsBeforeDeleteMember.size()).isEqualTo(2);
    assertThat(postsAfterDeleteMember.size()).isEqualTo(0);
  }
}
