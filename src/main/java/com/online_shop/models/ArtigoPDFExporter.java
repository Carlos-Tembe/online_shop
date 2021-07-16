package com.online_shop.models;

import java.awt.Color;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

public class ArtigoPDFExporter {

	private List<Artigo> artigos;

	public ArtigoPDFExporter(List<Artigo> artigos) {
		super();
		this.artigos = artigos;
	}

	private void writeTableHeader(PdfPTable table) {
		PdfPCell cell = new PdfPCell();
		cell.setBackgroundColor(Color.BLUE);
		cell.setPadding(5);

		Font font = FontFactory.getFont(FontFactory.HELVETICA);
		font.setColor(Color.WHITE);

		cell.setPhrase(new Phrase("ID", font));
		table.addCell(cell);
		cell.setPhrase(new Phrase("Nome", font));
		table.addCell(cell);
		cell.setPhrase(new Phrase("Codigo_barras", font));
		table.addCell(cell);
		cell.setPhrase(new Phrase("categoria", font));
		table.addCell(cell);
		// cell.setPhrase(new Phrase("Artigo ID"));

	}

	private void writeTableData(PdfPTable table) {
		for (Artigo artigo : artigos) {
			table.addCell(String.valueOf(artigo.getId()));
			table.addCell(artigo.getDescricao());
			table.addCell(artigo.getCodigo_barra());
			table.addCell(artigo.getCategoria().getDescricao());
		}
	}

	public void export(HttpServletResponse response) throws DocumentException, IOException {

		Document document = new Document(PageSize.A4);
		PdfWriter.getInstance(document, response.getOutputStream());
		document.open();

		Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
		font.setColor(Color.BLUE);
		font.setSize(18);

		Paragraph title = new Paragraph("Lista de todos artigos", font);
		title.setAlignment(Paragraph.ALIGN_CENTER);
		document.add(title);

		PdfPTable table = new PdfPTable(4);
		table.setSpacingBefore(15);
		table.setWidthPercentage(100);
		table.setWidths(new float[] { 1.0f, 3.0f, 3.2f, 2.0f });
		writeTableHeader(table);
		writeTableData(table);
		document.add(table);
		document.close();
	}
}
