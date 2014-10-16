/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.versatel.noc.vsafe.common.services;

/**
 *
 * @author ulrich.stemmer
 */
public abstract class ServiceExecutor implements ServiceExecutorInterface{

    public final Service service;

    public ServiceExecutor(Service service){
        this.service = service;
    }

}
