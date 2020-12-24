package jpabook.jpashop.service;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

@RunWith(SpringRunner.class) //스프링과 엮어서 실행
@SpringBootTest // 스프링부트 엮어서 실행
@Transactional //기본적으로 롤백을 한다

public class MemberServiceTest {

    @Autowired MemberService memberService;
    @Autowired MemberRepository memberRepository;
    //s@Autowired EntityManager em;

    @Test
    //@Rollback(false), 롤백을 하지않을경우
    public void 회원가입() throws Exception {

        //Given
        Member member = new Member();
        member.setName("kim");

        //When
        Long saveId = memberService.join(member);

        //Then
        //em.flush();
        assertEquals(member, memberRepository.findOne(saveId));
    }

    @Test(expected = IllegalStateException.class)
    public void 중복_회원_예외() throws Exception {

        //Given
        Member member1 = new Member();
        member1.setName("kim");

        Member member2 = new Member();
        member2.setName("kim");

        //When
        memberService.join(member1);

        memberService.join(member2); //예외가 발생해야 한다.

        //Then
        fail("예외가 발생해야 한다.");
    }
}