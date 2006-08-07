/**
 * AccountingService_PortType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.3 Oct 05, 2005 (05:23:37 EDT) WSDL2Java emitter.
 */

package is.idega.idegaweb.egov.accounting.wsimpl;

import is.idega.idegaweb.egov.accounting.business.AccountingEntry;


public interface AccountingService_PortType extends java.rmi.Remote {
    public AccountingEntry[] getBillingEntries(is.idega.idegaweb.egov.accounting.wsimpl.BillingEntriesRequest getBillingEntriesRequest) throws java.rmi.RemoteException;
}
