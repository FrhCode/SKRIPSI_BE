package com.farhan.skripsibe.runner;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.transaction.support.TransactionTemplate;

import com.farhan.skripsibe.model.Diese;
import com.farhan.skripsibe.model.Symptom;
import com.farhan.skripsibe.repository.DieseRepository;
import com.farhan.skripsibe.repository.SymtomRepository;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
@Profile("test-case-1")
public class TestRunner implements CommandLineRunner {
	private final DieseRepository dieseRepository;
	private final SymtomRepository symtomRepository;

	private final TransactionTemplate transactionTemplate;
	private List<Symptom> symtoms = new ArrayList<>();

	@Override
	public void run(String... args) throws Exception {
		generateDieses();

		generaSymtom();

		generateRuleBase();
	}

	private void generateRuleBase() {
		transactionTemplate.execute(transactionStatus -> {
			try {
				Diese P1 = dieseRepository.findByCode("P1").orElseThrow();
				P1.addSymptom(getSystomByCode("KG01"));
				P1.addSymptom(getSystomByCode("KG02"));
				P1.addSymptom(getSystomByCode("KG03"));
				P1.addSymptom(getSystomByCode("KG04"));
				P1.addSymptom(getSystomByCode("KG05"));
				dieseRepository.save(P1);

				Diese P2 = dieseRepository.findByCode("P2").orElseThrow();
				P2.addSymptom(getSystomByCode("KG03"));
				P2.addSymptom(getSystomByCode("KG06"));
				P2.addSymptom(getSystomByCode("KG01"));
				P2.addSymptom(getSystomByCode("KG07"));
				P2.addSymptom(getSystomByCode("KG02"));
				P2.addSymptom(getSystomByCode("KG05"));
				P2.addSymptom(getSystomByCode("KG08"));
				dieseRepository.save(P2);

				Diese P3 = dieseRepository.findByCode("P3").orElseThrow();
				P3.addSymptom(getSystomByCode("KG09"));
				P3.addSymptom(getSystomByCode("KG05"));
				P3.addSymptom(getSystomByCode("KG02"));
				P3.addSymptom(getSystomByCode("KG01"));
				dieseRepository.save(P3);

				return null;
			} catch (Exception e) {
				transactionStatus.setRollbackOnly(); // Rollback transaction on exception
				throw e;
			}
		});
	}

	private Symptom getSystomByCode(String code) {
		return symtoms.stream().filter(sysmtom -> sysmtom.getCode().equals(code)).findFirst().orElseThrow();
	}

	private void generaSymtom() {
		List<Symptom> symtoms = List.of(
				new Symptom(null, "KG01", "Gatal", BigDecimal.valueOf(.1)),
				new Symptom(null, "KG02", "Sekitar kulit tampak berwarna kemerahan", BigDecimal.valueOf(.2)),
				new Symptom(null, "KG03", "Keadaan kulit yang mengelupas", BigDecimal.valueOf(.3)),
				new Symptom(null, "KG04", "Timbul lesi kurap berbentuk seperti cincin", BigDecimal.valueOf(.8)),
				new Symptom(null, "KG05", "Permukaan sekitar kulit bersisik", BigDecimal.valueOf(.3)),
				new Symptom(null, "KG06", "Kulit pecah-pecah", BigDecimal.valueOf(.8)),
				new Symptom(null, "KG07", "Tampak disekitar kulit terbakar", BigDecimal.valueOf(.7)),
				new Symptom(null, "KG08", "Pinggir lesi aktif", BigDecimal.valueOf(.4)),
				new Symptom(null, "KG09", "Muncul gelembung-gelembung berisi cairan", BigDecimal.valueOf(.8))

		);

		symtomRepository.saveAll(symtoms);

		this.symtoms = symtomRepository.findAll();
	}

	private void generateDieses() {
		List<Diese> dieses = List.of(
				new Diese(null, "P1", "Pseudomonas hydrophila",
						"Penyakit Pseudomonas hydrophila, juga dikenal sebagai penyakit aeromonas atau septicemia hemoragik, adalah penyakit infeksi bakteri yang serius pada ikan air tawar. Penyakit ini disebabkan oleh bakteri Pseudomonas hydrophila atau Aeromonas hydrophila."),
				new Diese(null, "P2", "Aeromonas hydrophila",
						"Penyakit Aeromonas hydrophila, juga dikenal sebagai Aeromonas septicemia, adalah infeksi bakteri yang dapat mempengaruhi ikan air tawar dan laut. Aeromonas hydrophila adalah bakteri Gram-negatif yang dapat menyebabkan berbagai gejala pada ikan."),
				new Diese(null, "P3", "Aeromonas punctata",
						"Aeromonas punctata adalah salah satu spesies bakteri dari genus Aeromonas. Bakteri ini dapat dengan cepat menjalar dan dapat menyebabkan kematian mendadak dengan kemungkinan yang tinggi.")

		);

		dieseRepository.saveAll(dieses);
	}

}
