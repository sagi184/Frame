package pOMCOR;

public class PomcOR {
	public static final String dropdown_PoOrderStatus_xpath="(//select[@class='col-md-5 col-sm-5 col-xs-12'])[1]";
	public static final String dropdown_PoOrderType_xpath="(//select[@class='col-md-5 col-sm-5 col-xs-12'])[2]";
	public static final String dropdown_Buyer_xpath="(//select[@class='col-md-5 col-sm-5 col-xs-12'])[3]";
	public static final String dropdown_Supplier_xpath="(//select[@class='col-md-5 col-sm-5 col-xs-12'])[4]";
	
	public static final String button_search_xpath="(//button[@type='button'])[1]";
	public static final String button_clear_xpath="(//button[@type='button'])[2]";
	
	public static final String grid_Buyer_xpath ="(//mat-cell[@class='mat-cell cdk-column-buyer mat-column-buyer ng-star-inserted'])[1]";
	
	public static final String button_back_xpath ="//button[contains(text(),'Back')]";
	
	public static final String txtbox_POnumber_id = "poNumber";
	
	public static final String linkt_POnumber_xpath = "//mat-cell[@class='mat-cell cdk-column-po mat-column-po ng-star-inserted']";
	
	//public static final String txtbox_Date_id = "mat-input-5";
	
	public static final String txtbox_conDelDate_xpath="(//input[@class='mat-input-element mat-form-field-autofill-control'])[5]";
	
	public static final String button_undo_xpath ="//button[contains(text(),'Undo')]";
	
	public static final String button_save_xpath = "//button[contains(text(),'Save')]";
	
	public static final String txtbox_Pretxt_xpath ="//label[contains(text(),'Add PreText:')]//following::div[3]/textarea";
	
	public static final String button_SavePreText_xpath ="//button[contains(text(),'Save PreText')]";
}
