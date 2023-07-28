package com.main;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import com.mysql.cj.Query;

public class Demo 
{
	public static void main(String ar[]) throws IOException
	{
		System.out.println("Runinng.....");
		Connection con=ConnectionProvider.getConnection();
	try {			
		File file=new File("C://Users//Lenovo//Downloads//2004_Vol3.pdf");
//		System.out.println("File.....");
		PDDocument document=PDDocument.load(file);
		PDFTextStripper pdfTextStripper=new PDFTextStripper();
		pdfTextStripper.setStartPage(9);
		pdfTextStripper.setEndPage(13);
		
		pdfTextStripper.setSortByPosition(true);
		String extractText=pdfTextStripper.getText(document);
		
		String[] lines=extractText.split("\n");
		System.out.println("Printing line ======================="+ lines.length);
		Map<String, Object> map=new HashMap<>();
		
		for(String line : lines)
		{
          //  System.out.println(line);
			if(line.startsWith("State-UT Code & Name :"))
			{	
				String[] parts=line.split(":");
				String stateNameAndCode="";
				System.out.println("State1---"+ parts[1]);
				stateNameAndCode=parts[1];
				
				map.put("stateNameAndCode", stateNameAndCode);

			}
			
			if(line.startsWith("PC No. & Name :"))
			{
			 String[] pName=line.split(":");
			 String pcName="";
			 System.out.println("PC1---"+pName[1]); 
	         pcName=pName[1];
	         
	         map.put("pcName", pcName);
			}
			
			if(line.startsWith("AC Number and AC Name :"))
			{
			 String[] aName=line.split(":");
			 System.out.println("AC1---"+aName[1]); 
			 String acName="";
			 acName=aName[1];
			 
			 map.put("acName",acName);
					 
          	}
			
		
		
			
       for(int j=1; j<=15; j++)
       {  
		String p=String.valueOf(j);
		if(line.startsWith(p))
		{
			String[] candiName=line.split("\\s");
		
			//System.out.println("Len----------"+candiName.length);
			String votes = candiName[candiName.length - 1];
			String party = candiName[candiName.length - 2];
			String candi = candiName[candiName.length - 3];
			String candi2= candiName[candiName.length -4];
			String candi3=candiName[candiName.length- 5];
			//String candi4=candiName[candiName.length-6];
			
			System.out.print("votes ========"+votes+"  ");
			System.out.print("party ========"+party+"  ");
			System.out.print("candi ========"+candi+"  ");
			System.out.print("candi2========"+candi2+"  ");
			System.out.print("candi3======="+candi3+"  ");
			String space=" ";
			String fullName=candi3+space+candi2+space+candi;
			System.out.println("full Nama======"+fullName);
			
			System.out.println("\t");
			
			map.put("votes", votes);
			map.put("party", party);
			map.put("candiName", fullName);
			
			
			String sql="insert into 2004_vol3(`state`,`PCNAME`,`CNAME`,`Candiname`,`Abbr`,gen_Vote) values(?,?,?,?,?,?)";
			PreparedStatement psmt=con.prepareStatement(sql);
		
			psmt.setString(1,  map.get("stateNameAndCode").toString());
			psmt.setString(2,  map.get("pcName").toString());
			psmt.setString(3,  map.get("acName").toString());
			
			psmt.setString(4,  map.get("candiName").toString());
			psmt.setString(5,  map.get("party").toString());
			psmt.setString(6,  map.get("votes").toString());
			
			
			int res=psmt.executeUpdate();
		
			if(res>0)
			{
				System.out.println("Record inserted.......................");
			}
		

			 if(line.startsWith("Election Commission of India - GE-2004 AC Segment Details for PCs"))
		       {
				  System.out.println("Break ...........");
				  break;
			    }
		}
		
		
		
       }//loop bracket
			
//	  if(line.startsWith("Election Commission of India - GE-2004 AC Segment Details for PCs"))
//       {
//		  System.out.println("Break ...........");
//		  break;
//	    }
			
		}
		
		//System.out.println(extractText);
		document.close();
	
	}catch(Exception e)
	{
		
	}

	}
}







