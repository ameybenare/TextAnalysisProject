package com.search.engine.docs;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.lucene.analysis.CharArraySet;
import org.apache.lucene.analysis.StopFilter;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.en.EnglishAnalyzer;
import org.apache.lucene.analysis.en.PorterStemFilter;
import org.apache.lucene.analysis.standard.StandardTokenizer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.context.annotation.Configuration;

import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.CoreDocument;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import opennlp.tools.ngram.NGramGenerator;

@Configuration
public class Engine {
	List<ReaderCsvSkills> skillData = new ArrayList<ReaderCsvSkills>();
	String stemString = "";
	List<ReaderCsvOccupations> occupationData = new ArrayList<ReaderCsvOccupations>();
	DocumentList docList = new DocumentList();
	Map<String, ArrayList<String>> finalKeywords = new HashMap<String,ArrayList<String>>();
	Map<String, Double> countingMap = new HashMap<String, Double>();
	Map<String, Double> tfidfMap = new HashMap<String, Double>();
	Map<String, String> keyWordDocMap = new HashMap<String, String>();
	Map<String, String> docKeywordMap = new HashMap<String, String>();//map containing keyword in each module
	Map<String, String> nGramMap = new HashMap<String, String>();//map with key as ngram word and value as document index in which it contains
	Map<String, List<ReaderCsvSkills>> finalSkills = new HashMap<String, List<ReaderCsvSkills>>();
	Map<String, List<ReaderCsvOccupations>> finalOccupations = new HashMap<String, List<ReaderCsvOccupations>>();
	public Map<String, String> moduleName = new HashMap<String, String>();//map containing keyword in each module
	public List<ReaderCsvSkills> getSkillData() {
		return skillData;
	}

	public void setSkillData(List<ReaderCsvSkills> skillData) {
		this.skillData = skillData;
	}

	public List<ReaderCsvOccupations> getOccupationData() {
		return occupationData;
	}

