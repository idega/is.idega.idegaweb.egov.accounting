package is.idega.idegaweb.egov.accounting.procedure;

import is.idega.idegaweb.egov.accounting.business.AccountingEntry;

import java.util.Date;

public class AccountingEntryImpl implements AccountingEntry {

	private int numberOfDaysPrWeek;

	@Override
	public String getName() {
		return null;
	}

	@Override
	public void setName(String name) {
	}

	public AccountingEntryImpl() {
	}

	@Override
	public String getPersonalId() {
		return null;
	}

	@Override
	public void setPersonalId(String string) {
	}

	@Override
	public String getPayerPersonalId() {
		return null;
	}

	@Override
	public void setPayerPersonalId(String string) {
	}

	@Override
	public String getProviderCode() {
		return null;
	}

	@Override
	public void setProviderCode(String string) {
	}

	@Override
	public String getProductCode() {
		return null;
	}

	@Override
	public void setProductCode(String string) {
	}

	@Override
	public String getProjectCode() {
		return null;
	}

	@Override
	public void setProjectCode(String string) {
	}

	@Override
	public int getAmount() {
		return 0;
	}

	@Override
	public void setAmount(int i) {
	}

	@Override
	public Date getStartDate() {
		return null;
	}

	@Override
	public void setStartDate(Date date) {
	}

	@Override
	public Date getEndDate() {
		return null;
	}

	@Override
	public void setEndDate(Date date) {
	}

	@Override
	public String getPaymentMethod() {
		return null;
	}

	@Override
	public void setPaymentMethod(String string) {
	}

	@Override
	public float getUnitPrice() {
		return 0.0f;
	}

	@Override
	public void setUnitPrice(float f) {
	}

	@Override
	public float getUnits() {
		return 0.0f;
	}

	@Override
	public void setUnits(float f) {
	}

	@Override
	public String getCardNumber() {
		return null;
	}

	@Override
	public void setCardNumber(String string) {
	}

	@Override
	public String getCardType() {
		return null;
	}

	@Override
	public void setCardType(String string) {
	}

	@Override
	public int getCardExpirationMonth() {
		return 0;
	}

	@Override
	public void setCardExpirationMonth(int i) {
	}

	@Override
	public int getCardExpirationYear() {
		return 0;
	}

	@Override
	public void setCardExpirationYear(int i) {
	}

	@Override
	public Object getExtraInformation() {
		return null;
	}

	@Override
	public void setExtraInformation(Object extraInformation) {
	}

	@Override
	public int getNumberOfDaysPrWeek() {
		return 	numberOfDaysPrWeek;
	}

	@Override
	public void setNumberOfDaysPrWeek(int numberOfDays) {
		this.numberOfDaysPrWeek = numberOfDays;
	}

	@Override
	public String getFamilyNumber() {
		return null;
	}

	@Override
	public void setFamilyNumber(String familyNumber) {

	}

	@Override
	public int getSiblingNumber() {
		return 0;
	}

	@Override
	public void setSiblingNumber(int siblingNumber) {

	}

	@Override
	public String toString() {
		return getClass().getName() + ": student: " + getPersonalId() + ", payer: " + getPayerPersonalId();
	}

}