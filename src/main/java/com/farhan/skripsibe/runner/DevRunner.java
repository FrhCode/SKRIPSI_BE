package com.farhan.skripsibe.runner;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.support.TransactionTemplate;

import com.farhan.skripsibe.entities.MassFuntion;
import com.farhan.skripsibe.model.Consultation;
import com.farhan.skripsibe.model.Diese;
import com.farhan.skripsibe.model.Role;
import com.farhan.skripsibe.model.Solution;
import com.farhan.skripsibe.model.Symptom;
import com.farhan.skripsibe.model.User;
import com.farhan.skripsibe.model.json.ResultJson;
import com.farhan.skripsibe.model.json.SymtomJson;
import com.farhan.skripsibe.repository.DieseRepository;
import com.farhan.skripsibe.repository.SolutionRepository;
import com.farhan.skripsibe.repository.SymtomRepository;
import com.farhan.skripsibe.repository.UserRepository;
import com.farhan.skripsibe.service.ConsultationService;
import com.farhan.skripsibe.service.ConverterService;
import com.farhan.skripsibe.service.DateService;
import com.farhan.skripsibe.service.DempsterShaferService;
import com.farhan.skripsibe.service.SymptomService;
import com.github.javafaker.Faker;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
@Profile({ "dev", "test-dev", "staging" })
public class DevRunner implements CommandLineRunner {
	private final DieseRepository dieseRepository;
	private final SymtomRepository symtomRepository;
	private final SolutionRepository solutionRepository;
	private final ConsultationService consultationService;
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final DateService dateService;
	private final DempsterShaferService dempsterShaferService;
	private final SymptomService symptomService;
	private final ConverterService converterService;

	private final TransactionTemplate transactionTemplate;

	@Override
	public void run(String... args) throws Exception {
		generateDieses();

		generaSymtom();

		generateRuleBase();

		generateSolution();

		for (int i = 0; i < 500; i++) {
			generateConsultation();
		}

		generateUser();

	}

	private void generateUser() {
		Role adminRole = new Role(null, "Admin");

		User user = new User(null, "farhan", "farhan7534031b@gmail.com", "082188513499",
				"/images/2024-lamborghini-revuelto-127-641a1d518802b.jpg", passwordEncoder.encode("indonesia123B"));

		user.addRole(adminRole);

		userRepository.save(user);
	}

