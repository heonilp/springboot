package hellojpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class JpaMain {
    public static void main(String[] args){
        //HHH000206: hibernate.properties not found -> JDK 11문제 JAXB 사라짐 pom 수정
        //https://bbubbush.tistory.com/15
      EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
     //code
      EntityManager em = emf.createEntityManager();

      EntityTransaction tx  = em.getTransaction();
      tx.begin();
      try{
        //삽입
        //Member2 member =new Member2();
        //member.setId(1L);
        //member.setName("HelloA");
        //em.persist(member);
        //조회
        //Member2 findMember =em.find(Member2.class, 1L);
       // System.out.println("FindMember.id = " + findMember.getId());
       // System.out.println("FindMember.Name = " + findMember.getName());
        //변경
       // Member2 findMember =em.find(Member2.class, 1L);
       // findMember.setName("HelloJPA");
        //삭제
        //Member2 findMember =em.find(Member2.class, 1L);
        //em.remove(findMember);
        //JPQL
        List<Member2> result = em.createQuery("select  m from Member2 as m", Member2.class)
                .getResultList();

        for(Member2 member :result){
          System.out.println("FindMember.Name = " + member.getName());
        }

        tx.commit();
      }catch (Exception e){
        tx.rollback();
      }finally {
        em.close();
      }

      emf.close();

    }
}
