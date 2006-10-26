/**
 * WiseWebServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.3 Oct 05, 2005 (05:23:37 EDT) WSDL2Java emitter.
 */

package is.idega.idegaweb.egov.accounting.wsimpl;

public class WiseWebServiceLocator extends org.apache.axis.client.Service implements is.idega.idegaweb.egov.accounting.wsimpl.WiseWebService {

    public WiseWebServiceLocator() {
    }


    public WiseWebServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public WiseWebServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for WiseWebServiceSoap
    private java.lang.String WiseWebServiceSoap_address = "http://postur.arborg.is:81/ApprovalsWS/Wisews.asmx";

    public java.lang.String getWiseWebServiceSoapAddress() {
        return WiseWebServiceSoap_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String WiseWebServiceSoapWSDDServiceName = "WiseWebServiceSoap";

    public java.lang.String getWiseWebServiceSoapWSDDServiceName() {
        return WiseWebServiceSoapWSDDServiceName;
    }

    public void setWiseWebServiceSoapWSDDServiceName(java.lang.String name) {
        WiseWebServiceSoapWSDDServiceName = name;
    }

    public is.idega.idegaweb.egov.accounting.wsimpl.WiseWebServiceSoap_PortType getWiseWebServiceSoap() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(WiseWebServiceSoap_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getWiseWebServiceSoap(endpoint);
    }

    public is.idega.idegaweb.egov.accounting.wsimpl.WiseWebServiceSoap_PortType getWiseWebServiceSoap(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            is.idega.idegaweb.egov.accounting.wsimpl.WiseWebServiceSoap_BindingStub _stub = new is.idega.idegaweb.egov.accounting.wsimpl.WiseWebServiceSoap_BindingStub(portAddress, this);
            _stub.setPortName(getWiseWebServiceSoapWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setWiseWebServiceSoapEndpointAddress(java.lang.String address) {
        WiseWebServiceSoap_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (is.idega.idegaweb.egov.accounting.wsimpl.WiseWebServiceSoap_PortType.class.isAssignableFrom(serviceEndpointInterface)) {
                is.idega.idegaweb.egov.accounting.wsimpl.WiseWebServiceSoap_BindingStub _stub = new is.idega.idegaweb.egov.accounting.wsimpl.WiseWebServiceSoap_BindingStub(new java.net.URL(WiseWebServiceSoap_address), this);
                _stub.setPortName(getWiseWebServiceSoapWSDDServiceName());
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
        if ("WiseWebServiceSoap".equals(inputPortName)) {
            return getWiseWebServiceSoap();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://tempuri.org/", "WiseWebService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://tempuri.org/", "WiseWebServiceSoap"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("WiseWebServiceSoap".equals(portName)) {
            setWiseWebServiceSoapEndpointAddress(address);
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
