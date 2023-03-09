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

    @Autowired BasketService basketService;

    @Autowired EntityManager em;

    @Test
    @Rollback(value = false)
    public void 회원가입() throws Exception {
        //given
        Basket basket = new Basket();
        Member member = Member.builder()
                .basket(basket)
                .userId("1111")
                .password("1234")
                .name("kim")
                .phone("010-0000-0000")
                .email("kim@naver.com")
                .build();
        //when
        Long saveId = memberService.join(member);
        basket.addMemberToBasket(member);
        basketService.createBasket(basket);

        //then
        em.flush();
        assertEquals(member, memberRepository.findOne(saveId));
        assertEquals(member.getName(), basket.getMember().getName());
    }

    @Test(expected = IllegalStateException.class)
    public void 중복_확인() throws Exception {
        //given
        Basket basket1 = new Basket();
        Member member1 = Member.builder()
                .basket(basket1)
                .userId("1111")
                .password("1234")
                .name("kim")
                .phone("010-0000-0000")
                .email("kim@naver.com")
                .build();

        Basket basket2 = new Basket();
        Member member2 = Member.builder()
                .basket(basket2)
                .userId("1111")
                .password("1234")
                .name("kim")
                .phone("010-0000-0000")
                .email("kim@naver.com")
                .build();

        //when
        memberService.join(member1);
        memberService.join(member2);

        //then
        fail("예외 발생");
    }

}
