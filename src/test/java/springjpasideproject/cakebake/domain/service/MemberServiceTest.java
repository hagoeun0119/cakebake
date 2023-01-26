package springjpasideproject.cakebake.domain.service;

import jakarta.persistence.EntityManager;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import springjpasideproject.cakebake.domain.Member;
import springjpasideproject.cakebake.domain.repository.MemberRepository;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class MemberServiceTest {

    @Autowired MemberService memberService;

    @Autowired MemberRepository memberRepository;

    @Autowired EntityManager em;

    @Test
    @Rollback(value = false)
    public void 회원가입() throws Exception {
        //given
        Member member = new Member();
        member.setUserId("hi1234");
        member.setName("새우튀김");
        member.setPassword("j");
        member.setPhone("010-0000-0000");
        member.setEmail("todnxnlrla@naver.com");

        //when
        Long saveId = memberService.join(member);

        //then
        em.flush();
        assertEquals(member, memberRepository.findOne(saveId));
    }

    @Test(expected = IllegalStateException.class)
    public void 중복_확인() throws Exception {
        //given
        Member member1 = new Member();
        member1.setUserId("hi1234");

        Member member2 = new Member();
        member2.setUserId("hi1234");

        //when
        memberService.join(member1);
        memberService.join(member2);

        //then
        fail("예외 발생");
    }

}
