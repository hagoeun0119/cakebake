package springjpasideproject.cakebake.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import springjpasideproject.cakebake.Member;
import springjpasideproject.cakebake.repository.MemberRepository;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
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
        List<Member> findMembers = memberRepository.findByUserId(member.getUserId());
        if (!findMembers.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    public List<Member> findMembers() { return memberRepository.findAll(); }

    public Member findOne(Long memberId) { return memberRepository.findOne(memberId); }

}


