package jpql;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

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

			String query = "select distinct t from Team t join fetch t.members"; // 지연로딩보다 먼저 작동
			List<Team> result = em.createQuery(query, Team.class)
					.getResultList();

			for (Team item : result) {
				System.out.println("member = " + item.getName() + " / " + item.getMembers().size());

				for (Member itemMember : item.getMembers()) {
					System.out.println("itemMember = " + itemMember);

				}

				// 회원 1, 팀A(SQL)
				// 회원 2, 팀A(1차캐시)
				// 회원 3, 팀B(SQL)

				// 회원 100, N + 1
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
