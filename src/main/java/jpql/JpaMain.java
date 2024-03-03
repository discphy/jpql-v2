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

			Member member2 = new Member();
			member2.setUsername("member2");
			member2.setAge(10);
			member2.setTeam(team);
			member2.setType(MemberType.USER);

			em.persist(member);
			em.persist(member2);

			String query = "select function('group_concat', m.username) from Member m";
			List<String> result = em.createQuery(query, String.class)
					.getResultList();

			for (String item : result) {
				System.out.println("item = " + item);
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
