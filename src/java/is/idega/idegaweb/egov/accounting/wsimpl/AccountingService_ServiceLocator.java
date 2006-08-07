/**
 * AccountingService_ServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.3 Oct 05, 2005 (05:23:37 EDT) WSDL2Java emitter.
 */

package is.idega.idegaweb.egov.accounting.wsimpl;

public class AccountingService_ServiceLocator extends org.apache.axis.client.Service implements is.idega.idegaweb.egov.accounting.wsimpl.AccountingService_Service {

    public AccountingService_ServiceLocator() {
    }


    public AccountingService_ServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public AccountingService_ServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for AccountingServiceSOAP
    private java.lang.String AccountingServiceSOAP_address = "http://www.idega.com";

    public java.lang.String getAccountingServiceSOAPAddress() {
        return this.AccountingServiceSOAP_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String AccountingServiceSOAPWSDDServiceName = "AccountingServiceSOAP";

    public java.lang.String getAccountingServiceSOAPWSDDServiceName() {
        return this.AccountingServiceSOAPWSDDServiceName;
    }

    public void setAccountingServiceSOAPWSDDServiceName(java.lang.String name) {
    	this.AccountingServiceSOAPWSDDServiceName = name;
    }

    public is.idega.idegaweb.egov.accounting.wsimpl.AccountingService_PortType getAccountingServiceSOAP() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(this.AccountingServiceSOAP_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getAccountingServiceSOAP(endpoint);
    }

    public is.idega.idegaweb.egov.accounting.wsimpl.AccountingService_PortType getAccountingServiceSOAP(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            is.idega.idegaweb.egov.accounting.wsimpl.AccountingServiceSOAPStub _stub = new is.idega.idegaweb.egov.accounting.wsimpl.AccountingServiceSOAPStub(portAddress, this);
            _stub.setPortName(getAccountingServiceSOAPWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setAccountingServiceSOAPEndpointAddress(java.lang.String address) {
    	this.AccountingServiceSOAP_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (is.idega.idegaweb.egov.accounting.wsimpl.AccountingService_PortType.class.isAssignableFrom(serviceEndpointInterface)) {
                is.idega.idegaweb.egov.accounting.wsimpl.AccountingServiceSOAPStub _stub = new is.idega.idegaweb.egov.accounting.wsimpl.AccountingServiceSOAPStub(new java.net.URL(this.AccountingServiceSOAP_address), this);
                _stub.setPortName(getAccountingServiceSOAPWSDDServiceName());
                return _stub;
            }
        }
        catch (java.lang.Throwable t) {
            throw new javax.xml.rpc.ServiceException(t);
        }
        throw new javax.xml.rpc.ServiceException("There is no stub implementation for the interface:  " + (serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName()));
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(javax.xml.namespace.QName portName, Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        if (portName == null) {
            return getPort(serviceEndpointInterface);
        }
        java.lang.String inputPortName = portName.getLocalPart();
        if ("AccountingServiceSOAP".equals(inputPortName)) {
            return getAccountingServiceSOAP();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://www.idega.com/AccountingService/", "AccountingService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (this.ports == null) {
        	this.ports = new java.util.HashSet();
        	this.ports.add(new javax.xml.namespace.QName("http://www.idega.com/AccountingService/", "AccountingServiceSOAP"));
        }
        return this.ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("AccountingServiceSOAP".equals(portName)) {
            setAccountingServiceSOAPEndpointAddress(address);
        }
        else 
{ // Unknown Port Name
            throw new javax.xml.rpc.ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
        }
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(javax.xml.namespace.QName portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        setEndpointAddress(portName.getLocalPart(), address);
    }

}
