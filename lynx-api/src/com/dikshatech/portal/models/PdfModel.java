package com.dikshatech.portal.models;
	


	public class PdfModel {/*
		static Properties pro=PropertyLoader.loadProperties("conf.pdf_file.properties");
		private static String FILE =pro.getProperty("FILE");
		private static Font catFont1 = new Font(Font.TIMES_ROMAN,9,Font.BOLD,Color.black);
		private static Font catFont2 = new Font(Font.TIMES_ROMAN,8,Font.NORMAL,Color.black);
		private static String imgPath2 ="//opt/dev-env/workspace/DikshaPortalv2.0/calendar/img/image002.jpg";
		
		private static String imgPath1 ="//opt/dev-env/workspace/DikshaPortalv2.0/calendar/img/image003.jpg";

		
		public static void calendarPdfConverter(Calendar []cal,String regName) {
			try {
				Document document = new Document(PageSize.A4, 10,10,10,10);
				PdfWriter.getInstance(document, new FileOutputStream(FILE.concat("calendar2012_").concat(regName).concat(".pdf")));
				
				document.addTitle("DikshaPortal_Holiday PDF");
				Paragraph preface = new Paragraph();
				com.lowagie.text.Image image = 
					com.lowagie.text.Image.getInstance(imgPath1);
				com.lowagie.text.Image image2 = 
					com.lowagie.text.Image.getInstance(imgPath2);
				image.disableBorderSide(0);
				image2.disableBorderSide(0);
				image.scaleToFit(205f,75f);
				
				image2.scaleToFit(55f,50f);
				PdfPTable table1=new PdfPTable(7);
				PdfPTable table2=new PdfPTable(2);
				PdfPTable table3=new PdfPTable(1);
				
				addEmptyLine(preface,2);
				document.open();
				document.add(preface);
				document.add(createTable(image, image2, document,table2));
				document.add(createTable(cal,document,table1,regName));
				table3.addCell(table2);
				table3.addCell(table1);
				document.close();
     		} catch (Exception e) {
				e.printStackTrace();
			}
		}

		
		private static  PdfPTable createTable(Image image,Image image2,Document document,PdfPTable table)
				throws DocumentException {
			PdfPCell c1 = new PdfPCell(image);
			c1.setHorizontalAlignment(Element.ALIGN_LEFT);
			c1.setBorderColor(Color.WHITE);
			table.addCell(c1);
			c1 = new PdfPCell(image2);	
			c1.setHorizontalAlignment(Element.ALIGN_RIGHT);
			c1.setBorderColor(Color.WHITE);
			table.addCell(c1);

			return table;
		}
		
		
		private static  PdfPTable createTable(Calendar []cal,Document document,PdfPTable table,String regName)
				throws DocumentException {
			
				PdfPCell c1 = new PdfPCell(new Phrase("Name",catFont1));
				c1.setHorizontalAlignment(Element.ALIGN_CENTER);
				c1.setBackgroundColor(Color.ORANGE);
				table.addCell(c1);

				c1 = new PdfPCell(new Phrase("Region",catFont1));
				c1.setHorizontalAlignment(Element.ALIGN_CENTER);
				c1.setBackgroundColor(Color.ORANGE);
				table.addCell(c1);

				c1 = new PdfPCell(new Phrase("Year",catFont1));
				c1.setHorizontalAlignment(Element.ALIGN_CENTER);
				c1.setBackgroundColor(Color.ORANGE);
				table.addCell(c1);
				
				c1 = new PdfPCell(new Phrase("Holiday Name",catFont1));
				c1.setHorizontalAlignment(Element.ALIGN_CENTER);
				c1.setBackgroundColor(Color.ORANGE);
				table.addCell(c1);
				
				c1 = new PdfPCell(new Phrase("Date of Holiday",catFont1));
				c1.setHorizontalAlignment(Element.ALIGN_CENTER);
				c1.setBackgroundColor(Color.ORANGE);
				table.addCell(c1);
				
				c1 = new PdfPCell(new Phrase("Enable from",catFont1));
				c1.setHorizontalAlignment(Element.ALIGN_CENTER);
				c1.setBackgroundColor(Color.ORANGE);
				table.addCell(c1);
				
				c1 = new PdfPCell(new Phrase("Enable To",catFont1));
				c1.setHorizontalAlignment(Element.ALIGN_CENTER);
				c1.setBackgroundColor(Color.ORANGE);
				table.addCell(c1);
				
				table.setHeaderRows(1);
				
			for(Calendar calendar:cal){	
			if(calendar.getName()!=null){
				table.addCell(new Phrase(calendar.getName(),catFont2));
			}else{
				table.addCell("    ");
			}if(Integer.toString(calendar.getRegion())!=null){
				table.addCell(new Phrase(regName,catFont2));
			}else{
				table.addCell("    ");
			}if(Integer.toString(calendar.getYear())!=null){
				table.addCell(new Phrase(Integer.toString(calendar.getYear()),catFont2));
			}else{
				table.addCell("    ");
			}if(calendar.getHolidayName()!=null){
				table.addCell(new Phrase(calendar.getHolidayName().toString(),catFont2));
			}else{
				table.addCell("    ");
			}
			if(calendar.getDatePicker()!=null){
				table.addCell(new Phrase(calendar.getDatePicker().toString(),catFont2));
			}else{
				table.addCell("    ");
			}if(calendar.getApplicableFrom()!=null){
				table.addCell(new Phrase(calendar.getApplicableFrom().toString(),catFont2));
			}else{
				table.addCell("    ");
			}if(calendar.getApplicableTo()!=null){
				table.addCell(new Phrase(calendar.getApplicableTo().toString(),catFont2));
			}else{
				table.addCell("    ");
			}
		}
			return table;
		
		}
		private static void addEmptyLine(Paragraph paragraph, int number) {
			for (int i = 0; i < number; i++) {
				paragraph.add(new Paragraph(" "));
			}
		}

		


*/}
