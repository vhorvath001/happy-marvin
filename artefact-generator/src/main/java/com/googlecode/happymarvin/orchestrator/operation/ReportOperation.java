package com.googlecode.happymarvin.orchestrator.operation;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.googlecode.happymarvin.artefactgenerator.ArtefactGenerator;
import com.googlecode.happymarvin.artefactgenerator.writer.VirtualWriter;
import com.googlecode.happymarvin.common.beans.InstructionBean;
import com.googlecode.happymarvin.common.beans.JiraIssueBean;
import com.googlecode.happymarvin.common.exceptions.ConfigurationException;
import com.googlecode.happymarvin.common.exceptions.InvalidInstructionException;
import com.googlecode.happymarvin.common.utils.ConfigurationReaderUtil;
import com.googlecode.happymarvin.common.utils.Constants;
import com.googlecode.happymarvin.common.utils.StringUtility;
import com.googlecode.happymarvin.jiraexplorer.RestClient;
import com.googlecode.happymarvin.jiraminer.SurfaceMining;
import com.googlecode.happymarvin.jiraminer.UndergroundMining;

import freemarker.template.TemplateException;

public class ReportOperation implements IOperation {

	
	private static final Logger LOGGER = LoggerFactory.getLogger(GenerateOperation.class);
	
	private RestClient restClient;
	private SurfaceMining surfaceMining;
	private UndergroundMining undergroundMining;
	private ConfigurationReaderUtil configurationReaderUtil;
	private ArtefactGenerator artefactGenerator;

	
	public ReportOperation(RestClient restClient,
                           ConfigurationReaderUtil configurationReaderUtil,
                           SurfaceMining surfaceMining, 
                           UndergroundMining undergroundMining,
                           ArtefactGenerator artefactGenerator) {
		this.restClient = restClient;
		this.configurationReaderUtil = configurationReaderUtil;
		this.surfaceMining = surfaceMining;
		this.undergroundMining = undergroundMining;
		this.artefactGenerator = artefactGenerator;
	}
	
	public void perform(String... args) throws ConfigurationException, IOException, InvalidInstructionException, TemplateException {
		String reportPath = getReportPath(args);
		
		// calling the JIRA REST service
		LOGGER.info("----- 1/5 Calling the JIRA REST service -----");
		LinkedHashMap<String, Object> responseFromREST = restClient.getJiraIssueAsJson(
				configurationReaderUtil.getJiraRestUrlWithTrailingPer(), args[0]);
		
		// mining the JIRA ticket received from REST
		LOGGER.info("----- 2/5 Starting the surface mining -----");
		JiraIssueBean jiraIssueBean = surfaceMining.mine(responseFromREST);
		LOGGER.info("----- 3/5 Starting the underground mining -----");
		jiraIssueBean = undergroundMining.mine(jiraIssueBean);

		// getting the artefact information without generating them
		LOGGER.info("----- 4/5 Getting the artefact information without generating them -----");
		artefactGenerator.generate(jiraIssueBean, false);

		// generating the report
		LOGGER.info(String.format("----- 5/5 Generating the report to %s -----", reportPath));
		generateReport(jiraIssueBean, artefactGenerator.getVirtualWriterManager().getVirtualWriters(), reportPath);
	}

	
	private void generateReport(JiraIssueBean jiraIssueBean, List<VirtualWriter> virtualWriters, String reportPath) throws IOException {
		File reportFile = new File(reportPath);
		BufferedWriter writer = null;
		try {
			writer = new BufferedWriter(new FileWriter(reportFile));
	
			// writing the jiraIssueBean data
			writer.write(String.format("JIRA description:\n%s\n", jiraIssueBean.getDescription()));
			int ind = 0;
			for(InstructionBean instructionBean : jiraIssueBean.getInstructions()) {
				writer.write("\n\n");
				writer.write(String.format("**************************** The %s instruction ****************************", StringUtility.ordinal(ind)));
				writer.write(String.format("\n\tType:      %s", instructionBean.getType()));
				writer.write(String.format("\n\tTemplate:  %s", instructionBean.getTemplate()));
				writer.write(String.format("\n\tName:      %s", instructionBean.getName()));
				writer.write(String.format("\n\tProject:   %s", instructionBean.getProject()));
				writer.write(String.format("\n\tLocation:  %s", instructionBean.getLocation()));
				if (instructionBean.getProperties().size() > 0) {
					// TODO displaying if mandatory
					writer.write(String.format("\n\tProperties:", instructionBean.getProperties()));
				}
				for(String propKey : instructionBean.getProperties().keySet()) {
					writer.write(String.format("\n\t\t%s: %s", propKey, instructionBean.getProperties().get(propKey)));
				}
				ind++;
			}
			
			// writing the artefacts to be created
			writer.write("\nThe artefacts to be created:");
			for(VirtualWriter virtualWriter : virtualWriters) {
				String exist = new Boolean(new File(virtualWriter.getArtefactName()).exists()).toString();
				writer.write(String.format("\n\tType: %s Exist: %s,\t Path: %s", StringUtility.padRight(virtualWriter.getArtefactType()+",",10), 
						exist, virtualWriter.getArtefactName() ));
			}
		} finally {
			if (writer != null) {
				writer.close();
			}
		}
	}
	

	private String getReportPath(String... args) {
		if (args.length > 2) {
			return args[2];
		} else {
			DateFormat dateFormat = new SimpleDateFormat(Constants.REPORT_FILE_NAME_DATEFORMAT);
			return Constants.REPORT_DEFAULT_FILE_PREFIX + dateFormat.format(new Date()) + ".txt";
		}
	}

}
