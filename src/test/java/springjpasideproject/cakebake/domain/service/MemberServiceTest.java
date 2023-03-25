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

        //when
        Long saveId = memberService.join("1111", "1234", "kim", "010-0000-0000", "kim@naver.com");

        //then
        em.flush();
        assertEquals("1111", memberRepository.findOne(saveId).getUserId());
    }

    @Test(expected = IllegalStateException.class)
    public void 중복_확인() throws Exception {
        //given

        //when
        memberService.join("1111", "1234", "kim", "010-0000-0000", "kim@naver.com");
        memberService.join("1111", "1234", "kim", "010-0000-0000", "kim@naver.com");

        //then
        fail("예외 발생");
    }

}
