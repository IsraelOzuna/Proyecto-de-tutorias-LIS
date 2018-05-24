/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package negocio;

import java.util.List;
import persistencia.Promocion;

/**
 *
 * @author Renato
 */
public interface IPromocion {
    public boolean registrarPromocion(persistencia.Promocion nuevaPromocion);
    public List<persistencia.Promocion> consultarPromociones ();
    public Promocion adquirirPromocionPorNombre(String nombrePromocion);
}
