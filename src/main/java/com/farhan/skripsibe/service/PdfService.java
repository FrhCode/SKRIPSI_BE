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

		for (int i = 0; i < report.getCalculationData().size(); i++) {
			List<ReportJson.MassData> combination = report.getCalculationData().get(i);

			List<String> mCombinationList = report.getMCombinationList().get(i);

			getMCombinationList(document, combination, mCombinationList);

			boolean isNotLastPage = i < report.getCalculationData().size() - 1;

			if (isNotLastPage) {
				document.add(new AreaBreak(AreaBreakType.NEXT_PAGE));
			}
		}

		document.close();
	}

	private void getMCombinationList(Document document, List<ReportJson.MassData> combination,
			List<String> mCombinationList) {
		document.add(new Paragraph());
		document.add(new Paragraph("o = Nilai plausability (1 - belief)"));

		Table tableCombination = new Table(3);
		tableCombination.setWidth(UnitValue.createPercentValue(100));

		for (ReportJson.MassData massData : combination) {
			boolean isFirstIndex = massData.getDieses() == null && massData.getDieses() == null;
			if (isFirstIndex) {
				tableCombination.addCell(new Cell().add(new Paragraph("")));

			} else {
				Table tableValue = new Table(1);
				tableValue.setWidth(UnitValue.createPercentValue(100));

				List<String> dieses = massData.getDieses();
				String value = massData.getValue();

				boolean isTeta = dieses.size() == 0;
				boolean isNointersection = dieses.contains("empty");
				if (isTeta) {
					tableValue.addCell(
							new Cell().add(new Paragraph("o")).setTextAlignment(TextAlignment.CENTER).setBorder(Border.NO_BORDER));
				} else if (isNointersection) {
					tableValue
							.addCell(new Cell().add(new Paragraph("{}")).setTextAlignment(TextAlignment.CENTER)
									.setBorder(Border.NO_BORDER));
				} else {
					tableValue
							.addCell(new Cell().add(new Paragraph(String.join(", ", dieses))).setTextAlignment(TextAlignment.CENTER)
									.setBorder(Border.NO_BORDER));

				}

				tableValue.addCell(new Cell().add(new Paragraph(value)).setBorder(Border.NO_BORDER));

				tableCombination.addCell(new Cell().add(tableValue).setTextAlignment(TextAlignment.CENTER))
						.setTextAlignment(TextAlignment.CENTER);
			}

		}

		document.add(tableCombination);

		document.add(new Paragraph());
		document.add(new Paragraph("Mx").setFontSize(13));

		Table tableMCombination = new Table(new float[] { 200f, 50f });

		for (String mCombination : mCombinationList) {
			tableMCombination.addCell(new Cell().add(new Paragraph(mCombination)).setBorder(Border.NO_BORDER));
		}

		document.add(tableMCombination);

	}
}
