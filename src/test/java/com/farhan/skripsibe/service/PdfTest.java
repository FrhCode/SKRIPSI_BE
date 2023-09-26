package com.farhan.skripsibe.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

import com.farhan.skripsibe.entities.MassData;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.AreaBreak;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.AreaBreakType;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;

// @ActiveProfiles("test-case-1")
// @SpringBootTest
public class PdfTest {
	@Test
	public void test1() throws IOException {
		// Create a PdfWriter object
		PdfWriter writer = new PdfWriter("example.pdf");

		// Create a PdfDocument object
		PdfDocument pdf = new PdfDocument(writer);
		pdf.setDefaultPageSize(PageSize.A4);
		// Create a Document object
		Document document = new Document(pdf);

		// Add content to the document
		document.add(new Paragraph("Diketahui").setBold().setFontSize(14f));

		Map<String, String> symptom1 = new HashMap<>();
		symptom1.put("diese", "Gatal (P001, P002, P003)");
		symptom1.put("belief", "0.1");

		Map<String, String> symptom2 = new HashMap<>();
		symptom2.put("diese", "Sekitar kulit tampak berwarna kemerahan (P001, P002, P003)");
		symptom2.put("belief", "0.2");

		Map<String, String> symptom3 = new HashMap<>();
		symptom3.put("diese", "Pinggir lesi aktif (P002)");
		symptom3.put("belief", "0.4");

		Map<String, String> symptom4 = new HashMap<>();
		symptom4.put("diese", "Tampak disekitar kulit terbakar (P002)");
		symptom4.put("belief", "0.7");

		List<Map<String, String>> knownSymptomList = List.of(
				symptom1, symptom2, symptom3, symptom4);

		Table tableSymtoms = new Table(2);
		tableSymtoms.setWidth(UnitValue.createPercentValue(100));

		for (Map<String, String> knownSymptom : knownSymptomList) {
			String diese = knownSymptom.get("diese");
			String belief = knownSymptom.get("belief");

			tableSymtoms.addCell(cellWithoutBorder(diese));
			tableSymtoms.addCell(cellWithoutBorder(belief));
		}

		// Add the table to the document
		document.add(tableSymtoms);

		document.add(new Paragraph());
		document.add(new Paragraph("o = Nilai plausability (1 - belief)"));

		getMCombinationList(document);
		getMCombinationList(document);
		getMCombinationList(document);
		getMCombinationList(document);
		getMCombinationList(document);

		document.add(new AreaBreak(AreaBreakType.NEXT_PAGE));

		document.add(new Paragraph("Page 2"));
		document.add(new Paragraph("Zydan"));

		// Close the document
		document.add(new Paragraph("θ")); // This will add a paragraph with the Greek letter "θ"
		document.add(new Paragraph("BWO")); // This will add a paragraph with the Greek letter "θ"

		document.close();
	}

	private void getMCombinationList(Document document) {
		List<MassData> combination = List.of(
				new MassData(null, null),
				new MassData(List.of("P001", "P002", "P003"), BigDecimal.valueOf(.20)),
				new MassData(List.of(), BigDecimal.valueOf(.80)),
				new MassData(List.of("P001", "P002", "P003"), BigDecimal.valueOf(.10)),
				new MassData(List.of("P001", "P002", "P003"), BigDecimal.valueOf(.02)),
				new MassData(List.of("P001", "P002", "P003"), BigDecimal.valueOf(.08)),
				new MassData(List.of(), BigDecimal.valueOf(.90)),
				new MassData(List.of("P001", "P002", "P003"), BigDecimal.valueOf(.18)),
				new MassData(List.of(), BigDecimal.valueOf(.72))

		);

		Table tableCombination = new Table(3);
		tableCombination.setWidth(UnitValue.createPercentValue(100));

		for (MassData massData : combination) {
			boolean isFirstIndex = massData.getDieses() == null && massData.getDieses() == null;
			if (isFirstIndex) {
				tableCombination.addCell(new Cell().add(new Paragraph("")));

			} else {
				Table tableValue = new Table(1);
				tableValue.setWidth(UnitValue.createPercentValue(100));

				List<String> dieses = massData.getDieses();
				BigDecimal value = massData.getValue();

				boolean isTeta = dieses.size() == 0;
				if (isTeta) {
					tableValue.addCell(
							new Cell().add(new Paragraph("o")).setTextAlignment(TextAlignment.CENTER).setBorder(Border.NO_BORDER));
				} else {
					tableValue
							.addCell(new Cell().add(new Paragraph(String.join(", ", dieses))).setTextAlignment(TextAlignment.CENTER)
									.setBorder(Border.NO_BORDER));
				}

				BigDecimal bigDecimalValue = value.setScale(2, RoundingMode.HALF_UP);
				String valueWith4Precision = bigDecimalValue.toString();

				tableValue.addCell(new Cell().add(new Paragraph(valueWith4Precision)).setBorder(Border.NO_BORDER));

				tableCombination.addCell(new Cell().add(tableValue).setTextAlignment(TextAlignment.CENTER))
						.setTextAlignment(TextAlignment.CENTER);
			}

		}

		document.add(tableCombination);

		document.add(new Paragraph());
		document.add(new Paragraph("Mx").setFontSize(13));

		Table tableMCombination = new Table(new float[] { 200f, 50f });

		List<String> mCombinationList = List.of(
				"P001, P002, P003",
				"0.04",
				"o",
				"0.4"

		);

		for (String mCombination : mCombinationList) {
			tableMCombination.addCell(new Cell().add(new Paragraph(mCombination)).setBorder(Border.NO_BORDER));
		}

		document.add(tableMCombination);

		document.add(new Paragraph());
		document.add(new Paragraph());
	}

	private Cell cellWithoutBorder(String text) {

		return new Cell().setBorder(Border.NO_BORDER).add(new Paragraph(text));
	}
}
