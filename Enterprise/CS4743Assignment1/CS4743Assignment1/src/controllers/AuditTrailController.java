package controllers;
import org.apache.logging.log4j.LogManager;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import view.ViewType;

import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.Logger;
import application.*;

public class AuditTrailController {
	
	private static final Logger logger = LogManager.getLogger();
	private AuditTrailEntry audit;
	private Books book;

	@FXML
	private ListView<AuditTrailEntry> auditList;
	private List<AuditTrailEntry> audits;
	@FXML private TextField title;
	
	public AuditTrailController(List<AuditTrailEntry> bookAudit) {
		this.audits = bookAudit;
	}
	
	public void initialize() {
		logger.info("Begining of init Audit List");
		ObservableList<AuditTrailEntry> items = auditList.getItems();
		
		items.addAll(audits);
	}
	
	
	
}