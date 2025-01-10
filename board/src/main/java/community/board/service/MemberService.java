package community.board.service;

import community.board.exception.MemberAlreadyExistsException;
import community.board.exception.NoSuchMemberException;
import community.board.exception.PasswordMismatchException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import community.board.repository.MemberRepository;
import community.board.domain.Member;
import lombok.RequiredArgsConstructor;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {

  private final MemberRepository memberRepository;

  public void join(Member member) {
    validateDuplicateMember(member);
    memberRepository.save(member);
  }

  private void validateDuplicateMember(Member member) {
    memberRepository.findByUsername(member.getUsername())
        .ifPresent(m -> {
          throw new MemberAlreadyExistsException("이미 존재하는 회원입니다.");
        });
  }
  
  public Member login(String username, String password) {
    Member member = findOne(username);
    if (!member.getPassword().equals(password)) {
      throw new PasswordMismatchException("비밀번호가 일치하지 않습니다.");
    }
    return member;
  }

  public Member findOne(String username) {
    return memberRepository.findByUsername(username)
            .orElseThrow(() -> new NoSuchMemberException("회원이 존재하지 않습니다."));
  }

  public void withdrawalMember(String username) {
    Member member = findOne(username);
    memberRepository.delete(member);
  }

  public List<Member> findMembers() {
    return memberRepository.findAll();
  }

}
