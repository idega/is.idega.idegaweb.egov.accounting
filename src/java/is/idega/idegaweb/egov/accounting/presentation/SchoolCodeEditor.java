package is.idega.idegaweb.egov.accounting.presentation;

import is.idega.idegaweb.egov.accounting.business.AccountingKeyBusiness;
import is.idega.idegaweb.egov.accounting.data.SchoolCode;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import javax.ejb.CreateException;

import com.idega.block.school.business.SchoolBusiness;
import com.idega.block.school.data.School;
import com.idega.block.school.data.SchoolType;
import com.idega.business.IBOLookup;
import com.idega.business.IBOLookupException;
import com.idega.business.IBORuntimeException;
import com.idega.data.IDORelationshipException;
import com.idega.presentation.IWContext;
import com.idega.presentation.Layer;
import com.idega.presentation.Span;
import com.idega.presentation.Table2;
import com.idega.presentation.TableCell2;
import com.idega.presentation.TableColumn;
import com.idega.presentation.TableColumnGroup;
import com.idega.presentation.TableRow;
import com.idega.presentation.TableRowGroup;
import com.idega.presentation.text.Link;
import com.idega.presentation.text.Text;
import com.idega.presentation.ui.Form;
import com.idega.presentation.ui.HiddenInput;
import com.idega.presentation.ui.Label;
import com.idega.presentation.ui.SubmitButton;
import com.idega.presentation.ui.TextInput;

public class SchoolCodeEditor extends AccountingBlock {

	private static final String PARAMETER_ACTION = "prm_action";

	private static final String PARAMETER_SCHOOL_ID = "prm_school_id";
	private static final String PARAMETER_TYPE = "prm_type";
	private static final String PARAMETER_ACCOUNTING_KEY = "prm_accounting_key";

	private static final int ACTION_VIEW = 1;
	private static final int ACTION_EDIT = 2;
	private static final int ACTION_SAVE = 3;

	private String iSchoolCategory;

	public void present(IWContext iwc) {
		if (iSchoolCategory == null) {
			add("No school category defined...");
			return;
		}

		try {
			switch (parseAction(iwc)) {
				case ACTION_VIEW:
					showList(iwc);
					break;

				case ACTION_EDIT:
					Object schoolPK = iwc.getParameter(PARAMETER_SCHOOL_ID);
					showEditor(iwc, schoolPK);
					break;

				case ACTION_SAVE:
					save(iwc);
					showList(iwc);
					break;
			}
		}
		catch (RemoteException re) {
			throw new IBORuntimeException(re);
		}
	}

	private int parseAction(IWContext iwc) {
		if (iwc.isParameterSet(PARAMETER_ACTION)) {
			return Integer.parseInt(iwc.getParameter(PARAMETER_ACTION));
		}
		return ACTION_VIEW;
	}

	private void save(IWContext iwc) {
		String schoolPK = iwc.getParameter(PARAMETER_SCHOOL_ID);
		String[] types = iwc.getParameterValues(PARAMETER_TYPE);
		String[] accountingKeys = iwc.getParameterValues(PARAMETER_ACCOUNTING_KEY);

		if (types != null && accountingKeys != null) {
			for (int i = 0; i < types.length; i++) {
				try {
					getAccountingKeyBusiness(iwc).storeSchoolCode(schoolPK, types[i], accountingKeys[i]);
				}
				catch (CreateException ce) {
					add(ce.getMessage());
				}
				catch (RemoteException re) {
					throw new IBORuntimeException(re);
				}
			}
		}
	}

	public void showList(IWContext iwc) {
		Form form = new Form();
		form.setStyleClass("adminForm");

		Table2 table = new Table2();
		table.setCellpadding(0);
		table.setCellspacing(0);
		table.setWidth("100%");
		table.setStyleClass("adminTable");
		table.setStyleClass("ruler");

		TableColumnGroup columnGroup = table.createColumnGroup();
		TableColumn column = columnGroup.createColumn();
		column.setSpan(5);
		column = columnGroup.createColumn();
		column.setSpan(2);
		column.setWidth("12");

		Collection schools = null;
		Collection schoolTypes = null;
		try {
			schools = getSchoolBusiness(iwc).findAllSchoolsByCategory(getSchoolCategory());
			schoolTypes = getSchoolBusiness(iwc).findAllSchoolTypesInCategory(getSchoolCategory());
		}
		catch (RemoteException rex) {
			schools = new ArrayList();
		}

		TableRowGroup group = table.createHeaderRowGroup();
		TableRow row = group.createRow();
		TableCell2 cell = row.createHeaderCell();
		cell.setStyleClass("firstColumn");
		cell.setStyleClass("school");
		cell.add(new Text(this.iwrb.getLocalizedString("school", "School")));

		Iterator iterator = schoolTypes.iterator();
		while (iterator.hasNext()) {
			SchoolType type = (SchoolType) iterator.next();

			cell = row.createHeaderCell();
			cell.setStyleClass(type.getPrimaryKey().toString());
			cell.add(new Text(type.getSchoolTypeName()));
		}

		cell = row.createHeaderCell();
		cell.setStyleClass("lastColumn");
		cell.setStyleClass("edit");
		cell.add(new Text(this.iwrb.getLocalizedString("edit", "Edit")));

		group = table.createBodyRowGroup();
		int iRow = 1;
		java.util.Iterator iter = schools.iterator();
		while (iter.hasNext()) {
			School school = (School) iter.next();
			row = group.createRow();

			try {
				Link edit = new Link(iwb.getImage("edit.png", iwrb.getLocalizedString("edit", "Edit")));
				edit.addParameter(PARAMETER_SCHOOL_ID, school.getPrimaryKey().toString());
				edit.addParameter(PARAMETER_ACTION, ACTION_EDIT);

				cell = row.createCell();
				cell.setStyleClass("firstColumn");
				cell.setStyleClass("school");
				cell.add(new Text(school.getName()));

				iterator = schoolTypes.iterator();
				while (iterator.hasNext()) {
					SchoolType type = (SchoolType) iterator.next();
					SchoolCode code = getAccountingKeyBusiness(iwc).getSchoolCode(school, type);

					cell = row.createCell();
					cell.setStyleClass(type.getPrimaryKey().toString());
					if (code != null) {
						cell.add(new Text(code.getSchoolCode()));
					}
					else {
						cell.add(Text.getNonBrakingSpace());
					}
				}

				cell = row.createCell();
				cell.setStyleClass("lastColumn");
				cell.setStyleClass("edit");
				cell.add(edit);

				if (iRow % 2 == 0) {
					row.setStyleClass("even");
				}
				else {
					row.setStyleClass("odd");
				}
			}
			catch (Exception ex) {
				ex.printStackTrace();
			}
			iRow++;
		}
		form.add(table);

		add(form);
	}

