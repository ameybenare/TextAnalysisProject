package com.search.engine;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.opencsv.CSVReader;
import com.opencsv.bean.CsvToBeanBuilder;
import com.search.engine.docs.Engine;
import com.search.engine.docs.ReaderCsvOccupations;
import com.search.engine.docs.ReaderCsvSkills;

@SpringBootApplication
public class SearchEngineApplication implements CommandLineRunner {

	@Bean
	public Engine getEngine() {
		Engine obj = new Engine();
		return obj;
	}

	@Bean
	public ReaderCsvSkills getCSVReader() {
		ReaderCsvSkills csvReaderSkills = new ReaderCsvSkills();
		return csvReaderSkills;
	}
	
	@Bean
	public ReaderCsvOccupations getCSVOccupations() {
		ReaderCsvOccupations readerCsvOccupations = new ReaderCsvOccupations();
		return readerCsvOccupations;
	}
	
	public static void main(String[] args) {
		SpringApplication.run(SearchEngineApplication.class, args);
	}

	@Override
	public void run(String args[]) throws Exception {

		System.out.println("In run method");
		loadEscoCsvData();
		getEngine().stemCsvData();
		String text = getEngine().pdfToText();
		text = text.replaceAll("[0-9].{2,3}", "");
		 //System.out.println("After >>>>> \n"+text);
		getCourseNames(text);
		getEngine().removeStopWord(text);
		getEngine().showResult();
		System.out.println("run method Exit");
	}
	/***
	 * This Method is written just to get the names of modules, 
	 * so here we have not performed stemming,lemma etc...
	 * as those operation will not provide us the exact name
	 * @param text
	 */
	public void getCourseNames(String text) {
		text=text.replace("Modul ", "Module ");
		text=text.strip();
		Map<String,String> moduleName = new HashMap<String,String>();
		boolean b =  true;int num=1;
		while(b) {
			int i =text.indexOf("Module Name");
			if(i>0) {
			text=text.substring(i+12);
			int j =text.indexOf("Module");
			String name = text.substring(0,j);
			getEngine().moduleName.put(String.valueOf(num), name.strip());
			num++;
			}else {
				b=false;
			}
		}
		
		
	}
	/***
	 * Load the esco data from CSV files.*/
	public void loadEscoCsvData() {
		loadSkillsData();
		loadOccupationsData();
	}

	/***
	 * Load esco skills data from CSV fie to
	 * map with object of ReaderCsvSkills.class*/
	public void loadSkillsData() {
		String skillsFile = "skills_en.csv";
		try {
			ClassLoader classLoader = SearchEngineApplication.class.getClassLoader();
			List<ReaderCsvSkills> skillData = new CsvToBeanBuilder(new FileReader(classLoader.getResource(skillsFile).getFile()))
					.withType(ReaderCsvSkills.class).build().parse();
			getEngine().setSkillData(skillData);
		} catch (IllegalStateException | FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/***
	 * Load esco occupations data from CSV fie to
	 * map with object of ReaderCsvOccupations.class*/
	public void loadOccupationsData() {
		String occpationFile = "occupations_en.csv";
		ClassLoader classLoader = SearchEngineApplication.class.getClassLoader();
		try {
			List<ReaderCsvOccupations> occupationData = new CsvToBeanBuilder(new FileReader(classLoader.getResource(occpationFile).getFile()))
					.withType(ReaderCsvOccupations.class).build().parse();
			getEngine().setOccupationData(occupationData);
		} catch (IllegalStateException | FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}	
}
