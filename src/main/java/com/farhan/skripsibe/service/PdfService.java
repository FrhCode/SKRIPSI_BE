package com.farhan.skripsibe.service;

import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import org.springframework.stereotype.Service;

import com.farhan.skripsibe.entities.MassData;
import com.farhan.skripsibe.model.json.ReportJson;
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

@Service
public class PdfService {
	public void generateConsultationReport(ReportJson report) throws FileNotFoundException {
		PdfWriter writer = new PdfWriter(report.getName());

		// Create a PdfDocument object
		PdfDocument pdf = new PdfDocument(writer);
		pdf.setDefaultPageSize(PageSize.A4);
		// Create a Document object
		Document document = new Document(pdf);

		// Add content to the document
		document.add(new Paragraph("Diketahui").setBold().setFontSize(14f));

		Table tableSymtoms = new Table(2);
		tableSymtoms.setWidth(UnitValue.createPercentValue(100));

		for (ReportJson.Known known : report.getKnowns()) {
			String diese = known.getDiese();
			String belief = known.getBelief();

			tableSymtoms.addCell(new Cell().add(new Paragraph(diese).setBorder(Border.NO_BORDER)));
			tableSymtoms.addCell(new Cell().add(new Paragraph(belief).setBorder(Border.NO_BORDER)));
		}

		// Add the table to the document
		document.add(tableSymtoms);

		document.add(new AreaBreak(AreaBreakType.NEXT_PAGE));

		int iteration = 4;
		for (int i = 0; i < iteration; i++) {
			List<MassData> combination = List.of(new MassData(null, null),
					new MassData(List.of("P001", "P002", "P003"), BigDecimal.valueOf(.20)),
					new MassData(List.of(), BigDecimal.valueOf(.80)),
					new MassData(List.of("P001", "P002", "P003"), BigDecimal.valueOf(.10)),
					new MassData(List.of("P001", "P002", "P003"), BigDecimal.valueOf(.02)),
					new MassData(List.of("P001", "P002", "P003"), BigDecimal.valueOf(.08)),
					new MassData(List.of(), BigDecimal.valueOf(.90)),
					new MassData(List.of("P001", "P002", "P003"), BigDecimal.valueOf(.18)),
					new MassData(List.of(), BigDecimal.valueOf(.72))

			);

			getMCombinationList(document, combination);

			boolean isNotLastPage = i < iteration - 1;

			if (isNotLastPage) {
				document.add(new AreaBreak(AreaBreakType.NEXT_PAGE));
			}
		}

		document.close();
	}

	private void getMCombinationList(Document document, List<MassData> combination) {
		document.add(new Paragraph());
		document.add(new Paragraph("o = Nilai plausability (1 - belief)"));

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

	}
}
