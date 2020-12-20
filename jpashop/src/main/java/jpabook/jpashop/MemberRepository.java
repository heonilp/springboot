package jpabook.jpashop;

import jpabook.jpashop.Member;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


@Repository //엔티티를 찾아줌, 컴포턴트 대상
public class MemberRepository {
    @PersistenceContext //엔티티 매니저
    private EntityManager em;

    public Long save(Member member) {
        em.persist(member); // 커맨드랑 쿼리랑 분리
        return member.getId(); //id만 조회
    }
    public Member find(Long id) {
        return em.find(Member.class, id);
    }
}