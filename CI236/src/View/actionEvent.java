package View;

import java.util.EventObject;
/*work done by Damian Grabarczyk,Luke Willmer, Ian Smith and Evangelos Papaefthymiou*/
public class actionEvent extends EventObject {


	private static final long serialVersionUID = 1L;
	private  boolean clicked;
	private boolean newEvent;
	private boolean save;
	private boolean cancel;
	private boolean edit;
	private boolean search;
	private boolean addFr;
	private boolean reFr;
	private boolean mouse;
	
	public actionEvent(Object source, boolean clicked, String s){
		super(source);
		
		switch ( s ) {
		case "login" : this.clicked = clicked;
						break;
		case "logout": this.clicked = clicked;
						break;
		case "new"   : this.newEvent = clicked;
						break;
		case "save"  : this.save = clicked;
						break;
		case "cancel": this.cancel = clicked;
						break;
		case "edit"  : this.edit = clicked;
						break;
		case "search": this.search = clicked;
						break;
		case "addFr" : this.addFr = clicked;
						break;
		case "reFr"  : this.reFr = clicked;
						break;
		case "mouse"  : this.mouse = clicked;
		break;
		default      : System.out.println("Error - S case");
		}
		/*
		if(s == "logout" ||s == "login" ){
		this.clicked = clicked;
		}else if(s == "new"){
			this.newEvent = clicked;
		}else if (){
			else{
		}
			System.out.println("Error");
		}*/
	}
	public boolean getClicked() {
		
		return clicked;
		
	}
	public boolean getNewEventClicked() {
		return newEvent;
	}
	public boolean getSaveClicked() {
		return save;
	}
	public boolean getCancelClicked() {
		return cancel;
	}
	public boolean getEditClicked() {
		return edit;
	}
	public boolean getSearchClicked() {
		return search;
	}
	public boolean getAddFrClicked() {
		return addFr;
	}
	public boolean getReFrClicked() {
		return reFr;
	}
	public boolean getMousePressed() {
		return mouse;
	}
}