	public void showEditor(IWContext iwc, Object courseDiscountPK) throws java.rmi.RemoteException {
		Form form = new Form();
		form.setStyleClass("adminForm");
		form.add(new HiddenInput(PARAMETER_ACTION, String.valueOf(ACTION_VIEW)));
		form.maintainParameter(PARAMETER_SCHOOL_ID);

		Layer section = new Layer(Layer.DIV);
		section.setStyleClass("formSection");
		form.add(section);

		Layer helpLayer = new Layer(Layer.DIV);
		helpLayer.setStyleClass("helperText");
		helpLayer.add(new Text(iwrb.getLocalizedString("school_code_edit.help_text", "Please fill in all the information and then click 'Save'.")));
		section.add(helpLayer);

		School school = getSchoolBusiness(iwc).getSchool(new Integer(iwc.getParameter(PARAMETER_SCHOOL_ID)));
		Collection types = null;
		try {
			types = school.getSchoolTypes();
		}
		catch (IDORelationshipException e) {
			e.printStackTrace();
			types = new ArrayList();
		}

		Layer layer;
		Label label;

		layer = new Layer(Layer.DIV);
		layer.setID("name");
		layer.setStyleClass("formItem");
		label = new Label();
		label.setLabel(iwrb.getLocalizedString("school", "School"));
		Span span = new Span();
		span.add(new Text(school.getName()));
		layer.add(label);
		layer.add(span);
		section.add(layer);

		Iterator iterator = types.iterator();
		while (iterator.hasNext()) {
			SchoolType type = (SchoolType) iterator.next();
			SchoolCode code = getAccountingKeyBusiness(iwc).getSchoolCode(school, type);

			TextInput input = new TextInput(PARAMETER_ACCOUNTING_KEY);
			if (code != null) {
				input.setContent(code.getSchoolCode());
			}

			HiddenInput hidden = new HiddenInput(PARAMETER_TYPE, type.getPrimaryKey().toString());

			layer = new Layer(Layer.DIV);
			layer.setStyleClass("formItem");
			label = new Label(type.getSchoolTypeName(), input);
			layer.add(label);
			layer.add(input);
			layer.add(hidden);
			section.add(layer);
		}

		Layer clearLayer = new Layer(Layer.DIV);
		clearLayer.setStyleClass("Clear");

		section.add(clearLayer);

		Layer buttonLayer = new Layer(Layer.DIV);
		buttonLayer.setStyleClass("buttonLayer");
		form.add(buttonLayer);

		SubmitButton save = new SubmitButton(iwrb.getLocalizedString("save", "Save"));
		save.setValueOnClick(PARAMETER_ACTION, String.valueOf(ACTION_SAVE));

		SubmitButton cancel = new SubmitButton(iwrb.getLocalizedString("cancel", "Cancel"));
		cancel.setValueOnClick(PARAMETER_ACTION, String.valueOf(ACTION_VIEW));

		buttonLayer.add(cancel);
		buttonLayer.add(save);

		add(form);
	}

	public AccountingKeyBusiness getAccountingKeyBusiness(IWContext iwc) {
		try {
			return (AccountingKeyBusiness) IBOLookup.getServiceInstance(iwc, AccountingKeyBusiness.class);
		}
		catch (IBOLookupException e) {
			throw new IBORuntimeException(e);
		}
	}

	public SchoolBusiness getSchoolBusiness(IWContext iwc) {
		try {
			return (SchoolBusiness) IBOLookup.getServiceInstance(iwc, SchoolBusiness.class);
		}
		catch (IBOLookupException e) {
			throw new IBORuntimeException(e);
		}
	}

	public String getSchoolCategory() {
		return this.iSchoolCategory;
	}

	public void setSchoolCategory(String schoolCategory) {
		this.iSchoolCategory = schoolCategory;
	}
}