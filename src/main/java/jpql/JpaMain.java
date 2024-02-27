package jpql;

import javax.persistence.*;
import java.util.List;

public class JpaMain {

	public static void main(String[] args) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

		EntityManager em = emf.createEntityManager();

		EntityTransaction tx = em.getTransaction();
		tx.begin();

		try {
			Member member = new Member();
			member.setUsername("member1");
			member.setAge(10);
			em.persist(member);

			List<MemberDto> result = em.createQuery("select new jpql.MemberDto(m.username, m.age) from Member m", MemberDto.class).getResultList();

			for (MemberDto memberDto : result) {
				System.out.println("memberDto = " + memberDto);
			}

			tx.commit();
		} catch (Exception e) {
			tx.rollback();
			e.printStackTrace();
		} finally {
			em.close();
		}

		emf.close();
	}
}
