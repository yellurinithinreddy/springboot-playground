package com.nithin.EmpRules.EmployeeHandBook;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.document.Document;
import org.springframework.ai.reader.pdf.PagePdfDocumentReader;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.io.Resource;

import java.util.List;

@SpringBootApplication
@RequiredArgsConstructor
public class EmployeeHandBookApplication implements CommandLineRunner {

	@Value("classpath:policy.pdf")
	Resource pdfFile;

	private final VectorStore vectorStore;


	public static void main(String[] args) {
		SpringApplication.run(EmployeeHandBookApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		PagePdfDocumentReader pagePdfDocumentReader = new PagePdfDocumentReader(pdfFile);
		TokenTextSplitter splitter = TokenTextSplitter.builder()
				.withChunkSize(200)
				.build();

		List<Document> docs = splitter.apply(pagePdfDocumentReader.get());
		vectorStore.add(docs);
	}
}
