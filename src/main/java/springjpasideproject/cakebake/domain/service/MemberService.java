package springjpasideproject.cakebake.domain.service;

import jakarta.validation.constraints.Null;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import springjpasideproject.cakebake.domain.Member;
import springjpasideproject.cakebake.domain.repository.MemberRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional
    public Long join(Member member) {
        validateDuplicateMember(member);
        memberRepository.save(member);
        return member.getId();
    }

    // 회원 아이디로 검색
    private void validateDuplicateMember(Member member) {
        List<Member> findMember = memberRepository.findByUserId(member.getUserId());
        if (!findMember.isEmpty() ) {
            throw new IllegalStateException("Already exists");
        }
    }

    public Member findOne(Long memberId) {
        return memberRepository.findOne(memberId);
    }

    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    public boolean findByUserId(String userId, String password) {
        List<Member> findUserId = memberRepository.findByUserId(userId);
        log.info(password);
        if (findUserId.get(0).getPassword().equals(password)) {
            log.info("Login Successful");
            return true;
        }

        return false;
    }
}


