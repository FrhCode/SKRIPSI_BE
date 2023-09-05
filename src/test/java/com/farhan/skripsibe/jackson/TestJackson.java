package com.farhan.skripsibe.jackson;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.farhan.skripsibe.SkripsiBeApplication;
import com.farhan.skripsibe.model.UserTest;
import com.farhan.skripsibe.model.json.DieseJson;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest(classes = SkripsiBeApplication.class)
public class TestJackson {
	private final ObjectMapper objectMapper = new ObjectMapper();

	@Test
	public void stringfyObject() throws JsonProcessingException {
		UserTest userTest = new UserTest("Farhan", 12);
		String userTestJson = objectMapper.writeValueAsString(userTest);
		assertEquals("{\"name\":\"Farhan\",\"age\":12}", userTestJson);
	}

	@Test
	public void parseObject() throws JsonProcessingException {
		String json = "{\"name\":\"Farhan\",\"age\":12}";
		UserTest userTestFromJson = objectMapper.readValue(json, UserTest.class);
		UserTest userTest = new UserTest("Farhan", 12);

		assertEquals(userTest, userTestFromJson);
	}

	@Test
	public void parseListofObject() throws JsonProcessingException {
		String json = """
					[
						{\"name\": \"Farhan1\", \"age\": 13},
						{\"name\": \"Farhan2\", \"age\": 14},
						{\"name\": \"Farhan3\", \"age\": 15},
						{\"name\": \"Farhan4\", \"age\": 16},
						{\"name\": \"Farhan5\", \"age\": 17}
				]
						""";
		;

		List<UserTest> userTests = List.of(
				new UserTest("Farhan1", 13),
				new UserTest("Farhan2", 14),
				new UserTest("Farhan3", 15),
				new UserTest("Farhan4", 16),
				new UserTest("Farhan5", 17));
		List<UserTest> userTestsFromJson = objectMapper.readValue(json, new TypeReference<List<UserTest>>() {
		});

		assertEquals(userTests.get(1), userTestsFromJson.get(1));
	}

