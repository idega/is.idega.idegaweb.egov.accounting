/**
 * WiseWebService.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.3 Oct 05, 2005 (05:23:37 EDT) WSDL2Java emitter.
 */

package is.idega.idegaweb.egov.accounting.wsimpl;

public interface WiseWebService extends javax.xml.rpc.Service {
    public java.lang.String getWiseWebServiceSoapAddress();

    public is.idega.idegaweb.egov.accounting.wsimpl.WiseWebServiceSoap_PortType getWiseWebServiceSoap() throws javax.xml.rpc.ServiceException;

    public is.idega.idegaweb.egov.accounting.wsimpl.WiseWebServiceSoap_PortType getWiseWebServiceSoap(java.net.URL portAddress) throws javax.xml.rpc.ServiceException;
}
