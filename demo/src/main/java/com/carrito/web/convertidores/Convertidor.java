package com.carrito.web.convertidores;

import com.carrito.web.excepciones.WebException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public abstract class Convertidor <M extends Object, E extends Object>{
    public abstract E modeloToEntidad(M m) throws WebException;

	public abstract M entidadToModelo(E e) throws WebException;

	protected Log log;

	public Convertidor() {
		this.log = LogFactory.getLog(getClass());
	}
}
