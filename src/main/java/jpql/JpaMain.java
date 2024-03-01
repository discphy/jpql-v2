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
			Team team = new Team();
			team.setName("teamA");
			em.persist(team);

			Member member = new Member();
			member.setUsername("member1");
			member.setAge(10);
			member.setTeam(team);
			member.setType(MemberType.USER);

			em.persist(member);

			String query = "select m from Member m where m.type = :userType";
			List<Member> result = em.createQuery(query, Member.class)
					.setParameter("userType", MemberType.ADMIN)
					.getResultList();

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
