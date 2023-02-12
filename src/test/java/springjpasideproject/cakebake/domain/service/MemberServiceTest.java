package springjpasideproject.cakebake.domain.service;

import jakarta.persistence.EntityManager;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import springjpasideproject.cakebake.domain.Basket;
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
        Basket basket = new Basket();
        Member member = new Member(basket, "hi1234", "Kim", "0123", "010-0000-0000", "kim@naver.com");

        //when
        Long saveId = memberService.join(member);

        //then
        em.flush();
        assertEquals(member, memberRepository.findOne(saveId));
    }

    @Test(expected = IllegalStateException.class)
    public void 중복_확인() throws Exception {
        //given
        Basket basket1 = new Basket();
        Member member1 = new Member(basket1, "hi1234", "Kim", "0123", "010-0000-0000", "kim@naver.com");

        Basket basket2 = new Basket();
        Member member2 = new Member(basket2, "hi1234", "Kim", "0123", "010-0000-0000", "kim@naver.com");

        //when
        memberService.join(member1);
        memberService.join(member2);

        //then
        fail("예외 발생");
    }

}
