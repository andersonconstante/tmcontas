package com.tm.exception;


public class TaxaException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public TaxaException(String mensagem) {
		super(mensagem);
	}
	
    public TaxaException(Throwable t) {
        super(t);
    }

}
