package com.arrkgroup.apps.pdf;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.arrkgroup.apps.form.SectionConsolidatedBean;
import com.arrkgroup.apps.model.Section;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

/**
 * This view class generates a PDF document 'on the fly' based on the data
 * contained in the model.
 *
 * @author www.codejava.net
 *
 */


public class PdfBuilder extends AbstractITextPdfView {

	List<Section> allSections;



	@Override
	protected void buildPdfDocument(Map<String, Object> model, Document doc,
			PdfWriter writer, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// get data model which is passed by the Spring container
		 allSections = (List<Section>) model.get("allSections");
		List allSectionAssessmentScore = (List) model
				.get("allSectionAssessmentScore");

		String relativeWebPath = "/resources/images/arrklogo.png";
		String absoluteDiskPath = getServletContext().getRealPath(
				relativeWebPath);
		Image arrkLogo = Image.getInstance(absoluteDiskPath);
		arrkLogo.scaleAbsolute(150f, 60f);
		doc.add(arrkLogo);
		doc.add(new Paragraph(" "));

		Paragraph preface = new Paragraph();
		preface.setAlignment(Element.ALIGN_CENTER);
		preface.add("Performance Assessment");

		/*new Paragraph("Performance Assessment").setAlignment(Element.ALIGN_CENTER)*/


		doc.add(preface);


		doc.add(new Paragraph(" "));
		doc.add(new Paragraph("Employee  ID:"+(int) model.get("empid")));
		doc.add(new Paragraph("Employee Name:"+(String) model.get("empname")));

		doc.add(new Paragraph("Cycle :"+(String) model.get("cycle")));
		doc.add(new Paragraph(" "));

		doc.add(new Paragraph("Organisation weightage of different parameters"));

		// write table row data
		for (Section section : allSections) {
			doc.add(new Paragraph(section.getSection() + " - "
					+ section.getSection_weightage()));

		}


		//setting summary Ratings






		PdfPTable table = new PdfPTable(2);

		PdfPTable table1 = new PdfPTable(2);

		table.setWidthPercentage(100.0f);
		table.setWidths(new float[] { 6.0f, 5.0f, });
		table.setSpacingBefore(10);

		table1.setWidthPercentage(100.0f);
		table1.setWidths(new float[] { 6.0f, 5.0f, });
		table1.setSpacingBefore(10);


		// define font for table header row
				Font font = FontFactory.getFont(FontFactory.HELVETICA);
				font.setColor(BaseColor.WHITE);

		// define table header cell
		PdfPCell cell = new PdfPCell();
		cell.setBackgroundColor(BaseColor.BLUE);
		cell.setPadding(5);

		// write table header
		cell.setPhrase(new Phrase("Section Title", font));
		table.addCell(cell);

		cell.setPhrase(new Phrase("Section Score", font));
		table.addCell(cell);



		ListIterator allSectionAssessmentScoreIterator = allSectionAssessmentScore
				.listIterator();
		System.out.println(allSectionAssessmentScore.size());

		List<SectionConsolidatedBean> conList=new ArrayList<SectionConsolidatedBean>();
		for (int i = 1; allSectionAssessmentScoreIterator.hasNext(); i++) {

			List<SectionConsolidatedBean> listSectionConsolidatedBean = (List) allSectionAssessmentScoreIterator
					.next();
			System.out.println("test"+ listSectionConsolidatedBean.size());


			ListIterator list = listSectionConsolidatedBean.listIterator();





			for (int j = 1; list.hasNext(); j++) {
				SectionConsolidatedBean sectionConsolidatedBean = (SectionConsolidatedBean) list
						.next();
				SectionConsolidatedBean consolidatedBean=new SectionConsolidatedBean();

consolidatedBean.setId(sectionConsolidatedBean.getId());
consolidatedBean.setSection(sectionConsolidatedBean.getSection());
consolidatedBean.setSection_point(consolidatedBean.getSection_point()+sectionConsolidatedBean.getSection_point());



	/*			table.addCell(sectionConsolidatedBean.getSection());
				table.addCell(String.valueOf((sectionConsolidatedBean)
						.getSection_point()));*/

				System.out.println("table row created");
				conList.add(consolidatedBean);
			}
		}

		 HashMap hm = new HashMap();

		 Map<String, Float> averageSection=new HashMap<String, Float>();

		for (SectionConsolidatedBean sectionConsolidatedBean : conList) {


			if( averageSection.get(sectionConsolidatedBean.getSection()) != null)
			{
				float temp=0;

				temp=averageSection.get(sectionConsolidatedBean.getSection());
				temp=temp+sectionConsolidatedBean.getSection_point();

				temp=temp/2;
temp=(float) Math.round(temp * 100) / 100;



				averageSection.put(sectionConsolidatedBean.getSection(),temp);


			}else
			{
				averageSection.put(sectionConsolidatedBean.getSection(),sectionConsolidatedBean.getSection_point());

			}






		}






		float final_weightage=0;

		Iterator<Section> sectionIterator = allSections.iterator();
		for (Entry<String, Float> entry : averageSection.entrySet())
		{
			float temp;
            float sectionWeightage;
		    System.out.println("after addtion"+entry.getKey() + "/" + entry.getValue());

		    table.addCell(entry.getKey());

			table.addCell(String.valueOf(entry.getValue()));
			sectionWeightage=sectionIterator.next().getSection_weightage();

			System.out.println("og sectoin points "+sectionWeightage);


 temp = (sectionWeightage * entry.getValue());

 final_weightage=temp+final_weightage;


System.out.println( "weightage is "+final_weightage);


		}
	/*	doc.add(new Paragraph("Final weightage is"  + " - "
				+ final_weightage));*/

		 table1.addCell("Final weightage");

			table1.addCell(String.valueOf((float) Math.round(final_weightage * 100) / 100  ));


			doc.add(table);
			doc.add(table1);


















	}

/*
	protected PdfPTable setSummaryDetail(List allSectionAssessmentScore) throws DocumentException
	{
		PdfPTable table = new PdfPTable(2);

		PdfPTable table1 = new PdfPTable(2);

		table.setWidthPercentage(100.0f);
		table.setWidths(new float[] { 6.0f, 5.0f, });
		table.setSpacingBefore(10);

		table1.setWidthPercentage(100.0f);
		table1.setWidths(new float[] { 6.0f, 5.0f, });
		table1.setSpacingBefore(10);


		// define font for table header row
				Font font = FontFactory.getFont(FontFactory.HELVETICA);
				font.setColor(BaseColor.WHITE);

		// define table header cell
		PdfPCell cell = new PdfPCell();
		cell.setBackgroundColor(BaseColor.BLUE);
		cell.setPadding(5);

		// write table header
		cell.setPhrase(new Phrase("Section Title", font));
		table.addCell(cell);

		cell.setPhrase(new Phrase("Section Score", font));
		table.addCell(cell);



		ListIterator allSectionAssessmentScoreIterator = allSectionAssessmentScore
				.listIterator();
		System.out.println(allSectionAssessmentScore.size());

		List<SectionConsolidatedBean> conList=new ArrayList<SectionConsolidatedBean>();
		for (int i = 1; allSectionAssessmentScoreIterator.hasNext(); i++) {

			List<SectionConsolidatedBean> listSectionConsolidatedBean = (List) allSectionAssessmentScoreIterator
					.next();
			System.out.println("test"+ listSectionConsolidatedBean.size());


			ListIterator list = listSectionConsolidatedBean.listIterator();





			for (int j = 1; list.hasNext(); j++) {
				SectionConsolidatedBean sectionConsolidatedBean = (SectionConsolidatedBean) list
						.next();
				SectionConsolidatedBean consolidatedBean=new SectionConsolidatedBean();

consolidatedBean.setId(sectionConsolidatedBean.getId());
consolidatedBean.setSection(sectionConsolidatedBean.getSection());
consolidatedBean.setSection_point(consolidatedBean.getSection_point()+sectionConsolidatedBean.getSection_point());



				table.addCell(sectionConsolidatedBean.getSection());
				table.addCell(String.valueOf((sectionConsolidatedBean)
						.getSection_point()));

				System.out.println("table row created");
				conList.add(consolidatedBean);
			}
		}

		 HashMap hm = new HashMap();

		 Map<String, Float> averageSection=new HashMap<String, Float>();

		for (SectionConsolidatedBean sectionConsolidatedBean : conList) {


			if( averageSection.get(sectionConsolidatedBean.getSection()) != null)
			{
				float temp=0;

				temp=averageSection.get(sectionConsolidatedBean.getSection());
				temp=temp+sectionConsolidatedBean.getSection_point();

				temp=temp/2;
temp=(float) Math.round(temp * 100) / 100;



				averageSection.put(sectionConsolidatedBean.getSection(),temp);


			}else
			{
				averageSection.put(sectionConsolidatedBean.getSection(),sectionConsolidatedBean.getSection_point());

			}






		}






		float final_weightage=0;

		Iterator<Section> sectionIterator = allSections.iterator();
		for (Entry<String, Float> entry : averageSection.entrySet())
		{
			float temp;
            float sectionWeightage;
		    System.out.println("after addtion"+entry.getKey() + "/" + entry.getValue());

		    table.addCell(entry.getKey());

			table.addCell(String.valueOf(entry.getValue()));
			sectionWeightage=sectionIterator.next().getSection_weightage();

			System.out.println("og sectoin points "+sectionWeightage);


 temp = (sectionWeightage * entry.getValue());

 final_weightage=temp+final_weightage;


System.out.println( "weightage is "+final_weightage);


		}
		doc.add(new Paragraph("Final weightage is"  + " - "
				+ final_weightage));

		 table1.addCell("Final weightage is");

			table1.addCell(String.valueOf(final_weightage));








		return table;
	}*/

}