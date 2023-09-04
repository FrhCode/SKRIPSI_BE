package com.farhan.skripsibe.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;

import com.farhan.skripsibe.SkripsiBeApplication;
import com.farhan.skripsibe.model.Diese;
import com.farhan.skripsibe.model.Solution;

@SpringBootTest(classes = SkripsiBeApplication.class)
public class DieseRepositoryTest {
	@Autowired
	private DieseRepository dieseRepository;
	@Autowired
	private SolutionRepository solutionRepository;
	@Autowired
	private TransactionTemplate transactionTemplate;

	@Test
	public void findByCode() {
		Diese diese = dieseRepository.findByCode("P1").orElseThrow();

		assertEquals("Pseudomonas hydrophila", diese.getName());
	}

	@Test
	@Transactional
	public void addSolutionToDiese() {
		Diese P1 = dieseRepository.findByCode("P1").orElseThrow();
		// P1.addSolution(Solution.builder()
		// .name("Isolasi dan Karantina")
		// .description(
		// "Segera setelah Anda melihat gejala Pseudomonas hydrophila pada ikan,
		// pisahkan ikan tersebut dari penghuni kolam lainnya untuk mencegah penyebaran
		// penyakit. Tempatkan ikan yang terinfeksi ke dalam tangki karantina dengan air
		// bersih.")
		// .diese(P1)
		// .build());
		// dieseRepository.save(P1);
		// System.out.println("test");
		solutionRepository.save(
				new Solution(null,
						"Isolasi dan Karantina",
						"Segera setelah Anda melihat gejala Pseudomonas hydrophila pada ikan, pisahkan ikan tersebut dari penghuni kolam lainnya untuk mencegah penyebaran penyakit. Tempatkan ikan yang terinfeksi ke dalam tangki karantina dengan air bersih.",
						P1));
		List<Solution> solutions = solutionRepository.findAll();
		System.out.println("OK");
	}

	@Test
	@Transactional
	public void showSolutionOfADiese() {
		Diese P1 = dieseRepository.findByCode("P1").orElseThrow();
		System.out.println(P1);
	}

	@Test
	public void testDeleteOneSolution() {
		int sizeBeforeDelete = solutionRepository.findByDieseId(1L).size();
		solutionRepository.deleteById(1L);
		int sizeAfterDelete = solutionRepository.findByDieseId(1L).size();

		assertEquals(sizeAfterDelete, sizeBeforeDelete - 1);
	}

}