	private void generateConsultation() {

		transactionTemplate.execute(transactionStatus -> {
			try {
				List<Symptom> symtoms = symptomService.getRandomData(4, 8);

				MassFuntion massFuntion = dempsterShaferService.calculate(symtoms);

				LocalDateTime start = LocalDateTime.of(2023, 1, 1, 0, 0);
				LocalDateTime end = LocalDateTime.of(2023, 12, 31, 23, 59);

				LocalDateTime randomDateTime = dateService.getRandomDateTimeBetween(start, end);

				Faker faker = new Faker(new Locale("in-ID"));

				String name = faker.name().fullName();

				List<ResultJson> consultationResults = converterService.massFuntionToResultJsonList(massFuntion);

				List<SymtomJson> symtomJsonsList = converterService.symptomsTosymtomJsonList(symtoms);

				Consultation consultation = new Consultation(null, null, name, randomDateTime, consultationResults,
						symtomJsonsList);

				consultationService.save(consultation);
				return null;
			} catch (Exception e) {
				transactionStatus.setRollbackOnly(); // Rollback transaction on exception
				throw e;
			}
		});

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
				P1.addSymptom(symtomRepository.findByCode("KG01").get());
				P1.addSymptom(symtomRepository.findByCode("KG02").get());
				P1.addSymptom(symtomRepository.findByCode("KG03").get());
				P1.addSymptom(symtomRepository.findByCode("KG04").get());
				P1.addSymptom(symtomRepository.findByCode("KG05").get());
				dieseRepository.save(P1);

				Diese P2 = dieseRepository.findByCode("P2").orElseThrow();
				P2.addSymptom(symtomRepository.findByCode("KG02").get());
				P2.addSymptom(symtomRepository.findByCode("KG03").get());
				P2.addSymptom(symtomRepository.findByCode("KG04").get());
				P2.addSymptom(symtomRepository.findByCode("KG07").get());
				P2.addSymptom(symtomRepository.findByCode("KG08").get());
				dieseRepository.save(P2);

				Diese P3 = dieseRepository.findByCode("P3").orElseThrow();
				P3.addSymptom(symtomRepository.findByCode("KG05").get());
				P3.addSymptom(symtomRepository.findByCode("KG09").get());
				dieseRepository.save(P3);

				Diese P4 = dieseRepository.findByCode("P4").orElseThrow();
				P4.addSymptom(symtomRepository.findByCode("KG01").get());
				P4.addSymptom(symtomRepository.findByCode("KG02").get());
				P4.addSymptom(symtomRepository.findByCode("KG10").get());
				dieseRepository.save(P4);

				Diese P5 = dieseRepository.findByCode("P5").orElseThrow();
				P5.addSymptom(symtomRepository.findByCode("KG01").get());
				P5.addSymptom(symtomRepository.findByCode("KG02").get());
				P5.addSymptom(symtomRepository.findByCode("KG06").get());
				P5.addSymptom(symtomRepository.findByCode("KG11").get());
				P5.addSymptom(symtomRepository.findByCode("KG12").get());
				dieseRepository.save(P5);

				Diese P6 = dieseRepository.findByCode("P6").orElseThrow();
				P6.addSymptom(symtomRepository.findByCode("KG06").get());
				P6.addSymptom(symtomRepository.findByCode("KG13").get());
				P6.addSymptom(symtomRepository.findByCode("KG14").get());
				dieseRepository.save(P6);

				Diese P7 = dieseRepository.findByCode("P7").orElseThrow();
				P7.addSymptom(symtomRepository.findByCode("KG15").get());
				P7.addSymptom(symtomRepository.findByCode("KG16").get());
				dieseRepository.save(P7);

				Diese P8 = dieseRepository.findByCode("P8").orElseThrow();
				P8.addSymptom(symtomRepository.findByCode("KG03").get());
				P8.addSymptom(symtomRepository.findByCode("KG17").get());
				P8.addSymptom(symtomRepository.findByCode("KG18").get());
				P8.addSymptom(symtomRepository.findByCode("KG19").get());
				dieseRepository.save(P8);

				Diese P9 = dieseRepository.findByCode("P9").orElseThrow();
				P9.addSymptom(symtomRepository.findByCode("KG03").get());
				P9.addSymptom(symtomRepository.findByCode("KG04").get());
				P9.addSymptom(symtomRepository.findByCode("KG19").get());
				dieseRepository.save(P9);

				Diese P10 = dieseRepository.findByCode("P10").orElseThrow();
				P10.addSymptom(symtomRepository.findByCode("KG04").get());
				P10.addSymptom(symtomRepository.findByCode("KG06").get());
				P10.addSymptom(symtomRepository.findByCode("KG19").get());
				P10.addSymptom(symtomRepository.findByCode("KG20").get());
				dieseRepository.save(P10);

				Diese P11 = dieseRepository.findByCode("P11").orElseThrow();
				P11.addSymptom(symtomRepository.findByCode("KG21").get());
				P11.addSymptom(symtomRepository.findByCode("KG22").get());
				dieseRepository.save(P11);

				return null;
			} catch (Exception e) {
				transactionStatus.setRollbackOnly(); // Rollback transaction on exception
				throw e;
			}
		});
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
	}

	private void generateDieses() {
		List<Diese> dieses = List.of(
				new Diese(null, "P1", "Pseudomonas hydrophila",
						"Penyakit Pseudomonas hydrophila, juga dikenal sebagai penyakit aeromonas atau septicemia hemoragik, adalah penyakit infeksi bakteri yang serius pada ikan air tawar. Penyakit ini disebabkan oleh bakteri Pseudomonas hydrophila atau Aeromonas hydrophila."),
				new Diese(null, "P2", "Aeromonas hydrophila",
						"Penyakit Aeromonas hydrophila, juga dikenal sebagai Aeromonas septicemia, adalah infeksi bakteri yang dapat mempengaruhi ikan air tawar dan laut. Aeromonas hydrophila adalah bakteri Gram-negatif yang dapat menyebabkan berbagai gejala pada ikan."),
				new Diese(null, "P3", "Aeromonas punctata",
						"Aeromonas punctata adalah salah satu spesies bakteri dari genus Aeromonas. Bakteri ini dapat dengan cepat menjalar dan dapat menyebabkan kematian mendadak dengan kemungkinan yang tinggi."),
				new Diese(null, "P4", "Columnaris",
						"Columnaris adalah infeksi bakteri pada ikan yang mempengaruhi kulit, insang, dan sirip. penyakit ini dapat menyebar dengan cepat dan dapat menyebabkan kematian mendadak dengan kemungkinan yang tinggi."),
				new Diese(null, "P5", "Edwardsiella",
						"Edwardsiella adalah bakteri yang bisa ditemukan di air tawar, air limbah, dan hewan. Bakteri ini dapat menyebabkan infeksi pada ikan dan hewan lainnya seperti reptil, amfibi, dan mamalia."),
				new Diese(null, "P6", "Tuberculosis",
						"Penyakit tuberkulosis pada ikan disebabkan oleh bakteri Mycobacterium spp., terutama Mycobacterium marinum dan Mycobacterium fortuitum. Penyakit ini sering disebut sebagai 'fish tuberculosis' atau 'fish TB'. Tuberkulosis pada ikan dapat menyerang berbagai jenis ikan air tawar dan air laut."),
				new Diese(null, "P7", "Jamur putih",
						"Penyakit jamur putih pada ikan, juga dikenal sebagai saprolegniasis, disebabkan oleh jamur dari genus Saproglena atau Achyla. Jamur ini merupakan organisme eukariotik bersel tunggal yang dapat menginfeksi ikan dan organisme air lainnya. Saprolegniasis biasanya muncul pada ikan yang mengalami luka atau stres, dan jamur tersebut memanfaatkan situasi tersebut untuk menyerang jaringan ikan."),
				new Diese(null, "P8", "Bintik putih (White spot)",
						"Bintik putih pada ikan merupakan gejala dari penyakit yang disebut 'Ichthyophthirius multifiliis,' yang lebih dikenal sebagai 'Ikan Putih' atau 'White Spot Disease' dalam bahasa Inggris. Ini adalah salah satu penyakit ikan air tawar paling umum dan dapat sangat merugikan bagi populasi ikan dalam akuarium atau kolam."),
				new Diese(null, "P9", "Gatal",
						"Penyakit gatal pada ikan adalah kondisi medis di mana ikan mengalami iritasi atau ketidaknyamanan pada kulit atau selaput lendirnya. Hal ini dapat disebabkan oleh berbagai faktor, termasuk infeksi bakteri, jamur, protozoa, atau parasit."),
				new Diese(null, "P10", "Trematoda",
						"Penyakit Trematoda pada ikan disebabkan oleh cacing pipih parasitik dari kelas Trematoda. Cacing-cacing ini juga dikenal sebagai fluke. Mereka memiliki siklus hidup kompleks yang melibatkan dua atau lebih inang selama fase perkembangan mereka."),
				new Diese(null, "P11", "Lernaea sp",
						"Lernaea, juga dikenal sebagai 'cacing benang ikan,' adalah parasit yang dapat mempengaruhi ikan air tawar dan air asin. Parasit ini termasuk dalam kelompok copepod (krustasea mikroskopis) dan umumnya dikenal dengan nama 'anchor worm' karena bentuknya yang menyerupai jangkar."));

		dieseRepository.saveAll(dieses);
	}
}
