package za.co.discovery.assignment.billBraun;

import java.io.File;

import javax.inject.Inject;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import lombok.extern.slf4j.Slf4j;
import za.co.discovery.assignment.billBraun.loader.boundry.LoaderService;

@SpringBootApplication
@Slf4j
public class BillBraunApplication implements ApplicationRunner {
	
	public static final String SAMPLE_XLSX_FILE_PATH = "./HR-Offsite AssignmentV3 0.xlsx";
	
	@Inject
	LoaderService loaderService;
	
	public static void main(String[] args) {
		log.info("Starting application");
		log.trace("A TRACE Message");
		log.debug("A DEBUG Message");
		log.info("An INFO Message");
		log.warn("A WARN Message");
		log.error("An ERROR Message");
		SpringApplication.run(BillBraunApplication.class, args);
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		log.info("Starting import");
		loaderService.loadFromFile(new File(SAMPLE_XLSX_FILE_PATH));
	}
	

}
