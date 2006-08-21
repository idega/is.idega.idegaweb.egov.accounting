package is.idega.idegaweb.egov.accounting.procedure;



public class AccountingProcedure {
    public AccountingProcedure() {
    }
    
    public static String tester(){
        return "tester";
    }
    
    public static String[] tester2(){
        return new String[]{"tester1","tester2"};
    }
    
    /*public static void getBillingEntries(OracleBillingEntry entry){
        
        OracleBillingEntry[] array = new OracleBillingEntry[2];
        
        OracleBillingEntry info = new OracleBillingEntry();
        try {
            info.setCardExpirationMonth("07");
    
            info.setStartDate((Timestamp)IWTimestamp.getTimestampRightNow());
            info.setEndDate((Timestamp)IWTimestamp.getTimestampRightNow());   
            info.setCardExpirationYear("2007");
            info.setCardNumber("1234-1234-1234-1234");
            info.setPaymentType("visa");
        } catch (SQLException e) {
            // TODO
        }
        array[0]=info;
        array[1]=info;
        //return array;
       //return info;
    }*/
    
}
