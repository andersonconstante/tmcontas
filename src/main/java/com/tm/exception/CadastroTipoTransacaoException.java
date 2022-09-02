package com.tm.exception;


public class CadastroTipoTransacaoException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public CadastroTipoTransacaoException(String mensagem) {
		super(mensagem);
	}
	
    public CadastroTipoTransacaoException(Throwable t) {
        super(t);
    }

}
