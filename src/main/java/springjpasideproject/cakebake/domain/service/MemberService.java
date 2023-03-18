package springjpasideproject.cakebake.domain.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import springjpasideproject.cakebake.domain.Member;
import springjpasideproject.cakebake.domain.repository.MemberRepository;

import java.util.List;
import java.util.Optional;

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

    private void validateDuplicateMember(Member member) {
        Optional<Member> findMember = memberRepository.findByUserId(member.getUserId());
        if (!findMember.isEmpty() ) {
            throw new IllegalStateException("Already exists");
        }
    }

    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    @Transactional
    public void update(Member loginMember, String name, String phone, String email) {
        loginMember.updateMember(name, phone, email);
    }
}