	@Test
	public void parseAnotherJson() throws JsonProcessingException {
		String json = "[{\"name\":\"Edwardsiella\",\"percentage\":0.8647293905865152,\"solutions\":[{\"name\":\"Isolasi dan Karantina\",\"description\":\"Segera setelah Anda melihat gejala Edwardsiella pada ikan, pisahkan ikan tersebut dari penghuni kolam lainnya untuk mencegah penyebaran penyakit. Tempatkan ikan yang terinfeksi ke dalam tangki karantina dengan air bersih.\"},{\"name\":\"Pemberian Antibiotik\",\"description\":\"Pemberian Antibiotik seperti fluoroquinolones atau oxytetracycline dapat digunakan untuk menargetkan bakteri Edwardsiella\"},{\"name\":\"Pemberian disinfektan pada kolam\",\"description\":\"Disinfeksi peralatan atau alat apa pun yang digunakan dalam menangani ikan yang terinfeksi untuk mencegah kontaminasi lebih lanjut. Bersihkan dan disinfeksi tangki atau kolam jika perlu.\"}]},{\"name\":\"Aeromonas hydrophila\",\"percentage\":0.6820697999545011,\"solutions\":[{\"name\":\"Isolasi dan Karantina\",\"description\":\"Segera setelah Anda melihat gejala Aeromonas hydrophila pada ikan, pisahkan ikan tersebut dari penghuni kolam lainnya untuk mencegah penyebaran penyakit. Tempatkan ikan yang terinfeksi ke dalam tangki karantina dengan air bersih.\"},{\"name\":\"Jaga Kualitas Air\",\"description\":\"Pertahankan kualitas air yang baik di akuarium ikan yang terinfeksi. Ini termasuk penggantian air secara teratur, penyaringan yang tepat, dan pemantauan kadar amonia, nitrit, dan nitrat. Air bersih membantu mengurangi stres pada ikan dan mendukung sistem kekebalan tubuh mereka\"},{\"name\":\"Pemberian Antibiotik\",\"description\":\"Pemberian Antibiotik seperti enrofloxacin atau florfenicol dapat digunakan untuk menargetkan bakteri Aeromonas hydrophila\"},{\"name\":\"Minimalisir stress pada ikan\",\"description\":\"Stres melemahkan sistem kekebalan ikan dan membuat mereka lebih rentan terhadap infeksi. Minimalkan pemicu stres seperti perubahan parameter air secara tiba-tiba, kepadatan yang berlebihan, dan teman seakuarium yang agresif.\"}]},{\"name\":\"Bintik putih (White spot)\",\"percentage\":0.6802553231639307,\"solutions\":[{\"name\":\"Isolasi dan Karantina\",\"description\":\"Segera setelah Anda melihat gejala bintik putih pada ikan, pisahkan ikan tersebut dari penghuni kolam lainnya untuk mencegah penyebaran penyakit. Tempatkan ikan yang terinfeksi ke dalam tangki karantina dengan air bersih.\"},{\"name\":\"Jaga Kualitas Air\",\"description\":\"Pertahankan kualitas air yang baik di akuarium ikan yang terinfeksi. Ini termasuk penggantian air secara teratur, penyaringan yang tepat, dan pemantauan kadar amonia, nitrit, dan nitrat. Air bersih membantu mengurangi stres pada ikan dan mendukung sistem kekebalan tubuh mereka\"},{\"name\":\"Minimalisir stress pada ikan\",\"description\":\"Stres melemahkan sistem kekebalan ikan dan membuat mereka lebih rentan terhadap infeksi. Minimalkan pemicu stres seperti perubahan parameter air secara tiba-tiba, kepadatan yang berlebihan, dan teman seakuarium yang agresif.\"},{\"name\":\"Pemberian garam ikan\",\"description\":\"Pemberian garam ikan pada kolam dapat membantu dalam menyembuhkan ikan dari penyakit bintik putih, disamping itu garam ikan memiliki banyak sekali manfaant jika diaplikasikan dengan takaran yang sesuai.\"}]},{\"name\":\"Aeromonas punctata\",\"percentage\":0.6176032392067576,\"solutions\":[{\"name\":\"Isolasi dan Karantina\",\"description\":\"Segera setelah Anda melihat gejala Aeromonas punctata pada ikan, pisahkan ikan tersebut dari penghuni kolam lainnya untuk mencegah penyebaran penyakit. Tempatkan ikan yang terinfeksi ke dalam tangki karantina dengan air bersih.\"},{\"name\":\"Pemberian Antibiotik\",\"description\":\"Pemberian Antibiotik seperti enrofloxacin atau florfenicol dapat digunakan untuk menargetkan bakteri Aeromonas punctata\"},{\"name\":\"Pemberian garam ikan\",\"description\":\"Pemberian garam ikan pada kolam dapat membantu dalam menyembuhkan ikan dari bakteri Aeromonas punctata, disamping itu garam ikan memiliki banyak sekali manfaant jika diaplikasikan dengan takaran yang sesuai.\"}]},{\"name\":\"Pseudomonas hydrophila\",\"percentage\":0.378903286369486,\"solutions\":[{\"name\":\"Isolasi dan Karantina\",\"description\":\"Segera setelah Anda melihat gejala Pseudomonas hydrophila pada ikan, pisahkan ikan tersebut dari penghuni kolam lainnya untuk mencegah penyebaran penyakit. Tempatkan ikan yang terinfeksi ke dalam tangki karantina dengan air bersih.\"},{\"name\":\"Jaga Kualitas Air\",\"description\":\"Pertahankan kualitas air yang baik di akuarium ikan yang terinfeksi. Ini termasuk penggantian air secara teratur, penyaringan yang tepat, dan pemantauan kadar amonia, nitrit, dan nitrat. Air bersih membantu mengurangi stres pada ikan dan mendukung sistem kekebalan tubuh mereka\"},{\"name\":\"Pemberian Antibiotik\",\"description\":\"Pemberian Antibiotik seperti enrofloxacin atau florfenicol dapat digunakan untuk menargetkan bakteri Pseudomonas\"},{\"name\":\"Pemberian Salep\",\"description\":\"Untuk infeksi eksternal, pengobatan salep sangat disarankan. Perawatan ini dapat membantu mencegah infeksi sekunder dan mempercepat penyembuhan\"},{\"name\":\"Pemberian disinfektan pada kolam\",\"description\":\"Disinfeksi peralatan atau alat apa pun yang digunakan dalam menangani ikan yang terinfeksi untuk mencegah kontaminasi lebih lanjut. Bersihkan dan disinfeksi tangki atau kolam jika perlu.\"}]},{\"name\":\"Trematoda\",\"percentage\":0.35452551469879534,\"solutions\":[{\"name\":\"Isolasi dan Karantina\",\"description\":\"Segera setelah Anda melihat gejala Trematoda  pada ikan, pisahkan ikan tersebut dari penghuni kolam lainnya untuk mencegah penyebaran penyakit. Tempatkan ikan yang terinfeksi ke dalam tangki karantina dengan air bersih.\"},{\"name\":\"Jaga Kualitas Air\",\"description\":\"Pertahankan kualitas air yang baik di akuarium ikan yang terinfeksi. Ini termasuk penggantian air secara teratur, penyaringan yang tepat, dan pemantauan kadar amonia, nitrit, dan nitrat. Air bersih membantu mengurangi stres pada ikan dan mendukung sistem kekebalan tubuh mereka\"},{\"name\":\"Minimalisir stress pada ikan\",\"description\":\"Stres melemahkan sistem kekebalan ikan dan membuat mereka lebih rentan terhadap infeksi. Minimalkan pemicu stres seperti perubahan parameter air secara tiba-tiba, kepadatan yang berlebihan, dan teman seakuarium yang agresif.\"},{\"name\":\"Perhatikan nutrisi dari makanan yang diberikan\",\"description\":\"Berikan makanan berkualitas tinggi dan bergizi untuk membantu sistem kekebalan ikan melawan infeksi. Pola makan yang sehat dapat membantu proses penyembuhan.\"}]},{\"name\":\"Jamur putih\",\"percentage\":0.3007966209151143,\"solutions\":[{\"name\":\"Isolasi dan Karantina\",\"description\":\"Segera setelah Anda melihat gejala Jamur putih pada ikan, pisahkan ikan tersebut dari penghuni kolam lainnya untuk mencegah penyebaran penyakit. Tempatkan ikan yang terinfeksi ke dalam tangki karantina dengan air bersih.\"},{\"name\":\"Jaga Kualitas Air\",\"description\":\"Pertahankan kualitas air yang baik di akuarium ikan yang terinfeksi. Ini termasuk penggantian air secara teratur, penyaringan yang tepat, dan pemantauan kadar amonia, nitrit, dan nitrat. Air bersih membantu mengurangi stres pada ikan dan mendukung sistem kekebalan tubuh mereka\"},{\"name\":\"Minimalisir stress pada ikan\",\"description\":\"Stres melemahkan sistem kekebalan ikan dan membuat mereka lebih rentan terhadap infeksi. Minimalkan pemicu stres seperti perubahan parameter air secara tiba-tiba, kepadatan yang berlebihan, dan teman seakuarium yang agresif.\"},{\"name\":\"Pemberian garam ikan\",\"description\":\"Pemberian garam ikan pada kolam dapat membantu dalam menyembuhkan ikan dari penyakit jamur putih, disamping itu garam ikan memiliki banyak sekali manfaant jika diaplikasikan dengan takaran yang sesuai.\"}]},{\"name\":\"Lernaea sp\",\"percentage\":0.027075934173432237,\"solutions\":[{\"name\":\"Isolasi dan Karantina\",\"description\":\"Segera setelah Anda melihat gejala Lernaea sp  pada ikan, pisahkan ikan tersebut dari penghuni kolam lainnya untuk mencegah penyebaran penyakit. Tempatkan ikan yang terinfeksi ke dalam tangki karantina dengan air bersih.\"},{\"name\":\"Lepaskan cacing yang terlihat secara perlahan\",\"description\":\"Gunakan pinset atau penjepit berujung runcing untuk memegang dan mengeluarkan cacing jangkar yang terlihat dari tubuh ikan dengan hati-hati. Berhati-hatilah agar tubuh cacing tidak pecah saat dikeluarkan, karena dapat menyebabkan infeksi.\"},{\"name\":\"Pemberian garam ikan\",\"description\":\"Pemberian garam ikan pada kolam dapat membantu dalam menyembuhkan ikan dari penyakit Lernaea sp , disamping itu garam ikan memiliki banyak sekali manfaant jika diaplikasikan dengan takaran yang sesuai.\"},{\"name\":\"Jaga Kualitas Air\",\"description\":\"Pertahankan kualitas air yang baik di akuarium ikan yang terinfeksi. Ini termasuk penggantian air secara teratur, penyaringan yang tepat, dan pemantauan kadar amonia, nitrit, dan nitrat. Air bersih membantu mengurangi stres pada ikan dan mendukung sistem kekebalan tubuh mereka\"}]}]";
		List<DieseJson> userTestFromJson = objectMapper.readValue(json, new TypeReference<List<DieseJson>>() {
		});
		System.out.println("BREAK");
		System.out.println(userTestFromJson);
		// assertEquals(userTest, userTestFromJson);
	}

}
