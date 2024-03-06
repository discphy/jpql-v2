package jpql;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class JpaMain {

	public static void main(String[] args) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

		EntityManager em = emf.createEntityManager();

		EntityTransaction tx = em.getTransaction();
		tx.begin();

		try {
			Team teamA = new Team();
			teamA.setName("teamA");
			em.persist(teamA);

			Team teamB = new Team();
			teamB.setName("teamB");
			em.persist(teamB);

			Member member = new Member();
			member.setUsername("member1");
			member.setAge(10);
			member.setTeam(teamA);

			Member member2 = new Member();
			member2.setUsername("member2");
			member2.setAge(10);
			member2.setTeam(teamA);

			Member member3 = new Member();
			member3.setUsername("member3");
			member3.setAge(10);
			member3.setTeam(teamB);

			em.persist(member);
			em.persist(member2);
			em.persist(member3);

			em.flush();
			em.clear();

			String query = "select m from Member m where m = :member";
			Member findMember = em.createQuery(query, Member.class)
					.setParameter("member", member)
					.getSingleResult();

			System.out.println("findMember = " + findMember);

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