	public void setOccupationData(List<ReaderCsvOccupations> occupationData) {
		this.occupationData = occupationData;
	}
	/**
	 * performs stemming on CSV data**/
	public String stemCsvData(String text) {
		String tokenString = "";
		try {
			Tokenizer standardTokenizer = new StandardTokenizer();
			StringReader stringReader = new StringReader(text);
			standardTokenizer.setReader(stringReader);
			CharArraySet charArraySet = CharArraySet.copy(EnglishAnalyzer.ENGLISH_STOP_WORDS_SET);
			TokenStream tokenStream = new StopFilter(standardTokenizer, charArraySet);// stop word removing
			tokenStream = new PorterStemFilter(tokenStream);

			tokenStream.reset();


			CharTermAttribute attribute = tokenStream.addAttribute(CharTermAttribute.class);
			List<String> stringResult = new ArrayList<String>();
			String currentAttribute = "";
			while (tokenStream.incrementToken()) {
				// for map counting
				currentAttribute = attribute.toString().toLowerCase();
				tokenString += currentAttribute + " ";
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return tokenString;
	}

	/**
	 * Loads each csv data and call other 
	 * method stemCsvData(String) to performs stemming of CSV data**/
	public void stemCsvData() {
		for(int i=0;i<this.skillData.size();i++) {
			String text = this.skillData.get(i).getDescription();
			String stem =stemCsvData(text.toLowerCase());
			this.skillData.get(i).setDescription(stem);

			String label =  this.skillData.get(i).getAltLabels();
			String stemLabel =stemCsvData(label.toLowerCase());
			this.skillData.get(i).setAltLabels(stemLabel);
		}

		for(int i=0;i<this.occupationData.size();i++) {
			String text = this.occupationData.get(i).getDescription();
			String stem =stemCsvData(text.toLowerCase());
			this.occupationData.get(i).setDescription(stem);

			String label =  this.occupationData.get(i).getAltLabels();
			String stemLabel =stemCsvData(label.toLowerCase());
			this.occupationData.get(i).setAltLabels(stemLabel);

		}
	}
	/***
	 * This methods performs operation of stop word removal and 
	 * has inner calls to generate keywords 
	 * **/
	public void removeStopWord(String text) {
		try {
			text = text.toLowerCase();
			// Version matchVersion = Version.LUCENE_8_9_0;
			Tokenizer standardTokenizer = new StandardTokenizer();
			StringReader stringReader = new StringReader(text);
			standardTokenizer.setReader(stringReader);

			CharArraySet charArraySet  = CharArraySet.copy(EnglishAnalyzer.ENGLISH_STOP_WORDS_SET);

			TokenStream tokenStream = new StopFilter(standardTokenizer, charArraySet);// stop word removing
			tokenStream = new PorterStemFilter(tokenStream);// stemming
			//tokenStream = new EnglishMinimalStemFilter(tokenStream);//removes 's'	from last letter
			tokenStream.reset();
			String tokenString = "";
			CharTermAttribute attribute = tokenStream.addAttribute(CharTermAttribute.class);

			StringBuilder temp = new StringBuilder();
			List<String> stringResult = new ArrayList<String>();


			stringResult = buildTokenStream( tokenStream , stringResult, attribute);

			generateNGram(stringResult,2);//method call to generate bi-gram using stemmed data
			generateNGram(stringResult,3);//method call to generate tri-gram using stemmed data

			/*FileWriter fWriter = new FileWriter("D:\\Schmalkalden-sem1\\TAADS\\before.txt");
			fWriter.write(text);
			fWriter.close();*/
			String result = "";

			for (String documentContent : stringResult) {
				Document document = new Document();
				document.setText(documentContent.toLowerCase());
				this.docList.getDocuments().add(document);
				result += documentContent;
			}


			tFIDF();// Method call for Term Frequency- Inverse Document Frequency

			//sructinizeKeywords();

			for(int k =1;k<=19;k++) {
				matchEscoData(k);	
				matchEscoDataOccupations(k);
			}

			//printCsvData();	

			/*FileWriter fWriter1 = new FileWriter("D:\\Schmalkalden-sem1\\TAADS\\now.txt");
			fWriter1.write(result);
			fWriter1.close();*/

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * This method is used for printing the result of application.
	 * for keywords: docKeywordMap
	 * for skills: finalSkills
	 * for occupation: finalOccupations
	 * for module name: moduleName
	 **/
	public void showResult() {

		for(int i=1;i<=this.moduleName.size();i++) {
			final String ANSI_RESET = "\u001B[0m";
			final String ANSI_YELLOW = "\u001B[33m";
			final String ANSI_BLUE ="\u001B[34m";

			//BLACK	\u001B[30m	BLACK_BACKGROUND	\u001B[40m
			System.out.print("\n"+ANSI_BLUE+" Module: "+this.moduleName.get(String.valueOf(i))+"\n"+ANSI_RESET);
			System.out.print(ANSI_YELLOW+"\t\tKeywords:\n"+ANSI_RESET);
			String keywords =docKeywordMap.get(String.valueOf(i));
			keywords = keywords.replace(";", "; ");
			System.out.print(keywords);
			System.out.print("\n");
			System.out.print(ANSI_YELLOW+"\t\tSkills:\n"+ANSI_RESET);
			int counter =1;
			for(ReaderCsvSkills skills: this.finalSkills.get(String.valueOf(i))) {
				System.out.print(counter+")"+skills.getPreferredLabel()+" ");
				counter++;
			}
			System.out.print("\n");
			System.out.print(ANSI_YELLOW+"\t\tOccupations:\n"+ANSI_RESET);
			counter =1;
			for(ReaderCsvOccupations occup: this.finalOccupations.get(String.valueOf(String.valueOf(i)))) {
				System.out.print(counter+")"+occup.getPreferredLabel()+" ");
				counter++;
			}
			System.out.print("\n\n");
		}

	}

	/***
	 * Methods generates list per module*/
	private List<String> buildTokenStream(TokenStream tokenStream,	List<String> stringResult, CharTermAttribute attribute) {
		String previousAttribute="" ,currentAttribute,bigram;
		String tokenString = "";

		StringBuilder temp = new StringBuilder();
		try {
			while (tokenStream.incrementToken()) {
				//for map counting
				currentAttribute = attribute.toString().toLowerCase();

				//currentAttribute = lemmatize(currentAttribute);
				if(attribute.toString().length()>1) {//to remove single character  i.e to remove 'c' from Prof. Ralf C. Staudemeyer
					if (this.countingMap.containsKey(currentAttribute)) {
						this.countingMap.put(currentAttribute,
								this.countingMap.get(currentAttribute) + 1);
					} else {
						this.countingMap.put(currentAttribute, 1.00);
					}
				}

				//to separate in array list
				if (attribute.toString().equalsIgnoreCase("Modul") || "Name".equalsIgnoreCase(attribute.toString())) {
					temp = temp.append(currentAttribute);

					if ("Name".equalsIgnoreCase(attribute.toString())) {

						stringResult.add(tokenString);
						tokenString = "";
						tokenString += "\n " + temp + " ";
						temp.setLength(0);
						temp.trimToSize();
					} else {
						tokenString += currentAttribute + " ";
						temp.setLength(0);
						temp.trimToSize();
					}

				} else {
					tokenString += currentAttribute + " ";
				}
				previousAttribute = currentAttribute;
				this.stemString = this.stemString + " "+currentAttribute;
			}
			tokenStream.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		stringResult.add(tokenString);// for the last module

		// purpose of below code is to check the output//
		return stringResult;
		// TODO Auto-generated method stub

	}

	/**
	 * Method to convert PDF to text(String)**/
	public String pdfToText() {
		String text = "",fileName = "module_manual_IMACS.pdf";
		try {
			ClassLoader classLoader = Engine.class.getClassLoader();
			File file = new File(classLoader.getResource(fileName).getFile());
			PDDocument pdfDocument = PDDocument.load(file);
			PDFTextStripper pdfTextStripper = new PDFTextStripper();
			text = pdfTextStripper.getText(pdfDocument);	
			//System.out.println(text);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return text;
	}

	/*
	 * Method return total terms in document*/
	public int termsInDocument(Document d) {
		String[] strArr = d.getText().split(" ");
		return strArr.length-1; // Doing -1 to remove \n as word
	}
	/*
	 * Method to calculate IDF
	 * IDF is calculated by log of  total number of documents 
	 * by the number of documents in the collection containing the term.*/
	public double inverseDocFrequency(String word) {
		double docCount = this.docList.getDocuments().size();
		double count = 0;
		int docNumber = 0;
		// int docIndex=0;
		if (word.contains(" ")) {// if condition if for n>1 gram
			String[] arr = this.nGramMap.get(word).split(";");
			count = arr.length;
		} else {
			for (Document d : this.docList.getDocuments()) {
				String[] strArr = d.getText().split(" ");

				// List<String> s = Arrays.asList(d.getText().split(word));
				if (Arrays.asList(strArr).contains(word)) { // else for n =1 gram
					count++;
				}
				if (Arrays.asList(strArr).contains(word)) {
					if (keyWordDocMap.containsKey(word)) {
						keyWordDocMap.put(word, keyWordDocMap.get(word) + docNumber + ";");
					} else {
						keyWordDocMap.put(word, docNumber + ";");
					}

				}
				docNumber++;
				// docIndex++;
			}
		}

		return Math.log((docCount / count));
	}

	/*
	 * Method to calculate TF-IDF
	 * TFIDF is term frequency * inverse document frequency*/
	public void tFIDF() {
		for(String word: this.countingMap.keySet()) {
			if(word.contains("agil")) {
				int oo = 0;
			}
			double tfidf =0.00;

			tfidf = this.countingMap.get(word)* inverseDocFrequency(word);
			if(word.contains(" ")) {
				if(tfidf >= 10) {
					this.tfidfMap.put(word, tfidf);	 
					//Logic to get keywords per module
					String s = this.keyWordDocMap.get(word);
					String s1[]  = s.split(";");
					for(String h : s1) {
						if(docKeywordMap.containsKey(h)) {
							docKeywordMap.put(h, docKeywordMap.get(h)+word+";");
						}else {
							docKeywordMap.put(h,word+";");
						}		
					}
				}
			}else {
				if(tfidf >= 15) {
					//	System.out.println(word+"  === "+tfidf);
					this.tfidfMap.put(word, tfidf);	 
					//Logic to get keywords per module
					String s = this.keyWordDocMap.get(word);
					String s1[]  = s.split(";");
					for(String h : s1) {
						if(docKeywordMap.containsKey(h)) {
							docKeywordMap.put(h, docKeywordMap.get(h)+word+";");
						}else {
							docKeywordMap.put(h,word+";");
						}		
					}
				}	 
			}



		}
		//System.out.println("*******************************************");

	}
	/**
	 * Method containg algorithm to match the skills with keywords
	 * **/

	public void matchEscoData(int moduleNO){
		//ArrayList<String> allKeywords = this.finalKeywords.get(String.valueOf(moduleNO));
		String allKeywords = this.docKeywordMap.get(String.valueOf(moduleNO));
		String keywords[] = allKeywords.split(";");
		int count =0,maxCount=0;

		Map<ReaderCsvSkills,String> skillsMap = new HashMap<ReaderCsvSkills,String>();
		for(ReaderCsvSkills skillsCsv : skillData) {
			count =0;
			for(String key: keywords) {
				if(skillsCsv.getDescription().contains(key) ||
						skillsCsv.getPreferredLabel().contains(key) ||
						skillsCsv.getAltLabels().contains(key)) {
					count++;
				}
			}
			if(maxCount<count) {
				maxCount = count;
			}
			if(count>0)
				skillsMap.put(skillsCsv,String.valueOf(count));
		}

		List<ReaderCsvSkills> finalListSkills = new ArrayList<ReaderCsvSkills>();
		for(ReaderCsvSkills skillsCsv : skillsMap.keySet()) {
			int minus=0;
			if(maxCount>3) {
				minus=1;
			}
			if(Integer.valueOf(skillsMap.get(skillsCsv))>maxCount-minus) {
				finalListSkills.add(skillsCsv);
			}

		}
		//System.out.println(maxCount);
		this.finalSkills.put(String.valueOf(moduleNO), finalListSkills);
	}
	/**
	 * Method contains algorithm to match the occupation with keywords
	 * **/
	public void matchEscoDataOccupations(int moduleNO){
		//ArrayList<String> allKeywords = this.finalKeywords.get(String.valueOf(moduleNO));
		String allKeywords = this.docKeywordMap.get(String.valueOf(moduleNO));
		String keywords[] = allKeywords.split(";");
		int count =0,maxCount=0,sum=0;
		Map<ReaderCsvOccupations,String> occupationMap = new HashMap<ReaderCsvOccupations,String>();
		for(ReaderCsvOccupations occupationsCsv : occupationData) {
			count =0;

			for(String key: keywords) {
				if(occupationsCsv.getDescription().contains(key) ||
						occupationsCsv.getPreferredLabel().contains(key) ||
						occupationsCsv.getAltLabels().contains(key)) {
					count++;
				}
			}

			if(maxCount<count) {
				maxCount = count;
			}
			if(count>0) {
				sum=sum+count;
				occupationMap.put(occupationsCsv,String.valueOf(count));
			}


		}

		List<ReaderCsvOccupations> finalListOccupations = new ArrayList<ReaderCsvOccupations>();
		for(ReaderCsvOccupations occupationCsv : occupationMap.keySet()) {
			//	int minus=1;
			if(Float.valueOf(occupationMap.get(occupationCsv))>maxCount-1) {
				finalListOccupations.add(occupationCsv);
			}


		}
		//System.out.println(maxCount);
		this.finalOccupations.put(String.valueOf(moduleNO), finalListOccupations);
	}



	/**
	 * Method to lemmatize string
	 * **/
	public String lemmatize(String attribute) {
		Properties props = new Properties();
		props.setProperty("annotators", "tokenize,ssplit,pos,lemma");
		StanfordCoreNLP pipeline = new StanfordCoreNLP(props);
		String lemmaAttribute =  "" ;
		CoreDocument document11 = pipeline.processToCoreDocument(attribute);
		for (CoreLabel tok : document11.tokens()) {
			lemmaAttribute =tok.lemma();
		}
		return lemmaAttribute.toLowerCase();
	}

	/*public void printCsvData() {
		for(ReaderCsvSkills cvs: this.skillData) {
			System.out.println(cvs.getConceptType()+" : "+cvs.getPreferredLabel());
		}
		for(ReaderCsvOccupations cvs: this.occupationData) {
			System.out.println("Occupations: "+cvs.getConceptType()+" : "+cvs.getPreferredLabel());
		}
		System.out.println(this.skillData.size()+" occ: "+this.occupationData.size());
	}*/

	/**
	 * Methods generates n-gram depending upon int nGram
	 * i.e for nGram =2 it generates bi-gram*/
	public void generateNGram(List<String> stringResult,int nGram) {
		//System.out.println(">>>>>>>>>>In of ngram generator >>>>>>>");
		NGramGenerator nGramGenerator = new NGramGenerator();
		List<String> gram = new ArrayList<String>() ;
		int docIndex =0;
		for(String s : stringResult) {
			gram = nGramGenerator.generate(Arrays.asList(s.split(" ")), nGram, " ");	
			for(String keyGram: gram) {
				if(keyGram.length()>3 && keyGram.indexOf(" ")!=1 && keyGram.indexOf(" ")!=2 &&  
						keyGram.indexOf(" ") != keyGram.length()-2 && keyGram.indexOf(" ") != keyGram.length()-4) {


					if (this.countingMap.containsKey(keyGram)) {
						this.countingMap.put(keyGram,
								this.countingMap.get(keyGram) + 1);
					} else {
						this.countingMap.put(keyGram, 1.00);
					}

					if(this.nGramMap.containsKey(keyGram)) {
						String keys = this.nGramMap.get(keyGram);
						if(!keys.contains(String.valueOf(docIndex))) {
							this.nGramMap.put(keyGram,this.nGramMap.get(keyGram)+docIndex+";");						
						}

					}else {
						this.nGramMap.put(keyGram, docIndex+";");
					}

				}}

			docIndex++;
		}

		keyWordDocMap.putAll(nGramMap);
		//System.out.println("Out of ngram generator >>>>>>>");
	}
	public void sructinizeKeywords() {

		for(int i=1;i<=19;i++) {
			String ke = this.docKeywordMap.get(String.valueOf(i));
			String keyArr[] = ke.split(";");
			List<String> singleArr = new ArrayList<String>();
			List<String> doubleArr = new ArrayList<String>();
			List<String> tripleArr = new ArrayList<String>();
			ArrayList<String> scrunitizeKey = new ArrayList<String>();
			for(String find : keyArr) {
				if(!find.contains(" ")) {
					singleArr.add(find);
				}else {
					int val = find.indexOf(" ",find.indexOf(" ")+1); 
					if(val== -1) {
						doubleArr.add(find);
					}else {
						tripleArr.add(find);
					}
				}
			}
			scrunitizeKey.addAll(tripleArr);
			Boolean put = true;
			for(String h : doubleArr) {
				put = true;
				for(String h1:tripleArr) {
					if(h1.contains(h)) {
						put=false;
					}	
				}
				if(put) {
					scrunitizeKey.add(h);
					put = true;
				}
			}

			for(String h : singleArr) {
				put = true;
				for(String h1:doubleArr) {
					if(h1.contains(h)) {
						put=false;
					}	
				}
				if(put) {
					scrunitizeKey.add(h);
					put = true;
				}
			}

			finalKeywords.put(String.valueOf(i), scrunitizeKey);
			System.out.println("asdf");
		}
	}
}
