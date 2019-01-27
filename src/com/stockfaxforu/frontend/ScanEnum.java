package com.stockfaxforu.frontend;

public enum ScanEnum
{
	 
		 
		 	TWENTY_PER_UP("TWENTYUP", "Twenty Per UP"),
		 	TEN_TWENTY_PER_UP("TENTWENTYUP", "Ten to Twenty Per Up"),
		 	FIVE_TEN_PER_UP("FIVETENUP", "Five To Ten Per UP"),
		 	FIVE_PER_UP("FIVEPERUP", "Five Per UP"),
		 	TWENTY_PER_DOWN("TWENTYDOWN", "Twenty Per Down"),
		 	TEN_TWENTY_PER_DOWN("TENTWENTYDOWN", "Ten to Twenty Per Down"),
		 	FIVE_TEN_PER_DOWN("FIVETENDOWN", "Five To Ten Per Down"),
		 	FIVE_PER_DOWN("FIVEPERDOWN", "Five Per Down");
		 	
		
		 	private String code;
	/*    */   private String description;
	/*    */ 
	/*    */   public String getCode()
	/*    */   {
	/* 55 */     return this.code;
	/*    */   }
	/*    */ 
	/*    */   public String getDescription()
	/*    */   {
	/* 68 */     return this.description;
	/*    */   }
	/*    */ 
	/*    */   public String toString()
	/*    */   {
	/* 81 */     return "Reason code : " + this.code + " Reason description : " + this.description;
	/*    */   }
	/*    */ 
	/*    */   private ScanEnum(String reasonCode, String reasonDescription)
	/*    */   {
	/* 94 */     this.code = reasonCode;
	/* 95 */     this.description = reasonDescription;
	/*    */   }
	/*    */ 

}
