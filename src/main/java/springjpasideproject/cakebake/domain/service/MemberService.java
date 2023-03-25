package springjpasideproject.cakebake.domain.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import springjpasideproject.cakebake.domain.Basket;
import springjpasideproject.cakebake.domain.Member;
import springjpasideproject.cakebake.domain.MemberRole;
import springjpasideproject.cakebake.domain.dto.MemberDto;
import springjpasideproject.cakebake.domain.repository.BasketRepository;
import springjpasideproject.cakebake.domain.repository.MemberRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final BasketRepository basketRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public Long join(String userId, String password, String name, String phone, String email) {

        validateDuplicateMember(userId);

        Basket basket = new Basket();
        Member newMember = Member.builder()
                .basket(basket)
                .userId(userId)
                .password(passwordEncoder.encode(password))
                .name(name)
                .phone(phone)
                .email(email)
                .social(false)
                .build();

        basket.addMemberToBasket(newMember);
        newMember.addRole(MemberRole.USER);
        basketRepository.save(basket);
        memberRepository.save(newMember);
        return newMember.getId();
    }

    private void validateDuplicateMember(String userId) {
        Optional<Member> findMember = memberRepository.findByUserId(userId);
        if (!findMember.isEmpty()) {
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

    public MemberDto showProfile(String userId) {
        Member member = memberRepository.findByUserId(userId).get();
        return new MemberDto(member.getUserId(), member.getName(), member.getPhone(), member.getEmail());
    }

    @Transactional
    public void changePassword(String userId, String name, String password) {
        Member member = findByUserIdAndName(userId, name);
        member.changePassword(password);
    }

    public Member findId(String name, String email) {
        return memberRepository.findByName(name)
                .filter(m -> m.getEmail().equals(email))
                .orElse(null);
    }

    public Member findByUserIdAndName(String userId, String name) {
        return memberRepository.findByUserId(userId)
                .filter(m -> m.getName().equals(name))
                .stream().findFirst()
                .orElse(null);
    }
}


