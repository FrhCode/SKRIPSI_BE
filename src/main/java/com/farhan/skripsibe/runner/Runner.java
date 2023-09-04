package com.farhan.skripsibe.runner;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;

import com.farhan.skripsibe.model.Consultation;
import com.farhan.skripsibe.model.Diese;
import com.farhan.skripsibe.model.Solution;
import com.farhan.skripsibe.model.Symptom;
import com.farhan.skripsibe.model.json.DieseJson;
import com.farhan.skripsibe.model.json.SolutionJson;
import com.farhan.skripsibe.model.json.SymtomJson;
import com.farhan.skripsibe.repository.ConsultationRepository;
import com.farhan.skripsibe.repository.DieseRepository;
import com.farhan.skripsibe.repository.SolutionRepository;
import com.farhan.skripsibe.repository.SymtomRepository;
import com.farhan.skripsibe.service.ConsultationService;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class Runner implements CommandLineRunner {
	private final DieseRepository dieseRepository;
	private final SymtomRepository symtomRepository;
	private final SolutionRepository solutionRepository;
	private final ConsultationService consultationService;

	private final TransactionTemplate transactionTemplate;
	private List<Symptom> symtoms = new ArrayList<>();

	@Override
	public void run(String... args) throws Exception {
		generateDieses();

		generaSymtom();

		generateRuleBase();

		generateSolution();

		generateConsultation();
	}

	private void generateConsultation() {
		Comparator<DieseJson> soryDiesesByPercentage = new Comparator<DieseJson>() {
			@Override
			public int compare(DieseJson arg0, DieseJson arg1) {
				return arg1.getPercentage().compareTo(arg0.getPercentage());
			}

		};

		List<DieseJson> dieses = dieseRepository.findAll().stream()
				.filter(diese -> Math.random() > 0.3)
				.map(diese -> {
					List<SolutionJson> solutions = solutionRepository.findByDieseId(diese.getId()).stream()
							.map(solution -> new SolutionJson(solution.getName(), solution.getDescription()))
							.collect(Collectors.toList());

					DieseJson dieseJson = new DieseJson();
					dieseJson.setName(diese.getName());
					dieseJson.setPercentage(BigDecimal.valueOf(Math.random()));
					dieseJson.setSolutions(solutions);

					return dieseJson;
				}).sorted(soryDiesesByPercentage).collect(Collectors.toList());

		List<SymtomJson> symtoms = symtomRepository.findAll().stream().filter(symtom -> Math.random() > 0.3).map(symtom -> {
			return new SymtomJson(symtom.getName(), symtom.getDsValue());
		}).collect(Collectors.toList());

		consultationService
				.save(new Consultation(null, null, "Farhan", LocalDateTime.now(), dieses, symtoms));
	}

	private void generateSolution() {
		Diese P1 = dieseRepository.findByCode("P1").orElseThrow();
		Diese P2 = dieseRepository.findByCode("P2").orElseThrow();
		Diese P3 = dieseRepository.findByCode("P3").orElseThrow();
		Diese P4 = dieseRepository.findByCode("P4").orElseThrow();
		Diese P5 = dieseRepository.findByCode("P5").orElseThrow();
		Diese P6 = dieseRepository.findByCode("P6").orElseThrow();
		Diese P7 = dieseRepository.findByCode("P7").orElseThrow();
		Diese P8 = dieseRepository.findByCode("P8").orElseThrow();
		Diese P9 = dieseRepository.findByCode("P9").orElseThrow();
		Diese P10 = dieseRepository.findByCode("P10").orElseThrow();
		Diese P11 = dieseRepository.findByCode("P11").orElseThrow();

		List<Solution> solutions = List.of(
				new Solution(null,
						"Isolasi dan Karantina",
						"Segera setelah Anda melihat gejala Pseudomonas hydrophila pada ikan, pisahkan ikan tersebut dari penghuni kolam lainnya untuk mencegah penyebaran penyakit. Tempatkan ikan yang terinfeksi ke dalam tangki karantina dengan air bersih.",
						P1),
				new Solution(null,
						"Jaga Kualitas Air",
						"Pertahankan kualitas air yang baik di akuarium ikan yang terinfeksi. Ini termasuk penggantian air secara teratur, penyaringan yang tepat, dan pemantauan kadar amonia, nitrit, dan nitrat. Air bersih membantu mengurangi stres pada ikan dan mendukung sistem kekebalan tubuh mereka",
						P1),
				new Solution(null,
						"Pemberian Antibiotik",
						"Pemberian Antibiotik seperti enrofloxacin atau florfenicol dapat digunakan untuk menargetkan bakteri Pseudomonas",
						P1),
				new Solution(null,
						"Pemberian Salep",
						"Untuk infeksi eksternal, pengobatan salep sangat disarankan. Perawatan ini dapat membantu mencegah infeksi sekunder dan mempercepat penyembuhan",
						P1),
				new Solution(null,
						"Pemberian Salep",
						"Untuk infeksi eksternal, pengobatan salep sangat disarankan. Perawatan ini dapat membantu mencegah infeksi sekunder dan mempercepat penyembuhan",
						P1),
				new Solution(null,
						"Pemberian disinfektan pada kolam",
						"Disinfeksi peralatan atau alat apa pun yang digunakan dalam menangani ikan yang terinfeksi untuk mencegah kontaminasi lebih lanjut. Bersihkan dan disinfeksi tangki atau kolam jika perlu.",
						P1),
				new Solution(null,
						"Isolasi dan Karantina",
						"Segera setelah Anda melihat gejala Aeromonas hydrophila pada ikan, pisahkan ikan tersebut dari penghuni kolam lainnya untuk mencegah penyebaran penyakit. Tempatkan ikan yang terinfeksi ke dalam tangki karantina dengan air bersih.",
						P2),
				new Solution(null,
						"Jaga Kualitas Air",
						"Pertahankan kualitas air yang baik di akuarium ikan yang terinfeksi. Ini termasuk penggantian air secara teratur, penyaringan yang tepat, dan pemantauan kadar amonia, nitrit, dan nitrat. Air bersih membantu mengurangi stres pada ikan dan mendukung sistem kekebalan tubuh mereka",
						P2),
				new Solution(null,
						"Pemberian Antibiotik",
						"Pemberian Antibiotik seperti enrofloxacin atau florfenicol dapat digunakan untuk menargetkan bakteri Aeromonas hydrophila",
						P2),
				new Solution(null,
						"Minimalisir stress pada ikan",
						"Stres melemahkan sistem kekebalan ikan dan membuat mereka lebih rentan terhadap infeksi. Minimalkan pemicu stres seperti perubahan parameter air secara tiba-tiba, kepadatan yang berlebihan, dan teman seakuarium yang agresif.",
						P2),
				new Solution(null,
						"Isolasi dan Karantina",
						"Segera setelah Anda melihat gejala Aeromonas punctata pada ikan, pisahkan ikan tersebut dari penghuni kolam lainnya untuk mencegah penyebaran penyakit. Tempatkan ikan yang terinfeksi ke dalam tangki karantina dengan air bersih.",
						P3),
				new Solution(null,
						"Pemberian Antibiotik",
						"Pemberian Antibiotik seperti enrofloxacin atau florfenicol dapat digunakan untuk menargetkan bakteri Aeromonas punctata",
						P3),
				new Solution(null,
						"Pemberian garam ikan",
						"Pemberian garam ikan pada kolam dapat membantu dalam menyembuhkan ikan dari bakteri Aeromonas punctata, disamping itu garam ikan memiliki banyak sekali manfaant jika diaplikasikan dengan takaran yang sesuai.",
						P3),
				new Solution(null,
						"Isolasi dan Karantina",
						"Segera setelah Anda melihat gejala Columnaris pada ikan, pisahkan ikan tersebut dari penghuni kolam lainnya untuk mencegah penyebaran penyakit. Tempatkan ikan yang terinfeksi ke dalam tangki karantina dengan air bersih.",
						P4),
				new Solution(null,
						"Jaga Kualitas Air",
						"Pertahankan kualitas air yang baik di akuarium ikan yang terinfeksi. Ini termasuk penggantian air secara teratur, penyaringan yang tepat, dan pemantauan kadar amonia, nitrit, dan nitrat. Air bersih membantu mengurangi stres pada ikan dan mendukung sistem kekebalan tubuh mereka",
						P4),
				new Solution(null,
						"Minimalisir stress pada ikan",
						"Stres melemahkan sistem kekebalan ikan dan membuat mereka lebih rentan terhadap infeksi. Minimalkan pemicu stres seperti perubahan parameter air secara tiba-tiba, kepadatan yang berlebihan, dan teman seakuarium yang agresif.",
						P4),
				new Solution(null,
						"Pemberian Antibiotik",
						"Pemberian Antibiotik seperti kanamycin atau erythromycin dapat digunakan untuk menargetkan bakteri Columnaris",
						P4),
				new Solution(null,
						"Pemberian garam ikan",
						"Pemberian garam ikan pada kolam dapat membantu dalam menyembuhkan ikan dari bakteri Columnaris, disamping itu garam ikan memiliki banyak sekali manfaant jika diaplikasikan dengan takaran yang sesuai.",
						P4),
				new Solution(null,
						"Pemberian disinfektan pada kolam",
						"Disinfeksi peralatan atau alat apa pun yang digunakan dalam menangani ikan yang terinfeksi untuk mencegah kontaminasi lebih lanjut. Bersihkan dan disinfeksi tangki atau kolam jika perlu.",
						P4),
				new Solution(null,
						"Isolasi dan Karantina",
						"Segera setelah Anda melihat gejala Edwardsiella pada ikan, pisahkan ikan tersebut dari penghuni kolam lainnya untuk mencegah penyebaran penyakit. Tempatkan ikan yang terinfeksi ke dalam tangki karantina dengan air bersih.",
						P5),
				new Solution(null,
						"Pemberian Antibiotik",
						"Pemberian Antibiotik seperti fluoroquinolones atau oxytetracycline dapat digunakan untuk menargetkan bakteri Edwardsiella",
						P5),
				new Solution(null,
						"Pemberian disinfektan pada kolam",
						"Disinfeksi peralatan atau alat apa pun yang digunakan dalam menangani ikan yang terinfeksi untuk mencegah kontaminasi lebih lanjut. Bersihkan dan disinfeksi tangki atau kolam jika perlu.",
						P5),
				new Solution(null,
						"Isolasi dan Karantina",
						"Segera setelah Anda melihat gejala Tuberculosis pada ikan, pisahkan ikan tersebut dari penghuni kolam lainnya untuk mencegah penyebaran penyakit. Tempatkan ikan yang terinfeksi ke dalam tangki karantina dengan air bersih.",
						P6),
				new Solution(null,
						"Jaga Kualitas Air",
						"Pertahankan kualitas air yang baik di akuarium ikan yang terinfeksi. Ini termasuk penggantian air secara teratur, penyaringan yang tepat, dan pemantauan kadar amonia, nitrit, dan nitrat. Air bersih membantu mengurangi stres pada ikan dan mendukung sistem kekebalan tubuh mereka",
						P6),
				new Solution(null,
						"Minimalisir stress pada ikan",
						"Stres melemahkan sistem kekebalan ikan dan membuat mereka lebih rentan terhadap infeksi. Minimalkan pemicu stres seperti perubahan parameter air secara tiba-tiba, kepadatan yang berlebihan, dan teman seakuarium yang agresif.",
						P6),
				new Solution(null,
						"Isolasi dan Karantina",
						"Segera setelah Anda melihat gejala Jamur putih pada ikan, pisahkan ikan tersebut dari penghuni kolam lainnya untuk mencegah penyebaran penyakit. Tempatkan ikan yang terinfeksi ke dalam tangki karantina dengan air bersih.",
						P7),
				new Solution(null,
						"Jaga Kualitas Air",
						"Pertahankan kualitas air yang baik di akuarium ikan yang terinfeksi. Ini termasuk penggantian air secara teratur, penyaringan yang tepat, dan pemantauan kadar amonia, nitrit, dan nitrat. Air bersih membantu mengurangi stres pada ikan dan mendukung sistem kekebalan tubuh mereka",
						P7),
				new Solution(null,
						"Minimalisir stress pada ikan",
						"Stres melemahkan sistem kekebalan ikan dan membuat mereka lebih rentan terhadap infeksi. Minimalkan pemicu stres seperti perubahan parameter air secara tiba-tiba, kepadatan yang berlebihan, dan teman seakuarium yang agresif.",
						P7),
				new Solution(null,
						"Pemberian garam ikan",
						"Pemberian garam ikan pada kolam dapat membantu dalam menyembuhkan ikan dari penyakit jamur putih, disamping itu garam ikan memiliki banyak sekali manfaant jika diaplikasikan dengan takaran yang sesuai.",
						P7),
				new Solution(null,
						"Isolasi dan Karantina",
						"Segera setelah Anda melihat gejala bintik putih pada ikan, pisahkan ikan tersebut dari penghuni kolam lainnya untuk mencegah penyebaran penyakit. Tempatkan ikan yang terinfeksi ke dalam tangki karantina dengan air bersih.",
						P8),
				new Solution(null,
						"Jaga Kualitas Air",
						"Pertahankan kualitas air yang baik di akuarium ikan yang terinfeksi. Ini termasuk penggantian air secara teratur, penyaringan yang tepat, dan pemantauan kadar amonia, nitrit, dan nitrat. Air bersih membantu mengurangi stres pada ikan dan mendukung sistem kekebalan tubuh mereka",
						P8),
				new Solution(null,
						"Minimalisir stress pada ikan",
						"Stres melemahkan sistem kekebalan ikan dan membuat mereka lebih rentan terhadap infeksi. Minimalkan pemicu stres seperti perubahan parameter air secara tiba-tiba, kepadatan yang berlebihan, dan teman seakuarium yang agresif.",
						P8),
				new Solution(null,
						"Pemberian garam ikan",
						"Pemberian garam ikan pada kolam dapat membantu dalam menyembuhkan ikan dari penyakit bintik putih, disamping itu garam ikan memiliki banyak sekali manfaant jika diaplikasikan dengan takaran yang sesuai.",
						P8),
				new Solution(null,
						"Isolasi dan Karantina",
						"Segera setelah Anda melihat gejala gatal pada ikan, pisahkan ikan tersebut dari penghuni kolam lainnya untuk mencegah penyebaran penyakit. Tempatkan ikan yang terinfeksi ke dalam tangki karantina dengan air bersih.",
						P9),
				new Solution(null,
						"Jaga Kualitas Air",
						"Pertahankan kualitas air yang baik di akuarium ikan yang terinfeksi. Ini termasuk penggantian air secara teratur, penyaringan yang tepat, dan pemantauan kadar amonia, nitrit, dan nitrat. Air bersih membantu mengurangi stres pada ikan dan mendukung sistem kekebalan tubuh mereka",
						P9),
				new Solution(null,
						"Minimalisir stress pada ikan",
						"Stres melemahkan sistem kekebalan ikan dan membuat mereka lebih rentan terhadap infeksi. Minimalkan pemicu stres seperti perubahan parameter air secara tiba-tiba, kepadatan yang berlebihan, dan teman seakuarium yang agresif.",
						P9),
				new Solution(null,
						"Perhatikan nutrisi dari makanan yang diberikan",
						"Berikan makanan berkualitas tinggi dan bergizi untuk membantu sistem kekebalan ikan melawan infeksi. Pola makan yang sehat dapat membantu proses penyembuhan.",
						P9),
				new Solution(null,
						"Isolasi dan Karantina",
						"Segera setelah Anda melihat gejala Trematoda  pada ikan, pisahkan ikan tersebut dari penghuni kolam lainnya untuk mencegah penyebaran penyakit. Tempatkan ikan yang terinfeksi ke dalam tangki karantina dengan air bersih.",
						P10),
				new Solution(null,
						"Jaga Kualitas Air",
						"Pertahankan kualitas air yang baik di akuarium ikan yang terinfeksi. Ini termasuk penggantian air secara teratur, penyaringan yang tepat, dan pemantauan kadar amonia, nitrit, dan nitrat. Air bersih membantu mengurangi stres pada ikan dan mendukung sistem kekebalan tubuh mereka",
						P10),
				new Solution(null,
						"Minimalisir stress pada ikan",
						"Stres melemahkan sistem kekebalan ikan dan membuat mereka lebih rentan terhadap infeksi. Minimalkan pemicu stres seperti perubahan parameter air secara tiba-tiba, kepadatan yang berlebihan, dan teman seakuarium yang agresif.",
						P10),
				new Solution(null,
						"Perhatikan nutrisi dari makanan yang diberikan",
						"Berikan makanan berkualitas tinggi dan bergizi untuk membantu sistem kekebalan ikan melawan infeksi. Pola makan yang sehat dapat membantu proses penyembuhan.",
						P10),
				new Solution(null,
						"Isolasi dan Karantina",
						"Segera setelah Anda melihat gejala Lernaea sp  pada ikan, pisahkan ikan tersebut dari penghuni kolam lainnya untuk mencegah penyebaran penyakit. Tempatkan ikan yang terinfeksi ke dalam tangki karantina dengan air bersih.",
						P11),
				new Solution(null,
						"Lepaskan cacing yang terlihat secara perlahan",
						"Gunakan pinset atau penjepit berujung runcing untuk memegang dan mengeluarkan cacing jangkar yang terlihat dari tubuh ikan dengan hati-hati. Berhati-hatilah agar tubuh cacing tidak pecah saat dikeluarkan, karena dapat menyebabkan infeksi.",
						P11),
				new Solution(null,
						"Pemberian garam ikan",
						"Pemberian garam ikan pada kolam dapat membantu dalam menyembuhkan ikan dari penyakit Lernaea sp , disamping itu garam ikan memiliki banyak sekali manfaant jika diaplikasikan dengan takaran yang sesuai.",
						P11),
				new Solution(null,
						"Jaga Kualitas Air",
						"Pertahankan kualitas air yang baik di akuarium ikan yang terinfeksi. Ini termasuk penggantian air secara teratur, penyaringan yang tepat, dan pemantauan kadar amonia, nitrit, dan nitrat. Air bersih membantu mengurangi stres pada ikan dan mendukung sistem kekebalan tubuh mereka",
						P11)

		);

		solutionRepository.saveAll(solutions);
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
				P2.addSymptom(getSystomByCode("KG02"));
				P2.addSymptom(getSystomByCode("KG03"));
				P2.addSymptom(getSystomByCode("KG04"));
				P2.addSymptom(getSystomByCode("KG07"));
				P2.addSymptom(getSystomByCode("KG08"));
				dieseRepository.save(P2);

				Diese P3 = dieseRepository.findByCode("P3").orElseThrow();
				P3.addSymptom(getSystomByCode("KG05"));
				P3.addSymptom(getSystomByCode("KG09"));
				dieseRepository.save(P3);

				Diese P4 = dieseRepository.findByCode("P4").orElseThrow();
				P4.addSymptom(getSystomByCode("KG01"));
				P4.addSymptom(getSystomByCode("KG02"));
				P4.addSymptom(getSystomByCode("KG10"));
				dieseRepository.save(P4);

				Diese P5 = dieseRepository.findByCode("P5").orElseThrow();
				P5.addSymptom(getSystomByCode("KG01"));
				P5.addSymptom(getSystomByCode("KG02"));
				P5.addSymptom(getSystomByCode("KG06"));
				P5.addSymptom(getSystomByCode("KG11"));
				P5.addSymptom(getSystomByCode("KG12"));
				dieseRepository.save(P5);

				Diese P6 = dieseRepository.findByCode("P6").orElseThrow();
				P6.addSymptom(getSystomByCode("KG06"));
				P6.addSymptom(getSystomByCode("KG13"));
				P6.addSymptom(getSystomByCode("KG14"));
				dieseRepository.save(P6);

				Diese P7 = dieseRepository.findByCode("P7").orElseThrow();
				P7.addSymptom(getSystomByCode("KG15"));
				P7.addSymptom(getSystomByCode("KG16"));
				dieseRepository.save(P7);

				Diese P8 = dieseRepository.findByCode("P8").orElseThrow();
				P8.addSymptom(getSystomByCode("KG03"));
				P8.addSymptom(getSystomByCode("KG17"));
				P8.addSymptom(getSystomByCode("KG18"));
				P8.addSymptom(getSystomByCode("KG19"));
				dieseRepository.save(P8);

				Diese P9 = dieseRepository.findByCode("P9").orElseThrow();
				P9.addSymptom(getSystomByCode("KG03"));
				P9.addSymptom(getSystomByCode("KG04"));
				P9.addSymptom(getSystomByCode("KG19"));
				dieseRepository.save(P9);

				Diese P10 = dieseRepository.findByCode("P10").orElseThrow();
				P10.addSymptom(getSystomByCode("KG04"));
				P10.addSymptom(getSystomByCode("KG06"));
				P10.addSymptom(getSystomByCode("KG19"));
				P10.addSymptom(getSystomByCode("KG20"));
				dieseRepository.save(P10);

				Diese P11 = dieseRepository.findByCode("P11").orElseThrow();
				P11.addSymptom(getSystomByCode("KG21"));
				P11.addSymptom(getSystomByCode("KG22"));
				dieseRepository.save(P11);

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
				new Symptom(null, "KG01", "Borok pada kulit", BigDecimal.valueOf(.3)),
				new Symptom(null, "KG02", "Pendarahan pada kulit", BigDecimal.valueOf(.2)),
				new Symptom(null, "KG03", "Lemah", BigDecimal.valueOf(.2)),
				new Symptom(null, "KG04", "Kurus", BigDecimal.valueOf(.4)),
				new Symptom(null, "KG05", "Nafsu makan hilang", BigDecimal.valueOf(.6)),
				new Symptom(null, "KG06", "Kulit gelap", BigDecimal.valueOf(.2)),
				new Symptom(null, "KG07", "Kulit kasar", BigDecimal.valueOf(.8)),
				new Symptom(null, "KG08", "Susah bernafas", BigDecimal.valueOf(.9)),
				new Symptom(null, "KG09", "Infeksi kulit kepala, badan belakang, insang dan sirip", BigDecimal.valueOf(.8)),
				new Symptom(null, "KG10", "Pendarahan pada daging", BigDecimal.valueOf(.8)),
				new Symptom(null, "KG11", "Mata dan tubuh samping menonjol", BigDecimal.valueOf(.9)),
				new Symptom(null, "KG12", "Luka kecil di kulit, lalu meluas ke daging", BigDecimal.valueOf(.9)),
				new Symptom(null, "KG13", "Perut membengkak", BigDecimal.valueOf(.6)),
				new Symptom(null, "KG14", "Hati bercak-bercak", BigDecimal.valueOf(.9)),
				new Symptom(null, "KG15", "Kepala, tutup insang dan sirip ditumbuhi benang halus seperti kapas",
						BigDecimal.valueOf(.9)),
				new Symptom(null, "KG16", "Pada telur diliputi benang seperti kapas", BigDecimal.valueOf(.7)),
				new Symptom(null, "KG17", "Sering muncul ke permukaan", BigDecimal.valueOf(.8)),
				new Symptom(null, "KG18", "Timbul bintik putih pada sirip dan insang", BigDecimal.valueOf(.9)),
				new Symptom(null, "KG19", "Ikan menggesekkan badannya pada benda keras", BigDecimal.valueOf(.4)),
				new Symptom(null, "KG20", "Sirip rontok", BigDecimal.valueOf(.9)),
				new Symptom(null, "KG21", "Parasit menempel dan menusukkan diri pada tutup insang, sirip atau mata",
						BigDecimal.valueOf(.8)),
				new Symptom(null, "KG22", "Bagian tutup insang, sirip atau mata mengalami luka", BigDecimal.valueOf(.8)));

		symtomRepository.saveAll(symtoms);

		this.symtoms = symtomRepository.findAll();
	}

	private void generateDieses() {
		List<Diese> dieses = List.of(
				new Diese(null, "P1", "Pseudomonas hydrophila"),
				new Diese(null, "P2", "Aeromonas hydrophila"),
				new Diese(null, "P3", "Aeromonas punctata"),
				new Diese(null, "P4", "Columnaris"),
				new Diese(null, "P5", "Edwardsiella"),
				new Diese(null, "P6", "Tuberculosis"),
				new Diese(null, "P7", "Jamur putih"),
				new Diese(null, "P8", "Bintik putih (White spot)"),
				new Diese(null, "P9", "Gatal"),
				new Diese(null, "P10", "Trematoda"),
				new Diese(null, "P11", "Lernaea sp"));

		dieseRepository.saveAll(dieses);
	}

}
